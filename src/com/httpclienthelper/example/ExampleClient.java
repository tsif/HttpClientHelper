package com.httpclienthelper.example;

import java.util.Observable;
import java.util.Observer;

import com.httpclienthelper.RequestNotification;

public class ExampleClient implements Observer {

	ExampleManager _manager;
	
	public ExampleClient() {
		
	    _manager = new ExampleManager();
	    
	    /* We are the Observer */
	    _manager.setObserver(this);
	    
	    /* This is what we listen to */
		_manager.addObserver(ExampleManager.REPOS_SUCCESS);
		_manager.addObserver(ExampleManager.REPOS_FAIL);
		_manager.addObserver(ExampleManager.REPOS_BAD);
		
		/* kick off the request */
		_manager.getRepos();
		
	}
	
	@Override public void update(Observable observable, Object object) {
		
		RequestNotification requestnotification = (RequestNotification)object;
		String              requestname         = requestnotification.getRequestName();
		
		/* Your handling is based on the status */
		if(requestname.equals(ExampleManager.REPOS_SUCCESS)) {
			
		} else if(requestname.equals(ExampleManager.REPOS_BAD)) {
			
		} else if(requestname.equals(ExampleManager.REPOS_FAIL)) {
			
		}
	}
}
