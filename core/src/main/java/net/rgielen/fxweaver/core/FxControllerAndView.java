package net.rgielen.fxweaver.core;

import javafx.scene.Node;

import java.util.Optional;

/**
 * FxControllerAndView is a container class for Controller beans and their corresponding Views.
 *
 * @author Rene Gielen
 */
public interface FxControllerAndView<C, V extends Node> {

    C getController();

    Optional<V> getView();

}
