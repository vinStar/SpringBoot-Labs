package cn.iocoder.springboot.lab39.skywalkingdemo.controller;

/**
 * Copyright: Copyright (C) 2021 mumway, Inc. All rights reserved. <p>
 *
 * @author lt 2021/08/03 16:42
 * @Description
 */
public class test {

    public static void main(String[] args) {
        System.out.println("Output: "+Integer.parseInt("1000",2));
        System.out.println("Output: "+Integer.parseInt("10001000",2));
        System.out.println("Output: "+Integer.parseInt("100010001000",2));
        System.out.println("Output: "+Integer.parseInt("1000100010001000",2));
        System.out.println("Output: "+Long.parseLong("100010001000100010001000100010001000100010001000",2));
    }
}
