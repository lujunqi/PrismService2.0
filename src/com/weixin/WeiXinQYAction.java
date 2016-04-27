package com.weixin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

public class WeiXinQYAction extends HttpServlet {
	private static final long serialVersionUID = 4440739483644821986L;
	private static final String sToken = WeiXinEnum.sToken.getName();// 这个Token是随机生成，但是必须跟企业号上的相同
	private static final String sCorpID = WeiXinEnum.sCorpID.getName();// 这里是你企业号的CorpID
	private static final String sEncodingAESKey = WeiXinEnum.sEncodingAESKey.getName();// 这个EncodingAESKey是随机生成，但是必须跟企业号上的相同
	private static final String APPSECRET = WeiXinEnum.APPSECRET.getName();// 管理组的凭证密钥

	public void init() throws ServletException {
		System.out.println("WeiXinction loading..............");
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 微信加密签名 
        String sVerifyMsgSig = request.getParameter("msg_signature");
        // 时间戳
        String sVerifyTimeStamp = request.getParameter("timestamp");
        // 随机数
        String sVerifyNonce = request.getParameter("nonce");
        // 随机字符串
        String sVerifyEchoStr = request.getParameter("echostr");
        String sEchoStr; //需要返回的明文
        PrintWriter out = response.getWriter();  
        WXBizMsgCrypt wxcpt;
        try {
            wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
            sEchoStr = wxcpt.VerifyURL(sVerifyMsgSig, sVerifyTimeStamp,sVerifyNonce, sVerifyEchoStr);
            // 验证URL成功，将sEchoStr返回
            out.print(sEchoStr);  
        } catch (AesException e1) {
            e1.printStackTrace();
        }
        // 业务
        
	}

}
