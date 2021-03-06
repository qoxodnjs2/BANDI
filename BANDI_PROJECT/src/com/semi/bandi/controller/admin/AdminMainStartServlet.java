package com.semi.bandi.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.semi.bandi.model.service.admin.AdminDQLService;
import com.semi.bandi.model.vo.adminVo.AnnualIncome;
import com.semi.bandi.model.vo.adminVo.BookStocks;
import com.semi.bandi.model.vo.adminVo.CommentAdmin;
import com.semi.bandi.model.vo.adminVo.OrderAdmin;

/**
 * Servlet implementation class AdminMainStartServlet
 */
@WebServlet("/admin.start")
public class AdminMainStartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminMainStartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminDQLService ads = new AdminDQLService();
		
		String sYear = request.getParameter("year");
		int year = -1;
		if(sYear == null)
		{
			Calendar cal = new GregorianCalendar();
			year = cal.get(Calendar.YEAR);
		}else
			year = Integer.parseInt(sYear);
		
		// Temperate Setting Minimum is 100
		ArrayList<BookStocks> arrayStock = ads.getBookStocksArray(100);
		
		// Income Array
		ArrayList<AnnualIncome> annualList = ads.getAnnualArrayList(year);
		
		ArrayList<CommentAdmin> commentList = ads.getCommentArrayList(year);
		
		ArrayList<OrderAdmin> orderList = ads.getOrderArrayList(year);
		
		if(arrayStock != null || annualList != null || commentList != null || orderList != null)
		{
			HttpSession session = request.getSession();
			session.setAttribute("stocks", arrayStock);
			session.setAttribute("annual", annualList);
			session.setAttribute("comments", commentList);
			session.setAttribute("orders", orderList);
			session.setAttribute("adminYear", year+"");
			RequestDispatcher views = request.getRequestDispatcher("/views/admin/AdminMain.jsp");
			views.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
