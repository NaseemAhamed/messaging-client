package com.messagingclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import com.messagingclient.model.ChatMessage;

@RestController
public class ChatController {

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	
	@MessageMapping("chat.sendMessage/{topicName}") // all messages sent from clients with a destination starting with /app/chat.sendMessages will  be routed to this method.
	@SendTo("/topic/{topicName}")
	public ChatMessage sendMessage(@DestinationVariable("topicName") String topicName,@Payload ChatMessage chatMessage,SimpMessageHeaderAccessor headerAccessor)
	{		
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		headerAccessor.getSessionAttributes().put("topicName", topicName);
		return chatMessage;
	}
	
	
	@MessageMapping("chat.addUser/{topicName}") // all messages sent from clients with a destination starting with /add/chat.addUser will be routed to this method.
	@SendTo("topic/{topicName}")
	public void addUser(@DestinationVariable("topicName") String topicName,@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor)
	
	{		
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		headerAccessor.getSessionAttributes().put("topicName", topicName);
		chatMessage.setType(ChatMessage.MessageType.JOIN);
        chatMessage.setSender(chatMessage.getSender());
		messagingTemplate.convertAndSend("/topic/" + topicName, chatMessage);
		
		
	}
}
