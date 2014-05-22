package com.httpclienthelper.example;

import org.apache.http.HttpResponse;

import com.httpclienthelper.HttpClientBuilder;
import com.httpclienthelper.HttpClientManager;
import com.httpclienthelper.RequestAttributes;
import com.httpclienthelper.RequestNotification;

public class ExampleManager extends HttpClientManager {

	/*
	 * It helps your observer code to define a tag, success, fail and 
	 * bad notification string 
	 */
	public static final String REPOS_TAG     = "reposTag";
	public static final String REPOS_SUCCESS = "reposSuccess";
	public static final String REPOS_FAIL    = "reposFail";
	public static final String REPOS_BAD     = "reposBad";
	
	/*
	 * Make a request to the Github public API
	 */
	
	public void getRepos() {
		
		String url = "https://api.github.com/users/tsif/repos";
		
		/* 
		 * Create a request attributes object with a URL and tag
		 * you can use to identify the object later and also
		 * assists with creating request notifications
		 */
		RequestAttributes  attributes = new RequestAttributes(url, REPOS_TAG);
		
		/* 
		 * Add any required headers this way
		 */
		attributes.addHeader("Content-Type", "application/json");
		
		/*
		 * Set the verb
		 */
		attributes.setMethod(HttpClientBuilder.HTTP_GET_METHOD);
		
		/*
		 * You can add your own key value to the request attributes
		 * which you can later retrieve with getValue(String key)
		 */
		attributes.addValue("format", "json");
			
		execute(attributes); 
	}
	
	@Override public RequestNotification parseResult(HttpResponse result, RequestAttributes attributes) {

		RequestNotification requestnotification = new RequestNotification();
		
		if(!isRunning()) {
			return null;
			
		} else if(result == null) {
			
			requestnotification.setStatusCode(HttpClientBuilder.STATUS_CODE_ERROR);
			assignNotificationName(HttpClientBuilder.STATUS_CODE_ERROR, attributes.getTag(), requestnotification);
			return requestnotification;
		}
		
		/*
		 * Get the status code here
		 */
		int statuscode = result.getStatusLine().getStatusCode();
		
		requestnotification.setStatusCode(statuscode);
		assignNotificationName(statuscode, attributes.getTag(), requestnotification);

		if(HttpClientBuilder.statusGood(statuscode)) {
			/* do the good stuff here, such as get the JSON */
			
		} else if(HttpClientBuilder.statusBad(statuscode)) {
			
			/* do the good stuff here, such as get the JSON */
		}
        
		return requestnotification;	
	}
	
    @Override public String successNotification(String tag) {
		
		if(tag.equals(REPOS_TAG)) {
			return REPOS_SUCCESS;
		}
		return EMPTY_STRING;
	}

	@Override public String failNotification(String tag) {		
		 
        if(tag.equals(REPOS_TAG)) {
        	return REPOS_FAIL;
		}
		return EMPTY_STRING;
	}

	@Override public String badNotification(String tag) {
		
        if(tag.equals(REPOS_TAG)) {
        	return REPOS_BAD;
		}
		return EMPTY_STRING;
	}
}
