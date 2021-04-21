package cn.iocoder.springboot.lab72.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;

import java.util.Date;

//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class MessageContent {
    private Integer id;
    private String title;
    private String message;

    @ExcelProperty(value = "时间")
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    private Date createDate;

    @Override
    public String toString() {
        return "MessageContent{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", createDate=" + createDate +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}

