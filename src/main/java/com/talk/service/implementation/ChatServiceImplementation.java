package com.talk.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.entity.Chat;
import com.talk.entity.User;
import com.talk.exception.ChatException;
import com.talk.exception.UserException;
import com.talk.repository.ChatRepository;
import com.talk.request.GroupChatRequest;
import com.talk.service.ChatService;
import com.talk.service.UserService;


@Service
public class ChatServiceImplementation implements ChatService {

	@Autowired
	private UserService userService;

	@Autowired
	private ChatRepository chatRepo;

	@Override
	public Chat createChat(User requser, Long userId2) throws UserException {

		User user2 = userService.findUserById(userId2);
		Chat isChatExist = chatRepo.findSingleChatByUserId(user2, requser);

		if (isChatExist != null) {
			return isChatExist;
		}

		Chat chat = new Chat();
		chat.setGroup(false);
		chat.setCreatedBy(requser);
		chat.getUsers().add(user2);
		chat.getUsers().add(requser);
		return chat;
	}

	@Override
	public Chat findChatById(Integer chatid) throws ChatException {

		Optional<Chat> chat = chatRepo.findById(chatid);

		if (chat.isPresent()) {
			return chat.get();
		}
		throw new ChatException("Chat is not found with id :" + chatid);
	}

	@Override
	public List<Chat> findAllChatByUserId(Long userId) throws UserException {
		User user = userService.findUserById(userId);

		List<Chat> chat = chatRepo.findChatByUserId(user.getId());
		return chat;
	}

	@Override
	public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException {

		Chat group = new Chat();
		group.setChat_image(req.getChat_image());
		group.setChat_name(req.getChat_name());
		group.setCreatedBy(reqUser);
		group.setGroup(true);
		group.getAdmins().add(reqUser);
		for (Long userId : req.getUserId()) {
			User user = userService.findUserById(userId);
			group.getUsers().add(user);
		}
		return group;
	}

	@Override
	public Chat addUserToGroup(Long userid, Integer chatid, User reqUser) throws UserException, ChatException {

		Optional<Chat> opt = chatRepo.findById(chatid);
		User user = userService.findUserById(userid);

		if (opt.isPresent()) {
			Chat chat = opt.get();
			if (chat.getAdmins().contains(reqUser)) {
				chat.getUsers().add(user);
				return chatRepo.save(chat);
			} else {
				throw new UserException("user not have access of admin");
			}
		}
		throw new ChatException("chat not found with chatId:" + chatid);
	}

	@Override
	public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws ChatException, UserException {
		// TODO Auto-generated method stub

		Optional<Chat> opt = chatRepo.findById(chatId);

		if (opt.isPresent()) {
			Chat chat = opt.get();
			if (chat.getUsers().contains(reqUser)) {
				chat.setChat_name(groupName);
				return chatRepo.save(chat);
			} else {
				throw new UserException("user is not present in group");

			}

		}
		throw new ChatException("chat not found with chatId:" + chatId);

	}

	@Override
	public Chat removeFromGroup(Integer chatid, Long userid, User reqUser) throws UserException, ChatException {
		// TODO Auto-generated method stub

		Optional<Chat> opt = chatRepo.findById(chatid);
		User user = userService.findUserById(userid);

		if (opt.isPresent()) {
			Chat chat = opt.get();
			if (chat.getAdmins().contains(reqUser)) {
				chat.getUsers().remove(user);
				return chatRepo.save(chat);
			} else if (chat.getUsers().contains(reqUser)) {
				if (user.getId().equals(reqUser.getId())) {
					chat.getUsers().remove(user);
					return chatRepo.save(chat);

				}
				else {
					throw new UserException("user does not exist in group");

				}
			}
			else {
				throw new UserException("user not have access of admin");

			}
		}
		throw new ChatException("chat not found with chatId:" + chatid);
	}

	@Override
	public void deleteChat(Integer chatid, User user) throws ChatException, UserException {
		
		  Optional<Chat> chat = chatRepo.findById(chatid);
		  
		  if(chat.isPresent()) {
			  
			  Chat deleteChat = chat.get();
			  chatRepo.deleteById(deleteChat.getId());
		  }
		  throw new ChatException("chat is not present with id :" +chatid);
	}

}
