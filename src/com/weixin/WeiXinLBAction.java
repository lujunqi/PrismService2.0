package com.weixin;

/**
 * 
 */
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WeiXinLBAction extends HttpServlet {
	private static final long serialVersionUID = -6762535467585337086L;

//	public void service(HttpServletRequest req, HttpServletResponse res)
//			throws ServletException, IOException {
//		String code = req.getParameter("code");
//		String agentid = req.getParameter("state");
//		// 业务
//		WeiXinResponse w = new WeiXinResponse(req.getSession());
//		String token = w.getCoAccessToken(WeiXinEnum.sCorpID.getName(),
//				WeiXinEnum.APPSECRET.getName());
//		String s = w.getCoUserId(token, code, agentid);
//		PrintWriter out = res.getWriter();
//		out.print(s);
//		System.out.println(s);
//	}
//
//	public static void main(String[] args) {
//		// 业务
//		WeiXinResponse w = new WeiXinResponse();
//		String token = w.getCoAccessToken(WeiXinEnum.sCorpID.getName(),
//				WeiXinEnum.APPSECRET.getName());
//		System.out.println(token);
//	}

}
