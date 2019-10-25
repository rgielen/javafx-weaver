package net.rgielen.fxweaver.samples.springboot.starter.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.stereotype.Component;


/**
 * DialogController.
 *
 * @author Rene Gielen
 * @noinspection WeakerAccess
 */
@Component
@FxmlView
public class SomeDialog {

    private Stage stage;

    @FXML
    private Button closeButton;
    @FXML
    private VBox dialog;

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));

        closeButton.setOnAction(
                actionEvent -> stage.close()
        );
    }

    public void show() {
        stage.show();
    }

}
