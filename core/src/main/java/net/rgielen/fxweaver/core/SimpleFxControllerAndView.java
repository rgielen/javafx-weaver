package net.rgielen.fxweaver.core;

import javafx.scene.Node;

import java.util.Optional;

/**
 * SimpleFxControllerAndView is a container class for Controller beans and their corresponding Views.
 *
 * @author Rene Gielen
 * @noinspection WeakerAccess
 */
public class SimpleFxControllerAndView<C, V extends Node> implements FxControllerAndView<C, V> {

    public static <C, V extends Node> FxControllerAndView<C, V> ofController(C controller) {
        return new SimpleFxControllerAndView<>(controller, null);
    }

    public static <C, V extends Node> FxControllerAndView<C, V> of(C controller, V view) {
        return new SimpleFxControllerAndView<>(controller, view);
    }

    private final C controller;
    private final V view;

    public SimpleFxControllerAndView(C controller, V view) {
        this.view = view;
        this.controller = controller;
    }

    @Override
    public C getController() {
        return controller;
    }

    @Override
    public Optional<V> getView() {
        return Optional.ofNullable(view);
    }

    @Override
    public String toString() {
        return "SimpleFxControllerAndView {" +
                "controller=" + controller +
                ", view=" + view +
                '}';
    }
}
