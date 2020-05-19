package com.prism.job;

import java.util.HashMap;
import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzManager {
	private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

	private static String JOB_GROUP_NAME = "DEFAULT_JOBGROUP_NAME";  //自定义

	private static String TRIGGER_GROUP_NAME = "DEFAULT_TRIGGERGROUP_NAME";  //自定义


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void addJob(String jobName, String triggerName, Class jobClass, String cron,Map<String,Object> param) {  

	       try {  
	           Scheduler sched = schedulerFactory.getScheduler();  
	           // 任务名，任务组，任务执行类
	           JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, JOB_GROUP_NAME).build();
	            
	           
	           jobDetail.getJobDataMap().putAll(param);
	           // 触发器  
	           TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
	           // 触发器名,触发器组  
	           triggerBuilder.withIdentity(triggerName, TRIGGER_GROUP_NAME);
	           triggerBuilder.startNow();
	           // 触发器时间设定  
	           triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
	           // 创建Trigger对象
	           CronTrigger trigger = (CronTrigger) triggerBuilder.build();
	           // 调度容器设置JobDetail和Trigger
	           sched.scheduleJob(jobDetail, trigger);  
	           // 启动  
	           if (!sched.isShutdown()) {  
	               sched.start();  
	           }  
	       } catch (Exception e) {  

	           throw new RuntimeException(e);  

	       }  

	   }


}
