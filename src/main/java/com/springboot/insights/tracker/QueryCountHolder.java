package com.springboot.insights.tracker;


import java.util.ArrayList;
import java.util.List;

public class QueryCountHolder {
    private static final ThreadLocal<Integer> queryCount=ThreadLocal.withInitial(()->0);
    public static int getCount(){
      return  queryCount.get();
    }
    public static void addCount(){
        queryCount.set(queryCount.get()+1);
    }
    public static void resetCount(){
        queryCount.set(0);
    }
    public static void clearQueryCount(){
        queryCount.remove();
    }
    public static void clearAllQueries(){
        queryCount.remove();
        queries.remove();
    }
    private static final ThreadLocal<List<String>> queries=ThreadLocal.withInitial(ArrayList::new);
    public static void addQuery(String query){
        queries.get().add(query);
    }
    public static void clearQueryList(){
        queries.get().clear();
    }
    public static List<String> getQueryList(){
        return queries.get();
    }
}
