package com.talk.request;

import lombok.Setter;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserRequest {
	private String name;
	private String profile;
}
