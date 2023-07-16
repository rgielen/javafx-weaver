package net.rgielen.fxweaver.samples.springboot.starter.controller;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;


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
