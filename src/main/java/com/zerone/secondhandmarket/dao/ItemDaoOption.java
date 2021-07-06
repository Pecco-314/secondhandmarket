package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.entity.SimplifiedItem;
import com.zerone.secondhandmarket.enums.ItemType;
import com.zerone.secondhandmarket.enums.Ordering;
import com.zerone.secondhandmarket.mapper.ItemRowMapper;
import com.zerone.secondhandmarket.mapper.SimplifiedItemRowMapper;
import com.zerone.secondhandmarket.message.ItemFilter;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemDaoOption {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    // 用于添加商品
    public int insertItem(Item item) {
        String sql = "insert into item(seller_id, item_name, item_type, quantity, price_now, price_original,introduction, item_pic_path, checked)" +
                "values(:seller_id, :item_name, :item_type, :quantity, :price_now,:price_original, :introduction, :item_pic_path, :checked)";
        Map<String, Object> param = new HashMap<>();
        param.put("item_name", item.getName());
        param.put("item_id", item.getId());
        param.put("seller_id", item.getSeller());
        param.put("item_type", item.getType().toString());
        param.put("quantity", item.getQuantity());
        param.put("price_now", item.getPrice());
        param.put("price_original", item.getOriginalPrice());
        param.put("introduction", item.getIntroduction());
        param.put("item_pic_path", item.getImagePath());
        param.put("checked", item.getCheckCondition().toString());
        jdbcTemplate.update(sql, param);
        return 0;
    }


    // 用于删除商品
    public int deleteItem(int itemId) {
        String sql = "delete from item where item_id=:id";
        Map<String, Object> param = new HashMap<>();
        param.put("id", itemId);
        jdbcTemplate.update(sql, param);
        return 0;
    }

    // 用于更新商品
    public int updateItem(Item item) {
        String sql = "update item set seller_id=:seller_id,item_name=:item_name,item_type=:item_type,quantity=:quantity,price_now=:price_now,price_original=:price_original,introduction=:introduction,item_pic_path=:item_pic_path,checked=:checked where item_id=:item_id";
        Map<String, Object> param = new HashMap<>();
        param.put("item_name", item.getName());
        param.put("item_id", item.getId());
        param.put("seller_id", item.getSeller());
        param.put("item_type", item.getType().toString());
        param.put("quantity", item.getQuantity());
        param.put("price_now", item.getPrice());
        param.put("price_original", item.getOriginalPrice());
      //  param.put("keyword", item.getKeyword());
        param.put("introduction", item.getIntroduction());
        param.put("item_pic_path", item.getImagePath());
        param.put("checked", item.getCheckCondition().toString());
        jdbcTemplate.update(sql, param);
        return 0;
    }


    // 通过类型查询商品
    public List<Item> getItemByType(ItemType itemtype) {
        String sql = "select * from item where item_type=:item_type";
        Map<String, Object> param = new HashMap<>();
        param.put("item_type", itemtype.toString());
        List<Item> items;
        try {
            items = jdbcTemplate.query(sql, param, new ItemRowMapper());
        } catch (Exception e) {
            return null;
        }
        return items;
    }

    // 通过id查询商品
    public Item getItemById(int itemId) {
        Item item = new Item();
        String sql = "select * from item where item_id=:id";
        Map<String, Object> param = new HashMap<>();
        param.put("id", itemId);
        try {
            item = jdbcTemplate.queryForObject(sql.toString(), param, new ItemRowMapper());
        } catch (Exception e) {
            return null;
        }
        return item;
    }

    // 通过类似商品名（关键字）查询商品
    public List<Item> getItemByKeyword(String keyword) {
        String sql = "select * from item where item_name LIKE :item_name";
        Map<String, Object> param = new HashMap<>();
        String str = "%" + keyword + "%";
        param.put("item_name", str);
        List<Item> items;
        try {
            items = jdbcTemplate.query(sql, param, new ItemRowMapper());
        } catch (Exception e) {
            return null;
        }
        return items;
    }

    // 用于查询所有商品列表
    public List<Item> getItemList() {
        String sql = "select * from item";

        List<Item> items;
        try {
            items = jdbcTemplate.query(sql, new ItemRowMapper());
        } catch (Exception e) {
            return null;
        }
        return items;
    }

    //按价格排序获取商品列表
    public List<Item> getItemListOrderByPrice(Ordering ordering) {
        String sql = "";
        if (ordering == Ordering.ASC)
            sql = "select * from item order by price_now ASC";
        else if (ordering == Ordering.DESC)
            sql = "select * from item order by price_now DESC";
        List<Item> items;
        try {
            items = jdbcTemplate.query(sql, new ItemRowMapper());
        } catch (Exception e) {
            return null;
        }
        return items;
    }

    //按filter获取商品列表
    public List<Item> getItemByFilter(ItemFilter itemFilter) {
        String sql = "select * from item";
        Map<String, Object> param = new HashMap<>();
        boolean has_where = false;
        if (itemFilter.getSeller() != null) {
            if (!has_where) {
                sql += " where";
                has_where = true;
                sql += " seller_id=:seller_id";
            }
            else
            {
                sql += " and seller_id=:seller_id";
            }

            param.put("seller_id", itemFilter.getSeller());
        }
        if (itemFilter.getType() != null) {
            if (!has_where) {
                sql += " where";
                has_where = true;
                sql += " item_type=:item_type";
            }
            else
            {
                sql += " and item_type=:item_type";
            }
            param.put("item_type", itemFilter.getType().toString());
        }
        if (itemFilter.getTags() != null) {
            if (!has_where) {
                sql += " where";
                has_where = true;
                sql += " item_name LIKE :item_name";
            }
            else
            {
                sql += " and item_name LIKE :item_name";
            }
            String str = "%" +itemFilter.getTags() + "%";
            param.put("item_name", str);
        }
        if (itemFilter.getCheckCondition() != null) {
            if (!has_where) {
                sql += " where";
                has_where = true;
                sql += " checked=:checked";
            }
            else
            {
                sql += " and checked=:checked";
            }
            param.put("checked", itemFilter.getCheckCondition().toString());
        }
        if (itemFilter.getPriceOrdering() == Ordering.ASC)
            sql += " order by price_now ASC";
        if (itemFilter.getPriceOrdering() == Ordering.DESC)
            sql += " order by price_now DESC";
        List<Item> items;
        try {
            items = jdbcTemplate.query(sql,param, new ItemRowMapper());
        } catch (Exception e) {
            return null;
        }
        return items;
    }
    //按filter获取商品列表
    public List<SimplifiedItem> getSimplifiedItemByFilter(ItemFilter itemFilter) {
        String sql = "select item_id,item_name,price_now,item_pic_path from item";
        Map<String, Object> param = new HashMap<>();
        boolean has_where = false;
        if (itemFilter.getSeller() != null) {
            if (!has_where) {
                sql += " where";
                has_where = true;
                sql += " seller_id=:seller_id";
            }
            else
            {
                sql += " and seller_id=:seller_id";
            }

            param.put("seller_id", itemFilter.getSeller());
        }
        if (itemFilter.getType() != null) {
            if (!has_where) {
                sql += " where";
                has_where = true;
                sql += " item_type=:item_type";
            }
            else
            {
                sql += " and item_type=:item_type";
            }
            param.put("item_type", itemFilter.getType().toString());
        }
        if (itemFilter.getKeyWords() != null) {
            if (!has_where) {
                sql += " where";
                has_where = true;
                sql += " item_name LIKE :item_name";
            }
            else
            {
                sql += " and item_name LIKE :item_name";
            }
            String str = "%" +itemFilter.getKeyWords() + "%";
            param.put("item_name", str);
        }
        if (itemFilter.getCheckCondition() != null) {
            if (!has_where) {
                sql += " where";
                has_where = true;
                sql += " checked=:checked";
            }
            else
            {
                sql += " and checked=:checked";
            }
            param.put("checked", itemFilter.getCheckCondition().toString());
        }
        if (itemFilter.getPriceOrdering() == Ordering.ASC)
            sql += " order by price_now ASC";
        if (itemFilter.getPriceOrdering() == Ordering.DESC)
            sql += " order by price_now DESC";
        List<SimplifiedItem> items;
        try {
            items = jdbcTemplate.query(sql,param, new SimplifiedItemRowMapper());
        } catch (Exception e) {
            return null;
        }
        return items;
    }
}
