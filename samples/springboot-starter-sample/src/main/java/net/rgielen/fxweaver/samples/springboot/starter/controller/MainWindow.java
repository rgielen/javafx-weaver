package net.rgielen.fxweaver.samples.springboot.starter.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import net.rgielen.fxweaver.core.FxmlView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView
public class MainWindow {

    @Autowired
    private SomeDialog someDialog;

    @FXML
    public Button openDialogButton;

    @FXML
    public void initialize() {
        openDialogButton.setOnAction(
                actionEvent -> this.someDialog.show()
        );
    }

}
