package com.seceh.basic.utils.shell;

import com.google.common.collect.Lists;
import com.seceh.basic.utils.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class CmdUtil {

    private static final Logger logger = LoggerFactory.getLogger(CmdUtil.class.getName());
    private static final String DEFAULT_CHARSET = System.getProperty("sun.jnu.encoding");

    private static String readFromStream(InputStream in) {
        String line;
        List<String> lines = Lists.newArrayList();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET))) {
            while ((line = br.readLine()) != null) {
                if (lines.size() > 100) {
                    lines.add("[TOO_MANY_LINES]");
                }
                if (line.length() > 10000) {
                    lines.add("[TOO_LONG_LINE]");
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            logger.error("cannot readFromStream", e);
        }

        return String.join("\n", lines);
    }

    public static CmdRunResult run(String cmd, boolean showDetail) {
        InputStream in;
        String[] cmds;
        String[] envs;

        if (OsUtil.isWindows()) {
            cmds = new String[]{"cmd", "/c", cmd};
            envs = new String[]{"PATH=C:\\windows\\system32;C:\\windows"};
        } else {
            cmds = new String[]{"/bin/bash", "-c", cmd};
            envs = new String[]{"LANG=UTF-8",
                    "PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin"};
        }

        String stdout;
        String error;
        CmdRunResult result = new CmdRunResult();

        try {
            Process p = Runtime.getRuntime().exec(cmds, envs, null);
            int exitCode = p.waitFor();
            result.setExitCode(exitCode);

            in = p.getInputStream();
            stdout = readFromStream(in);
            result.setStdout(stdout);

            in = p.getErrorStream();
            error = readFromStream(in);
            result.setError(error);

            if (showDetail && logger.isInfoEnabled()) {
                logger.info(JsonUtils.object2Line(result));
            }

            return result;
        } catch (InterruptedException e) {
            logger.info("Interrupted!", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            if (showDetail) {
                logger.error("error in running: " + cmd, e);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        CmdUtil.run("ping localhost", true);
    }

    public static class CmdRunResult {
        private int exitCode;
        private String stdout;
        private String error;

        public int getExitCode() {
            return exitCode;
        }

        public void setExitCode(int exitCode) {
            this.exitCode = exitCode;
        }

        public String getStdout() {
            return stdout;
        }

        public void setStdout(String stdout) {
            this.stdout = stdout;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}
