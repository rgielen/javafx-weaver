package net.rgielen.fxweaver.core;

import javafx.scene.Node;
import org.junit.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
