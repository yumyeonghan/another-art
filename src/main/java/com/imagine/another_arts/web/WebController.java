package com.imagine.another_arts.web;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class WebController {

    @GetMapping("/vue")
    public String vue() {
        return "vue/index";
    }
}
