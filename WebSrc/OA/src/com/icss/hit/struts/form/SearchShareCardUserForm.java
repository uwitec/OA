/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.icss.hit.struts.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/** 
 * MyEclipse Struts
 * Creation date: 08-03-2009
 * 
 * XDoclet definition:
 * @struts.form name="searchShareCardUserForm"
 */
public class SearchShareCardUserForm extends ActionForm {
	/*
	 * Generated fields
	 */

	/** type property */
	private String type;

	/** suUser property */
	private String suUser;

	/** suSex property */
	private String suSex;

	/** suDept property */
	private String suDept;

	/*
	 * Generated Methods
	 */

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if( suUser == null || suUser.equals("")){
			errors.add("suUserError", new ActionMessage("SearchOtherInfo.suUserError"));
		}
		return errors;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
	}

	/** 
	 * Returns the type.
	 * @return String
	 */
	public String getType() {
		return type;
	}

	/** 
	 * Set the type.
	 * @param type The type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/** 
	 * Returns the suUser.
	 * @return String
	 */
	public String getSuUser() {
		return suUser;
	}

	/** 
	 * Set the suUser.
	 * @param suUser The suUser to set
	 */
	public void setSuUser(String suUser) {
		this.suUser = suUser;
	}

	/** 
	 * Returns the suSex.
	 * @return String
	 */
	public String getSuSex() {
		return suSex;
	}

	/** 
	 * Set the suSex.
	 * @param suSex The suSex to set
	 */
	public void setSuSex(String suSex) {
		this.suSex = suSex;
	}

	/** 
	 * Returns the suDept.
	 * @return String
	 */
	public String getSuDept() {
		return suDept;
	}

	/** 
	 * Set the suDept.
	 * @param suDept The suDept to set
	 */
	public void setSuDept(String suDept) {
		this.suDept = suDept;
	}
}