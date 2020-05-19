package com.prism.job;

import java.util.HashMap;
import java.util.Map;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.prism.db.Prism;

public class MsgInstJob extends QuartzJob {
	
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
	    JobDataMap data = jobExecutionContext.getJobDetail().getJobDataMap();
	    Map<String,Object> m = data.getWrappedMap();
	    ApplicationContext beans = (ApplicationContext)m.get("beans");
		Prism prism = (Prism) beans.getBean("jobs.msg.inst");
		prism.setContext(beans);
		
		Map<String, Object> param = new HashMap<String,Object>();
		try {
			prism.db(param);	
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		;

	}

	

}
