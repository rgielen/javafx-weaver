package net.rgielen.fxweaver.samples.springboot.starter.application;

import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.samples.springboot.starter.controller.MainWindow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:rene.gielen@gmail.com">Rene Gielen</a>
 */
@Component
public class PrimaryStageInitializer implements ApplicationListener<StageReadyEvent> {

	private static final Logger log =LoggerFactory.getLogger(PrimaryStageInitializer.class);
	
	
    private final FxWeaver fxWeaver;

    @Autowired
    public PrimaryStageInitializer(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
        log.info("PrimaryStageInitializer ");
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
    	
    	log.info("Primary stage initializer");
    	
        Stage stage = event.stage;
        Scene scene = new Scene(fxWeaver.loadView(MainWindow.class), 400, 300);
        stage.setScene(scene);
        stage.show();
        
        log.info("Primary stage show");
        
    }
}
