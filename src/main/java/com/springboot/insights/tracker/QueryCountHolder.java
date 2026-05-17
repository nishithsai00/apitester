package com.springboot.insights.tracker;


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
    public static void clearCount(){
        queryCount.remove();
    }
}
