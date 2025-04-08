package com.project.java.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import com.project.java.model.Comment;

@RestController
public class WebsocketController {
	@MessageMapping("/comments")
	@SendTo("/topic/comments")
	public List<Comment> broadcastNews(@Payload String message) {
		
	  List<Comment> list =  new ArrayList<>();
	  list.add(Comment.builder().description(message).id(null).pComment(null).time(null).build());
	  return list;
	}
}
