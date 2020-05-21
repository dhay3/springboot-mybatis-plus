package com.chz.springbootjson.localdatetimeformat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
public class TestLocalDateTimeJsonController {
    @GetMapping("/get")
    public LocalDateTimeJson get(){
        return new LocalDateTimeJson(LocalDateTime.now(),new Date());
    }
}
