package com.example.demo.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMessageDetailBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String author;
	private String message;

}
