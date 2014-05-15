package com.httpclienthelper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observer;

import java.util.Observable;

public class ObservingService {

    private static ObservingService                   _uniqueInstance;
    private        HashMap<String, PrivateObservable> _observables;   
    
	private ObservingService() {		
		_observables = new HashMap<String, PrivateObservable>();
	}
		
	public static synchronized ObservingService getInstance() {
			
	    if (_uniqueInstance == null) {
	    	_uniqueInstance = new ObservingService();
		}
		return _uniqueInstance;
	}
	
	public void addObserver(String notification, Observer observer) {
		PrivateObservable observable = _observables.get(notification);
        if (observable == null) {
            observable = new PrivateObservable();
            _observables.put(notification, observable);
        }
        observable.addObserver(observer);
    }

    public void removeObserver(String notification, Observer observer) {
    	PrivateObservable observable = _observables.get(notification);
        if (observable!=null) {         
            observable.deleteObserver(observer);
        }
    }       

    public void removeObserver(Observer observer) {
    	
    	Map<String, PrivateObservable> map = _observables;
    	for(Iterator<Map.Entry<String, PrivateObservable>> it = map.entrySet().iterator(); it.hasNext(); ) {
    	      Map.Entry<String, PrivateObservable> entry = it.next();
    	      if(entry.getValue().equals(observer)) {
    	        it.remove();
    	      }
        }	
    }
    
    public void postNotification(Object notification) {    	
    	
    	try {
    		RequestNotification requestnotification = (RequestNotification)notification;
    	    PrivateObservable   observable  = _observables.get(requestnotification.getRequestName());
            if (observable!=null) {       	
        	    observable.notifyObservers(notification);
            }
    	} catch (NullPointerException e) {
    		e.printStackTrace();
    	} catch (ClassCastException e) {
    		e.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    private class PrivateObservable extends Observable {
    	
        /* To force notifications to be sent */
        public void notifyObservers(Object data) {
            setChanged();
            super.notifyObservers(data);
        }
    }
}
