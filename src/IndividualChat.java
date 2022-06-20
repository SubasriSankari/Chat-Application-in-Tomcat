import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.*;
import javax.websocket.Session;
import javax.websocket.*;
import javax.json.*;
import java.util.*;
import java.io.*;
import java.util.concurrent.CopyOnWriteArraySet;


@ServerEndpoint(value="/individual/{chatTo}", configurator=UserChatWindowConfigurator.class)
public class IndividualChat {
    
    static HashMap < String, String > usersMap = new HashMap < String, String > ();
   
    private static final Set < IndividualChat > connections = new CopyOnWriteArraySet < > ();
    private String chatTo;
    private String username;
	private String email;
    private Session session;
    
    public IndividualChat() {
        chatTo = null;
    }
	
    @OnOpen
    public void openConnection(EndpointConfig config, Session session, @PathParam("chatTo") String user) {
        this.session = session;
        connections.add(this); 
		this.username = (String)config.getUserProperties().get("nickname");
		this.email = (String)config.getUserProperties().get("email");
		this.chatTo = user;
        addUserInMap(session.getId(), username); 
		ConnectionDao.updateOnlineStatus(email, chatTo);
    }
    
    @OnMessage
    public void message(Session session, String message) {
		JsonReader reader = Json.createReader(new StringReader(message));
		JsonObject jsonObject = reader.readObject();
		
		String chat = jsonObject.getString("message");
		String time_at = jsonObject.getString("time");
		String from = getSessionIdOfUser(username); 
		String to = getSessionIdOfUser(chatTo); 
		
		ConnectionDao.updateChatDatabase(username, chatTo, chat, time_at);
		broadcast(JsonConversion.buildJsonData(username, chat, time_at), from, to); 
    }
	
	@OnClose
    public void closeConnection(Session session) {
        connections.remove(this); 
        removeUserInMap(session.getId(), username); 
		ConnectionDao.resetOnlineStatus(email);
    }
	
	private void broadcast(String messageToSend, String fromSessionId, String toSessionId) {  
        for (IndividualChat client: connections) {
            try {
                synchronized(client) {
                    if (client.session.getId().equals(fromSessionId)) {
                        client.session.getBasicRemote().sendText(messageToSend); 
                    }
					if(client.session.getId().equals(toSessionId) && ConnectionDao.checkIsToUserChatWithFromUser(email, chatTo)){
						client.session.getBasicRemote().sendText(messageToSend);
					}
                }
            } catch (IOException e) {
                connections.remove(client);
                try {
                    client.session.close();
                } catch (IOException e1) {}
            }
        }
    }
  
    private String getSessionIdOfUser(String to) {
        if (usersMap.containsValue(to)) {
            for (String key: usersMap.keySet()) {
                if (usersMap.get(key).equals(to)) {
                    return key;
                }
            }
        }
        return null;
    }
    private void addUserInMap(String id, String username) {
        usersMap.put(id, username); 
    }
    private void removeUserInMap(String id, String user) {
        usersMap.remove(id); 
    }
}