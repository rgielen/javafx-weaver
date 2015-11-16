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
 * FxWeaver is the core weaving facility.
 *
 * @author Rene Gielen
 */
public class FxWeaver {

    private static final Logger LOG = LoggerFactory.getLogger(FxWeaver.class);

    private final Callback<Class<?>, Object> beanFactory;
    private final Runnable closeCommand;

    public FxWeaver(Callback<Class<?>, Object> beanFactory, Runnable closeCommand) {
        this.beanFactory = beanFactory;
        this.closeCommand = closeCommand;
    }

    public  <V extends Node> V loadView(Object caller, String url) {
        return loadView(caller, url, null);
    }

    public <V extends Node, C> V loadView(Class<C> controllerClass) {
        return loadView(controllerClass, (ResourceBundle) null);
    }

    public <V extends Node, C> V loadView(Class<C> controllerClass, ResourceBundle resourceBundle) {
        return loadView(controllerClass, buildFxmlReference(controllerClass), resourceBundle);
    }

    @SuppressWarnings("unchecked")
    public  <V extends Node> V loadView(Object caller, String url, ResourceBundle resourceBundle) {
        List<V> viewContainer = new ArrayList<>(1);
        load(caller, url, resourceBundle, v -> viewContainer.add((V) v));
        return viewContainer.get(0);
    }

    public <C> C loadController(Object caller, String url) {
        return loadController(caller, url, null);
    }

    public <C> C loadController(Object caller, String url, ResourceBundle resourceBundle) {
        return load(caller, url, resourceBundle, null);
    }

    public <C> C loadController(Class<C> controllerClass) {
        return loadController(controllerClass, (ResourceBundle) null);
    }

    public <C> C loadController(Class<C> controllerClass, ResourceBundle resourceBundle) {
        return load(controllerClass, buildFxmlReference(controllerClass), resourceBundle, null);
    }

    @SuppressWarnings("unchecked")
    public <C> C getBean(Class<C> beanType) {
        return (C) beanFactory.call(beanType);
    }

    protected  <V extends Node, C> C load(Object caller, String location, ResourceBundle resourceBundle, Consumer<V> viewConsumer) {
        final Class<?> callerClass = caller instanceof Class ? (Class<?>) caller : caller.getClass();
        URL url = callerClass.getResource(location);
        try (InputStream fxmlStream = url.openStream()) {
            LOG.debug("[load]: Loading {}", url);
            FXMLLoader loader = new FXMLLoader();
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
