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

package com.comfirm.alphamail.services.client;
import java.net.URI;
import com.comfirm.alphamail.services.client.entities.EmailMessagePayload;
import com.comfirm.alphamail.services.client.entities.ServiceIdentityResponse;
import com.comfirm.alphamail.services.client.entities.ServiceResponse;
import com.comfirm.alphamail.services.client.entities.internal.InternalEmailMessagePayload;
import com.comfirm.alphamail.services.client.entities.internal.InternalServiceIdentityResponse;
import com.comfirm.services.client.rest.Restful;
import com.comfirm.services.client.rest.RestfulContext;
import com.comfirm.services.client.rest.UserCredentials;
import com.comfirm.services.client.rest.core.HttpResponse;
 
public class DefaultAlphaMailService implements AlphaMailService {
	private URI _serviceUrl;
	private RestfulContext _restfulContext;
	
	public DefaultAlphaMailService(){
		this._restfulContext = new RestfulContext();
	}
	
	public DefaultAlphaMailService(String serviceUrl, String apiToken){
		this._restfulContext = new RestfulContext();
		this.setServiceUrl(serviceUrl);
		this.setApiToken(apiToken);
	}
	
	@Override
    public URI getServiceUrl() { 
        return _serviceUrl;
    }

	public DefaultAlphaMailService setServiceUrl(String serviceUrl) {
		this._serviceUrl = URI.create(serviceUrl);
		return this;
	}

	@Override
	public String getApiToken() {
		return this._restfulContext.getBasicAuthenticationCredentials().getPassword();
	}

	public DefaultAlphaMailService setApiToken(String apiToken) {
		this._restfulContext.setBasicAuthenticationCredentials(new UserCredentials("", apiToken));
		return this;
	}

	@Override
	public ServiceIdentityResponse queue(EmailMessagePayload payload) throws AlphaMailServiceException {
		HttpResponse<InternalServiceIdentityResponse> response;
		
        try
        {
			Restful<InternalServiceIdentityResponse, InternalEmailMessagePayload> restClient = new Restful<InternalServiceIdentityResponse, InternalEmailMessagePayload>(this._restfulContext);
			response = restClient.post(this._serviceUrl.toString() + "email/queue/", InternalServiceIdentityResponse.class, InternalEmailMessagePayload.map(payload));
	    }
	    catch (Exception exception)
	    {
	        exception.printStackTrace();
	        throw new AlphaMailServiceException("An unhandled exception occurred", null, null);
	    }

	    HandleErrors(response);	    
		return response.Result.map();
	}
	
	private static HttpResponse<InternalServiceIdentityResponse> HandleErrors(HttpResponse<InternalServiceIdentityResponse> response) throws AlphaMailServiceException
    {
        switch (response.Head.Status.Code)
        {
            // Successful requests
            case Accepted:
            case Created:
            case OK:
                if (response.Result.geterror_code() != 0){
                    throw new AlphaMailInternalException(
                		"Service returned success while response error code was set ("+response.Result.geterror_code()+")",
                        response.Head.Status,
                        null
                    );
                }
                break;

            // Unauthorized
            case Forbidden:
            case Unauthorized:
                throw new AlphaMailAuthorizationException(
            		response.Result.getmessage(),
                    response.Head.Status,
                    new ServiceResponse(
                		response.Result.geterror_code(),
                        response.Result.getmessage()
                    )
                );

            // Validation error
            case MethodNotAllowed:
            case BadRequest:
                throw new AlphaMailValidationException(
            		response.Result.getmessage(),
                    response.Head.Status,
                    new ServiceResponse(
                		response.Result.geterror_code(),
                        response.Result.getmessage()
                    )
                );

            // Internal error
            case InternalServerError:
                throw new AlphaMailInternalException(
                    "An internal error occurred. Please contact our support for more information.",
                    response.Head.Status,
                    new ServiceResponse(
                		response.Result.geterror_code(),
                        response.Result.getmessage()
                    )
                );

            // Unknown
            default:
                throw new AlphaMailServiceException(
                    "An unknown error occurred. Please contact our support for more information.",
                    response.Head.Status,
                    new ServiceResponse(
                		response.Result.geterror_code(),
                        response.Result.getmessage()
                    )
                );
        }

        return response;
    }
}
