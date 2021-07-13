package com.zerone.secondhandmarket.controller.Admin;

import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.message.NotificationReleaseMessage;
import com.zerone.secondhandmarket.message.UserTokenMessage;
import com.zerone.secondhandmarket.service.NotificationService;
import com.zerone.secondhandmarket.tools.CodeProcessor;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("AdminNotification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService = new NotificationService();

    @ResponseBody
    @PostMapping("/requests/admin/notification/release")
    public String releaseNotification(@RequestBody NotificationReleaseMessage release) {
        if(CodeProcessor.validateIdToken(release.getReleaser(), release.getToken())) {
            return null;
        } else {
            return new Result(Status.ERROR, "", null).toString();
        }
    }

    @ResponseBody
    @PostMapping("/requests/admin/notification/get")
    public String getNotification(@RequestBody UserTokenMessage token) {
        if(CodeProcessor.validateIdToken(token.getUserID(), token.getToken())) {
            return null;
        } else {
            return new Result(Status.ERROR, "", null).toString();
        }
    }
}
