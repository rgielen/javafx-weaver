package net.rgielen.fxweaver.samples.springboot;

import javafx.application.Application;
import javafx.scene.Node;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.samples.springboot.application.SpringbootJavaFxApplication;
import net.rgielen.fxweaver.spring.InjectionPointLazyFxControllerAndViewResolver;
import net.rgielen.fxweaver.spring.SpringFxWeaver;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class JavafxWeaverSpringbootSampleApplication {

    public static void main(String[] args) {
        Application.launch(SpringbootJavaFxApplication.class, args);
    }

    @Bean
    public FxWeaver fxWeaver(ConfigurableApplicationContext applicationContext) {
        // Would also work with javafx-weaver-core only:
        // return new FxWeaver(applicationContext::getBean, applicationContext::close);
        return new SpringFxWeaver(applicationContext);
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
