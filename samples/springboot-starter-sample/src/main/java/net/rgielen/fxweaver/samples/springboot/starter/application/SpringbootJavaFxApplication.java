package net.rgielen.fxweaver.samples.springboot.starter.application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import net.rgielen.fxweaver.samples.springboot.starter.FxWeaverSpringBootStarterSampleApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author <a href="mailto:rene.gielen@gmail.com">Rene Gielen</a>
 * @noinspection RedundantThrows
 */
public class SpringbootJavaFxApplication extends Application {

	private static final Logger log=LoggerFactory.getLogger(SpringbootJavaFxApplication.class);
	private ConfigurableApplicationContext context;

	@Override
	public void init() throws Exception {
		
		log.info("Initial JavaFX ...");
		
		this.context = new SpringApplicationBuilder().sources(FxWeaverSpringBootStarterSampleApplication.class)
				.run(getParameters().getRaw().toArray(new String[0]));
	
		log.info("Initial completed.");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		context.publishEvent(new StageReadyEvent(primaryStage));
		log.info("Stage creation completed.");
	}

	@Override
	public void stop() throws Exception {
		context.close();
		Platform.exit();
	}
}
