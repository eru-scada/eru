package com.marlontrujillo.eru.logger;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Created by mtrujillo on 9/2/2015.
 */
public class LabelAppender extends WriterAppender {
    private static volatile StringProperty lastLog;

    @Override
    public void append(LoggingEvent event) {
        final String message = this.layout.format(event);
        // Append formatted message to text area using the Thread.
        try {
            Platform.runLater(() -> {
                try {
                    if (lastLog != null) {
                        lastLog.setValue(message);
                    }
                } catch (final Throwable t) {
                    LogUtil.logger.error("TextAreaAppender error:", t);
                }
            });
        } catch (final IllegalStateException e) {
            // ignore case when the platform hasn't yet been iniitialized
        }
    }

    public static void setObservableString(StringProperty observableString){
        LabelAppender.lastLog = observableString;
    }
}
