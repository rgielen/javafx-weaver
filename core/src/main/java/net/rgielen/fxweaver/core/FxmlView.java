package net.rgielen.fxweaver.core;

import java.lang.annotation.*;

/**
 * Controllers annotated with @FxmlView declare that a given FXML file should be loaded and weaved with the controller.
 *
 * <p/>
 * When loaded by {@link FxWeaver}, declared FXML views will be loaded automatically with the controller as per
 * JavaFX fx:controller facility.
 * <ul>
 *     <li>
 *         When no {@link FxmlView#value()} is given, the resource name is inferred by the simple classname.
 *         A class Foo in package com.acme then references a <pre>Foo.fxml</pre> resource found in the same package.
 *     </li>
 *     <li>
 *         When a {@link FxmlView#value()}, the given value is used as declared FXML resource. If no absolute path is
 *         given, it is assumed to reside in or below the package of the declaring controller
 *     </li>
 * </ul>
 *
 * Examples:
 * <pre>
 *     @FxmlView
 *     public class Foo {
 *
 *     }
 *
 *     @FxmlView("view.fxml")
 *     public class Foo {
 *
 *     }
 *
 *     @FxmlView("/somedir/view.fxml")
 *     public class Foo {
 *
 *     }
 *
 * </pre>
 *
 * @see FxWeaver
 * @see FxWeaver#loadView(Class)
 * @see FxWeaver#loadController(Class)
 *
 * @author Rene Gielen
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FxmlView {

    String value() default "";

}
