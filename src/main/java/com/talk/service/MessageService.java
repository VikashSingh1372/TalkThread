package com.talk.service;

import java.util.List;

import com.talk.entity.Message;
import com.talk.entity.User;
import com.talk.exception.ChatException;
import com.talk.exception.MessageException;
import com.talk.exception.UserException;
import com.talk.request.SendmessageRequest;

public interface MessageService {
	
	
    public	Message sendMessage(SendmessageRequest req) throws UserException, ChatException;

	public List<Message> getChatsMessages(Integer chatId,User user) throws ChatException, UserException;

	public Message findMessageById(Integer messageId) throws MessageException;

	public void deleteMessage(Integer messageId,User user) throws MessageException;

}
