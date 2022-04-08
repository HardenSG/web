package com.example.demo.component;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author websocket服务
 */
@Controller
@ServerEndpoint(value = "/imserver/{email}/{username}")
@Component
public class WebSocketServer {

    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * 记录当前在线连接数
     */
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     */

    @Autowired
    UserService userService = new UserServiceImpl();
    @OnOpen
    public void onOpen(Session session, @PathParam("email") String email, @PathParam("username") String username ) {

        //map里面放用户
        sessionMap.put(email, session);
        log.info("有新用户加入，username={}, 当前在线人数为：{}", username, sessionMap.size());
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        result.put("users", array);
        for (Object key : sessionMap.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", key);
            // {"username", "zhang", "username": "admin"}
            jsonObject.put("username",username);
            array.add(jsonObject);
        }
//        {"users": [{"username": "zhang"},{ "username": "admin"}]}
        sendAllMessage(JSONUtil.toJsonStr(result));  // 后台发送消息给所有的客户端
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("email") String email) {
        sessionMap.remove(email);
        log.info("有一连接关闭，移除username={}的用户session, 当前在线人数为：{}", email, sessionMap.size());
    }

    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     * @param message 客户端发送过来的消息
     */
//    @OnMessage
//    public void onMessage(String message, Session session, @PathParam("username") String username,@PathParam("email") String email , boolean isAll) {
//        log.info("服务端收到用户username={}的消息:{}", username, message);
//        JSONObject obj = JSONUtil.parseObj(message);
//        String toUsername = obj.getStr("to"); // to表示发送给哪个用户，比如 admin
//        String toUserEmail = obj.getStr("email"); // to表示发送给哪个用户，比如 admin
//        String text = obj.getStr("text"); // 发送的消息文本  hello
//        // {"to": "admin", "text": "聊天文本"}
//        Session toSession = sessionMap.get(toUserEmail); // 根据 to用户名来获取 session，再通过session发送消息文本
//        if (toSession != null) {
//            // 服务器端 再把消息组装一下，组装后的消息包含发送人和发送的文本内容
//            // {"from": "zhang", "text": "hello"}
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("from", username);  // from 是 zhang
//            jsonObject.put("text", text);  // text 同上面的text
//            this.sendMessage(jsonObject.toString(), toSession);
//            log.info(jsonObject.toString());
//            log.info("发送给用户userEmail={}，消息：{}", toUserEmail, jsonObject.toString());
//        } else {
//            log.info("发送失败，未找到用户username={}的session", toUsername);
//        }
//    }
    //发给全部人消息
        @OnMessage
    public void onMessage(String message, Session session, @PathParam("username") String username,@PathParam("email") String email) {
            log.info("服务端收到用户username={}的消息:{}", username, message);
            JSONObject obj = JSONUtil.parseObj(message);
            String type = obj.getStr("type");
            String headPicture = obj.getStr("headPicture");
            String text = obj.getStr("text"); // 发送的消息文本  hello
            Date date = new Date(System.currentTimeMillis());
            // {"to": "admin", "text": "聊天文本"}
            JSONObject result = new JSONObject();
            JSONArray array = new JSONArray();
            if(type.equals("1")){
                String toUsername = obj.getStr("to"); // to表示发送给哪个用户，比如 admin
                String toUserEmail = obj.getStr("toUserEmail"); // email表示被发送用户email
                Session toSession = sessionMap.get(toUserEmail); // 根据 to用户名来获取 session，再通过session发送消息文本
                if (toSession != null) {
                // 服务器端 再把消息组装一下，组装后的消息包含发送人和发送的文本内容
                    // {"from": "zhang", "text": "hello"}
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("from", username);  // from 是 zhang
                    jsonObject.put("text", text);  // text 同上面的text
                    jsonObject.put("date", date);  //时间
                    jsonObject.put("email", email);
                    jsonObject.put("headPicture", headPicture);
                    this.sendMessage(jsonObject.toString(), toSession);
                    log.info(jsonObject.toString());
                    log.info("发送给用户userEmail={}，消息：{}", toUserEmail, jsonObject.toString());
                } else {
                    log.info("发送失败，未找到用户username={}的session", toUsername);
                }
            }else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("from", username);
                jsonObject.put("date", date);
                jsonObject.put("email", email);
                jsonObject.put("text", text);
                jsonObject.put("headpicture", headPicture);
                sendAllMessage(jsonObject.toString());  // 后台发送消息给所有的客户端
            }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }

    /**
     * 服务端发送消息给所有客户端
     */
    private void sendAllMessage(String message) {
        try {
            for (Session session : sessionMap.values()) {
                log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }
}
