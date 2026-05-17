package com.api.tester.service;


import com.api.tester.model.ApiLog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RunTimeInterceptor implements HandlerInterceptor {
   @Autowired
    LogStore logStore;
     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         request.setAttribute("starttime",System.currentTimeMillis());
         return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
         long startTime=(long)request.getAttribute("starttime");
         long duration=System.currentTimeMillis()-startTime;
             ApiLog log=new ApiLog();
               log.setApi(request.getRequestURI());
               log.setDuration(duration);
               log.setMethod(request.getMethod());
               log.setTimeStamp(System.currentTimeMillis());
               log.setStatusCode(response.getStatus());
             logStore.save(log);

    }
}
