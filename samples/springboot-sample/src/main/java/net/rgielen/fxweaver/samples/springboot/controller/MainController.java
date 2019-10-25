package net.rgielen.fxweaver.samples.springboot.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@FxmlView // equal to: @FxmlView("MainController.fxml")
public class MainController {

    private final String greeting;
    private final FxWeaver fxWeaver;
    private final FxControllerAndView<DialogController, VBox> dialog;

    @FXML
    public Label label;
    @FXML
    public Button helloButton;
    @FXML
    public Button openSimpleDialogButton;
    @FXML
    public Button openTiledDialogButton;

    public MainController(@Value("${spring.application.demo.greeting}") String greeting,
                          FxWeaver fxWeaver,
                          FxControllerAndView<DialogController, VBox> dialog) {
        this.greeting = greeting;
        this.fxWeaver = fxWeaver;
        this.dialog = dialog;
    }

    @FXML
    public void initialize() {
        helloButton.setOnAction(
                actionEvent -> this.label.setText(greeting)
        );
/*
        openSimpleDialogButton.setOnAction(
                actionEvent -> fxWeaver.loadController(DialogController.class).show()
        );
*/
        openSimpleDialogButton.setOnAction(
                actionEvent -> dialog.getController().show()
        );

        openTiledDialogButton.setOnAction(
                actionEvent -> {
                    FxControllerAndView<TiledDialogController, VBox> tiledDialog =
                            fxWeaver.load(TiledDialogController.class);
                    tiledDialog.getView().ifPresent(
                            v -> {
                                Label label = new Label();
                                label.setText("Dynamically added Label");
                                v.getChildren().add(label);
                            }
                    );
                    tiledDialog.getController().show();
                }
        );
    }

}
