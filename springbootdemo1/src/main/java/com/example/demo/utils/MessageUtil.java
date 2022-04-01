package com.example.demo.utils;

import com.example.demo.entity.ResultMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Chu
 * @create 2022-04-01-16:35
 */
public class MessageUtil {
    public static String getMessage(boolean isSytemMessage,String fromName,Object message){
        ResultMessage result = new ResultMessage();
        result.setSystem(isSytemMessage);
        result.setMessage(message);
        if(fromName !=null){
            result.setFromName(fromName);
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
