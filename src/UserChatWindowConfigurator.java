import javax.servlet.http.*;
import javax.websocket.server.*;
import javax.websocket.*;


public class UserChatWindowConfigurator extends ServerEndpointConfig.Configurator{
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response){
		sec.getUserProperties().put("email", (String)((HttpSession) request.getHttpSession()).getAttribute("email"));
		sec.getUserProperties().put("nickname", (String)((HttpSession) request.getHttpSession()).getAttribute("nickname"));
	}
}