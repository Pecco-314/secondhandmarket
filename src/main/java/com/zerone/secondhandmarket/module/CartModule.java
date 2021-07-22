package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.entity.Cart;
import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.service.CartService;
import com.zerone.secondhandmarket.viewobject.Result;

import java.util.List;

public class CartModule {
    public static Result getItemsInCart(CartService service, int userId) {
        List<Cart> list = service.getCartListByUserId(userId);

        if (list == null)
            return new Result(Status.CART_ERROR, "", null);

        return new Result(Status.CART_OK, "", list);
    }

    public static Result modifyItemQuantity(CartService service, Cart cart) {
        try {
            if (cart.getQuantity() == 0) {
                service.deleteCart(cart.getUserId(), cart.getItemId());
            } else {
                service.insertOrUpdateCart(cart);
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

            return new Result(Status.OK, "", null);
        } catch (Exception e) {
            e.printStackTrace();

            return new Result(Status.ERROR, "", null);
        }
    }
}
