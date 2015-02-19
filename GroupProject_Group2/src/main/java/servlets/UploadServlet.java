package servlets;

import java.io.File;
import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.project.dao.BaseDataDAO;
import com.project.dao.ErrorBaseDataDAO;
import com.project.dao.LookUpDataDAO;
import com.project.reader.excel.ExcelBaseDataRead;
import com.project.reader.excel.ExcelLookupDataRead;


//import resources.ExcellLoader;

@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*2,	// 2MB 
				 maxFileSize=1024*1024*10,		// 10MB
				 maxRequestSize=1024*1024*50)	// 50MB
public class UploadServlet extends HttpServlet {

	/**
	 * Name of the directory where uploaded files will be saved, relative to
	 * the web application directory.
	 */
	private static final String SAVE_DIR = "uploadFiles";
	
	@EJB
	private BaseDataDAO dao;
	@EJB
	private ErrorBaseDataDAO errorDao;
	@EJB
	private LookUpDataDAO lookupDao;
	
	/**
	 * handles file upload
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// gets absolute path of the web application
		
		String appPath = request.getServletContext().getRealPath("");
		// constructs path of the directory to save uploaded file
		String savePath = appPath + File.separator + SAVE_DIR;
		
		// creates the save directory if it does not exists
		File fileSaveDir = new File(savePath);
		File myFile = new File(fileSaveDir + File.separator + "upload.txt");
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}
		
		String finalFilePath = "";
		String fileExtension = "*";
		for (Part part : request.getParts()) {
			String fileName = extractFileName(part);
			fileExtension = getFileExtension(fileName);
			finalFilePath = savePath + File.separator +"upload" + fileExtension;
			part.write(finalFilePath);
		}
		
		//TODO 
		/*
		 * request.getParameters("file");
		 * boolean addToTable = request.getParameters("importBoolean");
		 * 
		 * 
		 * response.setContentType("text/html")
		 * PrintWriter out = response.getWriter();
		 * out.println("<html><body> Hello! </body></html>");
		 * out.close();
		 * 
		 */
		
		String fileParam = request.getParameter("file");
		
		boolean addToTable = false;
		
		ExcelLookupDataRead lookupDataReader = new ExcelLookupDataRead();
		lookupDataReader.setInputFile(finalFilePath);
		lookupDataReader.setLookUpDao(lookupDao);
		lookupDataReader.read();
		lookupDataReader = null;
		
		ExcelBaseDataRead baseDataReader = new ExcelBaseDataRead();
		baseDataReader.setSheetNumber(0);
		baseDataReader.setInputFile(finalFilePath);
		baseDataReader.setBaseDataDao(dao);
		baseDataReader.setErrorBaseDataDao(errorDao);
		baseDataReader.read();
		int numOfInvalidRows = baseDataReader.getInvalidRowCount();
		baseDataReader = null;
		
		request.setAttribute("message", "Upload has completed successfully!"
				+ "<br>There were " + numOfInvalidRows + " invalid rows in the base data<br>" + 
				" fileParam = " + fileParam);
		getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
	}

	/**
	 * Extracts file name from HTTP header content-disposition
	 */
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length()-1);
			}
		}
		return "";
	}
	
	private String getFileExtension(String fileName){
		int location = 0;
		for(int i=fileName.length()-1; i>0; i--){
			if(fileName.charAt(i)=='.'){
				location = i;
				break;
			}
		}
		return fileName.substring(location);
	}
}