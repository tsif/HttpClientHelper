package com.httpclienthelper;

import java.util.ArrayList;
import java.util.HashMap;

public class RequestNotification {
	
	private String                  _requestName;
	private HashMap<String, Object> _values;
	private Integer                 _statusCode;
	private ArrayList<String>       _errorDescriptions;
	
	/*
	 * Constructors
	 */
	public RequestNotification() {
		_values            = new HashMap<String, Object>();
		_errorDescriptions = new ArrayList<String>();
	}
	
	public RequestNotification(String requestName) {
		_values            = new HashMap<String, Object>();
		_errorDescriptions = new ArrayList<String>();
		_requestName       = requestName;
	}
	
	/*
	 * Values
	 */
	public void addValueForKey(String key, Object value) {
		_values.put(key, value);
	}
	
	/*
	 * Getters
	 */
	public String getRequestName() {
		return _requestName;
	}
	
	public HashMap<String, Object> getValues() {
		return _values;
	}
	
	public ArrayList<String> getErrorDescriptions() {
		return _errorDescriptions;
	}
	
	public Integer getStatusCode() {
		return _statusCode;
	}
	
	/*
	 * Setters
	 */
	public void setRequestName(String requestName) {
		_requestName = requestName;
	}
	
	public void setStatusCode(Integer statusCode) {
		_statusCode = statusCode;
	}
	
	/*
	 * Support
	 */
	public String getDescription() {
		return _requestName + " " + _statusCode + " " + _values;
	}
}
