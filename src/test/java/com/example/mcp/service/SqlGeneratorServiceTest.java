package com.example.mcp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SqlGeneratorServiceTest {

    @Mock
    private AnthropicChatModel chatModel;

    @Mock
    private DatabaseService databaseService;

    @InjectMocks
    private SqlGeneratorService sqlGeneratorService;

    @Test
    void 자연어_SQL_변환_Mock() {
        // Mock 설정
        when(databaseService.getTables()).thenReturn("테이블: users\n  - id (int)\n  - name (varchar)");

        ChatResponse mockResponse = mock(ChatResponse.class);
        Generation mockGeneration = mock(Generation.class);
        AssistantMessage mockMessage = mock(AssistantMessage.class);

        when(chatModel.call(any(Prompt.class))).thenReturn(mockResponse);
        when(mockResponse.getResult()).thenReturn(mockGeneration);
        when(mockGeneration.getOutput()).thenReturn(mockMessage);
        when(mockMessage.getText()).thenReturn("SELECT * FROM users WHERE MONTH(created_at) = 4");

        // 실행
        String sql = sqlGeneratorService.generateSql("4월에 주문한 목록 보여줘");
        System.out.println("생성된 SQL: " + sql);

        // 검증
        assertThat(sql).contains("SELECT");
    }
}