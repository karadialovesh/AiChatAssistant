package com.example.rag.ai.controller;

import com.example.rag.ai.service.QuestionAnswerService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qa")
public class QuestionAnswerController {

    private final QuestionAnswerService questionAnswerService;

    public QuestionAnswerController(QuestionAnswerService questionAnswerService) {
        this.questionAnswerService = questionAnswerService;
    }


    @PostMapping("/ask")
    public String ask(
            @RequestParam String question,
            @RequestParam String userId) {
        return questionAnswerService.answer(question, userId);
    }
}
