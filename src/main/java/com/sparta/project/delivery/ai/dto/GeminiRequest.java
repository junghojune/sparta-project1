package com.sparta.project.delivery.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Getter
public class GeminiRequest {
    private List<Content> contents;

    public GeminiRequest(String text) {
        Part part = new TextPart(text);
        Content content = new Content(Collections.singletonList(part));
        this.contents = List.of(content);
    }

    interface Part {
    }

    @Getter
    @AllArgsConstructor
    private static class Content {
        private List<Part> parts;
    }

    @Getter
    @AllArgsConstructor
    private static class TextPart implements Part {
        public String text;
    }
}
