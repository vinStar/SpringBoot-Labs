package cn.iocoder.springboot.lab39.skywalkingdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/echo")
    public String echo() {
        return "echo";
    }

}
