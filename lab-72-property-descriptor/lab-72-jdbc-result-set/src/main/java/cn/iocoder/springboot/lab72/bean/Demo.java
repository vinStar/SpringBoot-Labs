package cn.iocoder.springboot.lab72.bean;

import lombok.ToString;

@ToString
public class Demo {
    private String test;

    public void setTest(String test) {
        this.test = test;
    }

    public String getTest() {
        return this.test;
    }
}
