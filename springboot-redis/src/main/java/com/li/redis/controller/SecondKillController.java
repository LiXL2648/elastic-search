package com.li.redis.controller;

import com.li.redis.service.SecondKillService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SecondKillController {

    @Resource
    private SecondKillService secondKillService;

    @RequestMapping("/secondKill")
    public String secondKill(String prodId) {

        return secondKillService.secondKillByScript(prodId);
    }
}
