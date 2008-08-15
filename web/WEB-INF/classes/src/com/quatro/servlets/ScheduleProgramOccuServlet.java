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
import org.oscarehr.PMmodule.service.ProgramOccupancyManager;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.quatro.common.KeyConstants;

import oscar.OscarProperties;

public class ScheduleProgramOccuServlet extends HttpServlet {
	 
	  private static final Logger logger = LogManager.getLogger(ScheduleProgramOccuServlet.class);

	    private static final Timer programOccuTimer = new Timer(true);
	    private static ProgramOccuTimerTask programOccuTimerTask = null;
	    private static boolean runAsPrevousDay = true;
	    private static ProgramOccupancyManager programOccupancyManager = null;

	    public static class ProgramOccuTimerTask extends TimerTask {
	    	String providerNo="1111";
	        public void run() {
	            logger.debug("ProgramOccuTimerTask timerTask started.");	
	            try {
	            	Calendar dt = Calendar.getInstance();
	            	if(runAsPrevousDay) dt.add(Calendar.DATE, -1);

	            	// delete old redirect entries if any
	                programOccupancyManager.deleteProgramOccupancy(dt);
	                programOccupancyManager.insertProgramOccupancy(providerNo, dt);
	                
	                //schedule next run
	                programOccuTimerTask.cancel();
	                scheduleNextRun();
	            }
	            catch (Exception e) {
	                logger.error("Error to generate the occupancy records. Re-scheduled to run in 5 minutes", e);
	                Calendar cal = Calendar.getInstance();
	                cal.add(Calendar.MINUTE, 5);
	                scheduleNextRun(cal);
	            }
	            logger.debug("ProgramOccuTimerTask timerTask completed.");
	        }
	    }
	    private static long getDelayTime(String startTime){
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
	        programOccupancyManager  = (ProgramOccupancyManager)webApplicationContext.getBean("programOccupancyManager");
	        scheduleNextRun();
	    }

	    public void destroy() {
	        programOccuTimer.cancel();
	        super.destroy();
	    }

	    /**
	     */
	    public void doGet(HttpServletRequest request, HttpServletResponse response) throws java.io.IOException, javax.servlet.ServletException {
	        try {
	           	            
	            // get provider
	            //String providerId = (String)request.getSession(true).getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
	           return;
	        }
	        catch (Exception e) {
	            logger.error("Error processing request. " + request.getRequestURL() + ", " + e.getMessage());
	            logger.debug(e);
	            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
	            return;
	        }
	    }
	    
	    private static void scheduleNextRun()
	    {
	        String sTime = StringUtils.trimToNull(OscarProperties.getInstance().getProperty("PROGRAM_OCCUPANCY_STARTTIME"));
	        String previousDay = StringUtils.trimToNull(OscarProperties.getInstance().getProperty("PROGRAM_OCCUPANCY_PREVIOUSDAY"));
	        if (sTime == null) return;
	        runAsPrevousDay = previousDay.toLowerCase().startsWith("y");
	        // dataRetentionTimeMillis = this.getDelayTime(temp);
	        String sHr = "";
	        String sMi = "";
	        if(sTime.indexOf(':')>0) {
	        	sHr=sTime.substring(0,2);
	        	sMi = sTime.substring(3);
	        }
	        else
	        {
	        	sHr=sTime.substring(0,2);
	        	sMi = sTime.substring(2);
	        }
            Calendar sDt = Calendar.getInstance();
        	sDt.set(Calendar.HOUR_OF_DAY,Integer.valueOf(sHr).intValue());
        	sDt.set(Calendar.MINUTE, Integer.valueOf(sMi).intValue());
        	sDt.set(Calendar.SECOND, 0);
        	sDt.set(Calendar.MILLISECOND, 0);
        	Calendar now = Calendar.getInstance();
        	if ((now.getTimeInMillis() - sDt.getTimeInMillis()) > 0) {
        		sDt.add(Calendar.DATE, 1);
        	}
            	
	        programOccuTimerTask = new ProgramOccuTimerTask();		
	        programOccuTimer.schedule(programOccuTimerTask,sDt.getTime());
	    }
	    private static void scheduleNextRun(Calendar startDate)
	    {
	        programOccuTimerTask = new ProgramOccuTimerTask();		
	        programOccuTimer.schedule(programOccuTimerTask,startDate.getTime());
	    }
}
