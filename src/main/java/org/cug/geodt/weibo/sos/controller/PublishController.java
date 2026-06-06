package org.cug.geodt.weibo.sos.controller;

import lombok.extern.slf4j.Slf4j;
import org.cug.geodt.weibo.sos.annotation.OperationInterceptor;
import org.cug.geodt.weibo.sos.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: PublishController
 * @author: caiyixun
 * @description: TODO
 * @date: 2024/10/31 16:38
 * @version: 1.0
 */
@RestController
@RequestMapping("/publish")
@Slf4j
public class PublishController {

    @Autowired
    private PublishService publishService;

    @OperationInterceptor
    @PostMapping(value = "/observation/WFS",produces = "application/json;charset=UTF-8")
    public String getObservation(@RequestBody String str){
        return publishService.publishWFS(str);
    }

}
