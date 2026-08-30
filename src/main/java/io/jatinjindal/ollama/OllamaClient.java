package io.jatinjindal.ollama;

import io.jatinjindal.exception.ArtCopilotException;
import io.jatinjindal.ollama.request.ChatRequest;
import io.jatinjindal.ollama.response.ChatResponse;
import okhttp3.*;
import tools.jackson.databind.ObjectMapper;
import java.io.IOException;

public class OllamaClient {

    private static final String URL = "https://localhost:11434/api/chat";
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public ChatResponse chat(ChatRequest request) {
        String jsonBody = mapper.writeValueAsString(request);
        Request httpRequest = new Request.Builder().url(URL)
                .post(RequestBody.create(
                        jsonBody, MediaType.parse("application/json"))
                ).build();

        try (Response response = client.newCall(httpRequest).execute()) {
            if (!response.isSuccessful()) {
                throw new ArtCopilotException("Ollama returned " +
                        response.code() + " response."
                );
            }

            return mapper.readValue(response.body().string(), ChatResponse.class);
        } catch (IOException e) {
            throw new ArtCopilotException("Failed to connect to Ollama.", e);
        }
    }
}
