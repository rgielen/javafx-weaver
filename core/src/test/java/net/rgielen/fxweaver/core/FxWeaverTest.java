package net.rgielen.fxweaver.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import net.rgielen.fxweaver.core.support.FxmlViewClassAnnotated;
import net.rgielen.fxweaver.core.support.FxmlViewClassAnnotatedWithValue;
import net.rgielen.fxweaver.core.support.FxmlViewClassNonAnnotated;
import net.rgielen.fxweaver.core.support.SimpleBean;

public class FxWeaverTest {

    private FxWeaver fxWeaver;

    @BeforeEach
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
        FxControllerAndView<SimpleBean, Node> load = fxWeaver.load(SimpleBean.class, null, null);
        assertThat(load).isNotNull();
        assertThat(load.getController()).isNotNull().isInstanceOf(SimpleBean.class);
    }

    @Test
    public void loadByViewLoadsControllerAndView() throws Exception {
        FXMLLoader fxmlLoader = mock(FXMLLoader.class);
        fxWeaver.loadByViewUsingFxmlLoader(fxmlLoader, this.getClass().getResource("foo.fxml"), null);
        verify(fxmlLoader).load(any(InputStream.class));
        verify(fxmlLoader).getController();
    }

    @Test
    public void loadControllerAndViewWorksWithValidView() throws Exception {
        FxControllerAndView<SimpleBean, Pane> cav = fxWeaver.load(SimpleBean.class, "/net/rgielen/fxweaver/core/foo.fxml", null);
        assertThat(cav).isNotNull();
        assertThat(cav.getController()).isNotNull().isInstanceOfAny(SimpleBean.class);
        assertThat(cav.getView()).isPresent();
        assertThat(cav.getView().get()).isInstanceOfAny(Pane.class);
    }

    @Test
    public void loadControllerAndViewThrowsExceptionForValidView() throws Exception {
        assertThatThrownBy(
                () -> fxWeaver.load(SimpleBean.class, "/net/rgielen/fxweaver/core/notvalid.fxml", null)
        ).isInstanceOf(FxLoadException.class);
    }
}
