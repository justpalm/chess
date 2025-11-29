package websocket;


import com.google.gson.Gson;
import exception.ResponseException;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsCloseHandler;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsConnectHandler;
import io.javalin.websocket.WsMessageContext;
import io.javalin.websocket.WsMessageHandler;
import org.eclipse.jetty.websocket.api.Session;
import org.jetbrains.annotations.NotNull;
import webSocketMessages.Action;
import webSocketMessages.Notification;



public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler{
    @Override
    public void handleClose(@NotNull WsCloseContext wsCloseContext) throws Exception {

    }

    @Override
    public void handleMessage(@NotNull WsMessageContext wsMessageContext) throws Exception {

    }

    @Override
    public void handleConnect(@NotNull WsConnectContext wsConnectContext) throws Exception {

    }
}
