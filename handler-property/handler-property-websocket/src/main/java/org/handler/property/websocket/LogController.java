package org.handler.property.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class LogController {

    private SimpMessagingTemplate template;

    @Autowired
    public LogController(SimpMessagingTemplate template) {
        System.out.println("init");
        this.template = template;
    }
    @MessageMapping("/hello") 
    @SendTo("/topic/greetings") // 订阅
    public Greeting greeting(HelloMessage message) throws Exception {
        System.out.println(message.getName());
        Thread.sleep(3000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }

}
