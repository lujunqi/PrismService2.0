package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Listener
 */
@WebServlet("/listener")
public class Listener extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Listener() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String method = req.getParameter("method");
		if ("set".equals(method)) {
			String value = req.getParameter("value");
			set(value);
		}
		if ("get".equals(method)) {
			get();
		}
	}

	private String get() {
		ServletContext application = this.getServletContext();
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) application.getAttribute("listener");
		try {
			String str = list.get(0);
			list.remove(0);
			return str;
		} catch (Exception e) {
			return "Null()";
		}
	}

	private void set(String value) {
		ServletContext application = this.getServletContext();
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) application.getAttribute("listener");
		if (list == null) {
			list = new ArrayList<String>();
		}
		System.out.println(list);
		if (value != null) {
			list.add(value);
		}
		application.setAttribute("listener", list);
	}

}
