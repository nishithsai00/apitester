package com.springboot.insights.tracker;


import com.springboot.insights.model.ApiLog;
import com.springboot.insights.service.LogStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@Component
public class RunTimeInterceptor implements HandlerInterceptor {

    private static int performanceWarning=10; // by default the performance metric is 10
private static  int nPlusOneSuspected=3; //by default the n+1 querie count is three

    public static int getnPlusOneSuspected() {
        return nPlusOneSuspected;  // to get the default metrics
    }

    public static void setnPlusOneSuspected(int nPlusOneSuspected) {
        RunTimeInterceptor.nPlusOneSuspected = nPlusOneSuspected;  // to change the default metrics to user specific number
    }

    public static int getPerformanceWarningLimit() {
        return performanceWarning;// to get the default metrics
    }

    public static void  setPerformanceWarningLimit(int num) {
        performanceWarning = num;  // to change the default metrics to user specific number
    }

    LogStore logStore;
    public RunTimeInterceptor(LogStore logStore){
        this.logStore=logStore;   // depedency injection
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         String url=request.getRequestURI();
         if(url.equals("/dashboard.html")||url.equals("/insights")||url.equals("/insights/summary")||url.equals("/insights/clear")){
             return true;
         }
         request.setAttribute("starttime",System.currentTimeMillis());
        QueryCountHolder.resetCount();     // this method runs before the request actually hit the controller


         return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
         String url =request.getRequestURI();     // this method runs the request work done  even if the request succed or fails
                                                  // it even works with the exception
        if(url.equals("/dashboard.html")||url.equals("/insights")||url.equals("/insights/summary")||url.equals("/insights/clear")){
            return;
        }
        Map<String,Integer> summary=new HashMap<>();
        for(String query :QueryCountHolder.getQueryList()){  // used to combine the same queries like n+1 queries together
            if(summary.containsKey(query)){
                summary.put(query,summary.get(query)+1);
            }
            else{
                summary.put(query,1);
            }
        }

      Map<String,Integer> nPlusOne=new HashMap<>();
        for(Map.Entry<String,Integer> entry:summary.entrySet()){ // it will count how many sus queries in the before map
           if(entry.getValue()>=nPlusOneSuspected){
               nPlusOne.put(entry.getKey(),entry.getValue());
           }

        }
         long startTime=(long)request.getAttribute("starttime");
         long duration=System.currentTimeMillis()-startTime;
             ApiLog log=new ApiLog();
               log.setApi(request.getRequestURI());
               log.setDuration(duration);
               log.setMethod(request.getMethod());
               log.setTimeStamp(System.currentTimeMillis());
               log.setStatusCode(response.getStatus());
             log.setQueryCount(QueryCountHolder.getCount());
             log.setPerformanceWarning(QueryCountHolder.getCount()>performanceWarning);
             log.setSuspectedN1(!nPlusOne.isEmpty());
             logStore.save(log);
             QueryCountHolder.clearAllQueries();

    }
}
