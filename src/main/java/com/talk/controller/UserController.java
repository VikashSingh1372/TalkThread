package com.talk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talk.entity.User;
import com.talk.exception.UserException;
import com.talk.request.UpdateUserRequest;
import com.talk.response.ApiResponse;
import com.talk.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfile(jwt);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/search/{query}")
	public ResponseEntity<List<User>> searchUserHandler(@PathVariable("query") String query) throws UserException {
		List<User> user = userService.searchUser(query);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/update/{id}")
	public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UpdateUserRequest request,  @PathVariable("id") Long id) throws UserException {
   User user = userService.updateUser(id, request);
   
   ApiResponse response = new ApiResponse();
   response.setMessage("updated Successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
