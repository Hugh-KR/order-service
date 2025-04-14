package com.onseju.orderservice.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// 하트비트 설정 (60초 간격)
		config.enableSimpleBroker("/topic")
				.setHeartbeatValue(new long[]{60000, 60000});

		// 클라이언트에서 보내는 메시지 주제는 "/app"으로 시작
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws")  // WebSocket 엔드포인트 설정
				.setAllowedOrigins(
						"http://onseju.store",
						"https://onseju.vercel.app",
						"http://localhost:3000")  // React 앱의 주소
				.withSockJS()  // SockJS 지원 추가
				// SockJS 재연결 설정 추가
                .setDisconnectDelay(30 * 1000)  // 30초
				.setClientLibraryUrl("https://cdn.jsdelivr.net/npm/sockjs-client@1.5.0/dist/sockjs.min.js");
	}
}
