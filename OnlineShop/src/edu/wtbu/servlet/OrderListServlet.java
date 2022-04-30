package edu.wtbu.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import edu.wtbu.dao.GoodsDao;
import edu.wtbu.dao.OrderDao;
import edu.wtbu.pojo.Goods;
import edu.wtbu.pojo.Order;
import edu.wtbu.pojo.Result;

/**
 * Servlet implementation class OrderListServlet
 */
@WebServlet("/orderList")
public class OrderListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderListServlet() {
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
		Result result = new Result("fail",null);
		if(email==null||email.equals("")) {
			result.setData("登录信息失效，请登录后重试！");
			response.getWriter().append(JSON.toJSONString(result));
			return;
		}
		ArrayList<Order> list = OrderDao.findOrderByEmail(email);
		if(list!=null) {
			result.setFlag("success");
			result.setData(list);
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
