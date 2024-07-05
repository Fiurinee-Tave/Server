package com.example.fiurinee.domain.inputMessage.dto;

import jakarta.validation.constraints.Size;

public record RequestMentDto (
        @Size(max = 50)
        String ment
){
}
