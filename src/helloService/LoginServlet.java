package helloService;


import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.IREM;

import bean.CommonJson;
import bean.LoginBean;
import bean.UploadBean;
import utls.SaveToDB;

//http://localhost:8888/helloService/login?userName=hello&passWord=56&number=8
public class LoginServlet extends HttpServlet {
	

	protected void doGet(HttpServletRequest request,HttpServletResponse response) {
		
//		Map<String,String> res=new HashMap<String, String>();
//		Enumeration<?> temp=request.getParameterNames();
//		if (null!=temp) {
//			while(temp.hasMoreElements()) {
//				String en=(String) temp.nextElement();
//				String value=request.getParameter(en);
//				res.put(en, value);
//				if (null==res.get(en)||"".equals(res.get(en))) {
//					res.remove(en);
//				}
//			}
//		}
		
		String userNameString=request.getParameter("userName");
		String passWordString=request.getParameter("passWord");
		String number=request.getParameter("number");
		try {
			LoginBean bean=new LoginBean(userNameString, passWordString,number);
			CommonJson<LoginBean> json=new CommonJson<LoginBean>();
			json.result="10000";
			json.msg="success";
			json.MerList=bean;
			Gson gson=new Gson();
			String result=gson.toJson(json).toString();
			response.setCharacterEncoding("UTF-8");
			response.setHeader("content-type", "text/html;charset=UTF-8");
			response.getWriter().write(result);
			System.out.println(result);
			System.out.println("接收到内容："+userNameString+"\t"+passWordString+"\t"+number);
			
			SaveToDB.saveToDB(userNameString, passWordString, number);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) {
		doGet(request, response);
	}
}
