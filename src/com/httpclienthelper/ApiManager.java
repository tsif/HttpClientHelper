package com.httpclienthelper;

import org.apache.http.HttpResponse;

public interface ApiManager {

	public RequestNotification parseResult(HttpResponse result, RequestAttributes attributes);
	public void                postFailNotification(String tag, String error);
	
	public String              successNotification(String tag);
	public String              failNotification(String tag);
	public String              badNotification(String tag);
}