package com.telsoft.dns;

import com.telsoft.dns.service.DnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DnsNotifApplication {
    static DnsService service = new DnsService();

    public static void main(String[] args) {
        SpringApplication.run(DnsNotifApplication.class, args);
        try {
            System.out.println(service.findDnsRecord("tenten.vn"));
            service.addOrUpdateDnsRecord("tenten.vn","137.59.104.97");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
