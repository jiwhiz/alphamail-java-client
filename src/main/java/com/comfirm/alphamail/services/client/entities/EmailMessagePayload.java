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

package com.comfirm.alphamail.services.client.entities;
import flexjson.JSONSerializer;

public class EmailMessagePayload {
	private int _projectId = 0;
	private long _receiverId = 0;
	private String _senderName = "";
	private String _senderEmail = "";
	private String _receiverName = "";
	private String _receiverEmail = "";
	private String _body = "";
	
	public int getProjectId(){
		return this._projectId;
	}
	
	public EmailMessagePayload setProjectId(int projectId){
		this._projectId = projectId;
		return this;
	}

	public long getReceiverId(){
		return this._receiverId;
	}
	
	public EmailMessagePayload setReceiverId(long receiverId){
		this._receiverId = receiverId;
		return this;
	}
	
	public String getSenderName(){
		return this._senderName;
	}
	
	public EmailMessagePayload setSenderName(String senderName){
		this._senderName = senderName;
		return this;
	}
	
	public String getSenderEmail(){
		return this._senderEmail;
	}
	
	public EmailMessagePayload setSenderEmail(String senderEmail){
		this._senderEmail = senderEmail;
		return this;
	}
	
	public String getReceiverName(){
		return this._receiverName;
	}
	
	public EmailMessagePayload setReceiverName(String receiverName){
		this._receiverName = receiverName;
		return this;
	}
	
	public String getReceiverEmail(){
		return this._receiverEmail;
	}
	
	public EmailMessagePayload setReceiverEmail(String receiverEmail){
		this._receiverEmail = receiverEmail;
		return this;
	}
	
    public EmailMessagePayload setReceiver(EmailContact receiver)
    {
    	this.setReceiverName(receiver.getName());
    	this.setReceiverEmail(receiver.getEmail());
        return this;
    }

    public EmailMessagePayload setSender(EmailContact sender)
    {
    	this.setSenderName(sender.getName());
    	this.setSenderEmail(sender.getEmail());
        return this;
    }
    
    public String getBody(){
    	return this._body;
    }
    
    public <TObject> EmailMessagePayload setBodyObject(TObject source)
	{
    	if(source == null){
    		throw new NullPointerException("Source annot be NULL");
    	}
    	
	    this._body = new JSONSerializer().exclude("class").serialize(source);
	    return this;
	}
}