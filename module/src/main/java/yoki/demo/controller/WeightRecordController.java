/**
 * Copyright (c) 2022 All Rights Reserved, Yuqing Yang.
 */
package yoki.demo.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import yoki.demo.model.TimeSlot;
import yoki.demo.model.UserEnum;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@RestController
@RequestMapping("weight")
public class WeightRecordController {

    private static UserEnum USERNAME = UserEnum.INVALID;
    private static final Logger logger = LogManager.getLogger(WeightRecordController.class);

    /**
     * Step 1: Verify valid user by user token
     * @throws IOException
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    boolean login(@RequestBody String userToken) throws IOException {
        logger.debug("Weight login" + userToken);
        switch(userToken) {
            case "xinran0210":
                USERNAME = UserEnum.XIAOPANG;
                return true;
            case "le0414":
                USERNAME = UserEnum.DRWANG;
                return true;
            case "yuqing0601":
                USERNAME = UserEnum.YUQING;
                return true;
            case "sixing":
                USERNAME = UserEnum.QINLAOSHI;
                return true;
            case "zhifeng":
                USERNAME = UserEnum.ZHIFENG;
                return true;
            default:
                USERNAME = UserEnum.INVALID;
                return false;
        }
    }

    @RequestMapping(value = "/writeToCSV")
    @ResponseBody
    String recordToCSV(@RequestParam String weight, @RequestParam String timeSlot,
                     @RequestParam String yyyy, @RequestParam String mm, @RequestParam String dd) throws IOException {

        if (USERNAME == UserEnum.INVALID) {
            return "UserToken is invalid.";
        }
        String date = yyyy + '-' + mm + '-' + dd;
        TimeSlot ts = TimeSlot.FASTEN_MORNING;
        switch (timeSlot) {
            case "afterDefecate":
                ts = TimeSlot.AFTER_DEFECATE;
                break;
            case "noon":
                ts = TimeSlot.NOON;
                break;
            case "lateNight":
                ts = TimeSlot.LATE_NIGHT;
                break;
        }

        String[] row = new String[]{weight, date, ts.name()};
        logger.info("weight writeToCSV user=" + USERNAME.name() + " weight=" + weight + " date=" + date + " slot=" + ts.name());

        FileWriter csvWriter = createNewCSV(this.USERNAME.name());
        csvWriter.append(String.join(",", row));
        csvWriter.append("\n");

        csvWriter.flush();
        csvWriter.close();
        return "Weight " + weight + " pounds saved for " + mm + "/" + dd;
    }

    private FileWriter createNewCSV(String fileName) throws IOException{
        try {
            String csvName = fileName + ".csv";
            FileWriter csvWriter = new FileWriter(csvName);
            csvWriter.append("Weight(lb)");
            csvWriter.append(",");
            csvWriter.append("Date");
            csvWriter.append(",");
            csvWriter.append("TimeSlot");
            csvWriter.append("\n");
            return csvWriter;
        } catch (Exception e) {
            logger.error("createNewCSV()" + e);
            return null;
        }
    }

    private String findFile(String fileName) throws IOException {
        File directory = new File("/home/user/");

        // store all names with same name
        // with/without extension
        String[] flist = directory.list();
        int flag = 0;
        if (flist == null) {
            logger.error("Weight findFile() Empty directory.");
            return false;
        } else {
            if (flist.length != 1) {
                logger.error("weight, findFile() more than one file found");
            } else {
                String currFile = flist[0];
            }
            for (int i = 0; i < flist.length; i++) {
                String currFile = flist[i];
                if (currFile.equalsIgnoreCase(fileName)) {
                    logger.info("WeightTracker, findFile() " + currFile + " found");
                    flag = 1;
                }
            }
        }

        if (flag == 0) {
            createNewCSV(fileName);
            logger.info("WeightTracker, " + fileName + "File Not Found");
        }
        return "Succeed";
    }
}
