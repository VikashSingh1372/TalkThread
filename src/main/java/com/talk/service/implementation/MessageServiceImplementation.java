package com.talk.service.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.entity.Chat;
import com.talk.entity.Message;
import com.talk.entity.User;
import com.talk.exception.ChatException;
import com.talk.exception.MessageException;
import com.talk.exception.UserException;
import com.talk.repository.MessageRepository;
import com.talk.request.SendmessageRequest;
import com.talk.service.ChatService;
import com.talk.service.MessageService;
import com.talk.service.UserService;


@Service
public class MessageServiceImplementation implements MessageService {

	@Autowired
	private MessageRepository messageRepo;

	@Autowired
	private ChatService chatService;

	@Autowired
	private UserService userService;

	@Override
	public Message sendMessage(SendmessageRequest req) throws UserException, ChatException {

		User user = userService.findUserById(req.getUserId());
		Chat chat = chatService.findChatById(req.getChatid());

		Message message = new Message();
		message.setUser(user);
		message.setChat(chat);
		message.setTimeStamp(LocalDateTime.now());
		message.setContent(req.getContent());

		return message;
	}

	@Override
	public List<Message> getChatsMessages(Integer chatId, User user) throws ChatException, UserException {

		Chat chat = chatService.findChatById(chatId);
		// authentication for messages
		if (!chat.getUsers().contains(user)) {
			throw new UserException("You are not autenticated user to get this message");
		}

		List<Message> message = messageRepo.findByChatId(chatId);

		return message;
	}

	@Override
	public Message findMessageById(Integer messageId) throws MessageException {
		Optional<Message> message = messageRepo.findById(messageId);

		if (message.isPresent()) {
			return message.get();
		}

		throw new MessageException("message does'nt exist with id:" + messageId);
	}

	@Override
	public void deleteMessage(Integer messageId, User user) throws MessageException {

		Message message = findMessageById(messageId);
		if (message.getUser().getId().equals(user.getId())) {
			messageRepo.deleteById(messageId);

		}
		else {
			throw new MessageException("you can't delete other's message");

		}

		throw new MessageException("message not found with id:" + messageId);
	}

}
