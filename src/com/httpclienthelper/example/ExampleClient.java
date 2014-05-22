package com.httpclienthelper.example;

import java.util.Observable;
import java.util.Observer;

import android.util.Log;

import com.httpclienthelper.HttpClientBuilder;
import com.httpclienthelper.ObservingService;
import com.httpclienthelper.RequestNotification;

public class ExampleClient implements Observer {

	ExampleManager _manager;
	
	public ExampleClient() {
		
	    _manager = new ExampleManager();
	    
	    /* We are the Observer */
	    _manager.setObserver(this);
	    
	    /* This is what we listen to */
		
	    /* repo request */
	    _manager.addObserver(ExampleManager.REPOS_SUCCESS);
		_manager.addObserver(ExampleManager.REPOS_FAIL);
		_manager.addObserver(ExampleManager.REPOS_BAD);
		
		/* image request */
		_manager.addObserver(ExampleManager.IMAGE_SUCCESS);
		_manager.addObserver(ExampleManager.IMAGE_FAIL);
		_manager.addObserver(ExampleManager.IMAGE_BAD);
		
		_manager.addObserver(HttpClientBuilder.UPLOAD_NOTIFICATION);
		
		/* kick off the request */
		_manager.getRepos();
		
	}
	
	@Override public void update(Observable observable, Object object) {
		
		RequestNotification requestnotification = (RequestNotification)object;
		String              requestname         = requestnotification.getRequestName();
		
		/* Your handling is based on the status */
		if(requestname.equals(ExampleManager.REPOS_SUCCESS)) {
			
			/* Once done, you can remove individual notifications like so */
			ObservingService.getInstance().removeObserver(ExampleManager.REPOS_SUCCESS, this);
			
		} else if(requestname.equals(ExampleManager.REPOS_BAD)) {
			
		} else if(requestname.equals(ExampleManager.REPOS_FAIL)) {
			
		} else if(requestname.equals(ExampleManager.IMAGE_SUCCESS)) {
			
		} else if(requestname.equals(ExampleManager.IMAGE_BAD)) {
			
		} else if(requestname.equals(ExampleManager.IMAGE_FAIL)) {
			
		} else if(requestname.equals(HttpClientBuilder.UPLOAD_NOTIFICATION)) {
			
			/* this is how you get the percentage of your progress */
			int progress = (Integer)requestnotification.getValues().get("progress"); 
			Log.d("progress", progress + "%");			
		}
		
	}
}
