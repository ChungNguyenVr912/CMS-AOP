package com.cms_aop.exception_handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public String handleProductException(Exception e){
        System.out.println("Something went wrong! " + e.getMessage());
        return "redirect:/customers";
    }
}
