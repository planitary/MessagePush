package com.planitary.message.push.controller;

import com.planitary.message.push.service.PushService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
public class PushController {
    @Autowired
    PushService pushService;
    @RequestMapping("manualPush")
    public String manualPush() throws IOException {
        return pushService.push();
    }
}
