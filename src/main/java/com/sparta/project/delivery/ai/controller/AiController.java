package com.sparta.project.delivery.ai.controller;

import com.sparta.project.delivery.ai.dto.AiRequest;
import com.sparta.project.delivery.ai.dto.AiResponse;
import com.sparta.project.delivery.ai.service.GeminiService;
import com.sparta.project.delivery.auth.UserDetailsImpl;
import com.sparta.project.delivery.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ai")
@Tag(name = "AI API", description = "AI 질문 API 입니다.")
public class AiController {

    private final GeminiService geminiService;

    @PostMapping
    @Operation(summary = "AI 질문 API", description = "AI 에게 질문하고 답변을 받을 수 있는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 자격 증명입니다.", content = @Content),
            @ApiResponse(responseCode = "900", description = "질문은 1자 이상 50자 이하로 작성해야 합니다.", content = @Content),
            @ApiResponse(responseCode = "901", description = "AI 응답 생성에 실패하였습니다.", content = @Content)
    })
    public AiResponse addResponse(
            @AuthenticationPrincipal UserDetailsImpl userDetail,
            @RequestBody AiRequest request
    ) {
        return AiResponse.from(geminiService.getCompletion(request.toDto(UserDto.from(userDetail.getUser()))));
    }
}
