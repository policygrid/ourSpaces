/**
 * 
 */
package org.policygrid.workflow;

import java.util.LinkedHashMap;

/**
 * @author Hengfei Li
 * 
 */
public class AjaxResponseContent {
	private AjaxResponseStatus responseStatus;
	private String errorMessage;
	private LinkedHashMap<String, Object> data;
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public AjaxResponseContent() {
		this.data = new LinkedHashMap<String, Object>();
	}

	public AjaxResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(AjaxResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public LinkedHashMap<String, Object> getData() {
		return data;
	}

	public void setData(LinkedHashMap<String, Object> data) {
		this.data = data;
	}
}