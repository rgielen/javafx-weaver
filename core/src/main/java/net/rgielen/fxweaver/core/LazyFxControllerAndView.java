package net.rgielen.fxweaver.core;

import javafx.scene.Node;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * A {@link FxControllerAndView} implementation that takes a supplier to lazily load the actual view and controller
 * when being on the JavaFX {@link javafx.application.Application} thread, which is not the case during constructor
 * injection.
 *
 * The implementation is <tt>NOT</tt> threadsafe, since JavaFX GUI is supposed to work single threaded.
 *
 * @author <a href="mailto:rene.gielen@gmail.com">Rene Gielen</a>
 */
public class LazyFxControllerAndView<C, V extends Node> implements FxControllerAndView<C, V> {

    private final Supplier<FxControllerAndView<C, V>> supplier;
    private FxControllerAndView<C, V> inner = null;

    public LazyFxControllerAndView(Supplier<FxControllerAndView<C, V>> supplier) {
        this.supplier = supplier;
    }

    @Override
    public C getController() {
        return initOrGet().getController();
    }

    @Override
    public Optional<V> getView() {
        return initOrGet().getView();
    }

    @Override
    public String toString() {
        return "LazyFxControllerAndView{" +
                "inner=" + (inner != null ? inner : "Not initialized") +
                '}';
    }

    /**
     * Non-threadsafe lazy loader implementation.
     *
     * @noinspection WeakerAccess
     * */
    protected FxControllerAndView<C, V> initOrGet() {
        if (inner == null) {
            inner = supplier.get();
        }
        return inner;
    }
}
