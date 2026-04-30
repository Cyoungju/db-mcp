package com.example.mcp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DatabaseService {
    private final JdbcTemplate jdbcTemplate;

    // 테이블 목록 + 구조 조회
    public String getTables(){
        List<Map<String, Object>> tables = jdbcTemplate.queryForList("SHOW TABLES");

        StringBuilder sb = new StringBuilder();

        for(Map<String, Object> table: tables){
            String tableName = table.values().iterator().next().toString();
            //테이블에서 테이블 이름만 꺼내기

            sb.append("테이블: ").append(tableName).append("\n");
            sb.append(getTableSchema(tableName)).append("\n");
        }

        return sb.toString();
    }

    public String getTableSchema(String tableName){
        List<Map<String, Object>> columns = jdbcTemplate.queryForList(
                "SHOW COLUMNS FROM " + tableName
        );

        StringBuilder sb = new StringBuilder();

        for(Map<String, Object> column : columns){
            sb.append(" - ").append(column.get("Field"))
                    .append(" (").append(column.get("Type")).append(")\n");
        }
        return sb.toString();
    }
}
