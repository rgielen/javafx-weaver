package net.rgielen.fxweaver.samples.springboot.starter;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javafx.application.Application;
import net.rgielen.fxweaver.samples.springboot.starter.application.SpringbootJavaFxApplication;

/**
 * FxWeaverSpringBootStarterSampleApplication.
 *
 * @author Rene Gielen
 */
@SpringBootApplication
@ComponentScan(basePackages = { "net.rgielen.fxweaver.spring.boot.autoconfigure",
		"net.rgielen.fxweaver.samples.springboot.starter.application",
		"net.rgielen.fxweaver.samples.springboot.starter.controller" })
@EnableAutoConfiguration
public class FxWeaverSpringBootStarterSampleApplication {
//FIXME can not start app
	public static void main(String[] args) {
		Application.launch(SpringbootJavaFxApplication.class, args);
	}

}
