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
            service.addOrUpdateDnsRecord("itlayer","10.212.9.108");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
