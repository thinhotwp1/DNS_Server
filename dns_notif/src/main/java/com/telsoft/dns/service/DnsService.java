package com.telsoft.dns.service;

import com.telsoft.dns.DnsNotifApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;
import org.xbill.DNS.*;
import org.xbill.DNS.Record;

import java.io.IOException;
import java.net.UnknownHostException;

@Service
public class DnsService {
    public static void main(String[] args) {
        DnsService service  = new DnsService();
        SpringApplication.run(DnsNotifApplication.class, args);
        try {
//            System.out.println(service.findDnsRecord("tenten.vn"));
            service.addOrUpdateDnsRecord("tenten.vn","137.59.104.97");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addOrUpdateDnsRecord(String domain, String ipAddress) throws IOException {
        Update update = new Update(Name.fromString("itlayer.com.")); // Thay "example.com" bằng tên miền DNS của bạn
        update.add(Name.fromString(domain + "."), Type.A, 86400L, ipAddress); // 86400L là TTL
        Resolver resolver = new SimpleResolver("8.8.8.8"); // Thay "your-dns-server-address" bằng địa chỉ IP của máy chủ DNS của bạn
//    resolver.setTSIGKey(new TSIG("your-tsig-key-name", "your-tsig-key")); // Thay "your-tsig-key-name" và "your-tsig-key" bằng thông tin TSIG key nếu cần
        resolver.setTCP(true);

        try {
            Message response = resolver.send(update);
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String findDnsRecord(String domain) {
        try {
//            Resolver resolver = new SimpleResolver("10.212.9.108");
            Resolver resolver = new SimpleResolver("8.8.8.8");
            resolver.setTCP(true); // (Tuỳ chọn) Sử dụng TCP cho truy vấn DNS

            Name name = Name.fromString(domain); // Chuyển tên miền thành định dạng DNS

            Lookup lookup = new Lookup(name, Type.A);
            lookup.setResolver(resolver);
            lookup.run();

            Record[] records = lookup.getAnswers();
            // Xử lý và trả về kết quả
            if (records != null && records.length > 0) {
                StringBuilder result = new StringBuilder();
                for (Record record : records) {
                    result.append(record).append("\n");
                }
                return result.toString();
            } else {
                return "Không tìm thấy bản ghi DNS cho tên miền " + domain;
            }
        } catch (TextParseException | UnknownHostException e) {
            e.printStackTrace();
            return "Lỗi khi tìm kiếm bản ghi DNS cho tên miền " + domain;
        }
    }
}

