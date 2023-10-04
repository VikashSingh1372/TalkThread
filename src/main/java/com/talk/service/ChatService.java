package com.talk.service;

import java.util.List;

import com.talk.entity.Chat;
import com.talk.entity.User;
import com.talk.exception.ChatException;
import com.talk.exception.UserException;
import com.talk.request.GroupChatRequest;

public interface ChatService {
	
	public Chat createChat(User requser, Long userId2) throws UserException;

		public Chat findChatById(Integer chatid) throws ChatException;

		public List<Chat> findAllChatByUserId (Long userId) throws UserException;

		public Chat createGroup (GroupChatRequest req, User reqUser) throws UserException;

		public Chat addUserToGroup (Long userid, Integer chatid,User reqUser) throws UserException, ChatException;

		public Chat renameGroup (Integer chatId, String groupName, User reqUser) throws ChatException, UserException;

		public Chat removeFromGroup (Integer chatid, Long userid, User reqUser) throws UserException, ChatException;

		public void deleteChat (Integer chatid, User userId) throws ChatException, UserException;

}
