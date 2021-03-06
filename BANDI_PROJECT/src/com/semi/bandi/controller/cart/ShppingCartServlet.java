package com.semi.bandi.controller.cart;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.semi.bandi.model.service.cart.CashService;
import com.semi.bandi.model.vo.User;

import com.semi.bandi.model.service.cart.CashService;
import com.semi.bandi.model.vo.Cart;
import com.semi.bandi.model.vo.User;

// 장바구니

@WebServlet("/cart.ct")
public class ShppingCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public ShppingCartServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		User user = (User)session.getAttribute("user");
		
		CashService cService = new CashService();

		ArrayList<Cart> result = null;
		
		String page = "";

		if (user == null) {		// 로그인 정보 확인 (로그인 되어있지 않으면 장바구니에 접근할 수 없다.)
			
			page = "/views/main/Main.jsp";
			
		} else {
			
			// 로그인 확인 후 로그인 ID의 장바구니 조회
			double pointRate = cService.changePointRate(user.getmGrade());
			
			result = cService.selectBasket(user.getmUser_UID());

			page = "views/cart/shoppingCart.jsp";
			
			
			if (result.size() != 0) {	// 장바구니에 데이터가 있을 경우

				request.setAttribute("cartList", result);
				
			} else {	// 장바구니에 데이터가 없을 경우
				
				request.setAttribute("cartList", null);
				
			}
			
			request.setAttribute("pointRate", pointRate);
		}
		
		request.getRequestDispatcher(page).forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
