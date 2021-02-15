package com.example.demo.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class QuestionDetailResponseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private long questionId;
	private String author;
	private String message;

}
