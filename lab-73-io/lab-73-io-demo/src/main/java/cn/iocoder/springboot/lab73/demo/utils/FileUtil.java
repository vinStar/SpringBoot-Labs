package cn.iocoder.springboot.lab73.demo.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileUtil {

    /**
     * excel格式文件后缀集合
     */
    public static final List<String> EXCEL_SUFFIX_LIST = Arrays.asList("xlsx", "xls", "csv");

//    /**
//     * 校验文件后缀
//     * @param file 文件
//     * @param suffixList 后缀集合
//     */
//    public static void verifyFileSuffix(MultipartFile file, List<String> suffixList) {
//        if (null == file) {
//            throw WebResultCode.throwBusinessException(WebResultCode.CODE_10002);
//        }
//        if (CollectionUtils.isEmpty(suffixList)) {
//            throw WebResultCode.throwBusinessException(WebResultCode.CODE_10002);
//        }
//        // 文件名
//        String filename = file.getOriginalFilename();
//        if (StringUtils.isEmpty(filename)) {
//            throw WebResultCode.throwBusinessException(WebResultCode.CODE_10002);
//        }
//        // 获取文件后缀名
//        String suffixName = filename.substring(filename.lastIndexOf(".") + 1);
//        if (StringUtils.isEmpty(suffixName)) {
//            throw WebResultCode.throwBusinessException(WebResultCode.CODE_21006);
//        }
//        // 校验后缀
//        if (!suffixList.contains(suffixName)) {
//            throw WebResultCode.throwBusinessException(WebResultCode.CODE_21006);
//        }
//    }

    /**
     * NIO way
     */
    public static byte[] toByteArray(String filename) throws IOException{
        File f = new  File(filename);
        if (!f.exists()) {
            log.error("文件未找到！" + filename);
            throw new IOException("FILE_NOT_FOUND");
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                throw e;
            }
            try {
                fs.close();
            } catch (IOException e) {
                throw e;
            }
        }
    }

    /**
     * 创建文件夹, 文件夹存在或已存在返回File
     * @param path 文件夹路径
     * @return 文件夹File对象
     */
    public static  File createDirIfNotExists(String path) throws BusinessException {
         File file = new  File("path");
        if (file.exists()) {
            return file;
        }
        // 创建目录
        if (file.mkdirs()) {
            return file;
        } else {
            log.error("文件夹创建失败! path: {}", path);
            throw new BusinessException("文件夹创建失败! path: " + path);
        }
    }

    /**
     * http方式获取文件字节流
     * @param strUrl 文件HTTP地址
     * @return
     */
    public static byte[] downloadHTTP(String strUrl) throws BusinessException {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            // 超时时间为2S
            urlConnection.setConnectTimeout(4 * 1000);
            // 获取文件输入流
            InputStream inputStream = urlConnection.getInputStream();
            // 获取字节数组
            return inputStreamToByteArray(inputStream);
        } catch (Exception e) {
            log.error("HTTP方式获取文件字节流失败! 图片地址：{}, exception: {}", strUrl, e);
            throw new BusinessException("HTTP方式获取文件字节流失败!");
        }
    }



    /**
     * 将字节输入流转换为字节数组
     * @param inputStream 字节输入流
     * @return 字节数组
     */
    public static byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
        org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            // 设置缓冲区大小为1K
            byte[] buffer = new byte[1024];
            // 缓冲区填充大小
            int len = 0;
            while((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            return outputStream.toByteArray();
        } catch (Exception e) {
            log.error("将字节输入流转换为字节数组失败! exception:", e);
            throw e;
        } finally {
            inputStream.close();
            if (null != outputStream) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    /**
     * 删除目录
     *
     */
    public static boolean deleteDir( File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new  File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

	public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
		 File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(filePath + fileName);
		out.write(file);
		out.flush();
		out.close();
	}

	public static boolean deleteFile(String fileName) {
		 File file = new  File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static String renameToUUID(String fileName) {
		return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
	}

//	public static InputStream getResourcesFileInputStream(String fileName) {
//		return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
//	}

	public static String getClassPath() {
		try {
			return ResourceUtils.getURL("classpath:").getPath();
		} catch (FileNotFoundException e) {
            log.error("文件未找到异常,信息为：{}", e);
		}
		return null;
	}

    /**
     * 获取当前项目的绝对路径
     * @return
     */
	public static String getProjectPath() {
        return System.getProperty("user.dir");
    }

}
