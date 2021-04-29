package cn.iocoder.springboot.lab72.controller;


import cn.iocoder.springboot.lab72.bean.loader.OrderInvoiceImportResultVO;
import cn.iocoder.springboot.lab72.bean.loader.OrderInvoiceImportVO;
import cn.iocoder.springboot.lab72.utils.DateUtil;
import cn.iocoder.springboot.lab72.utils.EasyExcelUtil;
import cn.iocoder.springboot.lab72.utils.POIUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lt-pc
 */
@RestController
//@AopRequestLog
//@Api(tags = "Excel")
@Slf4j
public class ExcelController {

    /**
     * 导入发票表格
     *
     * @return
     */
    @GetMapping(value = "/getData")
    public void getData(HttpServletResponse response
    ) {
        List<OrderInvoiceImportVO> orderInvoiceImportVOList = new ArrayList<>();
        OrderInvoiceImportVO vo = OrderInvoiceImportVO.builder()
                .invApplicationNo("aaaa")
                .orderNo("aa")
                .build();
        orderInvoiceImportVOList.add(vo);

        //调用导出工具类
        try {
            EasyExcelUtil.exportExcelXlsx(response, OrderInvoiceImportVO.class, orderInvoiceImportVOList,
                    "导出发票明细_" + DateUtil.date2Str(LocalDate.now()), "发票明细");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 导入发票表格
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/importData", consumes = "multipart/*", headers = "content-type=multipart/form-data")
//    @ApiOperation(value = "导入发票表格", notes = "导入表格", httpMethod = "POST")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "上传成功！"),
//            @ApiResponse(code = 500, message = "上传失败！")
//    })
    public void importData(HttpServletResponse response,
//                           @ApiParam(value = "excel导入数据", required = true)
                           @RequestParam("file") MultipartFile file) {
        List<OrderInvoiceImportVO> orderInvoiceImportVOList = fileUpload(file);


        //调用导出工具类
        try {
            EasyExcelUtil.exportExcelXlsx(response, OrderInvoiceImportVO.class, orderInvoiceImportVOList,
                    "导出发票明细_" + DateUtil.date2Str(LocalDate.now()), "发票明细");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public List<OrderInvoiceImportVO> fileUpload(MultipartFile file) {

        List<OrderInvoiceImportVO> invoiceImportVOS = null;
        List<OrderInvoiceImportVO> importVOList = new ArrayList<>();
        try {
//            if (!POIUtil.checkExtension(file)) {
//                baseResponse.setErrmsg("请求文件类型错误:后缀名错误");
//            }
//            if (POIUtil.isOfficeFile(file)) {
            log.info("开始导入表格...");
            // 对导入数据进行解析，并获取导入的列表List
            invoiceImportVOS = readExcel(file);
            log.info("获取导入的表格信息：{}" + JSON.toJSONString(invoiceImportVOS));
//            if (CollectionUtils.isEmpty(invoiceImportVOS)) {
//                //baseResponse.setErrmsg("请检查导入的表格数据！");
//            }
            if (!CollectionUtils.isEmpty(invoiceImportVOS)) {
                // 创建返回结果VO
                OrderInvoiceImportResultVO resultVO;
                // 导入成功条数
                Integer successCount = 0;
                // 导入失败条数
                Integer failCount = 0;
                // 遍历新插入的发票数据信息并入库

                List<String> invApplicationNoList = new ArrayList<>();
                for (OrderInvoiceImportVO invoiceImport : invoiceImportVOS) {
                    if (!StringUtils.isEmpty(invoiceImport.getInvApplicationNo())) {
                        invApplicationNoList.add(invoiceImport.getInvApplicationNo());
                    }
                }


                for (OrderInvoiceImportVO invoiceImportVO : invoiceImportVOS) {

                    failCount++;
                }
            }


        } catch (ParseException parseException) {
            parseException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
////            } else {
////                baseResponse.setErrmsg("请求文件类型错误:文件类型错误");
////            }
//        } catch (IOException e) {
//            log.error("上传文件时失败{},文件名称", e, file.getOriginalFilename());
//        } catch (ParseException e) {
//            log.error("文件读取失败，信息为：{}" + e);
//            // baseResponse.setErrmsg("Excel日期字段格式化错误：上传文件日期格式错误");
//            //throw new InvoiceException("文件解析错误", InvoiceErrorCode.IMPORT_PARSE_ERROR);
//        } catch (IllegalStateException e) {
//            //  baseResponse.setErrmsg("Excel数字格式错误!");
//            //  throw new InvoiceException("Excel数字格式错误", InvoiceErrorCode.IMPORT_PARSE_ERROR);
//        }

        return invoiceImportVOS;

    }

    /**
     * 利用Excel工具类从multipart中读取，并封装到List中
     *
     * @param file
     * @throws IOException
     * @throws ParseException
     */
    public List<OrderInvoiceImportVO> readExcel(MultipartFile file) throws IOException, ParseException {
        // 正确的文件类型 自动判断2003或者2007
        Workbook workbook = POIUtil.getWorkbookAuto(file);
        // 默认只有一个sheet
        Sheet sheet = workbook.getSheetAt(0);
        // 获得sheet有多少行
        int rows = sheet.getPhysicalNumberOfRows();
        Row row;
        boolean emptyRow = false;
        int cellNums = 0;
        String[] cells = null;
        Cell cell = null;
        // 用于接受表格数据
        List<OrderInvoiceImportVO> importDates = new ArrayList<>();
        // 读取表格花费时间
        Long poiReadStartTime = System.currentTimeMillis();
        // 读第一个sheet
        for (int i = 1; i < rows; i++) {
            row = sheet.getRow(i);

            // build构建OrderInvoiceImportVO实体类并赋值 用相应的List接受
            OrderInvoiceImportVO importVO = new OrderInvoiceImportVO();
//            if(StringUtils.isEmpty(row.getCell(0).getStringCellValue())){
//                throw new InvoiceException("发票申请单号为必填项!",InvoiceErrorCode.INV_APPLICATION_NO_NULL);
//            }
//            if(null == row.getCell(5)){
//                throw new InvoiceException("发票号为必填项!",InvoiceErrorCode.INV_NUM_NULL);
//            }
//            if(null == row.getCell(7).getDateCellValue()){
//                throw new InvoiceException("开票日期为必填项!",InvoiceErrorCode.INV_TIME_NULL);
//            }
//            if(StringUtils.isEmpty(row.getCell(8).getStringCellValue())){
//                throw new InvoiceException("领票人姓名为必填项!",InvoiceErrorCode.TICKET_HOLDER_NAME_NULL);
//            }
//            if(null == row.getCell(9)){
//                throw new InvoiceException("领票人手机号为空或者格式错误!",InvoiceErrorCode.TICKET_HOLDER_NAME_NULL);
//            }
            // 发票申请单编号
            importVO.setInvApplicationNo(row.getCell(0).getStringCellValue());
            // 订单号
            importVO.setOrderNo(row.getCell(1).getStringCellValue());
//            // 发票性质
//            importVO.setInvTitle(Optional.ofNullable(row.getCell(2).getStringCellValue()).orElse(""));
//            // 发票项目
//            importVO.setInvClassify(row.getCell(3).getStringCellValue());
//            // 发票抬头
//            importVO.setName(row.getCell(4).getStringCellValue());
//            // 发票号(必填)
//            Cell invNum = row.getCell(5);// 发票号
//            invNum.setCellType(CellType.STRING);
//            importVO.setInvNum(invNum.getStringCellValue());
//            // 开票日期(必填)
//            importVO.setInvTime(row.getCell(7).getDateCellValue());
//            // 申请开票金额
//            if (null != row.getCell(6)) {
//                importVO.setMoney(new BigDecimal(row.getCell(6).toString()));
//            }
//            // 领票人姓名(必填)
//            importVO.setTicketHolderName(row.getCell(8).getStringCellValue());
//            // 领票人手机号(必填)
//            importVO.setTicketHolderPhone(new DecimalFormat("#").format(row.getCell(9).getNumericCellValue()));
//            // 发票类型（普票/专票）
//            importVO.setInvType(row.getCell(10).getStringCellValue());
            importDates.add(importVO);
        }
        return importDates;
    }

}
