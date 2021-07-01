package com.zerone.secondhandmarket.Controller.OrdinaryUser;

import com.zerone.secondhandmarket.ViewObject.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("OrdinaryIndent")
public class IndentController {
    @GetMapping("user/{userid}/history")
    @ResponseBody
    public ResultVo getHistoryIdent(@PathVariable int userid){
        return null;
    }
}
