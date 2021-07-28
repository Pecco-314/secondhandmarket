package com.zerone.secondhandmarket.controller.Visitor;

import com.zerone.secondhandmarket.tools.Router;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller("Contact")
public class ContactController {
    @RequestMapping("/contact")
    public String openContactPage(HttpServletRequest request) {
        String res = Router.routerForUserAndVistor(request, "contact");
        //System.out.println(res);
        return res;

    }
}
