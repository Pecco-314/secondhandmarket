package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.entity.Wishlist;
import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.service.WishlistService;
import com.zerone.secondhandmarket.viewobject.Result;

import java.util.List;

public class WishlistModule {
    public static Result getWishlist(WishlistService service, int userId) {
        List<Wishlist> list = service.getWishlistByUserId(userId);

        if(list == null || list.isEmpty())
            return new Result(Status.ERROR, "", null);

        return new Result(Status.OK, "", list);
    }

    public static Result modifyWishlist(WishlistService service, int userId, int itemId, boolean isAdding) {
        try {
            if(isAdding) {
                service.insertWishlist(new Wishlist(userId, itemId));
            } else {
                service.deleteWishlist(userId, itemId);
            }
            return new Result(Status.OK, "", null);

        } catch (Exception e) {
            return new Result(Status.ERROR, "", null);
        }
    }

    public static Result checkExistence(WishlistService service, int userId, int itemId) {
        Wishlist wishlist = service.getWishlistByKey(userId, itemId);

        if(wishlist == null)
            return new Result(Status.OK, "", true);
        return new Result(Status.ERROR, "", false);
    }
}