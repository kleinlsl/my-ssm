package com.tujia.myssm.web.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 枚举天生单例
 * @author: songlinl
 * @create: 2021/12/24 18:27
 */
public enum NetworkInterfaceManager {

    INSTANCE;

    private InetAddress m_local;

    private NetworkInterfaceManager() {
        load();
    }

    //    /**
    //     * 单例模式
    //     * @return
    //     */
    //    public static NetworkInterfaceManager INSTANCE() {
    //            if (INSTANCE == null) {
    //                synchronized (NetworkInterfaceManager.class) {
    //                    if (INSTANCE == null) {
    //                        INSTANCE = new NetworkInterfaceManager();
    //                    }
    //                }
    //            }
    //            return INSTANCE;
    //        }

    public static void main(String[] args) {
        System.out.println("args = " + NetworkInterfaceManager.INSTANCE.getLocalHostAddress());
        System.out.println("UUID.randomUUID().toString() = " + UUID.randomUUID().toString());
        //        for (int i = 0; i < 100; i++) {
        //            String uuid = UUID.randomUUID().toString();
        //            uuid = uuid.replace("-", "");
        //            System.out.println("uuid = " + uuid);
        //            BigInteger intValue = new BigInteger(uuid, 16);
        //            System.out.println("intValue = " + intValue);
        //            System.out.println("Integer.toBinaryString(intValue) = " + intValue.toString(2));
        //            System.out.println();
        //
        //        }
    }

    public String getLocalHostAddress() {
        return m_local.getHostAddress();
    }

    private String getProperty(String name) {
        String value = null;

        value = System.getProperty(name);

        if (value == null) {
            value = System.getenv(name);
        }

        return value;
    }

    public InetAddress findValidateIp4(List<InetAddress> addresses) {
        InetAddress local = null;
        int maxWeight = -1;
        for (InetAddress address : addresses) {
            if (address instanceof Inet4Address) {
                int weight = 0;

                if (address.isSiteLocalAddress()) {
                    weight += 8;
                }

                if (address.isLinkLocalAddress()) {
                    weight += 4;
                }

                if (address.isLoopbackAddress()) {
                    weight += 2;
                }

                // has host name
                // TODO fix performance issue when calling getHostName
                if (!Objects.equals(address.getHostName(), address.getHostAddress())) {
                    weight += 1;
                }

                if (weight > maxWeight) {
                    maxWeight = weight;
                    local = address;
                }
            }
        }
        return local;
    }

    private void load() {
        String ip = getProperty("host.ip");

        if (ip != null) {
            try {
                m_local = InetAddress.getByName(ip);
                return;
            } catch (Exception e) {
                System.err.println(e);
                // ignore
            }
        }

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            List<NetworkInterface> nis = interfaces == null ? Collections.emptyList() : Collections.list(interfaces);
            List<InetAddress> addresses = new ArrayList<>();
            InetAddress local = null;

            try {
                for (NetworkInterface ni : nis) {
                    if (ni.isUp() && !ni.isLoopback()) {
                        addresses.addAll(Collections.list(ni.getInetAddresses()));
                    }
                }
                local = findValidateIp4(addresses);
            } catch (Exception e) {
                // ignore
            }
            if (local != null) {
                m_local = local;
                return;
            }
        } catch (SocketException e) {
            // ignore it
        }

        m_local = InetAddress.getLoopbackAddress();
    }

}
