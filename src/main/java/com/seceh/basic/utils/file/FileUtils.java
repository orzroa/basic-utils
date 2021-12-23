package com.seceh.basic.utils.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
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
            return getStringFromInputStream(in);
        } catch (IOException e) {
            logger.error("getStringFromResource failed", e);
            return null;
        }
    }

    public static String getStringFromFile(String fileName) {
        File file = new File(fileName);

        try (InputStream in = new FileInputStream(file)) {
            return getStringFromInputStream(in);
        } catch (IOException e) {
            logger.error("getStringFromFile failed", e);
            return null;
        }
    }

    private static String getStringFromInputStream(InputStream in) throws IOException {
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
    }
}