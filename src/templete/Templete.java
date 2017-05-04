package templete;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prism.exception.BMOException;

public interface Templete {
	public void service(Map<String, Object> sourceMap, HttpServletRequest req, HttpServletResponse res) throws BMOException;
}
