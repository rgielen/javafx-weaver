package net.rgielen.fxweaver.samples.springboot.controller;

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
@FxmlView("SimpleDialog.fxml")
public class DialogController {

    private Stage stage;

    @FXML
    private Button openAnotherDialogButton;
    @FXML
    private Button closeButton;
    @FXML
    private VBox dialog;

    private final FxControllerAndView<AnotherDialog, VBox> anotherControllerAndView;

    /**
     * This injection is powered by
     * {@link net.rgielen.fxweaver.samples.springboot.JavafxWeaverSpringbootSampleApplication#controllerAndView(FxWeaver, InjectionPoint)}
     * <p/>
     * Your IDE might get confused, but it works :)
     */
    public DialogController(FxControllerAndView<AnotherDialog, VBox> anotherControllerAndView) {
        this.anotherControllerAndView = anotherControllerAndView;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(dialog));

        openAnotherDialogButton.setOnAction(
                actionEvent -> anotherControllerAndView.getController().show()
        );
        closeButton.setOnAction(
                actionEvent -> stage.close()
        );
    }

    public void show() {
        stage.show();
    }

}
