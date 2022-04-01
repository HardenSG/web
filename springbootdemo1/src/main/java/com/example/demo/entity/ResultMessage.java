package com.example.demo.entity;

import lombok.Data;

/**
 * @author Chu
 * @create 2022-04-01-16:29
 */
@Data
public class ResultMessage {
    private boolean isSystem;
    private String fromName;
    private Object message;


}
