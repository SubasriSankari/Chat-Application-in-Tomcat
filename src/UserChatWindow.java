import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.*;
import javax.websocket.Session;
import javax.websocket.*;
import javax.json.*;
import java.util.*;
import java.io.*;

@ServerEndpoint(value="/userChatWindow/{chatroom}", configurator=UserChatWindowConfigurator.class)
public class UserChatWindow {
	
	static Map<String, Set<Session>> availableChatRooms = (Map<String, Set<Session>>) Collections.synchronizedMap(new HashMap<String, Set<Session>>());
	private String email;
	
	@OnOpen
	public void openConnection(EndpointConfig config, Session userSession, @PathParam("chatroom") String chatRoom){
		userSession.getUserProperties().put("username", config.getUserProperties().get("nickname"));
		userSession.getUserProperties().put("chatTo", chatRoom);
		email = (String)config.getUserProperties().get("email");
		Set<Session> currentlyAvailableUsers = getAllUsersInChat(chatRoom);
		currentlyAvailableUsers.add(userSession);
		ConnectionDao.updateOnlineStatus(email, chatRoom);
	}
	
	@OnMessage
	public void message(String message, Session userSession)throws IOException{
		JsonReader reader = Json.createReader(new StringReader(message));
		JsonObject jsonObject = reader.readObject();
		String chat = jsonObject.getString("message");
		String time_at = jsonObject.getString("time");
		String username = (String) userSession.getUserProperties().get("username");
		String chatTo = (String) userSession.getUserProperties().get("chatTo");
		
		ConnectionDao.updateChatDatabase(username, chatTo, chat, time_at);
		
		Set<Session> currentlyAvailableUsers = getAllUsersInChat(chatTo);
		currentlyAvailableUsers.stream().forEach( handle -> {
			
			try{
				if(ConnectionDao.checkIsToUserChatWithFromUser(email, chatTo)){
					handle.getBasicRemote().sendText(JsonConversion.buildJsonData(username, chat, time_at));
				}
			}catch(Exception e){
				e.printStackTrace();
				System.out.println(e);
			}
			
		});
		/*if(username == null){
			userSession.getUserProperties().put("username", message);
			userSession.getBasicRemote().sendText(buildJsonData("System", "You are connected as "+message));
			System.out.println("System...");
		}else{
			Iterator<Session> iterator = users.iterator();
			while(iterator.hasNext())
				iterator.next().getBasicRemote().sendText(buildJsonData(username, message));
			System.out.println("UserName "+username+"...");
		}*/
	}
	
	@OnClose
	public void closeConnection(Session userSession){
		String chatTo = (String)userSession.getUserProperties().get("chatTo");
		Set<Session> currentlyAvailableUsers = getAllUsersInChat(chatTo);
		currentlyAvailableUsers.remove(userSession);
		ConnectionDao.resetOnlineStatus(email);
	}
	
	public Set<Session> getAllUsersInChat(String chatRoom){
		Set<Session> currChatRoom = availableChatRooms.get(chatRoom);
		if(currChatRoom == null){
			currChatRoom = Collections.synchronizedSet(new HashSet<Session>());
			availableChatRooms.put(chatRoom, currChatRoom);
		}
		return currChatRoom;
	}
	
}