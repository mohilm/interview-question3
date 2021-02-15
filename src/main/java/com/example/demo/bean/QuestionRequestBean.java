package com.example.demo.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRequestBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String author;
	private String message;
 
}
