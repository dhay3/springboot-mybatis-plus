package com.chz.springbootjson.localdatetimeformat;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 同时对Date和LocalDateTime生效
 */
@Configuration
public class LocalDateTimeSerializerConfig {
    @Value("${spring.jackson.date-format}")
    private String pattern;
    @Value("${spring.jackson.time-zone}")
    private String timeZone;
    @Bean
    public  LocalDateTimeSerializer serializer(){
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern));
    }
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer builderCustomizer(){
        return mapperBuilder ->{
            //同理LocalDate, LocalTime
            mapperBuilder.serializerByType(LocalDateTime.class,serializer());
//            mapperBuilder.timeZone(TimeZone.getTimeZone(ZoneId.of("GMT+8")));
            mapperBuilder.timeZone(timeZone);
        };
    }
}
