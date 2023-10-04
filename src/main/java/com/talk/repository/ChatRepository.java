package com.talk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.talk.entity.Chat;
import com.talk.entity.User;

public interface ChatRepository  extends JpaRepository<Chat, Integer> {

	
	
	@Query("Select c from Chat c join  c.users u where u.id =:userId ")
	public List<Chat> findChatByUserId(@Param("userId") Long userId);

	
	
	@Query("select  c from Chat c where c.isGroup =false AND :user Member of c.users And :reqUser Member of c.users")
	public Chat findSingleChatByUserId(@Param("user") User user, @Param("reqUser") User reqUser);
}
