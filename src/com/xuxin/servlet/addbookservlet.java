package com.xuxin.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.sun.org.apache.commons.beanutils.BeanUtils;
import com.xuxin.domain.Book;
import com.xuxin.service.BookServiceImpl;
import com.xuxin.utils.UUIDUtil;

public class addbookservlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//创建一个工厂
		DiskFileItemFactory factory=new DiskFileItemFactory();
		//创建一个ServletFileUpload对象
		ServletFileUpload sfu=new ServletFileUpload(factory);
		sfu.setHeaderEncoding("UTF-8");//解决上传文件的乱码
		
		//解析request对象
		List<FileItem> fileitem=new ArrayList<FileItem>(0);
		//用于封装普痛表单项数据
		Map<String, String[]> map = new HashMap<String, String[]>();
		
		try {
			fileitem=sfu.parseRequest(request);
			//迭代
			for (FileItem fileItem2 : fileitem) {
				if(fileItem2.isFormField()){
					//普通表单项
					String name=fileItem2.getFieldName();//得到表单的名
					String value=fileItem2.getString("UTF-8");//得到字段的值
					/*System.out.println(name+"+"+value);*/
					map.put(name, new String[]{value});//向map中赋值
				}else{
					//文件表单项
					InputStream inputStream=fileItem2.getInputStream();
					String filename=fileItem2.getName();//得到上传的文件名
					//解决不安全的问题，即如果后缀名是exe或者jsp就不让上传：但是不严谨，可以将exe文件的后缀名改为其他的后缀名一样可以上传
					String extension = FilenameUtils.getExtension(filename);
					
					if(!("jsp".equals(extension)||"exe".equals(extension))){
						//创建目录
						File storeDirectory=new File(this.getServletContext().getRealPath("/upload"));
						if(!storeDirectory.exists()){
							storeDirectory.mkdirs();//如果目录不存在，就创建目录
						}
						if(filename!=null){
							//获取filename的文件名，即删除绝对路径
							filename=FilenameUtils.getName(filename);
						}
						//目录打散
						String childDirectory = makeChildDirectory(storeDirectory, filename); 
						//文件上传   ,File.separator为斜杠
						filename=childDirectory+File.separator+filename;
						fileItem2.write(new File(storeDirectory,filename));
						fileItem2.delete();//删除缓存（临时）文件
						map.put(fileItem2.getFieldName(), new String[]{filename});//将图片表单项的的name和value存入map
					}
				}
			}
		/*	Iterator<Map.Entry<String, String[]>> iterator = map.entrySet().iterator();
	        while (iterator.hasNext()) {
	            Entry<String, String[]> entry = iterator.next();
	            System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
	        }*/
			Book book=new Book();
			BeanUtils.populate(book, map);
			book.setId(UUIDUtil.getUUID());
			//调用业务逻辑
			BookServiceImpl bs=new BookServiceImpl();
			bs.addBook(book);
			//分发转向
			request.getRequestDispatcher("bookListServlet").forward(request, response);
			
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		request.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html;charset=UTF-8");
//		Book book=new Book();
//		try {
//			BeanUtils.populate(book, request.getParameterMap());
//			book.setId(UUIDUtil.getUUID());
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(book.toString()+"servlet中");
//		BookServiceImpl bs=new BookServiceImpl();
//		bs.addBook(book);
		//分发转向
//		request.getRequestDispatcher("bookListServlet").forward(request, response);
	
	}
	// 目录打散
		private String makeChildDirectory(File storeDirectory, String filename) {
			int hashcode = filename.hashCode();// 返回字符转换的32位hashcode码
			System.out.println(hashcode);
			String code = Integer.toHexString(hashcode); // 把hashcode转换为16进制的字符
															// abdsaf2131safsd
			System.out.println(code);
			String childDirectory = code.charAt(0) + File.separator
					+ code.charAt(1); // a/b
			// 创建指定目录
			File file = new File(storeDirectory, childDirectory);
			if (!file.exists()) {
				file.mkdirs();
			}
			return childDirectory;
		}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
		
	}

}
