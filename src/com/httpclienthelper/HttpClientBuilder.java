package com.httpclienthelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.net.Uri; 

public class HttpClientBuilder {

	public  static final String              API_LAST_BUILD_DATE     = "2013-02-18";
	public  static final String              X_BUILD_DATE_HEADER     = "X-Build-Date";
	public  static final String              IMAGE_COMPONENT         = "image";
	public  static final String              CONTENT_TYPE_HEADER     = "Content-type";
	public  static final String              CONTENT_TYPE_VALUE      = "application/x-www-form-urlencoded";
	public  static final String              UPLOAD_NOTIFICATION     = "uploadProgressNotification";
	
	public  static final Integer             HTTP_GET_METHOD         = 0;
	public  static final Integer             HTTP_POST_METHOD        = 1;
	public  static final Integer             HTTP_PUT_METHOD         = 2;
	
	public  static final Integer             STATUS_CODE_OK          = 200;
	public  static final Integer             STATUS_CODE_BAD_REQUEST = 400;
	public  static final Integer             STATUS_CODE_ERROR       = 500;
	public  static final Integer             STATUS_UPGRADE_REQUIRED = 426;
	
	private              Integer             _httpMethod;
	private              RequestAttributes   _attributes;
	private              HttpClient          _client;
	private              long                _totalSize;
	private              HttpPost            _request;
	private              List<NameValuePair> _headers;
	
	/*
	 * Constructors
	 */

	public HttpClientBuilder(Integer method, RequestAttributes attributes) {
		
		_httpMethod = method;
		_attributes = attributes;
		_client     = new DefaultHttpClient();
		_totalSize  = 0;
		_headers    = new ArrayList<NameValuePair>();
		
		setRequestHeaders(attributes.getHeaders());
	}

	/*
	 * Actions
	 */
	
