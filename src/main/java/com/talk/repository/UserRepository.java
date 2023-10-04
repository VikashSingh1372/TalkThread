package com.talk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.talk.entity.User;

public interface UserRepository  extends JpaRepository<User,Long> {

	 User  findByEmail(String email);
	 
	 
	 @Query("Select  s from User s where s.name Like %:query% or s.email Like %:query%")
	 public List<User> searchUser(@Param("query") String query);

}
