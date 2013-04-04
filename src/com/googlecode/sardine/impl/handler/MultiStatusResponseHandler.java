/*
 * Copyright 2009-2011 Jon Stevens et al.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.sardine.impl.handler;

import com.googlecode.sardine.impl.SardineException;
import com.googlecode.sardine.model.Multistatus;
import com.googlecode.sardine.util.SardineUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

import java.io.IOException;
import java.io.InputStream;

/**
 * {@link org.apache.http.client.ResponseHandler} which returns the {@link Multistatus} response of
 * a {@link com.googlecode.sardine.impl.methods.HttpPropFind} request.
 *
 * @author mirko
 * @version $Id: MultiStatusResponseHandler.java 276 2011-06-28 08:13:28Z dkocher@sudo.ch $
 */
public class MultiStatusResponseHandler extends ValidatingResponseHandler<Multistatus>
{
	public Multistatus handleResponse(HttpResponse response) throws SardineException, IOException
	{
		super.validateResponse(response);

		// Process the response from the server.
		HttpEntity entity = response.getEntity();
		StatusLine statusLine = response.getStatusLine();
		if (entity == null)
		{
			throw new SardineException("No entity found in response", statusLine.getStatusCode(),
					statusLine.getReasonPhrase());
		}
		return this.getMultistatus(entity.getContent());
	}

	/**
	 * Helper method for getting the Multistatus response processor.
	 *
	 * @param stream The input to read the status
	 * @return Multistatus element parsed from the stream
	 * @throws IOException When there is a JAXB error
	 */
	protected Multistatus getMultistatus(InputStream stream)
			throws IOException
	{
		return SardineUtil.unmarshal(stream);
	}
}