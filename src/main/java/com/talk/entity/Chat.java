package com.talk.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Chat {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String chat_name;
	private String chat_image;
	private boolean isGroup;
	

	@ManyToMany
	private Set<User> admins = new HashSet<>();
	@ManyToOne
	private User createdBy;
	
	
	@ManyToMany
	private Set<User> users = new HashSet<>();
	
	
	@OneToMany
	private List<Message>  message = new ArrayList<>();
	
}
