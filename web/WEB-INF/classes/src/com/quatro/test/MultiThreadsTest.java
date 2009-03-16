
package com.quatro.test;



import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

class MultiThreadsTest extends DispatchAction{
    public static void main (String args[]) {
        
        int num = 10;
    	ArrayList simpleThreads = new ArrayList();
    	
    	for(int i =0; i<num;i++){
    		SimpleThread obj = new SimpleThread("Thread-" + i);
    		simpleThreads.add(obj);
    		obj.start();
        	
        }
    	
//    	for(int i =0; i<num;i++){
//    		if((SimpleThread)simpleThreads.get(i)!=null && ((SimpleThread)simpleThreads.get(i)).isAlive())
//    			((SimpleThread)simpleThreads.get(i)).stop();
//        	
//        }
    	
	}
    

    public ActionForward test1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	
    	int num = 10;
    	ArrayList simpleThreads = new ArrayList();
    	
    	for(int i =0; i<num;i++){
    		SimpleThread obj = new SimpleThread("Thread-" + i);
    		simpleThreads.add(obj);
    		obj.start();
        	
        }
    	return mapping.findForward("list");
	}
    
}