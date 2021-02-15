package com.example.demo.bean;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMessageBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String author;
	private String message;
	private List<ResponseMessageDetailBean> replies;

}
