package cn.iocoder.springboot.lab72.utils;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * mpx
 */
@Component
@Slf4j
public class EasyExcelUtil {

	public static final String UTF_8 = "UTF-8";
	public static final String ISO8859_1 = "ISO8859-1";


	public static final String XLS = ".xls";

	public static final String XLSX = ".xlsx";

	public static final String CONTENT_TYPE = "application/octet-stream;charset=UTF-8";

	public static final String CONTENT_DISPOSITION = "Content-Disposition";

	public static final String EXPOSE_HEADERS = "Access-Control-Expose-Headers";


	/**
	 * 建议使用xlsx  导出数据多
	 *
	 * @param response
	 * @param head
	 * @param data
	 * @param tableName
	 * @param <T>
	 * @throws Exception
	 */
	public static <T> void exportExcelXlsx(HttpServletResponse response, Class head, List data, String tableName, String sheetName) throws Exception {
		response.setContentType(CONTENT_TYPE);
		response.setCharacterEncoding(UTF_8);
		response.addHeader("Accept-Language", "zh-cn");
		String filename = tableName + XLSX;
		response.setHeader(CONTENT_DISPOSITION, "attachment;filename=" + java.net.URLEncoder.encode(filename, UTF_8));
		response.setHeader(EXPOSE_HEADERS, CONTENT_DISPOSITION);
		EasyExcel.write(response.getOutputStream(), head).autoCloseStream(Boolean.FALSE).sheet(sheetName)
				.doWrite(data);
	}

	/**
	 * @param response
	 * @param head
	 * @param data
	 * @param tableName
	 * @param <T>
	 * @throws Exception
	 */
	public static <T> void exportExcelXls(HttpServletResponse response, Class head, List data, String tableName, String sheetName) throws Exception {
		response.setContentType(CONTENT_TYPE);
		response.setCharacterEncoding(UTF_8);
		String filename = tableName + XLS;
//		response.setHeader(CONTENT_DISPOSITION, "attachment;filename=" + new String(filename.getBytes(UTF_8), ISO8859_1));
		response.addHeader("Accept-Language", "zh-cn");
		response.setHeader(CONTENT_DISPOSITION, "attachment;filename=" + java.net.URLEncoder.encode(filename, UTF_8));
		response.setHeader(EXPOSE_HEADERS, CONTENT_DISPOSITION);
		EasyExcel.write(response.getOutputStream(), head).autoCloseStream(Boolean.FALSE).sheet(sheetName)
				.doWrite(data);
	}

	public static String encodeFileName(String fileNames, HttpServletRequest request) {
		String codedFilename = null;
		try {
			String agent = request.getHeader("USER-AGENT");
			if (null != agent && -1 != agent.indexOf("MSIE") || null != agent && -1 != agent.indexOf("Trident") || null != agent && -1 != agent.indexOf("Edge")) {// ie浏览器及Edge浏览器
				String name = java.net.URLEncoder.encode(fileNames, "UTF-8");
				codedFilename = name;
			} else if (null != agent && -1 != agent.indexOf("Mozilla")) {
				// 火狐,Chrome等浏览器
				codedFilename = new String(fileNames.getBytes(UTF_8), "iso-8859-1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codedFilename;
	}
}
