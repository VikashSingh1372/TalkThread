package com.talk.service;

import java.util.List;

import com.talk.entity.User;
import com.talk.exception.UserException;
import com.talk.request.UpdateUserRequest;

public interface UserService {

	public User findUserById(Long id) throws UserException;

	public List<User> searchUser(String query);

	public User updateUser(Long id, UpdateUserRequest request) throws UserException;

	public User findUserProfile(String jwt) throws UserException;

}
