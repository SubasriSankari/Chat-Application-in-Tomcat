

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javafx.util.Pair;

import javax.servlet.ServletException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginPageServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
				
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		String password = request.getParameter("pass");
				
		ReturnUserResult authResult = ConnectionDao.authenticateUser(email, password);
		
		if(authResult.result){
			
			HttpSession session=request.getSession();  
			session.setAttribute("email", email); 
			session.setAttribute("nickname", authResult.nickname);

			RequestDispatcher rd=request.getRequestDispatcher("ChooseChatWindow.jsp");  
			rd.forward(request,response);  
		}else{
			out.print("Sorry Incorrect username or password");
			RequestDispatcher rd=request.getRequestDispatcher("login.html");  
			rd.include(request,response);  
		}	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
