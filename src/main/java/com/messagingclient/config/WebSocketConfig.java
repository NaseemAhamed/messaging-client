package com.messagingclient.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // Enables websocket server
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
	
	public void registerStompEndpoints(StompEndpointRegistry registry) // STOMP - Defines the format and rules for data exchange. 
	{
		registry.addEndpoint("/ws").withSockJS(); 
		// Registers a websocket endpoint that the clients will use to connect to our websocket server.
		// SocketJS is used to enable fallback options for browsers that do not support websocket.		
	}
	
	public void configureMessageBroker(MessageBrokerRegistry registry) // Messagebroker to route messages from one client to another.
	{
			registry.setApplicationDestinationPrefixes("/app"); // Messages whose destination starts with /app should be routed to message-handling methods.
			registry.enableSimpleBroker("/topic"); 
			// Messages whose destination starts with /topic should be routed to the message broker.
			//Message Broker broadcasts messages to all the connected clients who are subscribed to this topic.
	}
		
}
