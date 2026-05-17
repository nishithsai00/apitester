package com.springboot.insights.controller;

import com.springboot.insights.model.ApiLog;
import com.springboot.insights.tracker.QueryCountHolder;
import com.springboot.insights.service.LogStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
public class Insights_Controller {
    private final LogStore logStore;
public Insights_Controller(LogStore logStore){
    this.logStore=logStore;
}



    @RequestMapping("/insights")
public List<ApiLog> insights() throws Exception{

    return logStore.getlogs();
}
@RequestMapping("/insights/summary")
    public Map<String,Object> summary(){
      return logStore.insights();
}
@DeleteMapping("/insights/clear")
public void clear(){
    logStore.clear();
}

}
