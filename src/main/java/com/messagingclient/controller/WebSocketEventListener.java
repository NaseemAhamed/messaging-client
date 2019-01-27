package com.messagingclient.controller;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.messagingclient.model.ChatMessage;

@Component
public class WebSocketEventListener {
	private static final Logger logger = Logger.getLogger(WebSocketEventListener.class);
	
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) { // Get the user name and broadcast the user connection to all the connected clients.  
		logger.info("new connection");		         
	}

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) // Get the user name and broadcast the user disconnection to all the connected clients.
	{
		 StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		 

	        String username = (String) headerAccessor.getSessionAttributes().get("username");
	        String topicName = (String)headerAccessor.getSessionAttributes().get("topicName");
	        if(username != null) {
	            
	        	logger.info("User Disconnected : " + username);
	            ChatMessage chatMessage = new ChatMessage();
	            chatMessage.setType(ChatMessage.MessageType.LEAVE);
	            chatMessage.setSender(username);

	            messagingTemplate.convertAndSend("/topic/" + topicName, chatMessage); 
	        }
		
	}
}
