package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.entity.SimplifiedItem;
import com.zerone.secondhandmarket.enums.ItemType;
import com.zerone.secondhandmarket.enums.Ordering;
import com.zerone.secondhandmarket.mapper.ItemRowMapper;
import com.zerone.secondhandmarket.mapper.SimplifiedItemRowMapper;
import com.zerone.secondhandmarket.message.ItemFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ItemDaoOption {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    // 用于添加商品
    public int insertItem(Item item) {
        String sql = "insert into item(seller_id, item_name, item_type, quantity, price_now, price_original,introduction, coverPath, checked,release_time)" +
                "values(:seller_id,:item_name,:item_type,:quantity,:price_now,:price_original,:introduction,:coverPath,:checked,:release_time)";

        SqlParameterSource parameters = new MapSqlParameterSource().addValue("seller_id", item.getSeller())
                .addValue("item_name", item.getName()).addValue("item_type", item.getType().toString())
                .addValue("quantity", item.getQuantity()).addValue("price_now", item.getPrice())
                .addValue("price_original", item.getOriginalPrice()).addValue("introduction", item.getIntroduction())
                .addValue("coverPath", item.getCoverPath()).addValue("checked", item.getCheckCondition().toString())
                .addValue("release_time", item.getReleaseTime());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, parameters, keyHolder, new String[]{"item_id"});
        return keyHolder.getKey().intValue();
    }


    // 用于删除商品
    public int deleteItem(int itemId) {
        String sql = "delete from item where item_id=:id";
        Map<String, Object> param = new HashMap<>();
        param.put("id", itemId);
        return jdbcTemplate.update(sql, param);
    }

    // 用于更新商品
    public int updateItem(Item item) {
        String sql = "update item set seller_id=:seller_id,item_name=:item_name,item_type=:item_type,quantity=:quantity,price_now=:price_now,price_original=:price_original,introduction=:introduction,coverPath=:item_pic_path,checked=:checked where item_id=:item_id";
        Map<String, Object> param = new HashMap<>();
        param.put("item_name", item.getName());
        param.put("item_id", item.getId());
        param.put("seller_id", item.getSeller());
        param.put("item_type", item.getType().toString());
        param.put("quantity", item.getQuantity());
        param.put("price_now", item.getPrice());
        param.put("price_original", item.getOriginalPrice());
        param.put("introduction", item.getIntroduction());
        param.put("item_pic_path", item.getCoverPath());
        param.put("checked", item.getCheckCondition().toString());
        return jdbcTemplate.update(sql, param);
    }


    // 通过类型查询商品
    public List<Item> getItemByType(ItemType itemtype) {
        String sql = "select * from item where item_type=:item_type";
        Map<String, Object> param = new HashMap<>();
        param.put("item_type", itemtype.toString());
        try {
            return jdbcTemplate.query(sql, param, new ItemRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    // 通过id查询商品
    public Item getItemById(int itemId) {
        String sql = "select * from item where item_id=:id";
        Map<String, Object> param = new HashMap<>();
        param.put("id", itemId);
        try {
            return jdbcTemplate.queryForObject(sql, param, new ItemRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    // 用于查询所有商品列表
    public List<Item> getItemList() {
        String sql = "select * from item";

        try {
            return jdbcTemplate.query(sql, new ItemRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    //按价格排序获取商品列表
    public List<Item> getItemListOrderByPrice(Ordering ordering) {
        String sql;
        switch(ordering) {
            case ASC:
                sql = "select * from item order by price_now ASC";
                break;
            case DESC:
                sql = "select * from item order by price_now DESC";
                break;
            default:
                sql = "select * from item";
                break;
        }
        try {
            return jdbcTemplate.query(sql, new ItemRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    // 通过类似商品名（关键字）查询商品
    public List<Item> getItemByKeyword(String keyword) {
        String sql = "select * from item where item_name LIKE :item_name";
        Map<String, Object> param = new HashMap<>();
        String str = "%" + keyword + "%";
        param.put("item_name", str);
        try {
            return jdbcTemplate.query(sql, param, new ItemRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    //按filter获取商品列表
    public List<Item> getItemByFilter(ItemFilter itemFilter) {
        StringBuilder sql = new StringBuilder(500);
        if(itemFilter.getTags() == null)
            sql.append("select * from item");
        else
            sql.append("select * from item natural join keywords");

        Map<String, Object> param = new HashMap<>();
        boolean has_where = false;

        if (itemFilter.getSeller() != null) {
            sql.append(" where seller_id=:seller_id");
            has_where = true;

            param.put("seller_id", itemFilter.getSeller());
        }
        if (itemFilter.getType() != null) {
            if (!has_where) {
                sql.append(" where item_type=:item_type");
                has_where = true;
            } else {
                sql.append(" and item_type=:item_type");
            }
            param.put("item_type", itemFilter.getType().toString());
        }
        if (itemFilter.getTags() != null) {
            if(!has_where) {
                sql.append(" where (");
                has_where = true;
            } else {
                sql.append(" and (");
            }

            for(int i = 0; i < itemFilter.getTags().length; ++i) {
                if(i > 0) {
                    sql.append(" or");
                }
                sql.append(" keyword=:keyword" + i);
                param.put("keyword" + i, itemFilter.getTags()[i]);
            }
            sql.append(")");
        }
        if (itemFilter.getCheckCondition() != null) {
            if (!has_where) {
                sql.append(" where checked=:checked");
                //has_where = true;
            } else {
                sql.append(" and checked=:checked");
            }
            param.put("checked", itemFilter.getCheckCondition().toString());
        }

        if(itemFilter.getPriceOrdering() != null) {
            switch (itemFilter.getPriceOrdering()) {
                case ASC:
                    sql.append(" order by price_now ASC");
                    break;
                case DESC:
                    sql.append(" order by price_now DESC");
                    break;
                default:
                    break;
            }
        }

        try {
            return jdbcTemplate.query(sql.toString(), param, new ItemRowMapper())
                    .stream()
                    .distinct()
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    public List<Item> getItemListByFilterAndKeyword(ItemFilter filter, String keyword) {
        StringBuilder sql = new StringBuilder(500);
        if(filter.getTags() == null)
            sql.append("select * from item ");
        else
            sql.append("select * from item natural join keywords");

        Map<String, Object> param = new HashMap<>();
        boolean has_where = false;
        if (filter.getSeller() != null) {
            sql.append(" where seller_id=:seller_id");
            has_where = true;

            param.put("seller_id", filter.getSeller());
        }
        if (filter.getType() != null) {
            if (!has_where) {
                sql.append(" where item_type=:item_type");
                has_where = true;
            } else {
                sql.append(" and item_type=:item_type");
            }
            param.put("item_type", filter.getType().toString());
        }
        if (filter.getTags() != null) {
            if(!has_where) {
                sql.append(" where (");
                has_where = true;
            } else {
                sql.append(" and (");
            }

            for(int i = 0; i < filter.getTags().length; ++i) {
                if(i > 0) {
                    sql.append(" or");
                }
                sql.append(" keyword=:keyword" + i);
                param.put("keyword" + i, filter.getTags()[i]);
            }
            sql.append(")");
        }
        if (filter.getCheckCondition() != null) {
            if (!has_where) {
                sql.append(" where checked=:checked");
                has_where = true;
            } else {
                sql.append(" and checked=:checked");
            }
            param.put("checked", filter.getCheckCondition().toString());
        }
        if(keyword != null && !keyword.isEmpty()) {
            if(!has_where) {
                sql.append(" where item_name LIKE :item_name");
            } else {
                sql.append(" and item_name LIKE :item_name");
            }
            param.put("item_name", String.format("%%%s%%", keyword));
        }

        if(filter.getPriceOrdering() != null) {
            switch (filter.getPriceOrdering()) {
                case ASC:
                    sql.append(" order by price_now ASC");
                    break;
                case DESC:
                    sql.append(" order by price_now DESC");
                    break;
                default:
                    break;
            }
        }

        try {
            return jdbcTemplate.query(sql.toString(), param, new ItemRowMapper())
                    .stream()
                    .distinct()
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    //按filter获取商品列表
    /*public List<SimplifiedItem> getSimplifiedItemByFilter(ItemFilter itemFilter) {
        String sql = "select item_id,item_name,price_now,coverPath from item";
        Map<String, Object> param = new HashMap<>();
        boolean has_where = false;
        if (itemFilter.getSeller() != null) {
            if (!has_where) {
                sql += " where";
                has_where = true;
                sql += " seller_id=:seller_id";
            } else {
                sql += " and seller_id=:seller_id";
            }

            param.put("seller_id", itemFilter.getSeller());
        }
        if (itemFilter.getType() != null) {
            if (!has_where) {
                sql += " where";
                has_where = true;
                sql += " item_type=:item_type";
            } else {
                sql += " and item_type=:item_type";
            }
            param.put("item_type", itemFilter.getType().toString());
        }
        if (itemFilter.getTags() != null) {
            if (!has_where) {
                sql += " where";
                has_where = true;
                sql += " item_name LIKE :item_name";
            } else {
                sql += " and item_name LIKE :item_name";
            }
            String str = "%" + itemFilter.getTags() + "%";
            param.put("item_name", str);
        }
        if (itemFilter.getCheckCondition() != null) {
            if (!has_where) {
                sql += " where";
                has_where = true;
                sql += " checked=:checked";
            } else {
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
            return jdbcTemplate.query(sql, param, new SimplifiedItemRowMapper());
        } catch (Exception e) {
            return null;
        }
    }*/
}
