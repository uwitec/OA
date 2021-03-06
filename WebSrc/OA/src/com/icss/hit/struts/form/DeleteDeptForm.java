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

import com.icss.hit.bean.CardTypeBean;
import com.icss.hit.bean.DepartmentBean;
import com.icss.hit.bean.interfaces.CardTypeDao;
import com.icss.hit.bean.interfaces.Department;

/** 
 * MyEclipse Struts
 * Creation date: 08-11-2009
 * 
 * XDoclet definition:
 * @struts.form name="deleteDeptForm"
 */
public class DeleteDeptForm extends ActionForm {
	/*
	 * Generated Methods
	 */
	private String deptId;
	
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		ActionErrors errors = new ActionErrors();
		long deptid = Long.parseLong(deptId);
		Department dt = new DepartmentBean();
		//判断删除的部门里是否存在员工
		boolean check = dt.checkDept(deptid);
		
		if(check==false)
		
		errors.add("DeleteDeptExist", new ActionMessage("DeleteDept.Exist"));
		
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
}