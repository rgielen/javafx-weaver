package net.rgielen.fxweaver.core;

import java.lang.annotation.*;

/**
 * Controllers annotated with @FxmlView declare that a given FXML file should be loaded and weaved with the controller.
 * <p/>
 * When loaded by {@link FxWeaver}, declared FXML views will be loaded automatically with the controller as per JavaFX
 * fx:controller facility.
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
 * <p/>
 * Examples:
 * <pre>
 *     &#64;FxmlView
 *     public class Foo {
 *          // try to load Foo.fxml in same package
 *     }
 *
 *     &#64;FxmlView("view.fxml")
 *     public class Foo {
 *          // try to load view.fxml in same package
 *     }
 *
 *     &#64;FxmlView("/somedir/view.fxml")
 *     public class Foo {
 *          // try to load view.fxml in root package somedir
 *     }
 *
 * </pre>
 *
 * @author Rene Gielen
 * @see FxWeaver
 * @see FxWeaver#loadView(Class)
 * @see FxWeaver#loadController(Class)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FxmlView {

    /**
     * The FXML file to be loaded as view, as class loader location.
     * <p/>
     * If not given, defaulting to simple class name plus <tt>.fxml</tt> extension in the same package.
     */
    String value() default "";

}
