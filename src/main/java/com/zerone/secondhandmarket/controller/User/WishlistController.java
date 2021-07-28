package com.zerone.secondhandmarket.controller.User;

import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.message.CheckItemExistenceMessage;
import com.zerone.secondhandmarket.message.UserTokenMessage;
import com.zerone.secondhandmarket.message.UserTokenWithPageMessage;
import com.zerone.secondhandmarket.message.WishlistModificationMessage;
import com.zerone.secondhandmarket.module.CartModule;
import com.zerone.secondhandmarket.module.WishlistModule;
import com.zerone.secondhandmarket.service.WishlistService;
import com.zerone.secondhandmarket.tools.CodeProcessor;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("OrdinaryWishlist")
public class WishlistController {
    @Autowired
    private WishlistService wishlistService = new WishlistService();

    @ResponseBody
    @PostMapping("/requests/user/wishlist")
    public String getWishlist(@RequestBody UserTokenWithPageMessage token) {
        if (CodeProcessor.validateIdToken(token.getUserId(), token.getToken())) {
            Result result = WishlistModule.getWishlist(wishlistService, token.getUserId(), token.getPage());
            return result.toString();
        } else {
            return new Result(Status.ERROR, "ID与Token不符", null).toString();
        }
    }

    @ResponseBody
    @PostMapping("/requests/wishlist/count")
    public String getWishlistCount(@RequestBody UserTokenMessage token) {
        if (CodeProcessor.validateIdToken(token.getUserID(), token.getToken())) {
            Result result = WishlistModule.getWishlistCount(wishlistService, token.getUserID());
            return result.toString();
        } else {
            return new Result(Status.CART_ERROR, "ID与Token不符", null).toString();
        }
    }

    @ResponseBody
    @PostMapping("/requests/user/wishlist/modify")
    public String modifyWishlist(@RequestBody WishlistModificationMessage modification) {
        if (CodeProcessor.validateIdToken(modification.getUserID(), modification.getToken())) {
            Result result = WishlistModule.modifyWishlist(wishlistService, modification.getUserID(), modification.getItemID(), modification.getIsAdding());
            return result.toString();

        } else {
            return new Result(Status.ERROR, "ID与Token不符", null).toString();
        }
    }

    @ResponseBody
    @PostMapping("/requests/user/wishlist/exists")
    public String checkExistence(@RequestBody CheckItemExistenceMessage check) {
        if (CodeProcessor.validateIdToken(check.getUserId(), check.getToken())) {
            Result result = WishlistModule.checkExistence(wishlistService, check.getUserId(), check.getItemId());

            return result.toString();
        } else {
            return new Result(Status.ERROR, "ID与Token不符", null).toString();
        }
    }
}
