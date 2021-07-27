package com.zerone.secondhandmarket.controller.User;

import com.zerone.secondhandmarket.entity.Cart;
import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.message.CartModificationMessage;
import com.zerone.secondhandmarket.message.UserTokenMessage;
import com.zerone.secondhandmarket.module.CartModule;
import com.zerone.secondhandmarket.service.CartService;
import com.zerone.secondhandmarket.tools.CodeProcessor;
import com.zerone.secondhandmarket.tools.Router;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller("OrdinaryShoppingCart")
public class CartController {
    @Autowired
    private CartService cartService = new CartService();

    @RequestMapping("/cart")
    public String openCartPage(HttpServletRequest request) {
        String res = Router.routerForUser(request, "cart");
        System.out.println(res);
        return res;
    }

    @ResponseBody
    @PostMapping("/requests/cart/info")
    public String getCarts(@RequestBody UserTokenMessage token) {
        if (CodeProcessor.validateIdToken(token.getUserID(), token.getToken())) {
            Result result = CartModule.getItemsInCart(cartService, token.getUserID());

            return result.toString();
        } else {
            return new Result(Status.CART_ERROR, "ID与Token不符", null).toString();
        }
    }

    @ResponseBody
    @PostMapping("/requests/cart/count")
    public String getCartCount(@RequestBody UserTokenMessage token) {
        if (CodeProcessor.validateIdToken(token.getUserID(), token.getToken())) {
            Result result = CartModule.getCartCount(cartService, token.getUserID());
            System.out.println(result);
            return result.toString();
        } else {
            return new Result(Status.CART_ERROR, "ID与Token不符", null).toString();
        }
    }

    //添加、更改和删除都通过该方法
    @ResponseBody
    @PostMapping("/requests/cart/modifyCart")
    public String modifyCart(@RequestBody CartModificationMessage modification) {
        if (CodeProcessor.validateIdToken(modification.getUserID(), modification.getToken())) {
            Cart cart = new Cart(modification.getUserID(), modification.getItemID(), modification.getQuantity());

            Result result = CartModule.modifyItemQuantity(cartService, cart);

            return result.toString();

        } else {
            return new Result(Status.CART_ERROR, "ID与Token不符", null).toString();
        }
    }

    @ResponseBody
    @PostMapping("/requests/cart/clear")
    public String modifyCart(@RequestBody UserTokenMessage token) {
        if (CodeProcessor.validateIdToken(token.getUserID(), token.getToken())) {
            Result result = CartModule.clearCart(cartService, token.getUserID());

            return result.toString();
        } else {
            return new Result(Status.CART_ERROR, "ID与Token不符", null).toString();
        }
    }
}
