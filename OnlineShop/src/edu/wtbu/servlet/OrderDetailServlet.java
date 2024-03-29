package edu.wtbu.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import edu.wtbu.dao.GoodsDao;
import edu.wtbu.dao.OrderDao;
import edu.wtbu.dao.UserDao;
import edu.wtbu.eth.EthService;
import edu.wtbu.pojo.Goods;
import edu.wtbu.pojo.Order;
import edu.wtbu.pojo.OrderDetail;
import edu.wtbu.pojo.Result;
import edu.wtbu.pojo.User;

/**
 * Servlet implementation class OrderDetailServlet
 */
@WebServlet("/orderDetail")
public class OrderDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderDetailServlet() {
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
		int orderId = 0;
		try {
			orderId = Integer.parseInt(request.getParameter("orderId"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Result result = new Result("fail",null);
		if(email==null||email.equals("")) {
			result.setData("��¼��ϢʧЧ�����¼�����ԣ�");
			response.getWriter().append(JSON.toJSONString(result));
			return;
		}
		User user = UserDao.findEmail(email);
		Order order = OrderDao.findOrderById(orderId);
		Goods goods = GoodsDao.findGoodsByName(order.getGoodsName());
		OrderDetail orderDetail = EthService.getOrderInfo(user.getAddress(), order.getOrderAddress());
		if(orderDetail!=null) {
			result.setFlag("success");
			result.setData(orderDetail);
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
