package com.zerone.secondhandmarket.controller.User;

import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.message.ItemFilter;
import com.zerone.secondhandmarket.message.SellingItemMessage;
import com.zerone.secondhandmarket.message.SellingItemModificationMessage;
import com.zerone.secondhandmarket.module.ItemModule;
import com.zerone.secondhandmarket.module.UploadModule;
import com.zerone.secondhandmarket.service.ItemImageService;
import com.zerone.secondhandmarket.service.ItemService;
import com.zerone.secondhandmarket.service.TagsService;
import com.zerone.secondhandmarket.tools.CodeProcessor;
import com.zerone.secondhandmarket.tools.JSONMapper;
import com.zerone.secondhandmarket.tools.Router;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

@Controller("OrdinaryItem")
public class ItemController {
    @Autowired
    private ItemService itemService = new ItemService();
    @Autowired
    private TagsService tagsService = new TagsService();
    @Autowired
    private ItemImageService itemImageService = new ItemImageService();

    @RequestMapping("/post")
    public String openPostPage(HttpServletRequest request) {
        String res = Router.routerForUser(request, "post");
        //System.out.println(res);
        return res;
    }

    @RequestMapping("/item")
    public String openItemPage() {
        return "goods-details";
    }

    //上传照片
    @ResponseBody
    @PostMapping("/requests/upload/image")
    public String upload(@RequestParam("multipartfiles") MultipartFile[] multipartFiles) {
        try {
            Result result = UploadModule.upload("item", multipartFiles);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();

            return new Result(Status.ERROR, "文件传输失败", null).toString();
        }
    }

    //上传商品
    @ResponseBody
    @PostMapping("/requests/post")
    public String postItem(@RequestBody SellingItemMessage sellingItemMessage) {
        Result result;
        //检验id与token是否一致
        if (CodeProcessor.validateIdToken(sellingItemMessage.getSeller(), sellingItemMessage.getToken())) {
            result = ItemModule.releaseUserItem(itemService, tagsService, itemImageService, sellingItemMessage);
        } else {
            result = new Result(Status.ITEM_ERROR, "发布失败，id与token不一致", null);
        }

        return result.toString();
    }

    //获取图片
    @ResponseBody
    @GetMapping("/requests/image/{imagePath}")
    public byte[] getImage(@PathVariable("imagePath") String imagePath) {
        String courseFile = null;
        try {
            courseFile = new File("").getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

        File file = new File((courseFile) + "/uploadFiles/item/" + imagePath);

        try (FileInputStream inputStream = new FileInputStream(file)) {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    //搜索物品
    @ResponseBody
    @PostMapping("/requests/product/search")
    public String searchItems(@RequestBody ItemFilter filter) {
        try {
            filter.setKeyword(URLDecoder.decode(filter.getKeyword(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();

            return new Result(Status.ITEM_ERROR, "关键字解析失败", null).toString();
        }
        Result result = ItemModule.getItemsByFilter(itemService, itemImageService, tagsService, filter);

        return result.toString();
    }

    @ResponseBody
    @PostMapping("/requests/product/count")
    public String getItemCount(@RequestBody ItemFilter filter) {
        try {
            filter.setKeyword(URLDecoder.decode(filter.getKeyword(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();

            return new Result(Status.ITEM_ERROR, "关键字解析失败", null).toString();
        }
        Result result = ItemModule.getItemCount(itemService, filter);

        return result.toString();
    }

    /*//筛选物品
    @ResponseBody
    @PostMapping("/requests/product/filter")
    public String getItemListByFilter(@RequestBody ItemFilter itemFilter) {
        Result result = ItemModule.getItemsByFilter(itemService, itemImageService, tagsService, itemFilter);

        return result.toString();
    }*/

    //物品详情
    @ResponseBody
    @GetMapping("/requests/item/{itemId}")
    public String getItemInfoByItemId(@PathVariable("itemId") int itemId) {
        Result result = ItemModule.getItemInfo(itemService, itemImageService, tagsService, itemId);

        return result.toString();
    }

    @ResponseBody
    @PostMapping("requests/item/items")
    public String getItemsByIds(@RequestBody List<Integer> ids) {
        Result result = ItemModule.getItemListByIDs(itemService, ids);

        return result.toString();
    }

    /*//关键词搜索
    @ResponseBody
    @GetMapping("/requests/search/{keyword}")
    public String getItemInfoByKeyword(@PathVariable("keyword") String keyword) {
        Result result = ItemModule.getCheckedItemsByKeyWords(itemService, itemImageService, tagsService, keyword);

        return result.toString();
    }*/

    //更新物品信息（只修改商品名称、数量、金额、简介）
    @ResponseBody
    @PostMapping("requests/user/modifyItem")
    public String modifyUserItem(@RequestBody SellingItemModificationMessage sellingItemModificationMessage) {
        Item item = itemService.getItemById(sellingItemModificationMessage.getItemID());

        if (item == null)
            return new Result(Status.ITEM_ERROR, "获取物品失败", null).toString();

        //设置物品信息
        item.setName(sellingItemModificationMessage.getName());
//        item.setType(sellingItemModificationMessage.getType());
        item.setQuantity(sellingItemModificationMessage.getQuantity());
        item.setPrice(sellingItemModificationMessage.getPrice());
//        item.setOriginalPrice(sellingItemModificationMessage.getOriginalPrice());
        item.setIntroduction(sellingItemModificationMessage.getIntroduction());
        if (sellingItemModificationMessage.getImages().length != 0)
            item.setCoverPath(sellingItemModificationMessage.getImages()[0]);
        else
            item.setCoverPath(null);

        Result result = ItemModule.modifyUserItem(itemService, itemImageService, tagsService, item, Arrays.asList(sellingItemModificationMessage.getImages()));

        return result.toString();
    }

    //删除发布物品
    @ResponseBody
    @GetMapping("requests/user/deleteItem/{itemId}")
    public String deleteUserItem(@PathVariable("itemId") int itemId) {
        Result result = ItemModule.deleteUserItem(itemService, itemImageService, tagsService, itemId);

        return result.toString();
    }

    //获取某个用户发布的商品
    @ResponseBody
    @GetMapping("requests/user/items/{userId}")
    public String getUserItems(@PathVariable("userId") int userId) {
        ItemFilter itemFilter = new ItemFilter();
        itemFilter.setSeller(userId);
        itemFilter.setImagesNeeded(true);

        Result result = ItemModule.getItemsByFilter(itemService, itemImageService, tagsService, itemFilter);

        return result.toString();
    }
}
