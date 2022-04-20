package com.bartek.sulima.soft.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class TokenUtil {

    private static final String USER_ID_NODE = "usrId";

    private final ObjectMapper objectMapper;

    public String getUserIdFromToken(String tokenHeader) throws JsonProcessingException {
        final String token = tokenHeader.replace("Bearer ", "");
        final String[] chunks = token.split("\\.");
        final String decodedPayloadJson = new String(Base64.getDecoder().decode(chunks[1]));
        final JsonNode jsonNode= objectMapper.readTree(decodedPayloadJson);

        return jsonNode.get(USER_ID_NODE).asText();
    }

}
