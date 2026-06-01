package com.example.mcp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SqlGeneratorService {
    private final AnthropicChatModel chatModel;
    private final DatabaseService databaseService;

    public String generateSql(String question) {
        // DB 스키마 정보 가져오기
        String schema = databaseService.getTables();

        String prompt = """
                당신은 MySQL 전문가입니다.
                아래 DB 스키마를 참고해서 질문에 맞는 SQL을 작성해주세요.
                SQL만 반환하고 다른 설명은 절대 하지 마세요.
                마크다운 코드블록(```)도 사용하지 마세요.
                
                [DB 스키마]
                %s
                
                [질문]
                %s
                """.formatted(schema, question);

        return chatModel.call(new Prompt(new UserMessage(prompt)))
                .getResult()
                .getOutput()
                .getText();
    }
}
