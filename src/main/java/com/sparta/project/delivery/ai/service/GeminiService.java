package com.sparta.project.delivery.ai.service;

import com.sparta.project.delivery.ai.entity.Ai;
import com.sparta.project.delivery.ai.config.GeminiInterface;
import com.sparta.project.delivery.ai.dto.AiDto;
import com.sparta.project.delivery.ai.dto.GeminiRequest;
import com.sparta.project.delivery.ai.dto.GeminiResponse;
import com.sparta.project.delivery.ai.repository.AiRepository;
import com.sparta.project.delivery.common.exception.CustomException;
import com.sparta.project.delivery.common.type.UserRoleEnum;
import com.sparta.project.delivery.user.User;
import com.sparta.project.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.sparta.project.delivery.common.exception.DeliveryError.*;

@Service
@RequiredArgsConstructor
public class GeminiService {

    private final GeminiInterface geminiInterface;
    private final AiRepository aiRepository;
    private final UserRepository userRepository;

    @Value("${gemini.api.model}")
    private String apiModel;

    private GeminiResponse getCompletion(GeminiRequest request) {
        return geminiInterface.getCompletion(apiModel, request);
    }

    public AiDto getCompletion(AiDto aiDto) {
        User user = userRepository.findById(aiDto.userDto().userId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 사용자 권한 체크 (OWNER 만 사용 가능)
        if (user.getRole() != UserRoleEnum.OWNER) {
            throw new CustomException(AUTH_INVALID_CREDENTIALS);
        }

        // 질문 글자 수 체크
        if (aiDto.question() == null || aiDto.question().trim().length() > 50 || aiDto.question().trim().isEmpty()) {
            throw new CustomException(AI_QUESTION_INVALID);
        }

        String answer = "";

        try {
            GeminiRequest geminiRequest = new GeminiRequest(aiDto.question() + " 답변은 최대한 간결하게 50자 이하로");
            GeminiResponse response = getCompletion(geminiRequest);

            answer = response.getCandidates()
                    .stream()
                    .findFirst().flatMap(candidate -> candidate.getContent().getParts()
                            .stream()
                            .findFirst()
                            .map(GeminiResponse.TextPart::getText))
                    .orElse(null);
        } catch (Exception e) {
            // 외부 API 에러시 자체 에러로 변경
            throw new CustomException(AI_COMPLETION_FAILED);
        }

        Ai ai = aiRepository.save(aiDto.toEntity(user, answer));

        return AiDto.from(ai);
    }
}
