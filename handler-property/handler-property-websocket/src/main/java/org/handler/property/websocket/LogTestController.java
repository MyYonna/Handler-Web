package org.handler.property.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("log1")
public class LogTestController {

    @RequestMapping("hello1")
    public Greeting greeting(HelloMessage message) throws Exception {
        System.out.println(message.getName());
        Thread.sleep(3000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }

}
