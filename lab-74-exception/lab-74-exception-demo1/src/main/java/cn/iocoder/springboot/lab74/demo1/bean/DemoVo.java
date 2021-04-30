package cn.iocoder.springboot.lab74.demo1.bean;

import lombok.ToString;

@ToString

public class DemoVo {
    private String test;

    public DemoVo(String str) {
        this.test = str;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getTest() {
        return this.test;
    }
}
