package com.example.mcp.tool;


import com.example.mcp.service.DatabaseService;
import com.example.mcp.service.SqlGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueryTool {
    private final SqlGeneratorService sqlGeneratorService;
    private final DatabaseService databaseService;

    @Tool(description = "자연어로 DB를 조회합니다.")
    public String queryNaturalLanguage(String question){
        // 1. 자연어 → SQL 변환
        String sql = sqlGeneratorService.generateSql(question);
        System.out.println("생성된 SQL: " + sql);

        // 2. SQL 실행
        return databaseService.executeSql(sql);
    }

    @Tool(description = "SQL을 직접 실행합니다.")
    public String executeSql(String sql) {
        return databaseService.executeSql(sql);
    }
}
