package com.example.demo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.bean.QuestionDetailResponseBean;
import com.example.demo.bean.QuestionRequestBean;
import com.example.demo.bean.QuestionResponseBean;
import com.example.demo.controllers.QuestionController;
import com.example.demo.jpa.entity.QuestionEntity;
import com.example.demo.repository.QuestionDetailsRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.service.QuestionService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = QuestionController.class)
@WithMockUser
public class QuestionsControllerTest{

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private QuestionService studentService;

	@MockBean
	private QuestionRepository questionRepo;
	
	@MockBean
	private QuestionDetailsRepository questionDetailsRepo;
	
	String exampleQuestionJson = "{\"author\":\"Mohil\",\"message\":\"Mohil1\"}";


	@Test
	public void getQuestions() throws Exception {
		List<QuestionEntity> qe = new ArrayList<QuestionEntity>();
		qe.add(setQuestionEntity());
		Mockito.when(questionRepo.findAll()).thenReturn(qe);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/questions").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse().getContentAsString());
		String expected = "[{\"id\":1,\"author\":\"Mohil\",\"message\":\"Question with ID1\",\"replies\":1}]";

		// {"id":"Course1","name":"Spring","description":"10 Steps, 25 Examples and 10K Students","steps":["Learn Maven","Import Project","First Example","Second Example"]}

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
	

	
	private QuestionEntity setQuestionEntity() {
		QuestionEntity question = new QuestionEntity();
		question.setId(1L);
		question.setAuthor("Mohil");
		question.setMessage("Question with ID1");
		question.setReplies(1);
		return question;
		
	}



	@Test
	public void getQuestionById() throws Exception {
		QuestionEntity question = new QuestionEntity();
		question.setId(1L);
		question.setAuthor("Mohil");
		question.setMessage("Question with ID1");
		question.setReplies(1);
		
		Optional<QuestionEntity> mockQuestion = Optional.of(question);
		Mockito.when(
				studentService.findById(Mockito.anyLong())).thenReturn(mockQuestion);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/questions/1").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "{\"id\":1,\"author\":\"Mohil\",\"message\":\"Question with ID1\",\"replies\":[]}";

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
	 
	 
	@Test
	public void addQuestion() throws Exception {
		QuestionResponseBean question = new QuestionResponseBean();
		question.setId(1L);
		question.setAuthor("Mohil");
		question.setMessage("Question with ID1");
		question.setReplies(1);
		// studentService.addCourse to respond back with mockCourse
		Mockito.when(
				studentService.addQuestion(Mockito.any(QuestionRequestBean.class))).thenReturn(question);

		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/questions")
				.accept(MediaType.APPLICATION_JSON).content(exampleQuestionJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		

	}
	
	@Test
	public void addQuestionDetails() throws Exception {
		QuestionDetailResponseBean question = new QuestionDetailResponseBean();
		question.setId(1L);
		question.setAuthor("Mohil");
		question.setMessage("Question with ID1");
		// studentService.addCourse to respond back with mockCourse
		Mockito.when(
				studentService.addQuestionDetail(Mockito.any(QuestionRequestBean.class), Mockito.anyObject())).thenReturn(question);

		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/questions/1/reply")
				.accept(MediaType.APPLICATION_JSON).content(exampleQuestionJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());


	}
	
	
}