package com.jashu.shopping_website.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    LocalDateTime localDateTime;
    int status;
    String error;
    String message;
    String path;
}
