package edu.wtbu.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import edu.wtbu.dao.UserDao;
import edu.wtbu.eth.EthService;
import edu.wtbu.pojo.Result;
import edu.wtbu.pojo.User;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
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
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		Result result = new Result("fail",null);
		if(email==null||name==null||password==null||confirmPassword==null||
				email.equals("")||name.equals("")||password.equals("")||confirmPassword.equals("")) {
			result.setData("信息填写不完整");
			response.getWriter().append(JSON.toJSONString(result));
			return;
		}
		User user = UserDao.findEmail(email);
		if(user!=null) {
			result.setData("邮箱已注册");
			response.getWriter().append(JSON.toJSONString(result));
			return;
		}
		if(!password.equals(confirmPassword)) {
			result.setData("密码与确认密码不一致！");
			response.getWriter().append(JSON.toJSONString(result));
			return;
		}
		String address = EthService.addAccount(password);
		int rs = UserDao.addUser(name, email, password, address);
		if(rs>0) {
			result.setFlag("success");
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
