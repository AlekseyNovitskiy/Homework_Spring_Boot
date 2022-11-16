package com.skypro.homework_spring_boot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid data entered")
public class InvalidEmployeeRequestExeption extends RuntimeException {
}
