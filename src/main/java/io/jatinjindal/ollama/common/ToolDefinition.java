package io.jatinjindal.ollama.common;

import tools.jackson.databind.JsonNode;

public record ToolDefinition(
    String type,
    Function function
) {
    public record Function(
       String name,
       String description,
       JsonNode parameters
    ) {}
}
