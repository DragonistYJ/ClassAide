package love.dragonist.classaide.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/teacherMonitor")
@Component
public class TeacherSocket {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<TeacherSocket> sockets = new CopyOnWriteArraySet<TeacherSocket>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private Logger logger = LoggerFactory.getLogger(TeacherSocket.class);

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        sockets.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        logger.info("有新连接加入！当前在线人数为" + getOnlineCount());
        try {
            sendMessage("success");
        } catch (IOException e) {
            logger.error("IO异常");
        }
    }

    @OnClose
    public void onClose() {
        sockets.remove(this);
        subOnlineCount();
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("发生错误");
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发消息
     *
     * @param message
     * @throws IOException
     */
    public static void sendInfo(String message) throws IOException {
        for (TeacherSocket item : sockets) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        TeacherSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        TeacherSocket.onlineCount--;
    }
}
