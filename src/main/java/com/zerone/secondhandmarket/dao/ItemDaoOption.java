package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.Map;

public class ItemDaoOption {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    // 用于添加商品
    int insertItem(Item item)
    {
        String sql = "insert into item(item_id, seller_id, item_name, item_type, quantity, price_now, price_original, keyword, introduction, item_pic_path, checked)" +
                "values(:item_id, :seller_id, :item_name, :item_type, :quantity, :price_now,:price_original, :keyword, :introduction, :item_pic_path, :checked)";
        Map<String, Object> param = new HashMap<>();
        param.put("item_name", item.getItem_name());
        param.put("item_id", item.getItem_id());
        param.put("seller_id", item.getSeller_id());
        param.put("item_type", item.getType().toString());
        param.put("quantity", item.getQuantity());
        param.put("price_now", item.getPrice_now());
        param.put("price_original", item.getPrice_original());
        param.put("keyword", item.getKeyword());
        param.put("introduction", item.getIntroduction());
        param.put("item_pic_path", item.getItem_pic_path());
        param.put("checked", item.getChecked().toString());
        jdbcTemplate.update(sql, param);
        return 0;
    }


    // 用于删除商品
    int deleteItem(int itemId)
    {
        String sql = "delete from item where item_id=:id";
        Map<String, Object> param = new HashMap<>();
        param.put("id", itemId);
        jdbcTemplate.update(sql, param);
        return 0;
    }

    // 用于更新商品
    int updateItem(Item item)
    {
        String sql = "update item set seller_id=:seller_id,item_name=:item_name,item_type=:item_type,quantity=:quantity,price_now=:price_now,price_original=:price_original,keyword=:keyword,introduction=:introduction,item_pic_path=:item_pic_path,checked=:checked where item_id=:item_id";
        Map<String, Object> param = new HashMap<>();
        param.put("item_name", item.getItem_name());
        param.put("item_id", item.getItem_id());
        param.put("seller_id", item.getSeller_id());
        param.put("item_type", item.getType().toString());
        param.put("quantity", item.getQuantity());
        param.put("price_now", item.getPrice_now());
        param.put("price_original", item.getPrice_original());
        param.put("keyword", item.getKeyword());
        param.put("introduction", item.getIntroduction());
        param.put("item_pic_path", item.getItem_pic_path());
        param.put("checked", item.getChecked().toString());
        jdbcTemplate.update(sql, param);
        return 0;
    }


    // 通过类型查询商品
    /*List<Item> getItemByType(ItemType itemtype);
    // 通过id查询商品
    Item getItemById(int itemId);
    // 通过类似商品名（关键字）查询商品
    List<Item> getItemByKeyword(String keyword);
    // 用于查询所有商品列表
    List<Item> getItemList();*/
}
