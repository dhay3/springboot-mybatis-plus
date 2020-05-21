package com.chz.springbootjson.conf;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * 自定义Json序列化(对象转为json串的格式)
 * 使用时@JsonSerialize(UserSerializer.class)
 */
//public class UserSerializer extends JsonSerializer<User> {
public class UserSerializer extends JsonSerializer<Date> {

//    @Override
//    public void serialize(User value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//        gen.writeStartObject();
//        //序列化gender, json的key变为user-gender
//        gen.writeStringField("user-gender",value.getGender());
//        gen.writeEndObject();
//    }
    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumber(date.getTime()/1000);
        gen.writeEndObject();
    }
}
