package net.rgielen.fxweaver.spring;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringFxContextLoaderTest {

    @Test
    public void testStart() throws Exception {
        assertThat(
                new SpringFxContextLoader(() -> new AnnotationConfigApplicationContext("net.rgielen.fxweaver.spring"))
                        .start()
        ).isNotNull();
    }
}