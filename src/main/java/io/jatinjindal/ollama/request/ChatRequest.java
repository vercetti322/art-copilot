package io.jatinjindal.ollama.request;

import io.jatinjindal.ollama.common.Message;
import io.jatinjindal.ollama.common.ToolDefinition;

import java.util.List;

public record ChatRequest(
    String model,
    List<Message> messages,
    List<ToolDefinition> tools
) { }
