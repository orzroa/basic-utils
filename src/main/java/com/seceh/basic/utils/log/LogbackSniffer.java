package com.seceh.basic.utils.log;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.util.ContextSelectorStaticBinder;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.OutputStreamAppender;

import org.apache.commons.io.output.TeeOutputStream;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

public class LogbackSniffer {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LogbackSniffer.class);

    private ILogDivert logDivert;

    public LogbackSniffer(String loggerName, ILogDivert logDivert) {
        if (loggerName == null) return;
        this.logDivert = logDivert;

        OutputStreamAppender<ILoggingEvent> outputStreamAppender = getOutputStreamAppender(loggerName);
        if (outputStreamAppender == null) return;

        TeeOutputStream teeOutputStream = new TeeOutputStream(outputStreamAppender.getOutputStream(),
                new StringQueueOutputStream());
        outputStreamAppender.setOutputStream(teeOutputStream);
    }

    private OutputStreamAppender<ILoggingEvent> getOutputStreamAppender(String loggerName) {
        List<Logger> loggerList = ContextSelectorStaticBinder.getSingleton().
                getContextSelector().getDefaultLoggerContext().getLoggerList();
        for (Logger logger : loggerList) {
            Iterator<Appender<ILoggingEvent>> appenders = logger.iteratorForAppenders();
            while (appenders.hasNext()) {
                Appender<ILoggingEvent> appender = appenders.next();
                if (appender.getName().equalsIgnoreCase(loggerName) && appender instanceof OutputStreamAppender) {
                    return (OutputStreamAppender<ILoggingEvent>) appender;
                }
            }
        }

        LOGGER.error("cannot find appender named {}", loggerName);
        return null;
    }

    private class StringQueueOutputStream extends OutputStream {

        @Override
        public void write(int x) {
            //do nothing
        }

        @Override
        public void write(@Nonnull byte[] b) {
            logDivert.divert(new String(b));
        }

        @Override
        public void write(@Nonnull byte[] b, int off, int len) {
            //do nothing
        }
    }
}
