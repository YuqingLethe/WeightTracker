/**
 * Copyright (c) 2022 All Rights Reserved, Yuqing Yang.
 */
package yoki.demo.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yoki.demo.model.UserEnum;

import java.io.IOException;

@RestController
@RequestMapping("record")
public class WeightRecordController {

    private static UserEnum username;
    private static final Logger logger = LogManager.getLogger(WeightRecordController.class);

    /**
     * Step 1: Verify valid user by user token
     * @throws IOException
     */
    @RequestMapping(value = "/login")
    boolean login(@RequestBody String userToken) throws IOException {
        System.out.println("LOg in");
        switch(userToken) {
            case "xinran0210":
                this.username = UserEnum.XIAOPANG;
                return true;
            case "le0414":
                username = UserEnum.DRWANG;
                return true;
            case "yuqing0601":
                username = UserEnum.YUQING;
                return true;
            case "sixing":
                username = UserEnum.QINLAOSHI;
                return true;
            case "zhifeng":
                username = UserEnum.ZHIFENG;
                return true;
            default:
                username = UserEnum.INVALID;
                return false;
        }
    }
}
