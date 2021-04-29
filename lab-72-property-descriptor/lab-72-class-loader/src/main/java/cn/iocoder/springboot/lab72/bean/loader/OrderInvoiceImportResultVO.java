package cn.iocoder.springboot.lab72.bean.loader;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
@Builder

public class OrderInvoiceImportResultVO implements Serializable {

//    @ApiModelProperty(value = "发票表格名称")
    private String invoiceTableName;

//    @ApiModelProperty(value = "成功条数")
    private Integer successCount;

//    @ApiModelProperty(value = "失败条数")
    private Integer failCount;

//    @ApiModelProperty(value = "操作人id")
    private Long operatorId;

//    @ApiModelProperty(value = "操作人名称")
    private String operatorName;

//    @ApiModelProperty(value = "导入时间")
//    @JsonFormat(locale= LOCALE_DEFAULT, timezone= TIME_ZONE_DEFAULT, pattern = DATE_TIME_DEFAULT_PATTERN)
//    private Date importTime;

//    @ApiModelProperty(value = "导入明细数组")
    private List<OrderInvoiceImportVO> importList;

}
