package com.jonas.testebeer.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ToJsonConverter {
    
    public static String asJsonString(Object javaClass){
       try {
           ObjectMapper objectMapper = new ObjectMapper();
           objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
           objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
           objectMapper.registerModules(new JavaTimeModule());

           return objectMapper.writeValueAsString(javaClass);
       } catch(Exception e){
           throw new RuntimeException(e);
       }
    }

}