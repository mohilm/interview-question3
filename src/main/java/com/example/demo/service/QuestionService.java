package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.example.demo.bean.QuestionRequestBean;
import com.example.demo.bean.QuestionDetailResponseBean;
import com.example.demo.bean.QuestionResponseBean;
import com.example.demo.constants.ApplicationConstants;
import com.example.demo.jpa.entity.QuestionDetailsEntity;
import com.example.demo.jpa.entity.QuestionEntity;
import com.example.demo.repository.QuestionDetailsRepository;
import com.example.demo.repository.QuestionRepository;

@Service
@Transactional
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private QuestionDetailsRepository questionDetailsRepository;

	public QuestionResponseBean addQuestion(QuestionRequestBean questionRequestBean) {
		QuestionEntity questionEntity = questionRepository.findByAuthor(questionRequestBean.getAuthor());
		QuestionResponseBean response = null;

		if (ObjectUtils.isEmpty(questionEntity)) {

			try {
				questionEntity = new QuestionEntity();

				questionEntity.setAuthor(questionRequestBean.getAuthor());
				questionEntity.setMessage(questionRequestBean.getMessage());

				questionEntity = questionRepository.save(questionEntity);
			} catch (Exception e) {

				response = new QuestionResponseBean();
				 response.setMessage(ApplicationConstants.EXCEPTION_OCCURED);
			}
			response = new QuestionResponseBean();
			response.setId(questionEntity.getId());
			response.setAuthor(questionEntity.getAuthor());
			response.setMessage(questionEntity.getMessage());
			response.setReplies(questionEntity.getReplies());
		} else {
			
			  response = new QuestionResponseBean();
			  response.setMessage(ApplicationConstants.RECORD_ALREADY_EXIST);
			 
		}
		return response;
	}

	public QuestionDetailResponseBean addQuestionDetail(QuestionRequestBean questionRequestBean,
			Optional<QuestionEntity> questionList) {
		Optional<QuestionEntity> questionEntity = questionRepository.findById(questionList.get().getId());
		QuestionDetailResponseBean response = null;
		QuestionDetailsEntity questionDetails = new QuestionDetailsEntity();

		if (!ObjectUtils.isEmpty(questionEntity)) {

			try {

				questionDetails.setQuestionId(questionEntity.get());
				questionDetails.setAuthor(questionRequestBean.getAuthor());
				questionDetails.setMessage(questionRequestBean.getMessage());

				questionDetails = questionDetailsRepository.save(questionDetails);
				
				int count = questionEntity.get().getReplies();
				
				questionEntity.get().setReplies(count+1);
				
				questionRepository.save(questionEntity.get());  //updated questions table with reply count
			} catch (Exception e) {

				response = new QuestionDetailResponseBean();
				// response.setResponseCode(HttpStatus.ACCEPTED.toString());
				// response.setResponseMessage("exception occured!!");
			}
			response = new QuestionDetailResponseBean();
			response.setId(questionDetails.getId());
			response.setQuestionId(questionDetails.getQuestionId().getId());
			response.setAuthor(questionDetails.getAuthor());
			response.setMessage(questionDetails.getMessage());

		} else {
			/*
			 * response = new ResponseMessageBean();
			 * response.setResponseCode(HttpStatus.ACCEPTED.toString());
			 * response.setResponseMessage(ApplicationConstants.RECORD_ALREADY_EXIST);
			 */
		}
		return response;
	}

	public Optional<QuestionEntity> findById(Long questionId) {
		// TODO Auto-generated method stub
		Optional<QuestionEntity> questionEntity = questionRepository.findById(questionId);
		return questionEntity;
	}
}
