package net.rgielen.fxweaver.spring.boot.autoconfigure;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.spring.InjectionPointLazyFxControllerAndViewResolver;
import net.rgielen.fxweaver.spring.SpringFxWeaver;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * FxWeaverAutoConfiguration.
 *
 * @author Rene Gielen
 */
@Configuration
@ConditionalOnClass({
        Node.class,
        FXMLLoader.class,
        SpringFxWeaver.class
})
public class FxWeaverAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(FxWeaver.class)
    public FxWeaver fxWeaver(ConfigurableApplicationContext applicationContext) {
        return new SpringFxWeaver(applicationContext);
    }

    @Bean
    @ConditionalOnMissingBean(InjectionPointLazyFxControllerAndViewResolver.class)
    public InjectionPointLazyFxControllerAndViewResolver injectionPointLazyFxControllerAndViewResolver(
            FxWeaver fxWeaver) {
        return new InjectionPointLazyFxControllerAndViewResolver(fxWeaver);
    }

    @Bean
    @ConditionalOnMissingBean(FxControllerAndView.class)
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public <C, V extends Node> FxControllerAndView<C, V> fxControllerAndView (
            InjectionPointLazyFxControllerAndViewResolver injectionPointLazyFxControllerAndViewResolver,
            InjectionPoint injectionPoint) {
        return injectionPointLazyFxControllerAndViewResolver.resolve(injectionPoint);
    }


}
