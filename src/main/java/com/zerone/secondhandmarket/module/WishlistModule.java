package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.entity.Wishlist;
import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.service.WishlistService;
import com.zerone.secondhandmarket.viewobject.Result;

import java.util.List;

public class WishlistModule {
    public static Result getWishlist(WishlistService service, int userId, Integer page) {
        List<Wishlist> list = service.getWishlistByUserId(userId, page);

        if (list == null)
            return new Result(Status.ERROR, "", null);

        return new Result(Status.OK, "", list);
    }

    public static Result getWishlistCount(WishlistService service, int userId) {
        Integer count = service.getWishlistCount(userId);

        if(count == null)
            return new Result(Status.ERROR, "获取收藏夹数目失败", null);

        return new Result(Status.OK, "获取收藏夹数目成功", count);
    }

    public static Result modifyWishlist(WishlistService service, int userId, int itemId, boolean isAdding) {
        try {
            if (isAdding) {
                service.insertWishlist(new Wishlist(userId, itemId));
            } else {
                service.deleteWishlist(userId, itemId);
            }
            return new Result(Status.OK, "", null);

        } catch (Exception e) {
            e.printStackTrace();

            return new Result(Status.ERROR, "", null);
        }
    }

    public static Result checkExistence(WishlistService service, int userId, int itemId) {
        Wishlist wishlist = service.getWishlistByKey(userId, itemId);

        if (wishlist != null)
            return new Result(Status.OK, "", true);
        return new Result(Status.ERROR, "", false);
    }
}
