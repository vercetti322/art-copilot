package io.jatinjindal.ollama.common;

import java.util.List;

public record Message(
    String role,
    String content,
    List<ToolCall> toolCalls
) { }
