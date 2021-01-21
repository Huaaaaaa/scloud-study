package com.cyh.scloud.base.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * Author: Huaaaaaa
 * DateTime: 2021/1/21  17:57
 * Description:  网络相关
 */
@Slf4j
public class NetUtils {

    private static final String ANY_ADDRESS = "0.0.0.0";

    private static final String LOCALHOST_ADDRESS = "localhsot";

    private static final String LOCALHOST_IP = "127.0.0.1";

    /**
     * IPv4地址
     */
    private static final Pattern IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");


    /**
     * 获取真实的ip地址：获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
     *
     * @return
     */
    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        try {
            String IP_UNKNOWN = "unknown";
            if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }

            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ip;
    }

    public static boolean isAnyHost(String name) {
        return ANY_ADDRESS.equals(name);
    }

    public static boolean isLocalHost(String name) {
        return LOCALHOST_ADDRESS.equals(name) || LOCALHOST_IP.equals(name);
    }

    public static boolean isIpv4Host(String name) {
        return IPV4_PATTERN.matcher(name).matches();
    }

    /**
     * IP是否有效
     *
     * @param address InetAddress IP地址
     * @return 是否合法
     */
    private static boolean isValidAddress(InetAddress address) {
        if (address == null || address.isLoopbackAddress()) {
            return false;
        }
        String name = address.getHostAddress();
        return (name != null && !isAnyHost(name) && !isLocalHost(name) && isIpv4Host(name));
    }


    public static InetAddress getInetAddress() {

        InetAddress inetAddress = null;
        try {
            inetAddress = inetAddress.getLocalHost();
            if (isValidAddress(inetAddress)) {
                return inetAddress;
            }
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces == null) {
                return null;
            }
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                if (inetAddress == null) {
                    return null;
                }
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress1 = inetAddresses.nextElement();
                    if (isValidAddress(inetAddress1)) {
                        return inetAddress1;
                    }
                }


            }
        } catch (UnknownHostException | SocketException e) {
            log.error("get localNet failed,e={}", e);
        }
        return null;
    }


    public static String getRequestIp(HttpServletRequest request) {
        String ip = getRealIp(request);
        if (StringUtils.isEmpty(ip)) {
            ip = getInetAddress().getHostAddress();
        }
        return ip;
    }
}
