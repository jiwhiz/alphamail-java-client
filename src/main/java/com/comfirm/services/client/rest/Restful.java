/*
The MIT License

Copyright (c) 2011 Comfirm <http://www.comfirm.se/>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

package com.comfirm.services.client.rest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import javax.xml.bind.DatatypeConverter;
import com.comfirm.services.client.rest.core.HttpHeaderCollection;
import com.comfirm.services.client.rest.core.HttpResponse;
import com.comfirm.services.client.rest.core.HttpResponseHead;
import com.comfirm.services.client.rest.core.HttpResponseStatusHead;
import com.comfirm.services.client.rest.core.HttpStatusCode;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class Restful<TResult, TBody> {

	private RestfulContext _context;
	
	public Restful(){
		this._context = new RestfulContext();
	}
	
	public Restful(RestfulContext context){
		this._context = context;
	}
	
	public void setContext(RestfulContext context){
		if(context == null){
			throw new NullPointerException();
		}
		
		this._context = context;
	}
	
	public RestfulContext getContext(){
		return this._context;
	}
	
	public HttpResponse<TResult> post(String url, Class<?> targetClass, TBody body)
	{
		try {
	    	InputStream stream = null;
	        URL invocationAddress = new URL(url);
	        HttpResponse<TResult> response = new HttpResponse<TResult>();
	        HttpURLConnection connection = (HttpURLConnection) invocationAddress.openConnection();
		    
	        try
	        {
		        try
		        {
		        	connection.setUseCaches(false);
		        	connection.setAllowUserInteraction(false);
			        connection.setDoOutput(true);
			        connection.setInstanceFollowRedirects(false);
			        
			        connection.setRequestMethod("POST");
			        connection.setReadTimeout(this.getContext().getConnectionReadTimeout());
			        
			        connection.setRequestProperty("User-Agent", "Comfirm Restful Java 1.00");
			        connection.setRequestProperty("Accept", "application/json");
			        connection.setRequestProperty("Connection", "close");
			        
			        UserCredentials credentials = this.getContext().getBasicAuthenticationCredentials();
			        
			        if(credentials != null){
			        	String autorizationValue = DatatypeConverter.printBase64Binary((credentials.getUsername() + ":" + credentials.getPassword()).getBytes());
			        	connection.addRequestProperty("Authorization", "Basic " + autorizationValue);
			        }
			        
			        OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
		        	new JSONSerializer().exclude("class").serialize(body, streamWriter);
		        	
		        	new JSONSerializer().exclude("class").serialize(body, new OutputStreamWriter(System.out));
		        	streamWriter.close();
		        	
			        stream = connection.getInputStream();
			    }catch(IOException exception){
			        stream = connection.getErrorStream();
			    }
			    
			    @SuppressWarnings("unchecked")
			    TResult result = (TResult) new JSONDeserializer<Object>()
			    	.deserialize(this.convertStreamToString(stream), targetClass);
			    
			    response.Head = new HttpResponseHead();			    
			    response.Head.Status = new HttpResponseStatusHead();
			    response.Head.Status.Code = HttpStatusCode.getType(connection.getResponseCode());
			    response.Head.Status.Message = connection.getResponseMessage();
			    response.Head.Headers = HttpHeaderCollection.FromMap(connection.getHeaderFields());
			    response.Result = result;
			    
			    return response;
	        }
	        finally
	        {
	        	if(stream != null){
	        		stream.close();
	        	}
	        	
	        	connection.disconnect();
	        }
	    } catch(Exception exception) { 
	        throw new RuntimeException(exception);
	    }
	}
	
	private String convertStreamToString(InputStream is) { 
	    return new Scanner(is).useDelimiter("\\A").next();
	}
}
