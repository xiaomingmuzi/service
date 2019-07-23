package utls;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

//https://www.2cto.com/kf/201603/492916.html
public class MultipartRequestWrapper extends HttpServletRequestWrapper {
	// 存储参数的集合
	private Map<String, String[]> params = new HashMap<String, String[]>();
	private static String PATH = "D:\\uploadImg\\";// 设置上传图片路径

	public MultipartRequestWrapper(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
		setParams(request);
	}

	/**
	 * 通过StreamingAPI方式上传文件
	 * 
	 * @param request
	 */
	private void setParams(HttpServletRequest request) {
		// 获取上传文件类型
		if (ServletFileUpload.isMultipartContent(request)) {
			// 创建ServletFileUpload实例
			ServletFileUpload fileUpload = new ServletFileUpload();
			try {
				// 解析request请求，返回FileItemStream的iterator实例
				FileItemIterator iter = fileUpload.getItemIterator(request);
				InputStream is = null;// 输出流
				// 迭代取出
				while (iter.hasNext()) {
					FileItemStream itemStream = iter.next();// 获取文件流
					String name = itemStream.getFieldName();// 返回表单中标签的name值
					System.out.println("参数中的name标签："+name);
					is = itemStream.openStream();// 得到对应表单的输出流
					if (itemStream.isFormField()) {// 如果非文件域，设置进入map，这里注意多值处理
						setFormParam(name, is);
					} else {
						if (is.available() > 0) {// 如果输出流的内容大于0
							String fName = itemStream.getName();// 获取文件名
							long l=Streams.copy(is, new FileOutputStream(PATH + fName), true);// 拷贝内容到上传路径
							System.out.println("图片复制结果："+l);
							params.put(name, new String[] { fName });// 把文件名设置进request中
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else {
			params = request.getParameterMap();// 如果不是文件上传请求，则直接设置map
		}
	}

	/**
	 * 处理非上传的表单
	 * 
	 * @param name
	 * @param is
	 */
	private void setFormParam(String name, InputStream is) {
		try {
			if (params.containsKey(name)) {// 判断当前值是否存储过
				String[] values = params.get(name);// 去除已经存储过的值
				values = Arrays.copyOf(values, values.length + 1);// 把当前数组扩大
				values[values.length - 1] = Streams.asString(is);// 增加新值
				params.put(name, values);// 重新添加到map中
			} else {
				params.put(name, new String[] { Streams.asString(is) });// 直接存入参数
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 返回参数map集合，自定义后上传文件，上传成功则返回文件名
	 */
	@Override
	public Map<String, String[]> getParameterMap() {
		// TODO Auto-generated method stub
		return super.getParameterMap();
	}
	/**
	 * 从请求中取出参数
	 */
	@Override
	public String getParameter(String name) {
		// TODO Auto-generated method stub
		String[] values=params.get(name);
		if (values!=null) {
			return values[0];
		}
		return null;
	}
	
	/**
	 * 从参数中取出多个参数值，如checkbox的值
	 */
	@Override
	public String[] getParameterValues(String name) {
		// TODO Auto-generated method stub
		String[] values=params.get(name);
		if (values!=null) {
			return values;
		}
		return null;
	}
}
