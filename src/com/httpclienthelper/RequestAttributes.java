package com.httpclienthelper;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestAttributes {

	private String                  _url;
	private String                  _tag;
	private Integer                 _method;
	private HttpClient              _client;
	private HashMap<String, Object> _values;
	private List<NameValuePair>     _entities;
	private StringEntity            _entity;
	private List<NameValuePair>     _headers;
	private JSONObject              _singleEntity;
	private String                  _filePath;
	
	/* 
	 * Constructors
	 */
	
	public RequestAttributes() {
		
		_values       = new HashMap<String, Object>();
		_entities     = new ArrayList<NameValuePair>();
		_headers      = new ArrayList<NameValuePair>();
		_singleEntity = new JSONObject();
	}
	
	public RequestAttributes(String url, String tag) {
		
		_url          = url;
		_tag          = tag;
		
		_values       = new HashMap<String, Object>();
		_entities     = new ArrayList<NameValuePair>();
		_headers      = new ArrayList<NameValuePair>();
		_singleEntity = new JSONObject();
	}
	
	/*
	 * Getters
	 */
	
	public String getUrl() {
		return _url;
	}
	
	public String getTag() {
		return _tag;
	}
	
	public Integer getMethod() {
		return _method;
	}
	
	public HttpClient getClient() {
		return _client;
	}
	
	public HashMap<String, Object> getValues() {
		return _values;
	}
	
	public Object getValue(String key) {
	    return _values.get(key);	
	}
	
	public List<NameValuePair> getEntities() {
		return _entities;
	}
	
	public StringEntity getSingleStringEntity() {
		return _entity;
	}
	
	public List<NameValuePair> getHeaders() {
		return _headers;
	}
	
	public JSONObject getSingleEntity() {
	    return _singleEntity;	
	}
	
	public void setFilePath(String p) {
		_filePath = p;
	}
	
	/* 
	 * Setters
	 */
	
	public void setUrl(String url) {
		_url = url;
	}
	
	public void setTag(String tag) {
		_tag = tag;
	}
	
	public void setMethod(Integer method) {
		_method = method;
	}
	
	public void setClient(HttpClient client) {
		_client = client;
	}
	
	public void addValue(String key, Object value) {
		
		_values.put(key, value);
	}

	public void addPair(String name, String value) {
		_entities.add(new BasicNameValuePair(name, value));
	}	

	public void addSingleEntityPair(String name, String value) {
		try {
			_singleEntity.put(name, value);
		} catch (JSONException e) {}
	}	
	
	public void setSingleEntity(String entity) throws UnsupportedEncodingException {
		_entity = new StringEntity(entity.toString());
	}
	
	public void addHeader(String name, String value) {
		_headers.add(new BasicNameValuePair(name, value));
	}
	
	public String getFilePath() {
		return _filePath;
	}
}