package cn.iocoder.springboot.lab75.demo.service;

import cn.iocoder.springboot.lab75.demo.fegin.InfoFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright: Copyright (C) 2021 mumway, Inc. All rights reserved. <p>
 *
 * @author lt 2021/08/12 15:25
 * @Description
 */
@Service
public class InfoService {

    @Autowired
    InfoFeignClient infoFeignClient;

    public String getOrderInfo() {

        infoFeignClient.getOrderInfo();

        return infoFeignClient.getOrderInfo();
    }
}
