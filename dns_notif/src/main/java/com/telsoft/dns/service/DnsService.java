package com.telsoft.dns.service;

import org.springframework.stereotype.Service;
import org.xbill.DNS.*;
import org.xbill.DNS.Record;

import java.io.IOException;
import java.net.UnknownHostException;

@Service
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

    public String findDNS(String domain) throws Exception {
        domain = "84243993902.2.msisdn.sub.cs";
        try {
            Resolver resolver = new SimpleResolver("10.212.9.108");
            resolver.setTCP(true); // (Tuỳ chọn) Sử dụng TCP cho truy vấn DNS

            Name name = Name.fromString(domain + "."); // Chuyển tên miền thành định dạng DNS
            Record rec = Record.newRecord(name, Type.A, DClass.IN); // Tạo một bản ghi A (IPv4) cho tên miền

            Lookup lookup = new Lookup(name, Type.A);
            lookup.setResolver(resolver);
            lookup.run();

            Record[] records = lookup.getAnswers(); // Lấy các bản ghi DNS tìm thấy
            if (records != null) {
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

