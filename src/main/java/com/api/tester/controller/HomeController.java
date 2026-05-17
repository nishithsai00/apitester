package com.api.tester.controller;

import com.api.tester.model.ApiLog;
import com.api.tester.service.LogStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
public class HomeController {
@Autowired
    LogStore logStore;
@RequestMapping("/insights")
public List<ApiLog> hello() throws Exception{

    return logStore.getlogs();
}
@RequestMapping("/insights/summary")
    public Map<String,Object> summary(){
      return logStore.insights();
}

}
