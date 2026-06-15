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

    private static int performanceWarning=10;
private static  int nPlusOneSuspected=3;

    public static int getnPlusOneSuspected() {
        return nPlusOneSuspected;
    }

    public static void setnPlusOneSuspected(int nPlusOneSuspected) {
        RunTimeInterceptor.nPlusOneSuspected = nPlusOneSuspected;
    }

    public static int getPerformanceWarningLimit() {
        return performanceWarning;
    }

    public static void  setPerformanceWarningLimit(int num) {
        performanceWarning = num;
    }

    LogStore logStore;
    public RunTimeInterceptor(LogStore logStore){
        this.logStore=logStore;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         String url=request.getRequestURI();
         if(url.equals("/dashboard.html")||url.equals("/insights")||url.equals("/insights/summary")||url.equals("/insights/clear")){
             return true;
         }
         request.setAttribute("starttime",System.currentTimeMillis());
        QueryCountHolder.resetCount();
         if(!(QueryCountHolder.getQueryList().isEmpty())){
             QueryCountHolder.clearQueryList();
        }

         return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
         String url =request.getRequestURI();
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

    }
}
