package com.zerone.secondhandmarket;

import com.zerone.secondhandmarket.entity.*;
import com.zerone.secondhandmarket.enums.ITEMCHECK;
import com.zerone.secondhandmarket.enums.ItemType;
import com.zerone.secondhandmarket.enums.Ordering;
import com.zerone.secondhandmarket.enums.UserHead;
import com.zerone.secondhandmarket.message.ItemFilter;
import com.zerone.secondhandmarket.service.*;
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
    @Autowired
    ShoppingCartService cartService = new ShoppingCartService();
    @Autowired
    OrderService orderService = new OrderService();
    @RequestMapping("/")
    public String run() {
        User user = new User(6, "huhu", "123456", "1111111", "111@qq.com", UserHead.HEAD1);
        Administrator admin = new Administrator(2, "pecco", "123456");
        Item item = new Item(7, 5, "hat", ItemType.CLOTHES, 2, 100, 30, "cs", "this is a book", "1.jpg", ITEMCHECK.TRUE);
        Item item2 = new Item(1, 3, "c++++primer", ItemType.CLOTHES, 3, 10, 5, "cs+", "this is a book", "1.jpg", ITEMCHECK.UNCHECKED);
        ItemFilter filter=new ItemFilter();
        filter.setCheckCondition(ITEMCHECK.TRUE);
      //  filter.setType(ItemType.DIGITAL);
        //filter.setKeyWords("a++");
        filter.setPriceOrdering(Ordering.DESC);
        //itemService.insertItem(item);
        //itemService.insertItem(item2);
       // userService.deleteUser(3);
        //itemService.updateItem(item);
      //  List<Item> items = itemService.getItemByFilter(filter);
        //   List<Item> items=itemService.getItemList();
        //for (Item item1 : items)
          //  System.out.println(item1);
    //    List<Item> item1s = itemService.getItemListOrderByPrice(Ordering.DESC);
        //   List<Item> items=itemService.getItemList();
      //  for (Item item1 : item1s)
        //    System.out.println(item1);
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
//        ShoppingCart cart1=new ShoppingCart(1,2,1);
//        ShoppingCart cart2=new ShoppingCart(1,1,1);
//        ShoppingCart cart3=new ShoppingCart(1,3,2);
//        ShoppingCart cart4=new ShoppingCart(2,3,2);
      //  cartService.insertCart(cart1);
       // cartService.insertCart(cart2);
        //cartService.insertCart(cart3);
        //cartService.insertCart(cart4);
      //  cartService.deleteCart(1,2);
     //   cart4.setQuantity(5);
       //cartService.modifyItemQuantity(cart4);
//        List<ShoppingCart> carts = cartService.getCartListByUserId(1);
//
//        for (ShoppingCart cart0 : carts)
//          System.out.println(cart1);
Order order1=new Order(0,1,2,2,1,"2021-07-03 20:48:00");
        Order order2=new Order(0,1,2,3,1,"2021-07-13 20:48:00");
        Order order3=new Order(0,1,5,4,1,"2021-06-03 20:48:00");
//        orderService.insertOrder(order1);
//        orderService.insertOrder(order2);
//        orderService.insertOrder(order3);
      //  orderService.deleteOrder(3);
     List<Order> orders=orderService.getOrderByUserId(1);
        for (Order cart0 : orders)
          System.out.println(cart0);
        return "index";
    }

}
