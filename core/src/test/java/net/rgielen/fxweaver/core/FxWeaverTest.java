package net.rgielen.fxweaver.core;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FxWeaverTest {

    FxWeaver fxWeaver;

    @Before
    public void setUp() {
        fxWeaver = new FxWeaver(null, null);
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

}