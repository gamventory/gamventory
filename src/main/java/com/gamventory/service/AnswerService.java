package com.gamventory.service;

import com.gamventory.entity.Answer;
import com.gamventory.entity.Member;
import com.gamventory.entity.Question;
import com.gamventory.repository.AnswerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public Answer create(Question question, String content, Member member) {

        Answer answer = Answer.builder()
                .content(content)
                .question(question)
                .member(member)
                .build();

        this.answerRepository.save(answer);

        return answer;
    }

    public Answer getAnswer(Long id) {

        Answer answer = this.answerRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return answer;
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        this.answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }

}
