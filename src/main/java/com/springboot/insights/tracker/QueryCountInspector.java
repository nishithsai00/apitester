package com.springboot.insights.tracker;

import org.hibernate.resource.jdbc.spi.StatementInspector;


public class QueryCountInspector implements StatementInspector {

    @Override
    public String inspect(String s) {
        QueryCountHolder.addCount();
        return s;
    }
}
