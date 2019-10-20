package net.rgielen.fxweaver.core;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * FxWeaver is the core weaving facility, enabling Controllers and Views to be instantiated by
 * a dependency injection framework such as (but not limited to) Spring.
 * <p/>
 * The following example requires a Spring ConfigurableApplicationContext to be instantiated.
 * If now MainController is declared as Spring managed bean, it will get created and injected
 * by Spring.
 *
 * If a managed Controller class contains an {@link FxmlView} annotation, attached FXML views are
 * injected as well.
 *
 * <pre>
 *     ConfigurableApplicationContext applicationContext = ...
 *     FxWeaver fxWeaver = new FxWeaver(applicationContext::getBean, applicationContext::close);
 *     Scene scene = new Scene(fxWeaver.loadView(MainController.class), 400, 300);
 * </pre>
 *
 * @see FxmlView
 * @author Rene Gielen
 */
public class FxWeaver {

    private static final Logger LOG = LoggerFactory.getLogger(FxWeaver.class);

    private final Callback<Class<?>, Object> beanFactory;
    private final Runnable closeCommand;

    /**
     * Create a FxWeaver instance.
     * <p/>
     * Example:
     * <pre>
     *     ConfigurableApplicationContext applicationContext = ...
     *     FxWeaver fxWeaver = new FxWeaver(applicationContext::getBean, applicationContext::close);
     * </pre>
     * @param beanFactory The beanFactory callback to be called for requesting a bean of given class
     *                    when e.g. {@link #loadView(Class)} is called.
     * @param closeCommand The function to close a bean factory attached to FxWeaver
     *
     * @see #loadView(Class)
     * @see #loadView(Class, ResourceBundle)
     */
    public FxWeaver(Callback<Class<?>, Object> beanFactory, Runnable closeCommand) {
        this.beanFactory = beanFactory;
        this.closeCommand = closeCommand;
    }

    public <V extends Node, C> V loadView(Class<C> controllerClass, String url) {
        return loadView(controllerClass, url, null);
    }

    public <V extends Node, C> V loadView(Class<C> controllerClass) {
        return loadView(controllerClass, (ResourceBundle) null);
    }

    public <V extends Node, C> V loadView(Class<C> controllerClass, ResourceBundle resourceBundle) {
        return loadView(controllerClass, buildFxmlReference(controllerClass), resourceBundle);
    }

    @SuppressWarnings("unchecked")
    public <V extends Node, C> V loadView(Class<C> controllerClass, String url, ResourceBundle resourceBundle) {
        List<V> viewContainer = new ArrayList<>(1);
        load(controllerClass, url, resourceBundle, v -> viewContainer.add((V) v));
        return viewContainer.get(0);
    }

    public <C> C loadController(Class<C> controllerClass, String url) {
        return loadController(controllerClass, url, null);
    }

    public <C> C loadController(Class<C> controllerClass, String url, ResourceBundle resourceBundle) {
        return load(controllerClass, url, resourceBundle, null);
    }

    public <C> C loadController(Class<C> controllerClass) {
        return loadController(controllerClass, (ResourceBundle) null);
    }

    public <C> C loadController(Class<C> controllerClass, ResourceBundle resourceBundle) {
        return load(controllerClass, buildFxmlReference(controllerClass), resourceBundle, null);
    }

    public <C> C getBean(Class<C> beanType) {
        return beanType.cast(beanFactory.call(beanType));
    }

    protected <V extends Node, C> C load(Class<C> callerClass, String location, ResourceBundle resourceBundle,
                                         Consumer<V> viewConsumer) {
        return Optional.ofNullable(location)
                .map(callerClass::getResource)
                .<C>map(url -> loadByView(url, resourceBundle, viewConsumer))
                .orElseGet(() -> getBean(callerClass));
    }

    private <V extends Node, C> C loadByView(@Nonnull URL url, ResourceBundle resourceBundle, Consumer<V> viewConsumer) {
        return loadByViewUsingFxLoader(new FXMLLoader(), url, resourceBundle, viewConsumer);
    }

    <V extends Node, C> C loadByViewUsingFxLoader(FXMLLoader loader, @Nonnull URL url,
                                                  ResourceBundle resourceBundle, Consumer<V> viewConsumer) {
        try (InputStream fxmlStream = url.openStream()) {
            LOG.debug("[load]: Loading {}", url);
            loader.setLocation(url);
            loader.setControllerFactory(beanFactory);
            if (resourceBundle != null) {
                loader.setResources(resourceBundle);
            }
            V view = loader.load(fxmlStream);
            if (viewConsumer != null) {
                viewConsumer.accept(view);
            }
            return loader.getController();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    protected String buildFxmlReference(@Nonnull Class<?> c) {
        return Optional.ofNullable(c.getAnnotation(FxmlView.class)).map(FxmlView::value)
                .map(s -> s.isEmpty() ? null : s)
                .orElse(c.getSimpleName() + ".fxml");
    }

    public void shutdown() {
        closeCommand.run();
        Platform.exit();
    }
}
