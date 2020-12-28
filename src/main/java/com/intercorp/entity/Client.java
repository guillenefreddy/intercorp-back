package com.intercorp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Client {

	private String name;
	private String lastname;
	private Long age;
	private String birthday;
	private String deathdate;
	
}
