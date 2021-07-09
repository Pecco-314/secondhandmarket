package com.zerone.secondhandmarket.controller.User;

import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.message.ItemFilter;
import com.zerone.secondhandmarket.message.SellingItemDeleteMessage;
import com.zerone.secondhandmarket.message.SellingItemMessage;
import com.zerone.secondhandmarket.message.SellingItemModificationMessage;
import com.zerone.secondhandmarket.module.ItemModule;
import com.zerone.secondhandmarket.module.UploadModule;
import com.zerone.secondhandmarket.service.ItemImageService;
import com.zerone.secondhandmarket.service.ItemService;
import com.zerone.secondhandmarket.service.TagsService;
import com.zerone.secondhandmarket.tools.CodeProcessor;
import com.zerone.secondhandmarket.tools.JSONMapper;
import com.zerone.secondhandmarket.tools.PathGenerator;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static com.zerone.secondhandmarket.tools.JSONMapper.writeValueAsString;

@Controller("OrdinaryItem")
public class ItemController {
    @Autowired
    private ItemService itemService = new ItemService();
    @Autowired
    private TagsService tagsService = new TagsService();
    @Autowired
    private ItemImageService itemImageService = new ItemImageService();

    @RequestMapping("/post")
    public String openPostPage() {
        return "post";
    }

    //上传照片
    @ResponseBody
    @PostMapping("/requests/upload/image")
    public String upload(@RequestParam("multipartfiles") MultipartFile[] multipartfiles) throws IOException {
        Result result = UploadModule.upload("item", multipartfiles);
        System.out.println(JSONMapper.writeValueAsString(result));
        return result.toString();
    }

    //上传商品
    @ResponseBody
    @PostMapping("/requests/post")
    public String postItem(@RequestBody SellingItemMessage sellingItemMessage) {
        Result result;
        //检验id与token是否一致
        if (CodeProcessor.validateIdToken(sellingItemMessage.getSeller() + "", sellingItemMessage.getToken())) {
            result = ItemModule.releaseUserItem(itemService, tagsService, itemImageService, sellingItemMessage);
        } else {
            result = new Result(Status.ITEM_ERROR, "发布失败，id与token不一致", null);
        }

        return result.toString();
    }

    //获取图片
    @ResponseBody
    @PostMapping("/requests/image/{imagePath}")
    public byte[] getImage(@PathVariable("imagePath") String imagePath) throws IOException {
        File directory = new File("");//参数为空
        String courseFile = directory.getCanonicalPath() ;
        File file=new File((courseFile)+"/uploadFiles/item/"+imagePath);

        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }

    //筛选物品
    @ResponseBody
    @GetMapping("/requests/product/filter")
    public String getItemListByFilter(@RequestBody ItemFilter itemFilter) {
        Result result = ItemModule.getItemsByFilter(itemService, itemImageService, tagsService, itemFilter);

        return result.toString();
    }

    //物品详情
    @ResponseBody
    @GetMapping("/requests/item/{itemId}")
    public String getItemInfoByItemId(@PathVariable("itemId") int itemId) {
        Result result = ItemModule.getItemInfo(itemService, itemImageService, tagsService, itemId);

        return result.toString();
    }

    //关键词搜索
    @ResponseBody
    @GetMapping("/requests/search")
    public String getItemInfoByKeyword(@RequestBody String keyword) {
        Result result = ItemModule.getCheckedItemsByKeyWords(itemService, itemImageService, tagsService, keyword);

        return result.toString();
    }

    //更新物品信息（只修改商品名称、数量、金额）
    @ResponseBody
    @GetMapping("requests/user/modifyItem")
    public String modifyUserItem(@RequestBody SellingItemModificationMessage sellingItemModificationMessage) {
        Item item = itemService.getItemById(sellingItemModificationMessage.getItemID());
        //设置物品信息
        item.setName(sellingItemModificationMessage.getName());
//        item.setType(sellingItemModificationMessage.getType());
        item.setQuantity(sellingItemModificationMessage.getQuantity());
        item.setPrice(sellingItemModificationMessage.getPrice());
//        item.setOriginalPrice(sellingItemModificationMessage.getOriginalPrice());
//        item.setIntroduction(sellingItemModificationMessage.getIntroduction());

        Result result = ItemModule.modifyUserItem(itemService, itemImageService, tagsService, item);

        return result.toString();
    }

    //删除发布物品
    @ResponseBody
    @GetMapping("requests/user/deleteItem")
    public String deleteUserItem(@RequestBody SellingItemDeleteMessage sellingItemDeleteMessage) {
        Result result = ItemModule.deleteUserItem(itemService, itemImageService, tagsService, sellingItemDeleteMessage.getItemID());

        return result.toString();
    }
}
