/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.hateoas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * A representation model class to be rendered as specified for the media type {@code application/vnd.error}.
 * 
 * @see https://github.com/blongden/vnd.error
 * @author Oliver Gierke
 */
@XmlRootElement(name = "errors")
public class VndErrors implements Iterable<VndErrors.VndError> {

	@XmlElement(name = "error")
	private final List<VndError> vndErrors;

	/**
	 * Creates a new {@link VndErrors} instance containing a single {@link VndError} with the given logref, message and
	 * optional {@link Link}s.
	 * 
	 * @param logref must not be {@literal null} or empty.
	 * @param message must not be {@literal null} or empty.
	 * @param links
	 */
	public VndErrors(String logref, String message, Link... links) {
		this(new VndError(logref, message, links));
	}

	/**
	 * Creates a new {@link VndErrors} wrapper for at least one {@link VndError}.
	 * 
	 * @param errors must not be {@literal null}.
	 * @param errors
	 */
	public VndErrors(VndError error, VndError... errors) {

		Assert.notNull(error, "Error must not be null");

		this.vndErrors = new ArrayList<VndError>(errors.length + 1);
		this.vndErrors.add(error);
		this.vndErrors.addAll(Arrays.asList(errors));
	}

	/**
	 * Protected default constructor to allow JAXB marshalling.
	 */
	protected VndErrors() {
		this.vndErrors = new ArrayList<VndError>();
	}

	/**
	 * Adds an additional {@link VndError} to the wrapper.
	 * 
	 * @param errors
	 */
	public VndErrors add(VndError error) {
		this.vndErrors.add(error);
		return this;
	}

	/**
	 * Dummy method to allow {@link JsonValue} to be configured.
	 * 
	 * @return the vndErrors
	 */
	@com.fasterxml.jackson.annotation.JsonValue
	@org.codehaus.jackson.annotate.JsonValue
	private List<VndError> getErrors() {
		return vndErrors;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<VndErrors.VndError> iterator() {
		return this.vndErrors.iterator();
	}

	/**
	 * A single {@link VndError}.
	 * 
	 * @author Oliver Gierke
	 */
	@XmlType
	public static class VndError extends ResourceSupport {

		@com.fasterxml.jackson.annotation.JsonProperty
		@org.codehaus.jackson.annotate.JsonProperty
		@XmlAttribute
		private final String logref;

		@com.fasterxml.jackson.annotation.JsonProperty
		@org.codehaus.jackson.annotate.JsonProperty
		@XmlElement
		private final String message;

		/**
		 * Creates a new {@link VndError} with the given logref, a message as well as some {@link Link}s.
		 * 
		 * @param logref must not be {@literal null} or empty.
		 * @param message must not be {@literal null} or empty.
		 * @param links
		 */
		public VndError(String logref, String message, Link... links) {

			Assert.hasText(logref, "Logref must not be null or empty!");
			Assert.hasText(message, "Message must not be null or empty!");

			this.logref = logref;
			this.message = message;
			this.add(Arrays.asList(links));
		}

		/**
		 * Protected default constructor to allow JAXB marshalling.
		 */
		protected VndError() {

			this.logref = null;
			this.message = null;
		}

		/**
		 * Returns the logref of the error.
		 * 
		 * @return the logref
		 */
		public String getLogref() {
			return logref;
		}

		/**
		 * Returns the message of the error.
		 * 
		 * @return the message
		 */
		public String getMessage() {
			return message;
		}
	}
}
