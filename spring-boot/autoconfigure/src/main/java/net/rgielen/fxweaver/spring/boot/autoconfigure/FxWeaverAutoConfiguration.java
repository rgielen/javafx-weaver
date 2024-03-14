package net.rgielen.fxweaver.spring.boot.autoconfigure;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxLoadException;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import net.rgielen.fxweaver.spring.InjectionPointLazyFxControllerAndViewResolver;
import net.rgielen.fxweaver.spring.SpringFxWeaver;

import java.util.Arrays;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.type.filter.AnnotationTypeFilter;

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
    private static final Logger LOG = LoggerFactory.getLogger(FxWeaverAutoConfiguration.class);
    
    @Value("${fxweaver.autoscan.basepackages:#{null}}")
    private Optional<String[]> autoScanBasePackages;
    
    @Bean
    @ConditionalOnMissingBean(FxWeaver.class)
    public FxWeaver fxWeaver(ConfigurableApplicationContext applicationContext) {
        SpringFxWeaver springFxWeaver = new SpringFxWeaver(applicationContext);
        if (this.autoScanBasePackages.isPresent()) {
            ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
            scanner.addIncludeFilter(new AnnotationTypeFilter(FxmlView.class));
            Arrays.stream(this.autoScanBasePackages.get()).forEach(autoScanBasePackage -> {
                scanner.findCandidateComponents(autoScanBasePackage).forEach(beanDefinition -> {
                    try {
                        LOG.info("Initializing fxmlview annnotated bean '{}'...", beanDefinition.getBeanClassName());
                        springFxWeaver.load(Class.forName(beanDefinition.getBeanClassName()));
                    } catch (ClassNotFoundException cnfe) {
                        throw new FxLoadException(String.format("Class '%s' not found!", beanDefinition.getBeanClassName()), cnfe);
                    }
                });
            });
        } else {
            LOG.info("Config property 'fxweaver.autoscan.basepackages' not set, skipping auto fxmlview annotated bean registration.");
        }
        return springFxWeaver;
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
