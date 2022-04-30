package edu.wtbu.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import edu.wtbu.dao.UserDao;
import edu.wtbu.pojo.Result;
import edu.wtbu.pojo.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf8");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		Result result = new Result("fail",null);
		if(email==null||password==null||
				email.equals("")||password.equals("")) {
			result.setData("–≈œ¢ÃÓ–¥≤ªÕÍ’˚");
			response.getWriter().append(JSON.toJSONString(result));
			return;
		}
		User user = UserDao.findEmailAndPassword(email, password);
		if(user!=null) {
			result.setFlag("success");
		}else {
			user = UserDao.findEmail(email);
			if(user!=null) {
				result.setData("√‹¬Î¥ÌŒÛ");
			}else {
				result.setData("” œ‰Œ¥◊¢≤·");
			}
		}
		response.getWriter().append(JSON.toJSONString(result));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
