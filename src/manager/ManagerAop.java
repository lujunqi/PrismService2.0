package manager;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;

import com.prism.dbutil.DBCommand;
import com.prism.service.Service;

public class ManagerAop {
	// 判断验证码
	public void loginCheck(ProceedingJoinPoint pjp) {
		try {
			if (!"service".equals(getMethod(pjp))) {
				pjp.proceed();
				return;
			}
			Service s = (Service) pjp.getTarget();
			HttpServletRequest req = s.getRequest();

			// String valcode = req.getParameter("valcode");
			// if("3591".equals(valcode)){
			pjp.proceed();
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = (List<Map<String, Object>>) req
					.getAttribute("this");
			if (!list.isEmpty()) {
				HttpSession session = req.getSession();
				session.setAttribute("USER_ID", list.get(0).get("USER_ID"));
				session.setAttribute("USER_NAME", list.get(0).get("USER_NAME"));
			}
			// }else{
			// s.getResponse().getWriter().print("{\"code\":-1,\"info\":\"验证码错误\"}");
			// }
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void logs(ProceedingJoinPoint pjp) {
		try {
			Service s = (Service) pjp.getTarget();
			HttpServletRequest req = s.getRequest();
			String relativeuri = req.getRequestURI().replaceFirst(
					req.getContextPath(), "");
			HttpSession session = req.getSession();
			Map<String, Object> sessionMap = new HashMap<String, Object>();
			Enumeration<String> en2 = session.getAttributeNames();
			while (en2.hasMoreElements()) {
				String name = (String) en2.nextElement();
				Object value = session.getAttribute(name);
				sessionMap.put(name, value);
			}
			
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("LOG_NAME", relativeuri);
			m.put("LOG_INFO", req.getAttribute("reqMap")+"");
			m.put("LOG_SESSION", sessionMap+"");
			m.put("LOG_IP", req.getRemoteAddr()+"");
//			Object dbConn = req.getAttribute("DBConnection");
//			DBCommand cmd = new DBCommand(dbConn);
//			String sql = "INSERT INTO SM_LOGS(LOG_ID,LOG_NAME,LOG_INFO,LOG_SESSION,LOG_IP)VALUES"
//					+ "(SEQ_SM_LOGS.NEXTVAL,${LOG_NAME<STRING>},${LOG_INFO<STRING>},${LOG_SESSION<STRING>},${LOG_IP<STRING>})";
//			cmd.executeUpdate(sql, m);

			pjp.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	private String getMethod(ProceedingJoinPoint pjp) {
		return pjp.getSignature().getName();
	}
}
