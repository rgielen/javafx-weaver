package net.rgielen.fxweaver.samples.springboot;

import com.sun.javafx.stage.StageHelper;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.samples.springboot.controller.MainController;
import net.rgielen.fxweaver.spring.InjectionPointLazyFxControllerAndViewResolver;
import net.rgielen.fxweaver.spring.SpringFxWeaver;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class JavafxWeaverSpringbootSampleApplication {

    public static void main(String[] args) {
        Platform.startup(() -> {
            SpringApplication.run(JavafxWeaverSpringbootSampleApplication.class, args);
        });
    }

    @Bean
    public FxWeaver fxWeaver(ConfigurableApplicationContext applicationContext) {
        return new SpringFxWeaver(applicationContext);
    }

    @Bean
    CommandLineRunner commandLineRunner(FxWeaver fxWeaver) {
        return args -> {
            Stage stage = new Stage();
            StageHelper.setPrimary(stage, true);
            Scene scene = new Scene(fxWeaver.loadView(MainController.class), 400, 300);
            stage.setScene(scene);
            stage.show();
        };
    }

    /**
     * See {@link net.rgielen.fxweaver.samples.springboot.controller.DialogController#DialogController(FxControllerAndView)}
     * for an example usage.
     * <p/>
     * <strong>MUST be in scope prototype!</strong>
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public <C, V extends Node> FxControllerAndView<C, V> controllerAndView(FxWeaver fxWeaver,
                                                                           InjectionPoint injectionPoint) {
        return new InjectionPointLazyFxControllerAndViewResolver(fxWeaver)
                .resolve(injectionPoint);
    }

}
