package net.rgielen.fxweaver.core;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * FxLoadException wraps {@link IOException}s thrown during the processing with {@link FXMLLoader}.
 * It is a RuntimeException and helps with debugging the actual FXML location used.
 *
 * @author Rene Gielen
 */
public class FxLoadException extends RuntimeException {

    public FxLoadException() {
        super();
    }

    public FxLoadException(String message) {
        super(message);
    }

    public FxLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public FxLoadException(Throwable cause) {
        super(cause);
    }

    protected FxLoadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
