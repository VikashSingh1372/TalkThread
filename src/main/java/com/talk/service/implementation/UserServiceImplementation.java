package com.talk.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talk.config.JwtProvider;
import com.talk.entity.User;
import com.talk.exception.UserException;
import com.talk.repository.UserRepository;
import com.talk.request.UpdateUserRequest;
import com.talk.service.UserService;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private JwtProvider jwtProvider;
	
	

	@Override
	public User findUserById(Long id) throws UserException {
		Optional<User> user = userRepo.findById(id);
		if (user.isPresent()) {
			return user.get();
		}

		throw new UserException("user not found with id:" + id);
	}

	@Override
	public List<User> searchUser(String query) {
		return userRepo.searchUser(query);
	}

	@Override
	public User updateUser(Long id, UpdateUserRequest request) throws UserException {
		User user = findUserById(id);

		if (request.getName() != null) {
			user.setName(request.getName());
		}
		if (request.getName() != null) {
			user.setProfile(request.getProfile());
		}

		return userRepo.save(user);
	}

	@Override
	public User findUserProfile(String jwt) throws UserException {
		String email = jwtProvider.getEmailFromToken(jwt);
		if (email == null) {
			throw new UserException("user not exist with this token");
		}

		User user = userRepo.findByEmail(email);
		if (user == null) {
			throw new UserException("user not found with email :" + email);
		}

		return user;
	}

}
