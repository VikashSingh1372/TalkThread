package com.talk.request;



import lombok.Getter;
import lombok.Setter;


@Getter@Setter
public class SendmessageRequest {
	
	
	private Long userId;
	private Integer chatid;
	
	private String content;

}
