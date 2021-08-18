package cn.iocoder.springboot.lab75.demo.fegin;


import org.springframework.web.bind.annotation.*;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author ：gtz
 * @date ：Created in 2021/1/15 10:42
 * @description：oa-approve 系统调用
 */
@FeignClient(name = "info", url = "${system.url.info:}")
public interface InfoFeignClient {

    /**
     * 调用 oa审批系统
     *
     * @return
     */
    @GetMapping("/getInfo")
    String getOrderInfo();


}
