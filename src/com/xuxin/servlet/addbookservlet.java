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
		//����һ������
		DiskFileItemFactory factory=new DiskFileItemFactory();
		//����һ��ServletFileUpload����
		ServletFileUpload sfu=new ServletFileUpload(factory);
		sfu.setHeaderEncoding("UTF-8");//����ϴ��ļ�������
		
		//����request����
		List<FileItem> fileitem=new ArrayList<FileItem>(0);
		//���ڷ�װ��ʹ��������
		Map<String, String[]> map = new HashMap<String, String[]>();
		
		try {
			fileitem=sfu.parseRequest(request);
			//����
			for (FileItem fileItem2 : fileitem) {
				if(fileItem2.isFormField()){
					//��ͨ����
					String name=fileItem2.getFieldName();//�õ�������
					String value=fileItem2.getString("UTF-8");//�õ��ֶε�ֵ
					/*System.out.println(name+"+"+value);*/
					map.put(name, new String[]{value});//��map�и�ֵ
				}else{
					//�ļ�����
					InputStream inputStream=fileItem2.getInputStream();
					String filename=fileItem2.getName();//�õ��ϴ����ļ���
					//�������ȫ�����⣬�������׺����exe����jsp�Ͳ����ϴ������ǲ��Ͻ������Խ�exe�ļ��ĺ�׺����Ϊ�����ĺ�׺��һ�������ϴ�
					String extension = FilenameUtils.getExtension(filename);
					
					if(!("jsp".equals(extension)||"exe".equals(extension))){
						//����Ŀ¼
						File storeDirectory=new File(this.getServletContext().getRealPath("/upload"));
						if(!storeDirectory.exists()){
							storeDirectory.mkdirs();//���Ŀ¼�����ڣ��ʹ���Ŀ¼
						}
						if(filename!=null){
							//��ȡfilename���ļ�������ɾ������·��
							filename=FilenameUtils.getName(filename);
						}
						//Ŀ¼��ɢ
						String childDirectory = makeChildDirectory(storeDirectory, filename); 
						//�ļ��ϴ�   ,File.separatorΪб��
						filename=childDirectory+File.separator+filename;
						fileItem2.write(new File(storeDirectory,filename));
						fileItem2.delete();//ɾ�����棨��ʱ���ļ�
						map.put(fileItem2.getFieldName(), new String[]{filename});//��ͼƬ����ĵ�name��value����map
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
			//����ҵ���߼�
			BookServiceImpl bs=new BookServiceImpl();
			bs.addBook(book);
			//�ַ�ת��
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
//		System.out.println(book.toString()+"servlet��");
//		BookServiceImpl bs=new BookServiceImpl();
//		bs.addBook(book);
		//�ַ�ת��
//		request.getRequestDispatcher("bookListServlet").forward(request, response);
	
	}
	// Ŀ¼��ɢ
		private String makeChildDirectory(File storeDirectory, String filename) {
			int hashcode = filename.hashCode();// �����ַ�ת����32λhashcode��
			System.out.println(hashcode);
			String code = Integer.toHexString(hashcode); // ��hashcodeת��Ϊ16���Ƶ��ַ�
															// abdsaf2131safsd
			System.out.println(code);
			String childDirectory = code.charAt(0) + File.separator
					+ code.charAt(1); // a/b
			// ����ָ��Ŀ¼
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
