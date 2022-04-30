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
import edu.wtbu.pojo.Result;
import edu.wtbu.pojo.User;
import sun.java2d.pipe.SpanShapeRenderer.Simple;

/**
 * Servlet implementation class BuyServlet
 */
@WebServlet("/buy")
public class BuyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyServlet() {
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
		int goodsId = 0;
		try {
			goodsId = Integer.parseInt(request.getParameter("goodsId"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		int goodsNum = 0;
		try {
			goodsNum = Integer.parseInt(request.getParameter("goodsNum"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		Result result = new Result("fail",null);
		if(email==null||email.equals("")) {
			result.setData("µÇÂ¼ÐÅÏ¢Ê§Ð§£¬ÇëµÇÂ¼ºóÖØÊÔ£¡");
			response.getWriter().append(JSON.toJSONString(result));
			return;
		}
		User user = UserDao.findEmail(email);
		Goods goods = GoodsDao.findGoodsById(goodsId);
		float goodsPrice = goods.getPrice();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String buyTime = sdf.format(new Date());
		String transactionHash = EthService.getTranscationHash(String.valueOf(goodsPrice), goodsNum, String.valueOf(goodsPrice*goodsNum), user.getAddress(), goods.getSellerAddress(), buyTime);
		if(transactionHash==null) {
			result.setData("¹ºÂòÊ§°Ü");
			response.getWriter().append(JSON.toJSONString(result));
			return;
		}
		int updateResult = OrderDao.addOrder(goods.getImgUrl(), goods.getName(), goodsPrice*goodsNum, buyTime, 0, transactionHash, user.getEmail());
		if(updateResult>0) {
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
