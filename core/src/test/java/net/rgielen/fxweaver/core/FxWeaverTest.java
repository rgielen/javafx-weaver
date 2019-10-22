package net.rgielen.fxweaver.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import net.rgielen.fxweaver.core.support.FxmlViewClassAnnotated;
import net.rgielen.fxweaver.core.support.FxmlViewClassAnnotatedWithValue;
import net.rgielen.fxweaver.core.support.FxmlViewClassNonAnnotated;
import net.rgielen.fxweaver.core.support.SimpleBean;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FxWeaverTest {

    private FxWeaver fxWeaver;

    @Before
    public void setUp() throws Exception {
        fxWeaver = new FxWeaver(c -> {
            try {
                return c.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }, null);
    }

    @Test
    public void testFindFxmlName() throws Exception {
        assertThat(fxWeaver.buildFxmlReference(FxmlViewClassNonAnnotated.class))
                .isEqualTo("FxmlViewClassNonAnnotated.fxml");
        assertThat(fxWeaver.buildFxmlReference(FxmlViewClassAnnotated.class))
                .isEqualTo("FxmlViewClassAnnotated.fxml");
        assertThat(fxWeaver.buildFxmlReference(FxmlViewClassAnnotatedWithValue.class))
                .isEqualTo("foo.fxml");
    }

    @Test
    public void loadReturnsControllerWhenNoViewCanBeFound() throws Exception {
        FxmlViewClassNonAnnotated load = fxWeaver.load(FxmlViewClassNonAnnotated.class, null, null, null);
        assertThat(load).isNotNull();
    }

    @Test
    public void loadByViewLoadsControllerAndView() throws Exception {
        FXMLLoader fxmlLoader = mock(FXMLLoader.class);
        fxWeaver.loadByViewUsingFxLoader(fxmlLoader, this.getClass().getResource("foo.fxml"), null, null);
        verify(fxmlLoader).load(any(InputStream.class));
        verify(fxmlLoader).getController();
    }

    @Test
    public void load() throws Exception {
        FxWeaver.ControllerAndView<SimpleBean, Pane> cav = fxWeaver.load(SimpleBean.class, "/net/rgielen/fxweaver/core/foo.fxml");
        assertThat(cav).isNotNull();
        assertThat(cav.getController()).isNotNull().isInstanceOfAny(SimpleBean.class);
        assertThat(cav.getView()).isNotNull().isInstanceOfAny(Pane.class);
    }
}
