package com.gamventory.service;

import com.gamventory.entity.Member;
import com.gamventory.entity.Question;
import com.gamventory.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Page<Question> getList(int page) {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

        return this.questionRepository.findAll(pageable);
    }

    public Question getQuestion(Long id) {

        Question question = this.questionRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return question;
    }

    public void create(String subject, String content, Member member) {

        Question question = Question.builder()
                .subject(subject)
                .content(content)
                .member(member)
                .build();

        this.questionRepository.save(question);
    }

    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        this.questionRepository.save(question);
    }

    public void delete(Question question) {
        this.questionRepository.delete(question);
    }


}
