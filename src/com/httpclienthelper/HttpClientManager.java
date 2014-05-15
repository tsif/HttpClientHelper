package com.httpclienthelper;

import java.util.Observer;

import org.apache.http.HttpResponse;

import android.os.AsyncTask;

abstract public class HttpClientManager extends AsyncTask<RequestAttributes, Void, RequestNotification> implements ApiManager {
	
	public  final static String            EMPTY_STRING = "";
	private              HttpClientBuilder _builder;
	private              Boolean           _running;
	private              Observer          _observer;
	
	public HttpClientManager() {
		_running = true;
	}
	
	public void setObserver(Observer observer) {
		_observer = observer;
	}
	
	public void addObserver(String notificationName) {
		if(_observer != null) {
			ObservingService.getInstance().addObserver(notificationName, _observer);
		}
	}
	
	public void removeObserver(String notificationName) {
		if(_observer != null) {
			ObservingService.getInstance().removeObserver(notificationName, _observer);
		}
	}
	
	@Override protected RequestNotification doInBackground(RequestAttributes... params) {
		
		RequestAttributes attributes = params[0];
		_builder                     = new HttpClientBuilder(attributes.getMethod(), attributes);
		HttpResponse      result     = _builder.makeRequest(attributes.getUrl());

		return parseResult(result, attributes);
	}
	
	protected void onPostExecute(RequestNotification notification) {		

		if(notification != null) {
			ObservingService.getInstance().postNotification(notification);
		}
	}
	
	public void abort() {
		
		_running = false;
        _builder.abort();
        
        if(_observer != null) {
        	ObservingService.getInstance().removeObserver(_observer);
        }
    }
	
	public boolean isRunning() {
	    return _running;	
	}
	
	@Override public RequestNotification parseResult(HttpResponse result, RequestAttributes attributes) {
		return new RequestNotification();	
	}

	public void assignNotificationName(int statusCode, String tag, RequestNotification requestnotification) {
	
		if(HttpClientBuilder.statusBad(statusCode)) {
			requestnotification.setRequestName(badNotification(tag));	
			
		} else if(HttpClientBuilder.statusGood(statusCode)) {
			requestnotification.setRequestName(successNotification(tag));	
		
		} else if(HttpClientBuilder.statusError(statusCode)) {
			requestnotification.setRequestName(failNotification(tag));	
		
		} else {
		
			requestnotification.setRequestName(failNotification(tag));	
		}
		
		return;
	}
	
	@Override public void postFailNotification(String tag, String error) {
		ObservingService.getInstance().postNotification(failNotification(tag)); 
	}

	@Override public String successNotification(String tag) {
		return EMPTY_STRING;
	}

	@Override public String failNotification(String tag) {
		return EMPTY_STRING;
	}

	@Override public String badNotification(String tag) {
		return EMPTY_STRING;
	}
}
