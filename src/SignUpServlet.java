

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.servlet.ServletException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SignUpServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
				
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmpassword = request.getParameter("confirmpassword");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		
		boolean status = false;
		
		if(confirmpassword.equals(password))
		{
			UserInfo users = new UserInfo();
			users.setEmail(email);
			users.setFirstName(firstname);
			users.setLastName(lastname);
			try{
				HashPassword.getHashedPassword(password, users);
			}catch(NoSuchAlgorithmException e){
				System.out.println("Exception " + e);
			}catch(NoSuchProviderException e){
				System.out.println("Exception " + e);
			}
			
			if(ConnectionDao.addUser(users) == 1){
				status = true;
				out.println("User Account Created Successfully..");
				RequestDispatcher rd = request.getRequestDispatcher("login.html");  
				rd.forward(request,response);  
			}		
		}		
		
		if(!status){
			out.print("Sorry Incorrect Password or User Already Exist.."); 
			RequestDispatcher rd=request.getRequestDispatcher("signup.html");  
			rd.include(request,response); 
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
