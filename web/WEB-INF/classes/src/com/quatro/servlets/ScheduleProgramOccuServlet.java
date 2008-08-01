package com.quatro.servlets;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.oscarehr.PMmodule.dao.ProgramOccupancyDao;
import org.oscarehr.util.DbConnectionFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.quatro.common.KeyConstants;

import oscar.OscarProperties;

public class ScheduleProgramOccuServlet extends HttpServlet {
	  private static final Logger logger = LogManager.getLogger(ScheduleProgramOccuServlet.class);

	    private static final Timer programOccuTimer = new Timer(true);

	    private static final long GERNERATE_PERIOD = DateUtils.MILLIS_PER_DAY*24;
	    private static long dataRetentionTimeMillis = -1;
	    private static String startTime="";
	    private static ProgramOccuTimerTask programOccuTimerTask = null;
	   
	    private static ProgramOccupancyDao programOccupancyDao = null;

	    public static class ProgramOccuTimerTask extends TimerTask {
	    	String providerNo="1111";
	        public void run() {
	            logger.debug("ProgramOccuTimerTask timerTask started.");
	            long taskTime=dataRetentionTimeMillis;
	            Calendar today = Calendar.getInstance();
	            long now =today.getTimeInMillis(); 
	            if(now>dataRetentionTimeMillis) {
	            	//taskTime=dataRetentionTimeMillis+1000*60*60*24;
	            	//get time from dataRetentionTimeMillis and get date from now
	            	Calendar sDt = Calendar.getInstance();
	            	sDt.set(Calendar.HOUR_OF_DAY,Integer.valueOf(startTime.substring(0,2)).intValue());
	            	sDt.set(Calendar.MINUTE, Integer.valueOf(startTime.substring(2)).intValue());
	            	taskTime=sDt.getTimeInMillis();
	            }
	            try {
	                if (Math.abs(Calendar.getInstance().getTimeInMillis()-taskTime)<=1000) {
	                // delete old redirect entries
	                programOccupancyDao.deleteProgramOccupancy(Calendar.getInstance());
	                programOccupancyDao.insertProgramOccupancy(providerNo, Calendar.getInstance());
	                }
	            }
	            catch (Exception e) {
	                logger.error("Unexpected error flushing html open queue.", e);
	            }
	            finally {
	                DbConnectionFilter.releaseThreadLocalDbConnection();
	            }

	            logger.debug("ProgramOccuTimerTask timerTask completed.");
	        }
	    }
	    private long getDelayTime(String startTime){
	    	long delayTime=0;
	    	Integer hr=Integer.valueOf(startTime.substring(0,2));
        	Integer min=Integer.valueOf(startTime.substring(2));
        	Calendar now=Calendar.getInstance();
        	Calendar start = Calendar.getInstance();
        	start.set(Calendar.HOUR_OF_DAY,hr.intValue());
        	start.set(Calendar.MINUTE, min.intValue());
        	if(start.after(now)) delayTime=start.getTimeInMillis()-now.getTimeInMillis();
        	else{
        		 delayTime=now.getTimeInMillis()-start.getTimeInMillis()+24*60*60*1000;
        	}
	    	return delayTime;
	    }
	    public void init(ServletConfig servletConfig) throws ServletException {
	        super.init(servletConfig);

	        // yes I know I'm setting static variables in an instance method but it's okay.
	        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletConfig.getServletContext());	       
	        programOccupancyDao  = (ProgramOccupancyDao)webApplicationContext.getBean("programOccupancyDao");

	        logger.info("PROGRAM_OCCUPANCY_PERIOD=" + GERNERATE_PERIOD);

	        String temp = StringUtils.trimToNull(OscarProperties.getInstance().getProperty("PROGRAM_OCCUPANCY_STARTTIME"));
	        //period configed by user
	        String period = StringUtils.trimToNull(OscarProperties.getInstance().getProperty("PROGRAM_OCCUPANCY_PERIOD"));
	        if (temp != null) {
	        	startTime=temp;	        	
	            dataRetentionTimeMillis = this.getDelayTime(temp);
	            programOccuTimerTask = new ProgramOccuTimerTask();
		     //   programOccuTimer.scheduleAtFixedRate(programOccuTimerTask, GERNERATE_PERIOD, GERNERATE_PERIOD);
	            programOccuTimer.scheduleAtFixedRate(programOccuTimerTask, 10000, Long.valueOf(period).longValue());
	        }	      
	    }

	    public void destroy() {
	        programOccuTimerTask.cancel();	        
	        super.destroy();
	    }

	    /**
	     */
	    public void doGet(HttpServletRequest request, HttpServletResponse response) throws java.io.IOException, javax.servlet.ServletException {
	        try {
	           	            
	            // get provider
	            String providerId = (String)request.getSession(true).getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
	           
	        }
	        catch (Exception e) {
	            logger.error("Error processing request. " + request.getRequestURL() + ", " + e.getMessage());
	            logger.debug(e);
	            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
	            return;
	        }
	    }


}
