package com.example.demo.config.websocket;


import com.example.demo.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200", "https://fullstack-chatapp-frontend-angular.vercel.app"})
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    TokenService tokenService;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/chat");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat/{roomId}").withSockJS();
    }

    private class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                       WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

            StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.CONNECT);

            accessor.setNativeHeader("Authorization", request.getHeaders().getFirst("Authorization"));

            String accessToken = extractAccessToken(accessor);

            String nickname= tokenService.validateToken(accessToken);
            attributes.put("nickname", nickname);
            return true;
        }

        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Exception exception) {
        }

        private String extractAccessToken(StompHeaderAccessor accessor) {
            List<String> authorizationHeaders = accessor.getNativeHeader("Authorization");

            if (authorizationHeaders != null && !authorizationHeaders.isEmpty()) {
                String authorizationHeader = authorizationHeaders.get(0);

                if (authorizationHeader.startsWith("Bearer ")) {
                    return authorizationHeader.substring(7); // Remova o prefixo "Bearer "
                }
            }
            return null;
        }
    }
}
