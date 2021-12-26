package com.seceh.basic.utils.net;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtil {

    private IpUtil() {
    }

    /**
     * Developer must assign a header, or there would be some security issue.
     * Because somebody may fake a header which is commonly used.
     */
    public static String getRemoteAddress(HttpServletRequest request, String remoteAddressHeader) {
        String ip = "";
        if (remoteAddressHeader != null && remoteAddressHeader.length() > 0) {
            ip = request.getHeader(remoteAddressHeader);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            return null;
        }

        if (ip.contains(",")) {
            ip = ip.split(",")[0];
        }
        if (validIPAddress(ip)) {
            return ip;
        } else {
            return null;
        }
    }

    public static boolean validIPAddress(String ip) {
        try {
            InetAddress.getByName(ip);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }
}