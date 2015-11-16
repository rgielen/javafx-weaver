package net.rgielen.fxweaver.core;

import java.lang.annotation.*;

/**
 * Controllers annotated with @FxmlView declare that a given FXML file should be loeaded and weaved with the controller.
 *
 * @author Rene Gielen
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FxmlView {

    String value() default "";

}
