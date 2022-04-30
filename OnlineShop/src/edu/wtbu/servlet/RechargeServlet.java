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
 * Servlet implementation class RechargeServlet
 */
@WebServlet("/recharge")
public class RechargeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RechargeServlet() {
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
		String amount = request.getParameter("amount");
		Result result = new Result("fail",null);
		if(email==null||email.equals("")) {
			result.setData("登录信息失效，请登录后重试！");
			response.getWriter().append(JSON.toJSONString(result));
			return;
		}
		User user = UserDao.findEmail(email);
		String userAddress = user.getAddress();
		String hash = EthService.recharge(userAddress, amount);
		if(hash!=null) {
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
