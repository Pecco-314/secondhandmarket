package com.zerone.secondhandmarket.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONMapper {
    private static ObjectMapper mapper=new ObjectMapper();
    public static String writeValueAsString(Object obj){
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Error";
    }
}
