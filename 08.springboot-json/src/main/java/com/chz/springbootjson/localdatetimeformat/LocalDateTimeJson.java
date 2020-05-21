package com.chz.springbootjson.localdatetimeformat;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class LocalDateTimeJson {
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime dateTime;
    private Date date;

    public LocalDateTimeJson(LocalDateTime dateTime, Date date) {
        this.dateTime = dateTime;
        this.date = date;
    }
}
