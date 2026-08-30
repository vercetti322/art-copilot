package io.jatinjindal.ollama.common;

import tools.jackson.databind.JsonNode;

public record ToolCall(
    String function,
    JsonNode arguments
) { }
