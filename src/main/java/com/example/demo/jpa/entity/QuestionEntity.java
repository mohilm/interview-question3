package com.example.demo.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.boot.context.properties.ConstructorBinding;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Entity
@Table(name = "QUESTIONS")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class QuestionEntity {

	 
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "questionId", cascade = CascadeType.ALL)
	private List<QuestionDetailsEntity> questionDetailsList = new ArrayList<>();
	
	@Id
	@NonNull
	@GeneratedValue
	private Long id;

	@Column(name = "author")
	private String author;

	@Column(name = "message")
	private String message;

	@Column(name = "replies")
	private int replies;

}
