package com.zerone.secondhandmarket.tools;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Router {
    public static String routerForAdmin(HttpServletRequest request, String des) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            //System.out.println(cookies[i].getName() + ": " + cookies[i].getValue());
            if (cookies[i].getName().equals("userType") && cookies[i].getValue().equals("admin")) {
                return des;
            }
        }
        return "redirect:/";
    }

    public static String routerForUser(HttpServletRequest request, String des) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            //System.out.println(cookies[i].getName() + ": " + cookies[i].getValue());
            if (cookies[i].getName().equals("userType")) {
                if (cookies[i].getValue().equals("user"))
                    return des;
                if (cookies[i].getValue().equals("admin"))
                    return "redirect:/admin-user";
            }
        }
        return "redirect:/login";
    }

    public static String routerForUserAndVistor(HttpServletRequest request, String des) {
        Cookie[] cookies = request.getCookies();
        //System.out.println(cookies.length);
        for (int i = 0; i < cookies.length; i++) {
            //System.out.println(cookies[i].getName() + ": " + cookies[i].getValue());
            if (cookies[i].getName().equals("userType")) {
                if (cookies[i].getValue().equals("user"))
                    return des;
                if (cookies[i].getValue().equals("admin"))
                    return "redirect:/admin-user";
            }
        }
        return des;
    }
}
