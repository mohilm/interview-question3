package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.jpa.entity.QuestionDetailsEntity;
import com.example.demo.jpa.entity.QuestionEntity;

@Repository
@Transactional
public interface QuestionDetailsRepository extends JpaRepository<QuestionDetailsEntity, Long> {

	public List<QuestionDetailsEntity> findByQuestionId(QuestionEntity questionId);

}
