package net.rgielen.fxweaver.spring;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SpringFxContextLoaderTest {

    @Test
    public void testStart() throws Exception {
        assertThat(
                new SpringFxContextLoader(() -> new AnnotationConfigApplicationContext("net.rgielen.fxweaver.spring"))
                        .start()
        ).isNotNull();
    }
}