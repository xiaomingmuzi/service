package helloService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;

import bean.CommonJson;
import bean.LoginBean;
import bean.UploadBean;
import utls.MultipartRequestWrapper;

public class UploadImg extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

		try {
			// 设置字符编码
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("content-type", "text/html;charset=UTF-8");

			request = new MultipartRequestWrapper(request);// 重新包装
			String UserID = request.getParameter("UserID");
			String Content = request.getParameter("Content");
			String fileString = request.getParameter("File1");

			Gson gson = new Gson();
			CommonJson<UploadBean> json = new CommonJson<UploadBean>();
			System.out.println("上传图片接收到内容：UserID：" + UserID + "\t\tContent：" + Content +(fileString==null?"\t\t未接收到图片":"\t\t"+fileString));
			UploadBean bean = new UploadBean(UserID, Content);
			json.MerList = bean;
			
			if (null == fileString) {
				json.result = "10001";
				json.msg = "未接收到图片";
				System.out.println("未接收到图片");
			} else {
				json.result = "10000";
				json.msg = "接收成功图片";
				System.out.println("接收到图片");
			}
			String result = gson.toJson(json).toString();
			response.getWriter().write(result);
			
//			if (request.getContentType() != null && (request.getContentType().contains("multipart/form-data")
//					|| request.getContentType().contains("multipart/mixed"))) {
//
//				// 获取图片的存放路径
//				String floderPath = "D:/uploadImg";
//
//				// 判断文件夹是否存在
//				File file = new File(floderPath);
//				if (!file.exists()) {
//					file.mkdirs();
//				}
//
//				// 使用文件上传组件
//				DiskFileItemFactory factory = new DiskFileItemFactory();
//				ServletFileUpload upload = new ServletFileUpload(factory);
//				upload.setHeaderEncoding("UTF-8");
//
//				// 解析请求信息
//				List<FileItem> list = upload.parseRequest(request);
//				if (null != list) {
//					for (FileItem fileItem : list) {
//						System.out.println(fileItem.toString());
//						String fieldName=fileItem.getFieldName();
//						if (fieldName.startsWith("File")) {
//							// 处理上传的文件保存的名称
//							String imgName = fileItem.getName();
//							imgName = imgName.substring(imgName.lastIndexOf("\\") + 1);
//
//							// 输入输出流
//							InputStream iStream = fileItem.getInputStream();
//							FileOutputStream fos = new FileOutputStream(new File(floderPath + "/" + imgName));
//
//							// 写入数据
//							byte[] buffer = new byte[1024];
//							int len = 0;
//							while ((len = (iStream.read(buffer))) > -1) {
//								fos.write(buffer, 0, len);
//							}
//
//							// 关闭输入输出流
//							fos.close();
//							iStream.close();
//
//							System.out.println("图片成功上传到：" + floderPath + "/" + imgName);
//						}else {
//							String UserID = request.getParameter("UserID");
//							String Content = request.getParameter("Content");
//							System.out.println("接收到内容：UserID：" + UserID + "\t\tContent：" + Content);
//							UploadBean bean = new UploadBean(UserID, Content);
//							json.MerList = bean;
//						}
//						json.result = "10000";
//						json.msg = "success";
//						String result = gson.toJson(json).toString();
//						response.getWriter().write(result);
//						System.out.println(result);
//					}
//				}
//			} else {
//				String UserID = request.getParameter("UserID");
//				String Content = request.getParameter("Content");
//				System.out.println("接收到内容：UserID：" + UserID + "\t\tContent：" + Content);
//				UploadBean bean = new UploadBean(UserID, Content);
//				
//				json.MerList = bean;
//				response.setHeader("content-type", "text/html;charset=UTF-8");
//				json.result = "10001";
//				json.msg = "未接收到图片";
//				String result = gson.toJson(json).toString();
//				response.getWriter().write(result);
//				System.out.println("未接收到图片");
//			}

//			json.result="10000";
//			json.msg="success";
//			String result=gson.toJson(json).toString();
//			response.getWriter().write(result);
//			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
