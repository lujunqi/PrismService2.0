package com.prism.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

public abstract class QuartzJob implements Job {

	@Override
	public abstract void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException;

	
	private String cron = "";// 每隔三秒钟执行一次,?用于无指定日期
	private String jobName = "";
	private String triggerName = "";
	
	

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}



}
