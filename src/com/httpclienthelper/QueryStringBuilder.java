package com.httpclienthelper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;

public class QueryStringBuilder {

	private String _query = "";

    public QueryStringBuilder(HashMap<String, String> map) throws UnsupportedEncodingException {
    	
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
        	
        	String key   = (String)iterator.next();
            String value = (String)map.get(key);
            
            _query += URLEncoder.encode(key, "utf-8") + "=" + URLEncoder.encode(value, "utf-8");
           
            if (iterator.hasNext()) { 
            	_query += "&"; 
            }
        }
    }

    public QueryStringBuilder(Object name, Object value) throws UnsupportedEncodingException {
	    _query = URLEncoder.encode(name.toString(), "utf-8") + "=" + URLEncoder.encode(value.toString(), "utf-8");
    }

    public QueryStringBuilder() { 
	    _query = ""; 
    }
   
   
    public synchronized QueryStringBuilder add(Object name, Object value) throws UnsupportedEncodingException {
    	
        if (!_query.trim().equals("")) {
        	_query += "&";
        }
        
        _query += URLEncoder.encode(name.toString(), "utf-8") + "=" + URLEncoder.encode(value.toString(), "utf-8");
        
        return this;
    }

    public String toString() { 
    	return _query; 
    }
}
