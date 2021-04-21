package cn.iocoder.springboot.lab72.controller;

import cn.iocoder.springboot.lab72.component.RsaClassLoader;
import cn.iocoder.springboot.lab72.utils.DateUtil;
import cn.iocoder.springboot.lab72.utils.EasyExcelUtil;
import cn.iocoder.springboot.lab72.utils.PropertyUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DbController {
    public static String url = "jdbc:mysql://192.168.20.211:3306/test_demo";
    //?useSSL=false
    public static String driver = "com.mysql.jdbc.Driver";
    public static String username = "root";
    public static String password = "centos";

    @GetMapping(value = "/getDb")
    public void main(HttpServletResponse response) throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException, MalformedURLException, NoSuchMethodException {

        //加载驱动
        //Class.forName(driver);
        Connection connection = null;
        PreparedStatement stat = null;
        ResultSet row = null;
        Class<?> clazz = null;
        try {
            clazz = loadClass();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        List<Object> objectList = new ArrayList<>();

        //创建连接
        try {
            connection = DriverManager.getConnection(url, username, password);
            //构造sql
            String sql = "SELECT * FROM msgcontent limit ?,?";
            stat = connection.prepareStatement(sql);

            stat.setInt(1, 0);
            stat.setInt(2, 10);

            //执行查询
            row = stat.executeQuery();
            while (row.next()) {
                //映射进实体类
                Object t = convertResultToEntity(row, clazz);
                objectList.add(t);
                System.out.println(JSON.toJSONString(t));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (row != null) {
                row.close();
            }
            if (stat != null) {
                stat.close();
            }
        }

        //调用导出工具类
        try {
            EasyExcelUtil.exportExcelXlsx(response, clazz, objectList,
                    "导出发票明细_" + DateUtil.date2Str(LocalDate.now()), "发票明细");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static <T> T convertResultToEntity(ResultSet resultSet, Class<T> tClass) throws Exception {
        T t = tClass.newInstance();
        ResultSetMetaData metaData = resultSet.getMetaData();
        for (int i = 0; i < metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i + 1);

            PropertyDescriptor descriptor = PropertyUtil.getPropertyDescriptor(tClass, columnName);
            if (descriptor != null) {
                //获得应该用于写入属性值的方法
                Method method = descriptor.getWriteMethod();
                method.invoke(t, resultSet.getObject(columnName));
            }
        }
        return t;
    }

    @Autowired
    RsaClassLoader rsaClassLoader;

    Class<?> loadClass() throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {


        String className2 = "com.example.api.MessageContent";

        URL url2 = new URL("file:///D:\\logs\\api-0.0.1-SNAPSHOT.jar");
        URLClassLoader classLoaderLeft2 = new URLClassLoader(new URL[]{url2});
        Class<?> clazz = classLoaderLeft2.loadClass(className2);

        Object obj = clazz.getClass();
//
//        URL url = new URL("file:///D:\\devDemo\\SpringBoot-Labs-vin\\lab-72-property-descriptor\\lab-72-jdbc-result-set\\target\\lab-72-jdbc-result-set-2.2.1.RELEASE.jar");
//        String className = "cn.iocoder.springboot.lab72.bean.MessageContent";
//
//        URLClassLoader classLoaderLeft = new URLClassLoader(new URL[]{url});


        //Class<T> tClass = (Class<T>) classLoaderLeft.loadClass(className);


        return clazz;

    }
}

