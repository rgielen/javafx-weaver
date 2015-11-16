package net.rgielen.fxweaver.spring;

import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.context.ConfigurableApplicationContext;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * SpringFxmlLoader is a Spring-powered version of FxWeaver.
 *
 * @author Rene Gielen
 */
@Named
public class SpringFxWeaver extends FxWeaver {

    @Inject
	public SpringFxWeaver(ConfigurableApplicationContext context) {
        super(context::getBean, context::close);
    }

}
