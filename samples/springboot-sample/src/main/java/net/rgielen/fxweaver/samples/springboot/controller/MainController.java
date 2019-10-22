package net.rgielen.fxweaver.samples.springboot.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxWeaver;
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
    private final FxWeaver fxWeaver;

    @FXML
    public Label label;
    @FXML
    public Button helloButton;
    @FXML
    public Button openSimpleDialogButton;

    public MainController(@Value("${spring.application.demo.greeting}") String greeting,
                          FxWeaver fxWeaver) {
        this.greeting = greeting;
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize() {
        helloButton.setOnAction(
                actionEvent -> this.label.setText(greeting)
        );
        openSimpleDialogButton.setOnAction(
                actionEvent -> fxWeaver.loadController(DialogController.class).show()
        );
    }

}
