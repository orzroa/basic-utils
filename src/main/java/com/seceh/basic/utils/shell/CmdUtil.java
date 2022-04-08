package com.seceh.basic.utils.shell;

import com.google.common.collect.Lists;
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


    private static class CmdOutputReader implements Runnable {

        private InputStream in;
        private StringBuilder output;

        public CmdOutputReader(InputStream in, StringBuilder output) {
            this.in = in;
            this.output = output;
        }

        @Override
        public void run() {
            String line;
            List<String> lines = Lists.newArrayList();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET))) {
                while ((line = br.readLine()) != null) {
                    if (lines.size() > 1000) {
                        output.append("[TOO_MANY_LINES]").append("\n");
                    }
                    if (line.length() > 1000000) {
                        output.append("[TOO_LONG_LINE]").append("\n");
                    } else {
                        output.append(line).append("\n");
                    }
                }
            } catch (IOException e) {
                logger.error("cannot readFromStream", e);
            }
        }
    }

    public static CmdRunResult run(String cmd, boolean showDetail) {
        String[] cmds;
        String[] envs;

        if (showDetail) logger.info("running {}", cmd);

        if (OsUtil.isWindows()) {
            cmds = new String[]{"cmd", "/c", cmd};
            envs = new String[]{"PATH=C:\\windows\\system32;C:\\windows"};
        } else {
            cmds = new String[]{"/bin/bash", "-c", cmd};
            envs = new String[]{"LANG=UTF-8",
                    "PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin"};
        }

        StringBuilder stdout = new StringBuilder();
        StringBuilder error = new StringBuilder();
        CmdRunResult result = new CmdRunResult(cmd);

        try {
            Process p = Runtime.getRuntime().exec(cmds, envs, null);
            new Thread(new CmdOutputReader(p.getInputStream(), stdout)).start();
            new Thread(new CmdOutputReader(p.getErrorStream(), error)).start();

            result.setExitCode(p.waitFor());
            result.setStdout(stdout.toString());
            result.setError(error.toString());

            if (showDetail && logger.isInfoEnabled()) {
                logger.info("exit code: {}", result.getExitCode());
                logger.info("stdout:\n{}", result.getStdout());
                logger.info("stderr:\n{}", result.getError());
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
        CmdUtil.run("ping localhost -c 4", true);
    }

    public static class CmdRunResult {

        private String cmd;

        private int exitCode;

        private String stdout;

        private String error;

        public CmdRunResult() {
        }

        public CmdRunResult(String cmd) {
            this.cmd = cmd;
        }

        public String getCmd() {
            return cmd;
        }

        public void setCmd(String cmd) {
            this.cmd = cmd;
        }

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
