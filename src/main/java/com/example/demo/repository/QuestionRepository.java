package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.jpa.entity.QuestionEntity;


@Repository
@Transactional
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

	public QuestionEntity findByAuthor(String question);

}
