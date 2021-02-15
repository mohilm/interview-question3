package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.QuestionDetailResponseBean;
import com.example.demo.bean.QuestionRequestBean;
import com.example.demo.bean.QuestionResponseBean;
import com.example.demo.bean.ResponseMessageBean;
import com.example.demo.bean.ResponseMessageDetailBean;
import com.example.demo.constants.ApplicationConstants;
import com.example.demo.jpa.entity.QuestionDetailsEntity;
import com.example.demo.jpa.entity.QuestionEntity;
import com.example.demo.repository.QuestionDetailsRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.service.QuestionService;

@RestController
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private QuestionDetailsRepository questionDetailsRepository;

	@PostMapping
	public @ResponseBody ResponseEntity<QuestionResponseBean> saveQuestions(
			@RequestBody QuestionRequestBean questionRequestBean) {

		QuestionResponseBean response = new QuestionResponseBean();

		response = questionService.addQuestion(questionRequestBean);

		if (response.getMessage().equalsIgnoreCase(ApplicationConstants.RECORD_ALREADY_EXIST)) {

			return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

		}

		else if (response.getMessage().equalsIgnoreCase(ApplicationConstants.EXCEPTION_OCCURED)) {

			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		} else {

			return new ResponseEntity<>(response, HttpStatus.CREATED);

		}

	}

	@GetMapping
	public ResponseEntity<List<QuestionEntity>> getAllQuestions() {
		return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
	}

	@PostMapping("/{questionId}/reply")
	public @ResponseBody ResponseEntity<QuestionDetailResponseBean> saveQuestionDetails(
			@PathVariable("questionId") long questionId, @RequestBody QuestionRequestBean questionRequestBean) {

		QuestionDetailResponseBean response = new QuestionDetailResponseBean();

		Optional<QuestionEntity> questionList = questionRepository.findById(questionId);

		response = questionService.addQuestionDetail(questionRequestBean, questionList);

		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

	@GetMapping(value = "/{questionId}")
	public ResponseEntity<ResponseMessageBean> getQuestionsById(@PathVariable("questionId") Long questionId) {

		Optional<QuestionEntity> questionEntity = questionService.findById(questionId);

		ResponseMessageBean responseMessageBean = new ResponseMessageBean();
		responseMessageBean.setId(questionEntity.get().getId());
		responseMessageBean.setAuthor(questionEntity.get().getAuthor());
		responseMessageBean.setMessage(questionEntity.get().getMessage());

		List<QuestionDetailsEntity> questionDetailsEntity = questionDetailsRepository
				.findByQuestionId(questionEntity.get());

		if (!ObjectUtils.isEmpty(questionDetailsEntity)) {

			List<ResponseMessageDetailBean> responseMessageDetailBeanList = new ArrayList<>();

			for (QuestionDetailsEntity questionDetails : questionDetailsEntity) {

				ResponseMessageDetailBean responseDetailMessageBean = new ResponseMessageDetailBean();

				responseDetailMessageBean.setId(questionDetails.getId());
				responseDetailMessageBean.setAuthor(questionDetails.getAuthor());
				responseDetailMessageBean.setMessage(questionDetails.getMessage());

				responseMessageDetailBeanList.add(responseDetailMessageBean);

				responseMessageBean.setReplies(responseMessageDetailBeanList);

			}

		} else {

			responseMessageBean.setReplies(new ArrayList<ResponseMessageDetailBean>());
		}

		return new ResponseEntity<>(responseMessageBean, HttpStatus.OK);
	}

}
