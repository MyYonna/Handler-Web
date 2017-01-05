package org.handler.property.websocket;

import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

public class WebSocketClient {

	public static void main(String[] args) {
        try {
            /*WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            String uri = "ws://localhost:8081/log/hello/hello/websocket";
            Session session = container.connectToServer(Client.class, new URI(uri));
            char lf = 10; // 这个是换行
            char nl = 0; // 这个是消息结尾的标记，一定要
            StringBuilder sb = new StringBuilder();
            sb.append("SEND").append(lf); // 请求的命令策略
            sb.append("destination:/app/hello").append(lf); // 请求的资源
            sb.append("content-length:14").append(lf).append(lf); // 消息体的长度
            sb.append("{\"name\":\"123\"}").append(nl); // 消息体

            session.getBasicRemote().sendText(sb.toString()); // 发送消息
            Thread.sleep(50000); // 等待一小会
            session.close(); // 关闭连接
            
*/
        	
                try {
                    WebSocketContainer container = ContainerProvider.getWebSocketContainer(); // 获取WebSocket连接器，其中具体实现可以参照websocket-api.jar的源码,Class.forName("org.apache.tomcat.websocket.WsWebSocketContainer");
                    String uri = "https://120.24.209.167:8083/mqtt";
                    Session session = container.connectToServer(Client.class, new URI(uri)); // 连接会话
                    session.getBasicRemote().sendText("123132132131"); // 发送文本消息
                    //session.getBasicRemote().sendText("4564546");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	

}
