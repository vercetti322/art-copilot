package io.jatinjindal.ollama.response;

import io.jatinjindal.ollama.common.Message;

public record ChatResponse(
    Message message,
    boolean done
) { }
