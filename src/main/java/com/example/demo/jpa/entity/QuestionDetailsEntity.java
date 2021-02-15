package com.example.demo.jpa.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Entity
@Table(name = "QUESTION_DETAILS")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class QuestionDetailsEntity {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "QUESTION_ID", referencedColumnName = "ID")
	@JsonIgnoreProperties("questionDetailsList")
	private QuestionEntity questionId;

	@Column(name = "author")
	private String author;

	@Column(name = "message")
	private String message;

}
