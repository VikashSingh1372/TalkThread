package com.talk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.talk.entity.Message;
import com.talk.entity.User;
import com.talk.exception.ChatException;
import com.talk.exception.MessageException;
import com.talk.exception.UserException;
import com.talk.request.SendmessageRequest;
import com.talk.service.MessageService;
import com.talk.service.UserService;

@RestController
@RequestMapping("/api/message")
public class MessageController {
	
	@Autowired
	private  MessageService messageService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/send")
	public ResponseEntity<Message> sendChatHandler(@RequestBody SendmessageRequest req,@RequestHeader("Authorization") String jwt) throws UserException, ChatException{
		  User user = userService.findUserProfile(jwt);
		  req.setUserId(user.getId());
		  
		   Message sendMessage = messageService.sendMessage(req);
		
		return new ResponseEntity<>(sendMessage,HttpStatus.CREATED);
	}
	
	@GetMapping("/chat/{chatId}")
	public ResponseEntity<List<Message>> getChatHandler(@PathVariable Integer chatId,@RequestHeader("Authorization") String jwt) throws UserException, ChatException{
		  User user = userService.findUserProfile(jwt);
		  
		   List<Message> Message = messageService.getChatsMessages(chatId, user);
		
		return new ResponseEntity<>(Message,HttpStatus.CREATED);
	}
	
	@GetMapping("/{messageId}")
	public ResponseEntity<Message> getMessageByIdHandler(@PathVariable Integer messageId,@RequestHeader("Authorization") String jwt) throws UserException, MessageException{
		  
		 Message message= messageService.findMessageById(messageId);
		
		return new ResponseEntity<>(message,HttpStatus.OK);
	}
	@DeleteMapping("/delete/{messageId}")
	public ResponseEntity<String> deleteMessageHandler(@PathVariable Integer messageId,@RequestHeader("Authorization") String jwt) throws UserException, MessageException{
		  User user = userService.findUserProfile(jwt);
		  
		  messageService.deleteMessage(messageId, user);
		
		return new ResponseEntity<String>(" Message Deleted Successfully",HttpStatus.OK);
	}
}
