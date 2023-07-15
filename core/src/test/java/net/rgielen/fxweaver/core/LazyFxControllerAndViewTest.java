package net.rgielen.fxweaver.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import javafx.scene.Node;

/**
 * @author <a href="mailto:rene.gielen@gmail.com">Rene Gielen</a>
 */
public class LazyFxControllerAndViewTest {

    /** @noinspection unchecked*/
    @Test
    public void initOrGetCallsSupplierExactlyOnce() throws Exception {
        Supplier<FxControllerAndView<Object, Node>> supplier = mock(Supplier.class);
        when(supplier.get()).thenReturn(mock(FxControllerAndView.class));
        LazyFxControllerAndView<Object, Node> controllerAndView = new LazyFxControllerAndView<>(supplier);
        FxControllerAndView<Object, Node> cav1 = controllerAndView.initOrGet();
        FxControllerAndView<Object, Node> cav2 = controllerAndView.initOrGet();
        assertThat(cav1).isSameAs(cav2);
        verify(supplier, times(1)).get();
    }

}
