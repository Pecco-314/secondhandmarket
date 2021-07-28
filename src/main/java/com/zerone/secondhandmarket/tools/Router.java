package com.zerone.secondhandmarket.tools;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Router {
    public static String routerForAdmin(HttpServletRequest request, String des) {
        Cookie[] cookies = request.getCookies();

        if(cookies == null)
            return "redirect:/";

        for (Cookie cookie : cookies) {
            //System.out.println(cookies[i].getName() + ": " + cookies[i].getValue());
            if (cookie.getName().equals("userType") && cookie.getValue().equals("admin")) {
                return des;
            }
        }
        return "redirect:/";
    }

    public static String routerForUser(HttpServletRequest request, String des) {
        Cookie[] cookies = request.getCookies();

        if(cookies == null)
            return "redirect:/login";

        for (Cookie cookie : cookies) {
            //System.out.println(cookies[i].getName() + ": " + cookies[i].getValue());
            if (cookie.getName().equals("userType")) {
                if (cookie.getValue().equals("user"))
                    return des;
                if (cookie.getValue().equals("admin"))
                    return "redirect:/admin-user";
            }
        }
        return "redirect:/login";
    }

    public static String routerForUserAndVistor(HttpServletRequest request, String des) {
        Cookie[] cookies = request.getCookies();

        if(cookies == null)
            return des;

        //System.out.println(cookies.length);
        for (Cookie cookie : cookies) {
            //System.out.println(cookies[i].getName() + ": " + cookies[i].getValue());
            if (cookie.getName().equals("userType")) {
                if (cookie.getValue().equals("user"))
                    return des;
                if (cookie.getValue().equals("admin"))
                    return "redirect:/admin-user";
            }
        }
        return des;
    }
}
