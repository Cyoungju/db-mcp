package com.example.mcp.tool;

import com.example.mcp.service.DatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchemaTool {
    private final DatabaseService databaseService;

    @Tool(description = "DB의 모든 테이블 목록과 구조를 조회합니다.")
    public String getTables() {
        return databaseService.getTables();
    }

    @Tool(description = "특정 테이블의 컬럼 구조를 조회합니다.")
    public String getTableSchema(String tableName){
        return databaseService.getTableSchema(tableName);
    }
}
