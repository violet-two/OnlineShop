package edu.wtbu.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import edu.wtbu.pojo.Result;
import edu.wtbu.pojo.User;

/**
 * Servlet implementation class PayServlet
 */
@WebServlet("/pay")
public class PayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayServlet() {
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
			result.setData("登录信息失效，请登录后重试！");
			response.getWriter().append(JSON.toJSONString(result));
			return;
		}
		User user = UserDao.findEmail(email);
		Order order = OrderDao.findOrderById(orderId);
		Goods goods = GoodsDao.findGoodsByName(order.getGoodsName());
		float orderPrice = order.getOrderPrice();
		float userMoney = Float.parseFloat(EthService.getBalance(user.getAddress()));
		if(userMoney<orderPrice) {
			result.setData("余额不足，请充值!");
			response.getWriter().append(JSON.toJSONString(result));
			return;
		}
		EthService.transaction(user.getAddress(), goods.getSellerAddress(), user.getPassword(), String.valueOf(orderPrice));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String payTime = sdf.format(new Date());
		String orderAddress = EthService.getOrderAddressByHash(order.getTransactiomHash());
		String payOrder = EthService.payOrder(user.getAddress(), orderAddress, user.getPassword(), payTime);
		if(payOrder==null) {
			result.setData("更新合约失败");
			response.getWriter().append(JSON.toJSONString(result));
			return;
		}
		int update = OrderDao.updatePayOrder(payTime, 1, orderAddress, orderId);
		if(update>0) {
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
