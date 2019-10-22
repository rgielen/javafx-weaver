package net.rgielen.fxweaver.core;

import javafx.scene.Node;

import java.util.Optional;

/**
 * FxControllerAndView is a container class for Controller beans and their corresponding Views.
 *
 * @author Rene Gielen
 * @noinspection WeakerAccess
 */
public class FxControllerAndView<C, V extends Node> {

    public static <C, V extends Node> FxControllerAndView<C, V> ofController(C controller) {
        return new FxControllerAndView<>(controller, null);
    }

    public static <C, V extends Node> FxControllerAndView<C, V> of(C controller, V view) {
        return new FxControllerAndView<>(controller, view);
    }

    final C controller;
    final V view;

    public FxControllerAndView(C controller, V view) {
        this.view = view;
        this.controller = controller;
    }

    public C getController() {
        return controller;
    }

    public Optional<V> getView() {
        return Optional.ofNullable(view);
    }

    @Override
    public String toString() {
        return "ControllerAndView{" +
                "controller=" + controller +
                ", view=" + view +
                '}';
    }
}