	public void abort() {
		
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		        try {
		        	if(_request != null) {
						_request.abort();
					}
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		});
		thread.start();
	}
	
	/*
	 * Access
	 */
	
	public HttpClient getClient() {
		return _client;
	}

	/*
	 * Request
	 */

	public HttpResponse makeMultiPartEntityRequest(String link, String path, String name, HashMap<String, Object> parts) {
		
		HttpResponse result  = null;
		HttpPost     request = new HttpPost(link);			
		_request             = request;

		try {
			
			File                    file      = new File(path);
			ProgressMultipartEntity multipart = new ProgressMultipartEntity(new ProgressMultipartEntity.ProgressListener() {
				@Override public void transferred(long num) {
					
					Integer             progress            = (int) ((num / (float)_totalSize) * 100);
					RequestNotification requestnotification = new RequestNotification();
					requestnotification.setRequestName(UPLOAD_NOTIFICATION);
					requestnotification.addValueForKey("progress", progress);
					
					ObservingService.getInstance().postNotification(requestnotification);
					
				}
			});
			
			multipart.addPart((name.equals(IMAGE_COMPONENT) ? "image" : "file"), new FileBody(file));
			if(parts != null) {
				for (Map.Entry<String, Object> entry : parts.entrySet()) {
				    multipart.addPart(entry.getKey(), new StringBody(entry.getValue().toString()));
				}
			}
			_totalSize = multipart.getContentLength();
			
			request.setEntity(multipart);

			result = _client.execute(request);

		} catch (ClientProtocolException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		
		return result;
	}
	
	public HttpResponse makeMultiPartEntityRequest(String link, Uri uri, String name, HashMap<String, Object> parts) {
		
		HttpResponse result  = null;
		HttpPost     request = new HttpPost(link);			
		_request             = request;
		
		request.setHeader(API_LAST_BUILD_DATE, X_BUILD_DATE_HEADER);

		try {
			
			File            file              = new File(uri.getPath());
			ProgressMultipartEntity multipart = new ProgressMultipartEntity(new ProgressMultipartEntity.ProgressListener() {
				@Override public void transferred(long num) {
					
					Integer             progress            = (int) ((num / (float)_totalSize) * 100);
					RequestNotification requestnotification = new RequestNotification();
					requestnotification.setRequestName(UPLOAD_NOTIFICATION);
					requestnotification.addValueForKey("progress", progress);
					
					ObservingService.getInstance().postNotification(requestnotification);
					
				}
			});
			
			multipart.addPart((name.equals(IMAGE_COMPONENT) ? "image" : "file"), new FileBody(file));
			if(parts != null) {
				for (Map.Entry<String, Object> entry : parts.entrySet()) {
				    multipart.addPart(entry.getKey(), new StringBody(entry.getValue().toString()));
				}
			}
			_totalSize = multipart.getContentLength();
			
			request.setEntity(multipart);

			result = _client.execute(request);

		} catch (ClientProtocolException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		
		return result;
	}
	
	public HttpResponse makeRequest(String link) {
		
		HttpResponse result = null;
 
		if(_httpMethod == HTTP_GET_METHOD) {

			HttpGet request = new HttpGet(link);			
			_addHeaders(request);
			
			try {
				result = _client.execute(request);
			} catch (IOException e) {
				return null;
			} catch (Exception e) {
				return null;
			}

		} else if(_httpMethod == HTTP_PUT_METHOD) {

			HttpPut request = new HttpPut(link);			
			_addHeaders(request);

			try {
				
				if(_attributes == null) {
					return null;
				}

				List<NameValuePair> entities = _attributes.getEntities();
				if(entities != null && entities.size() > 0) {
					request.setEntity(new UrlEncodedFormEntity(entities, "UTF8"));	
				}	

				result = _client.execute(request);

			} catch (ClientProtocolException e) {
				return null;
			} catch (IOException e) {
				return null;
			}

		} else if(_httpMethod == HTTP_POST_METHOD) {


			HttpPost request = new HttpPost(link);			
			_addHeaders(request);
			
			_request         = request;
			 
			try {

				if(_attributes == null) {
					return null;
				}

				if(_attributes.getFilePath() != null) {
					
					File       file    = new File(_attributes.getFilePath());
					final long filesize = file.length(); 
					ProgressEntity reqEntity = new ProgressEntity(new FileInputStream(file), -1, new ProgressEntity.ProgressListener() {
						@Override public void transferred(long num) {
							
							Integer             progress            = (int) ((num / (float)filesize) * 100);
							RequestNotification requestnotification = new RequestNotification();
							requestnotification.setRequestName(UPLOAD_NOTIFICATION);
							requestnotification.addValueForKey("progress", progress);
							
							ObservingService.getInstance().postNotification(requestnotification);
							
						}
					});
				    reqEntity.setContentType("binary/octet-stream");
				    
					request.setEntity(reqEntity);
					
				} else if(_attributes.getSingleEntity() != null && _attributes.getSingleEntity().length() > 0) {
					request.setEntity(new StringEntity(_attributes.getSingleEntity().toString()));
					
				} else if(_attributes.getSingleStringEntity() != null) {
					request.setEntity(_attributes.getSingleStringEntity());

				} else {
					List<NameValuePair> entities = _attributes.getEntities();
					if(entities != null && entities.size() > 0) {
						request.setEntity(new UrlEncodedFormEntity(entities, "UTF8"));	
					}		
				}
 
				result = _client.execute(request);

			} catch (ClientProtocolException e) {
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

		} else {
			return null;
		}
		return result;
	}

	/*
	 * Staus codes
	 */
	
	public static boolean statusGood(int status) {
		return status >= STATUS_CODE_OK && status < STATUS_CODE_OK + 100;
	}
	
	public static boolean statusBad(int status) {
		return status >= STATUS_CODE_BAD_REQUEST && status < STATUS_CODE_BAD_REQUEST + 100;
	}
	
	public static boolean statusError(int status) {
		return status >= STATUS_CODE_ERROR && status < STATUS_CODE_ERROR + 100;
	}
	
	/*
	 * Headers
	 */

	public void setRequestHeaders(List<NameValuePair> headers) {
		
		_headers = headers;
	}
	
	public void addHeader(String name, String value) {
		
		_headers.add(new BasicNameValuePair(name, value));
	}
	
	/*
	 * Support
	 */
	
	public void _addHeaders(HttpRequestBase request) {		
 
		for(NameValuePair p : _headers) {
			request.addHeader(p.getName(), p.getValue());
		}
	}
}
