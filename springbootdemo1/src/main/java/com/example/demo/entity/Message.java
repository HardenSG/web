package com.example.demo.entity;

import lombok.Data;

/**
 * 浏览器发送给服务器的websocket数据
 * @author
 * @create 2022-04-01-16:26
 */
@Data
public class Message {
    private String toName;
    private String message;
}
