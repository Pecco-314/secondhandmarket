package com.zerone.secondhandmarket;

import com.zerone.secondhandmarket.entity.*;
import com.zerone.secondhandmarket.enums.ItemType;
import com.zerone.secondhandmarket.enums.Ordering;
import com.zerone.secondhandmarket.enums.UserHead;
import com.zerone.secondhandmarket.service.ItemService;
import com.zerone.secondhandmarket.service.UserService;
import com.zerone.secondhandmarket.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class SecondHandMarketController {
    @Autowired
    UserService userService = new UserService();
    @Autowired
    AdminService adminService = new AdminService();
    @Autowired
    ItemService itemService = new ItemService();

    @RequestMapping("/")
    public String run() {
        User user = new User(6, "huhu", "123456", "1111111", "111@qq.com", UserHead.HEAD1);
        Administrator admin = new Administrator(2, "pecco", "123456");
        Item item = new Item(7, 5, "hat", ItemType.CLOTHES, 2, 100, 30, "cs", "this is a book", "1.jpg", Item.ITEMCHECK.TRUE);
        Item item2 = new Item(1, 3, "c++++primer", ItemType.CLOTHES, 3, 10, 5, "cs+", "this is a book", "1.jpg", Item.ITEMCHECK.UNCHECKED);
        //itemService.insertItem(item);
        //itemService.insertItem(item2);
       // userService.deleteUser(3);
        //itemService.updateItem(item);
        List<Item> items = itemService.getItemListOrderByPrice(Ordering.ASC);
        //   List<Item> items=itemService.getItemList();
        for (Item item1 : items)
            System.out.println(item1);
        List<Item> item1s = itemService.getItemListOrderByPrice(Ordering.DESC);
        //   List<Item> items=itemService.getItemList();
        for (Item item1 : item1s)
            System.out.println(item1);
        // adminService.insertAdmin(admin);
        // userService.insertUser(user);
        //adminService.deleteAdmin(1);
        //userService.deleteUser(1);
        //System.out.println(userService.getUserById(3).toString());
        //System.out.println(adminService.getAdminById(2).toString());
        //System.out.println(userService.getUserByEmail("111@qq.com").toString());
        /*List<User> users=userService.getUserList();
        for(User user1:users)
            System.out.println(user1);*/
        //String str=userService.getUserByEmail("123@qq.com").toString();
        //System.out.println(str);
        //userService.insertUser(user);
        //userService.updateUser(user);
        //adminService.updateAdmin(admin);
        return "index";
    }

}
