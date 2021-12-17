package com.seceh.basic.utils.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    private FileUtils() {
    }

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static String getStringFromResource(String fileName) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        try (InputStream in = classloader.getResourceAsStream(fileName)) {
            if (in == null) {
                return null;
            }

            byte[] b = new byte[1024 * 1024];
            int len = 0;
            int temp;
            while ((temp = in.read()) != -1) {
                b[len++] = (byte) temp;
            }
            return new String(b, 0, len, StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("getStringFromResource failed", e);
        }
        return null;
    }
}