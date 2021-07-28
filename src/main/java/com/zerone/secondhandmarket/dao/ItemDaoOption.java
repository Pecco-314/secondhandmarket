package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.entity.SimplifiedItem;
import com.zerone.secondhandmarket.enums.ItemType;
import com.zerone.secondhandmarket.enums.Ordering;
import com.zerone.secondhandmarket.mapper.CountRowMapper;
import com.zerone.secondhandmarket.mapper.ItemRowMapper;
import com.zerone.secondhandmarket.mapper.SimplifiedItemRowMapper;
import com.zerone.secondhandmarket.message.ItemFilter;
import com.zerone.secondhandmarket.tools.IndexGenerator;
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

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("seller_id", item.getSeller())
                .addValue("item_name", item.getName())
                .addValue("item_type", item.getType().toString())
                .addValue("quantity", item.getQuantity())
                .addValue("price_now", item.getPrice())
                .addValue("price_original", item.getOriginalPrice())
                .addValue("introduction", item.getIntroduction())
                .addValue("coverPath", item.getCoverPath())
                .addValue("checked", item.getCheckCondition().toString())
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

    public List<Item> getItemListForHomepage() {
        String sql = "select * from LATEST_ITEMS";

        try {
            return jdbcTemplate.query(sql, new ItemRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    public Integer getItemCount(ItemFilter filter) {
        StringBuilder sql = new StringBuilder(500);

        Map<String, Object> param = new HashMap<>();

        if(filter.getTags() == null)
            sql.append("select COUNT(*) _count from item ");
        else
            sql.append("select COUNT(distinct item_id) _count from item natural join keywords");

        generateExpression(filter, sql, param);

        try {
            return jdbcTemplate.query(sql.toString(), param, new CountRowMapper()).get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Item> getItemListByFilter(ItemFilter filter) {
        StringBuilder sql = new StringBuilder(500);

        Map<String, Object> param = new HashMap<>();

        if(filter.getTags() == null)
            sql.append("select * from item ");
        else
            sql.append("select distinct item.* from item natural join keywords");

        generateExpression(filter, sql, param);

        if(filter.getPage() != null) {
            sql.append(" limit :start,:count");
            if(filter.isInShop()) {
                param.put("start", IndexGenerator.generateStartIndex(filter.getPage(), true));
                param.put("count", IndexGenerator.countPerPageInShop);
            } else {
                param.put("start", IndexGenerator.generateStartIndex(filter.getPage(), false));
                param.put("count", IndexGenerator.countPerPage);
            }
        }

        try {
            return jdbcTemplate.query(sql.toString(), param, new ItemRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    private static void generateExpression(ItemFilter filter, StringBuilder sql, Map<String, Object> param) {
        boolean hasOrder = false;
        boolean has_where = false;
        String keyword = filter.getKeyword();
        boolean hasKeyword = keyword != null && !keyword.isEmpty();

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
        if(filter.isNotEmpty()) {
            if(!has_where) {
                sql.append(" where quantity>0");
                has_where = true;
            } else {
                sql.append(" and quantity>0");
            }
        }
        if(hasKeyword) {
            if(!has_where) {
                sql.append(" where match_ratio(item_name,:item_name)>=49");
            } else {
                sql.append(" and match_ratio(item_name,:item_name)>=49");
            }
            param.put("item_name", keyword);
        }

        if(filter.getPriceOrdering() != null) {
            switch (filter.getPriceOrdering()) {
                case ASC:
                    sql.append(" order by price_now ASC");
                    hasOrder = true;
                    break;
                case DESC:
                    sql.append(" order by price_now DESC");
                    hasOrder = true;
                    break;
                default:
                    break;
            }
        }

        if(filter.getQuantityOrdering() != null)
            switch (filter.getQuantityOrdering()) {
                case ASC:
                    if(hasOrder)
                        sql.append(" ,quantity ASC");
                    else {
                        sql.append(" order by quantity ASC");
                        hasOrder = true;
                    }
                    break;
                case DESC:
                    if(hasOrder)
                        sql.append(" ,quantity DESC");
                    else {
                        sql.append(" order by quantity DESC");
                        hasOrder = true;
                    }
                    break;
                default:
                    break;
            }

        if(hasKeyword)
            if(hasOrder)
                sql.append(" ,match_ratio(item_name,:item_name) desc");
            else
                sql.append(" order by match_ratio(item_name,:item_name) desc");
    }
}
