package com.talk.request;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GroupChatRequest {
	
	
	private List<Long> userId;
	private String chat_name;
	private String chat_image;

}
