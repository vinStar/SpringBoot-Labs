package cn.iocoder.springboot.lab72.bean;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@ApiModel("发票导入表格模板VO")
public class OrderInvoiceImportVO implements Serializable {

    @ExcelProperty(value = "发票申请单号")
    public String invApplicationNo;

    @ExcelProperty(value = "订单编号")
    public String orderNo;

    @ExcelProperty(value = "发票性质")
    public String invTitle;

    @ExcelProperty(value = "发票项目")
    public String invClassify;

    @ExcelProperty(value = "发票抬头")
    public String name;

    @ExcelProperty(value = "发票号")
    public String invNum;

//    @ExcelProperty(value = "开票金额")
//    public BigDecimal money;
//
//    @ExcelProperty(value = "开票日期")
//    public Date invTime;

    @ExcelProperty(value = "领票人姓名")
    public String ticketHolderName;

    @ExcelProperty(value = "领票人手机号")
    public String ticketHolderPhone;

    @ExcelProperty(value = "发票类型 1-专票 2-普票")
    public String invType;

    @ExcelProperty(value = "失败原因")
    public String failReason;

}
