package com.springboot.insights.tracker;


import com.springboot.insights.model.ApiLog;
import com.springboot.insights.service.LogStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RunTimeInterceptor implements HandlerInterceptor {

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
         return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
         String url =request.getRequestURI();
        if(url.equals("/dashboard.html")||url.equals("/insights")||url.equals("/insights/summary")||url.equals("/insights/clear")){
            return;
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
             log.setSuspectedN1(QueryCountHolder.getCount()>10);
             logStore.save(log);

    }
}
