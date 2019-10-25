package net.rgielen.fxweaver.samples.springboot.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:rene.gielen@gmail.com">Rene Gielen</a>
 */
@Component
public class AnotherDialog {

    @FXML
    VBox dialog;

    private Stage stage;

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setTitle("Another Dialog");
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
