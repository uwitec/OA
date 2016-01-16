/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.icss.hit.struts.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.icss.hit.bean.WorkPlanBean;
import com.icss.hit.bean.interfaces.WorkPlanDao;
import com.icss.hit.hibernate.vo.Schedule;
import com.icss.hit.hibernate.vo.SysUser;
import com.icss.hit.struts.form.AddWorkConfigForm;

/**
 * �����ճ̰���
 * Creation date: 08-03-2009
 * @author xw-pc
 * XDoclet definition:
 * 
 * @struts.action path="/addWorkConfig" name="addWorkConfigForm"
 *                input="/newWorkConfig.do" scope="request" validate="true"
 */
public class AddWorkConfigAction extends Action {
	/*
	 * Generated Methods
	 */

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AddWorkConfigForm addWorkConfigForm = (AddWorkConfigForm) form;

		WorkPlanDao cw = new WorkPlanBean();
		long userId = 1;
		if (request.getSession().getAttribute("UserId") != null) {
			userId = Long.parseLong(request.getSession().getAttribute("UserId")
					.toString());
		} else {
			return mapping.findForward("NullLogin");
		}
		// ʵ����һ���ճ̰���
		Schedule sch = new Schedule();
		boolean flag = true;

		// �ж�ʱ���Ƿ�Ϊ��
		if (addWorkConfigForm.getSch_begintime() != null
				&& addWorkConfigForm.getSch_endtime() != null
				&& addWorkConfigForm.getSch_begintime().length() > 0
				&& addWorkConfigForm.getSch_endtime().length() > 0
				&& addWorkConfigForm.getSch_content() != null
				&& addWorkConfigForm.getSch_content().length() > 0
				&& addWorkConfigForm.getSch_title() != null
				&& addWorkConfigForm.getSch_title().length() > 0) {

			SimpleDateFormat bartDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd kk:mm:ss");

			try {
				//��ʽ��ʱ��
				Date beginTime = bartDateFormat.parse(addWorkConfigForm
						.getSch_begintime());
				Date endTime = bartDateFormat.parse(addWorkConfigForm
						.getSch_endtime());
				
				sch.setSchBegintime(beginTime);
				sch.setSchEndtime(endTime);

				sch.setSchTitle(addWorkConfigForm.getSch_title());
				sch.setSchContent(addWorkConfigForm.getSch_content());
				sch.setSchRead("0");
				sch.setSchComplete("0");

				SysUser suTo = new SysUser();
				SysUser suFr = new SysUser();

				// �жϱ��������Ƿ��Ǳ���
				if (Long.parseLong(addWorkConfigForm.getSu_id_to()) == 0) {
					suTo.setSuId(userId);
				} else {
					suTo.setSuId(Long
							.parseLong(addWorkConfigForm.getSu_id_to()));
				}
				suFr.setSuId(userId);
				sch.setSysUserBySuIdFrom(suFr);
				sch.setSysUserBySuIdTo(suTo);

				List<Schedule> slist = cw.getSchedule(suTo.getSuId());

				// �ж��ճ̰���ʱ���Ƿ��ͻ
				try {
					for (int i = 0; slist != null && i < slist.size(); i++) {
						Schedule schedule = slist.get(i);
						// ����Ŀ�ʼʱ��Ҫ�����Ѿ����ŵ��ճ̽�����ʱ��
						long m = beginTime.getTime()
								- schedule.getSchEndtime().getTime();
						// ����Ľ���ʱ��Ҫ�����Ѿ����ŵ��ճ̿�ʼ��ʱ��
						long n = endTime.getTime()
								- schedule.getSchBegintime().getTime();
						if (m > 0 || n < 0) {
							continue;
						} else {
							flag = false;
							break;
						}

					}
				} catch (RuntimeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//�����µ��ճ̵����ݿ�
				if (flag) {
					if (cw.saveSchedule(sch)) {
						return mapping.findForward("AddWorkConfig.succeed");
					} else {
						return mapping.findForward("AddWorkConfig.faild");

					}
				} else {
					//��ʱ���ͻ�����ش�����Ϣ
					ActionErrors errors = new ActionErrors();
					errors.add("sch_begintime", new ActionMessage(
							"WorkAdd.Timeconflict"));
					saveErrors(request, errors);
					request.setAttribute("form", addWorkConfigForm);
				}
			}

			catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		return mapping.findForward("AddWorkConfig.faild");
	}
}