/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.icss.hit.struts.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import com.icss.hit.bean.OutputGraphBean;
import com.icss.hit.bean.interfaces.outputGraphDao;
import com.icss.hit.component.Excel;
import com.icss.hit.hibernate.vo.RoomReg;
import com.icss.hit.hibernate.vo.SysUser;
import com.icss.hit.struts.form.GraphOutputForm;

/** 
 * ����������ʹ�������ID
 * Creation date: 08-11-2009
 * @author ��ӱ��
 * XDoclet definition:
 * @struts.action path="/roomExcelExport" name="graphOutputForm" input="/outputGraph.do" scope="request" validate="true"
 * @struts.action-forward name="roomExcelExport.successd" path="/outputGraph.do"
 * @struts.action-forward name="roomExcelExport.fail" path="/outputGraph.do"
 */
public class RoomExcelExportAction extends Action {
	/*
	 * Generated Methods
	 */

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		GraphOutputForm graphOutputForm = (GraphOutputForm) form;// TODO Auto-generated method stub
		long uid=1;
		if( request.getSession().getAttribute("UserId") != null ){
			uid = Long.parseLong(request.getSession().getAttribute("UserId").toString());
		}else{
			return mapping.findForward("NullLogin");
		}
		
		String year = graphOutputForm.getYear();
		String month = graphOutputForm.getMonth();
		//�����������
		int Year = Integer.parseInt(year);
		//���������·�
		int Month = Integer.parseInt(month);
		
		outputGraphDao outputGraph = new OutputGraphBean();
		
		int conflict = outputGraph.getRoomCount(Year, Month);
		if(conflict == -1||conflict == 0)
		{
			//����ط�����picԭ���Ƿ��������ʱ�򣬿��Ա���ǰ�������,ͼƬ�ȵ�
			request.setAttribute("conflict", "�Բ��𣡣�û�����ݣ����޷���������");
			request.setAttribute("pic", "pic");
			request.setAttribute("month", month);
			request.setAttribute("year", year);
			return mapping.findForward("roomExcelExport.fail");
		}

		List<RoomReg> roomRegs = new ArrayList<RoomReg>();
		
		roomRegs = outputGraph.getRoomRegs(Year, Month);
		if(roomRegs == null)
		{
			request.setAttribute("roomRegsErrors", "�Բ��𣡣���ѯ���󣡣�");
			request.setAttribute("pic", "pic");
			request.setAttribute("month", month);
			request.setAttribute("year", year);
			return mapping.findForward("roomExcelExport.fail");
		}
		
		//ִ�е�����ط��Ժ��Ǹ����������ȫ������Ϣ��ȫ����ȡ�����ˣ���
		
		try{
			//�����ļ���
			Date date = new Date();
			String ExcelName = date.getTime() + "_" + uid + ".xls";
			Excel ex = new Excel();
			int sheetIndex = 0;
			String fileName = request.getRealPath("/excel/")  + "\\" + ExcelName;
			String SheetName = year+ "��"+month + "��" + "����������";
			
			boolean result = false;
			//��ָ��·�����½�һ��Excel�ļ���������һ��Excel�ļ��ĵ�һ�������롣��
			result = ex.createExcel(fileName);
			//���õ�ǰsheet�����������Ƽ�sheet�ţ�����һ��Excel�ļ��ĵڶ��������롣��
			result = ex.setCurSheet(sheetIndex, SheetName);
			//�����ļ��ɹ���������ʼд������
			if(result)
			{
				//�����е�ͷ��
				result = ex.setCellText(0, 0, "���");
				result = ex.setCellText(1, 0, "����");
				result = ex.setCellText(2, 0, "��������");
				result = ex.setCellText(3, 0, "��������");
				result = ex.setCellText(4, 0, "��ʼʱ��");
				result = ex.setCellText(5, 0, "����ʱ��");
				result = ex.setCellText(6, 0, "��ע");
				result = ex.setCellText(7, 0, "����");
				result = ex.setCellText(8, 0, "��������");
				for(int i=0;i<roomRegs.size() ;i++)            //����ȡ����ֵ
				{
					RoomReg roomReg = roomRegs.get(i);
					result = ex.setCellText(0, i+1, roomReg.getRrId().toString());
					result = ex.setCellText(1, i+1, roomReg.getRrTitle());
					result = ex.setCellText(2, i+1, roomReg.getRoom().getRId().toString());
					result = ex.setCellText(3, i+1, roomReg.getSysUser().getSuUsername());
					result = ex.setCellText(4, i+1, roomReg.getRrBegintime().toLocaleString());
					result = ex.setCellText(5, i+1, roomReg.getRrEndtime().toLocaleString());
					result = ex.setCellText(6, i+1, roomReg.getRrInfo());
					result = ex.setCellText(7, i+1, roomReg.getRrContent());
					result = ex.setCellText(8, i+1, roomReg.getRrAttendsum().toString());
				}
				result = ex.saveExcel();//����Excel
			}
			
			ex.closeExcel();//�ر�Excel
			if(!result)
			{
				//����ʧ��
				return mapping.findForward("roomExcelExport.fail");
				
			}
			
			
			// ��������ҳ����ת
			
			ActionRedirect redirect = new ActionRedirect(mapping.findForward("roomExcelExport.successd"));
			//��Month��picһ�𴫵ݣ���õ��˵���Excel֮�󣬲�����ʾ����Month��pictures
			redirect.addParameter("pic", "pic");
			redirect.addParameter("month", month);
			redirect.addParameter("year", year);
			// �����ɵ�Excel�ļ����ִ���
			redirect.addParameter("excel", ExcelName);
			return redirect;
		}catch(Exception e){
			e.printStackTrace();
			return mapping.findForward("roomExcelExport.fail");
		}
	}
}