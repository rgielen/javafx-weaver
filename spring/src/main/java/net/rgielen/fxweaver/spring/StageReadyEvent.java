package net.rgielen.fxweaver.spring;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

/**
 * A re-usable event to signal stage readiness along with providing the actual stage as payload.
 *
 * @author Rene Gielen
 */
public class StageReadyEvent extends ApplicationEvent {

    public StageReadyEvent(Stage stage) {
        super(stage);
    }

    public Stage getStage() {
        return (Stage) getSource();
    }
}
