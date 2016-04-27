package templete;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface Templete {
	public void service(Map<String, Object> sourceMap, HttpServletRequest req);
}
