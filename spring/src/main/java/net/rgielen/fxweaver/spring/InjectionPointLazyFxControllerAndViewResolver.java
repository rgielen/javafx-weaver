package net.rgielen.fxweaver.spring;

import javafx.scene.Node;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.LazyFxControllerAndView;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.core.ResolvableType;

import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class helps to create a generic {@link FxControllerAndView} bean factory that can be used for direct injection
 * of {@link FxControllerAndView} instances into Spring components, based on generic type inspection.
 * <p/>
 * To use, define a {@link FxControllerAndView} bean factory method like shown below.
 * <strong>It is absolutely crucial to define it in prototype scope, since every injection point has to be investigated
 * for its declared generic types!</strong>
 * <pre>
 * &#64;Bean
 * public InjectionPointLazyFxControllerAndViewResolver controllerAndViewResolver(FxWeaver fxWeaver) {
 *     return new InjectionPointLazyFxControllerAndViewResolver(fxWeaver);
 * }
 *
 * &#64;Bean
 * &#64;Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
 * public <C, V extends Node> FxControllerAndView<C, V> controllerAndView(
 *         InjectionPointLazyFxControllerAndViewResolver controllerAndViewResolver,
 *         InjectionPoint injectionPoint) {
 *     return controllerAndViewResolver.resolve(injectionPoint);
 * }
 * </pre>
 * <p>
 * Based on this configuration, a  {@link LazyFxControllerAndView} instance can be injected directly into a component:
 * <pre>
 * &#64;Component
 * class ArbitraryComponent {
 *
 *     private final FxControllerAndView&#60;SomeController, VBox&#62; someController;
 *
 *     public ArbitraryComponent(FxControllerAndView&#60;SomeController, VBox&#62; someController) {
 *         this.someController = someController;
 *     }
 * }
 * </pre>
 *
 * @author <a href="mailto:rene.gielen@gmail.com">Rene Gielen</a>
 */
public class InjectionPointLazyFxControllerAndViewResolver {

    protected final FxWeaver fxWeaver;

    protected final ResourceBundle resourceBundle;

    public InjectionPointLazyFxControllerAndViewResolver(FxWeaver fxWeaver, ResourceBundle resourceBundle) {
        this.fxWeaver = fxWeaver;
        this.resourceBundle = resourceBundle;
    }

    public InjectionPointLazyFxControllerAndViewResolver(FxWeaver fxWeaver) {
        this(fxWeaver, null);
    }

    /**
     * Resolve generic type classes of a {@link FxControllerAndView} {@link InjectionPoint} and return a
     * {@link LazyFxControllerAndView} embedding the {@link FxWeaver#load(Class)} method for instance creation.
     *
     * @param injectionPoint the actual injection point for the {@link FxControllerAndView} to inject
     * @throws IllegalArgumentException when types could not be resolved from the given injection point
     * @noinspection unchecked
     */
    public <C, V extends Node> FxControllerAndView<C, V> resolve(InjectionPoint injectionPoint) {
        ResolvableType resolvableType = findResolvableType(injectionPoint);
        if (resolvableType == null) {
            throw new IllegalArgumentException("No ResolvableType found");
        }
        try {
            Class<C> controllerClass = (Class<C>) resolvableType.getGenerics()[0].resolve();
            return resourceBundle == null ? new LazyFxControllerAndView<>(() -> fxWeaver.load(controllerClass)) : new LazyFxControllerAndView<>(() -> fxWeaver.load(controllerClass, resourceBundle));
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Generic controller type not resolvable for injection point " + injectionPoint, e);
        }
    }

    protected ResolvableType findResolvableType(InjectionPoint injectionPoint) {
        return Optional.ofNullable(injectionPoint.getMethodParameter())
                .map(ResolvableType::forMethodParameter)
                // TODO: Refactor the following to use .or() when dropping Java 8 support
                .orElse(
                        Optional.ofNullable(injectionPoint.getField())
                                .map(ResolvableType::forField)
                                .orElse(null)
                );
    }

}
