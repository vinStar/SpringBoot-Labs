package cn.iocoder.springboot.lab74.demo1.controller;


import cn.iocoder.springboot.lab74.demo1.api.response.BaseResponse;
import cn.iocoder.springboot.lab74.demo1.bean.DemoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 用户 Controller
 * <p>
 * 示例代码，纯粹为了演示。
 */
@Slf4j
@RestController
public class UserController {

    @GetMapping("/get")
    public BaseResponse<DemoVo> get(HttpServletResponse response) {
        DemoVo demoVo = new DemoVo("test vo");
        return BaseResponse.success(demoVo);
    }

    @GetMapping("/get/1")
    public BaseResponse<DemoVo> getException(HttpServletResponse response) {
        DemoVo demoVo = new DemoVo("test vo");
        int num = 1 / 0;
        return BaseResponse.success(demoVo);
    }

}
