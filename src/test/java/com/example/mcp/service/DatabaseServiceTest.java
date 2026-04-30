package com.example.mcp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DatabaseServiceTest {
    @Autowired
    private DatabaseService databaseService;

    @Test
    void 테이블_목록_조회() {
        String result = databaseService.getTables();
        System.out.println("=== 테이블 구조 ===");
        System.out.println(result);
    }

    @Test
    void 특정_테이블_구조_조회() {
        String result = databaseService.getTableSchema("users");
        System.out.println("=== users 테이블 구조 ===");
        System.out.println(result);
    }
}