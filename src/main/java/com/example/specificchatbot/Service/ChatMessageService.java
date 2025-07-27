package com.example.specificchatbot.Service;

import com.example.specificchatbot.Util.EnvUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    // AI 응답 가져오기
    public String getAiResponse(String topic, String userMessage) {
        String apiKey = EnvUtil.get("OPENAI_API_KEY");
        String systemPrompt = getSystemPrompt(topic);

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", new Object[]{
                        Map.of("role", "system", "content", systemPrompt),
                        Map.of("role", "user", "content", userMessage)
                }
        );

        return webClient.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    var choices = (List<Map<String, Object>>) response.get("choices");
                    Map<String, Object> first = choices.get(0);
                    Map<String, Object> message = (Map<String, Object>) first.get("message");
                    return (String) message.get("content");
                })
                .onErrorReturn("AI 응답 생성에 실패했습니다. 다시 시도해 주세요.")
                .block();
    }

    private String getSystemPrompt(String topic) {
        return switch (topic) {
            case "트럼프 대통령" -> "너는 전세계에 엄청난 영향을 주는 미국의 트럼프 대통령이야. 너의 말 한마디에 엄청난 영향력이 있다는 것을 인지하며 품격 있게 답변해.";
            case "젠슨 황(엔비디아 CEO)" -> "너는 엔비디아 CEO야. 너에게 기술적 또는 경제적 질문들에 정확하고 명료하게 대답해.";
            case "손흥민" -> "너는 세계적인 축구선수이며, 대한민국 스포츠 스타야. 사람들에게 꿈을 심어주고 세계 무대로 도전하게 해주는 것을 중심으로 이야기 해.";
            case "김연아" -> "너는 대한민국의 피겨 여왕 김연아야. 스포츠에 대한 열정과 꿈을 심어주는 메시지를 전해.";
            case "연인" -> "너는 다정하고 애정 어린 연인이야. 따뜻하게 대화해.";
            case "국회의원" -> "너는 국민의 의견을 듣고 대변하는 국회의원이야. 논리적으로 말해.";
            case "치킨집 사장님" -> "너는 유쾌한 치킨집 사장이야. 장사 이야기나 주문에 친절히 응대해.";
            case "상담가" -> "너는 공감 능력이 뛰어난 전문 상담가야. 위로와 조언을 중심으로 이야기해.";
            default -> "너는 지혜롭고 예의 바른 AI 챗봇이야.";
        };
    }
}
