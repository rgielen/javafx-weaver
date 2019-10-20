package net.rgielen.fxweaver.samples.springboot.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:rene.gielen@gmail.com">Rene Gielen</a>
 */
@Component
@FxmlView // equal to: @FxmlView("MainController.fxml")
public class MainController {

    private final String greeting;

    public MainController(@Value("${spring.application.demo.greeting}") String greeting) {
        this.greeting = greeting;
    }

    @FXML
    public Label label;

    @FXML
    public Button button;

    @FXML
    public void initialize() {
        this.button.setOnAction(
                actionEvent -> this.label.setText(greeting)
        );
    }

}
