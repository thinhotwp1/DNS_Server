package com.telsoft.dns.service;

import org.springframework.stereotype.Component;
import org.xbill.DNS.*;

import java.io.IOException;
import java.net.InetAddress;

@Component
public class DnsService {
    public void addOrUpdateDnsRecord(String domain, String ipAddress) throws IOException {
        Update update = new Update(Name.fromString("itlayer.example.com.")); // Thay "example.com" bằng tên miền DNS của bạn
        update.add(Name.fromString(domain + ".example.com."), Type.A, 86400L, "10.212.9.108"); // 86400L là TTL

        Resolver resolver = new SimpleResolver("10.212.9.108"); // Thay "your-dns-server-address" bằng địa chỉ IP của máy chủ DNS của bạn
//    resolver.setTSIGKey(new TSIG("your-tsig-key-name", "your-tsig-key")); // Thay "your-tsig-key-name" và "your-tsig-key" bằng thông tin TSIG key nếu cần
        resolver.setTCP(true);

        try {
            Message response = resolver.send(update);
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

