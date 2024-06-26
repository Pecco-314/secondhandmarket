package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.entity.Cart;
import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.service.CartService;
import com.zerone.secondhandmarket.viewobject.Result;

import java.util.List;

public class CartModule {
    public static Result getItemsInCart(CartService service, int userId, Integer page) {
        List<Cart> list = service.getCartListByUserId(userId, page);

        if (list == null)
            return new Result(Status.CART_ERROR, "", null);

        return new Result(Status.CART_OK, "", list);
    }

    public static Result getCartCount(CartService service, int userId) {
        Integer count = service.getCartCount(userId);

        if(count == null)
            return new Result(Status.CART_ERROR, "", null);

        return new Result(Status.CART_OK, "", count);
    }

    public static Result modifyItemQuantity(CartService service, Cart cart, boolean accumulate) {
        try {
            if (cart.getQuantity() == 0) {
                service.deleteCart(cart.getUserId(), cart.getItemId());
            } else {
                service.insertOrUpdateCart(cart, accumulate);
            }
            return new Result(Status.CART_OK, "加入购物车成功", null);

        } catch (Exception e) {
            e.printStackTrace();

            return new Result(Status.CART_ERROR, "加入购物车失败", null);
        }
    }

    public static Result clearCart(CartService service, int userId) {
        try {
            service.clearCart(userId);

            return new Result(Status.CART_OK, "", null);
        } catch (Exception e) {
            return new Result(Status.CART_ERROR, "", null);
        }
    }
}
