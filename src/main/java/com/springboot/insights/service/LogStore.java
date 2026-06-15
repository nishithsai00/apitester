package com.springboot.insights.service;

import com.springboot.insights.model.ApiLog;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class LogStore {
    private List<ApiLog> logs=new ArrayList<>();


     public void save(ApiLog log){
      logs.add(log);
     } // this will save the api log (most recent lod is added to the list

     public List<ApiLog> getlogs(){
         return logs;
     }//this will give all the logs that are in the list

     public void clear(){
         logs.clear();
     } // this will clear the logs in the above list

public Map<String,Object> insights(){    // 1.this method will give summary about all api logs that are present in the List
         Map<String ,List<ApiLog>> endPoints=logs.stream() // 2. average time the request takes and summary about total queries fired
                                                 .collect(Collectors.groupingBy(ApiLog::getApi));
      Map<String,Object> stats = new LinkedHashMap<>();
         endPoints.forEach((api,calls )->{
             long avg=(long) calls.stream()
                     .mapToLong(ApiLog::getDuration)
                     .average()
                     .orElse(0);
             long slowest=(long) calls.stream()
                     .mapToLong(ApiLog::getDuration)
                     .max()
                     .orElse(0);
             long queriesFired=(long)calls.stream()
                     .mapToLong(ApiLog::getQueryCount)
                     .sum();
             long n1Count=calls.stream()
                     .filter(ApiLog::isSuspectedN1)
                     .count();
             long performance =calls.stream()
                     .filter(ApiLog::isPerformanceWarning)
                     .count();
             Map<String,Object> result=new LinkedHashMap<>();
             result.put("Count",calls.size());
             result.put("Slowest",slowest+"ms");
             result.put("Average",avg+"ms");
             result.put("QueriesFired",queriesFired);
             result.put("isN+1 Suspected",n1Count>0?"Yes":"NO");
             result.put("PerformanceWarning",performance>0?"Yes":"No");
             stats.put(api,result);

    });
         return stats;


}

}
