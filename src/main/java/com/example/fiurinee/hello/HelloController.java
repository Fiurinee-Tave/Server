package com.example.fiurinee.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("/docker")
    @ResponseBody
    public String sayHello() {
        return "ci/cd 테스트 다시 수정";
    }
}
