package net.rgielen.fxweaver.samples.springboot.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/**
 * DialogController.
 *
 * @author Rene Gielen
 * @noinspection WeakerAccess
 */
@FxmlView("SimpleDialog.fxml")
@Component
public class DialogController {

    private Stage stage;

    @FXML
    Button closeButton;

    @FXML
    VBox dialog;

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));
    }

    public void show() {
        stage.show();
    }

    @FXML
    void click(ActionEvent actionEvent) {
        stage.close();
    }
}
