package com.example.mcp;

import com.example.mcp.tool.QueryTool;
import com.example.mcp.tool.SchemaTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

@SpringBootApplication
public class McpApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(McpApplication.class);
		app.setWebApplicationType(WebApplicationType.NONE); // ⭐ 직접 설정!

		// If command-line SQL or natural-language query is provided, run it and exit.
		if (args != null && args.length > 0) {
			for (String a : args) {
				if (a.startsWith("--sql=") || a.startsWith("--nl=")) {
					var context = app.run(args);
					QueryTool queryTool = context.getBean(QueryTool.class);
					String payload;
					if (a.startsWith("--sql=")) {
						payload = a.substring("--sql=".length());
						String result = queryTool.executeSql(payload);
						System.out.println(result);
						System.exit(0);
					} else {
						payload = a.substring("--nl=".length());
						String result = queryTool.queryNaturalLanguage(payload);
						System.out.println(result);
						System.exit(0);
					}
				}
			}
		}

		app.run(args);
	}

	@Bean
	public ToolCallbackProvider tools(
			@Lazy QueryTool queryTool,
			@Lazy SchemaTool schemaTool) {
		return MethodToolCallbackProvider.builder()
				.toolObjects(queryTool, schemaTool)
				.build();
	}
}
