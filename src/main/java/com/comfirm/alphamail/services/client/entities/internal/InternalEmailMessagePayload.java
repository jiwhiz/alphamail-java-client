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

package com.comfirm.alphamail.services.client.entities.internal;
import com.comfirm.alphamail.services.client.entities.EmailMessagePayload;

public class InternalEmailMessagePayload {
	private int _projectId;
	private long _receiverId;
	private String _senderName;
	private String _senderEmail;
	private String _receiverName;
	private String _receiverEmail;
	private String _body;
	
	public int getproject_id(){
		return this._projectId;
	}
	
	public void setproject_id(int projectId){
		this._projectId = projectId;
	}

	public long getreceiver_id(){
		return this._receiverId;
	}
	
	public void setreceiver_id(long receiverId){
		this._receiverId = receiverId;
	}
	
	public String getsender_name(){
		return this._senderName;
	}
	
	public void setsender_name(String senderName){
		this._senderName = senderName;
	}
	
	public String getsender_email(){
		return this._senderEmail;
	}
	
	public void setsender_email(String senderEmail){
		this._senderEmail = senderEmail;
	}
	
	public String getreceiver_name(){
		return this._receiverName;
	}
	
	public void setreceiver_name(String receiverName){
		this._receiverName = receiverName;
	}
	
	public String getreceiver_email(){
		return this._receiverEmail;
	}
	
	public void setreceiver_email(String receiverEmail){
		this._receiverEmail = receiverEmail;
	}
	
    public void setbody(String body){
    	this._body = body;
    }
    
    public String getbody(){
    	return this._body;
    }
    
    public static InternalEmailMessagePayload map(EmailMessagePayload source)
    {
    	InternalEmailMessagePayload result = new InternalEmailMessagePayload();
        
    	result.setproject_id(source.getProjectId());
    	result.setreceiver_id(source.getReceiverId());
    	result.setreceiver_email(source.getReceiverEmail());
    	result.setreceiver_name(source.getReceiverName());
        result.setsender_email(source.getSenderEmail());
        result.setsender_name(source.getSenderName());
        result.setbody(source.getBody());
            
        return result;
    }
}
