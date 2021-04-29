package cn.iocoder.springboot.labs.controller;


import cn.iocoder.springboot.labs.utils.BusinessException;
import cn.iocoder.springboot.labs.utils.DateUtil;
import cn.iocoder.springboot.labs.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 用户 Controller
 * <p>
 * 示例代码，纯粹为了演示。
 */
@Slf4j
@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @GetMapping("/get")
    public void get(HttpServletResponse response) {

        exportCardPicture(response);

    }

    @Resource(name = "export")
    private ExecutorService executorService;


    public void exportCardPicture(HttpServletResponse response) {

        ZipOutputStream zos = null;
        try {


            // 文件名
            String folder = "student-phone_" + DateUtil.date2Str(LocalDate.now()) + ".zip";
            // 转为中文编码
            URLEncoder.encode(folder, "UTF-8");
            // 设置返回类型为文件流
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            // 设置下载框的文件名
            response.setHeader("Content-Disposition", "attachment;filename=" + folder);

            zos = new ZipOutputStream(response.getOutputStream());
            // 线程池批量读取后导出
            int subSize = 1;
            List<String> urlList = new ArrayList<>();
            urlList.add("http://wiki.mumway.com/download/attachments/13108718/image2021-4-10_15-45-50.png?version=1&modificationDate=1618040751000&api=v2");
            urlList.add("https://img-blog.csdnimg.cn/20190926132357465.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L05hdGhhbm5pdUJlZQ==,size_16,color_FFFFFF,t_70");
            urlList.add("https://img-blog.csdnimg.cn/20190926132727631.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L05hdGhhbm5pdUJlZQ==,size_16,color_FFFFFF,t_70");
            urlList.add("https://img-blog.csdnimg.cn/20190926133923814.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L05hdGhhbm5pdUJlZQ==,size_16,color_FFFFFF,t_70");
            urlList.add("https://img-blog.csdnimg.cn/20190926131702249.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L05hdGhhbm5pdUJlZQ==,size_16,color_FFFFFF,t_70");
            CountDownLatch latch = new CountDownLatch(urlList.size());
            int i = 0;
            for (String subVoList : urlList) {

                String fileName = String.valueOf(i) + ".png";
                DownLoadBatchRunnable task = new DownLoadBatchRunnable(latch, zos, fileName, subVoList);
                i++;
                executorService.submit(task);
            }
            // 主线程最多等待2分钟, 防止阻塞，无法释放资源
            latch.await(2, TimeUnit.MINUTES);

            //  zos.flush();
        } catch (Exception e) {
            log.error("申报证书——用户证件批量压缩失败!, exception: ", e);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");

        } finally {

            if (null != zos) {
                try {

                    zos.close();
                } catch (Exception e) {
                    log.error("申报证书——用户证件批量压缩失败!, exception: ", e);
                }
            }
        }

    }

//    public void exportCardPicture2(HttpServletResponse response) {
//
//        ZipOutputStream zos = null;
//        try {
//
//
//            // 文件名
//            String folder = "student-phone_" + DateUtil.date2Str(LocalDate.now()) + ".zip";
//            // 转为中文编码
//            URLEncoder.encode(folder, "UTF-8");
//            // 设置返回类型为文件流
//            response.setContentType("application/octet-stream;charset=UTF-8");
//            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
//            // 设置下载框的文件名
//            response.setHeader("Content-Disposition", "attachment;filename=" + folder);
//
//            zos = new ZipOutputStream(response.getOutputStream());
//            // 线程池批量读取后导出
//            int subSize = 1;
//            List<String> urlList = new ArrayList<>();
//            urlList.add("http://wiki.mumway.com/download/attachments/13108718/image2021-4-10_15-45-50.png?version=1&modificationDate=1618040751000&api=v2");
//            urlList.add("https://img-blog.csdnimg.cn/20190926132357465.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L05hdGhhbm5pdUJlZQ==,size_16,color_FFFFFF,t_70");
//            urlList.add("https://img-blog.csdnimg.cn/20190926132727631.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L05hdGhhbm5pdUJlZQ==,size_16,color_FFFFFF,t_70");
//            urlList.add("https://img-blog.csdnimg.cn/20190926133923814.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L05hdGhhbm5pdUJlZQ==,size_16,color_FFFFFF,t_70");
//            urlList.add("https://img-blog.csdnimg.cn/20190926131702249.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L05hdGhhbm5pdUJlZQ==,size_16,color_FFFFFF,t_70");
//            CountDownLatch latch = new CountDownLatch(urlList.size());
//            List<Future<byte[]>> futureList = new ArrayList<>();
//            int i = 0;
//            for (String subVoList : urlList) {
//
//                String fileName = String.valueOf(i) + ".png";
//                DownLoadBatchRunnable2 task = new DownLoadBatchRunnable2(latch, zos, fileName, subVoList);
//                i++;
//                Future<byte[]> future = executorService.submit(task);
//                futureList.add(future);
//            }
//            // 主线程最多等待2分钟, 防止阻塞，无法释放资源
//            latch.await(2, TimeUnit.MINUTES);
//            List<byte[]> arrayList = new ArrayList<>();
//            //for(byte[] bytesture)
//
//
//        } catch (Exception e) {
//            log.error("申报证书——用户证件批量压缩失败!, exception: ", e);
//            response.setContentType("application/json");
//            response.setCharacterEncoding("utf-8");
//
//        } finally {
//
//            if (null != zos) {
//                try {
//
//                    zos.close();
//                } catch (Exception e) {
//                    log.error("申报证书——用户证件批量压缩失败!, exception: ", e);
//                }
//            }
//        }
//
//    }

    private static class DownLoadBatchRunnable implements Runnable {

        private final CountDownLatch latch;
        private final ZipOutputStream zos;
        private final String fileName;
        private final String url;

        public DownLoadBatchRunnable(CountDownLatch latch, ZipOutputStream zos, String fileName, String url) {
            this.latch = latch;
            this.zos = zos;
            this.fileName = fileName;
            this.url = url;
        }

        @Override
        public void run() {
            try {
                byte[] bytes = FileUtil.downloadHTTP(url);
                log.info("download");
                zos.putNextEntry(new ZipEntry(fileName));
                log.info("filename {} ", fileName);
                zos.write(bytes, 0, bytes.length);
                log.info("zos write");
            } catch (Exception | BusinessException e) {
                log.error("申报证书——用户证件图片导出失败! fileName: {}, url: {}， e", fileName, url, e);
            } finally {
                try {
                    zos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    zos.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            }
        }
    }
//
//    private static class DownLoadBatchRunnable2 implements Callable<byte[]> {
//
//        private final CountDownLatch latch;
//        private final ZipOutputStream zos;
//        private final String fileName;
//        private final String url;
//
//        public DownLoadBatchRunnable2(CountDownLatch latch, ZipOutputStream zos, String fileName, String url) {
//            this.latch = latch;
//            this.zos = zos;
//            this.fileName = fileName;
//            this.url = url;
//        }
//
//        @Override
//        public byte[] call() {
//            try {
//                byte[] bytes = FileUtil.downloadHTTP(url);
//
//                return bytes;
//            } catch (Exception | BusinessException e) {
//                log.error("申报证书——用户证件图片导出失败! fileName: {}, url: {}， e", fileName, url, e);
//            } finally {
//                try {
//                    zos.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    zos.closeEntry();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                latch.countDown();
//            }
//        }
//    }


}
