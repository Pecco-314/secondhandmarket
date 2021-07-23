package com.zerone.secondhandmarket.controller.Visitor;

import com.zerone.secondhandmarket.module.ItemModule;
import com.zerone.secondhandmarket.service.ItemImageService;
import com.zerone.secondhandmarket.service.ItemService;
import com.zerone.secondhandmarket.service.TagsService;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("Contact")
public class ContactController {
    @RequestMapping("/contact")
    public String openContactPage() {
        return "contact";
    }
}
