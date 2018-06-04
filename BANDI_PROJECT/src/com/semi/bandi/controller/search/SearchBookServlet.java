package com.semi.bandi.controller.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.bandi.model.service.search.SearchBookService;
import com.semi.bandi.model.vo.SearchBook;

@WebServlet("/searchBook.sb")
public class SearchBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SearchBookServlet() { }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String option = request.getParameter("searchBy");
		String getText = request.getParameter("searchText");
		String text = getText.replaceAll(" ", "");

		ArrayList<SearchBook> list = new SearchBookService().searchBook(option, text);
		HashMap<String, Integer> genreCount = new SearchBookService().searchBookGenre(option, text);
		
		request.setAttribute("option", option);
		request.setAttribute("getText", getText);
		request.setAttribute("list", list);
		request.setAttribute("genreCount", genreCount);
		
		ServletContext context = getServletContext();
		RequestDispatcher rq = context.getRequestDispatcher("/views/search/searchBook.jsp");
		rq.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
