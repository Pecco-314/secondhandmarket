package com.zerone.secondhandmarket.service;

import com.zerone.secondhandmarket.dao.ItemDao;
import com.zerone.secondhandmarket.dao.ItemDaoOption;
import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.enums.ItemType;
import com.zerone.secondhandmarket.enums.Ordering;
import com.zerone.secondhandmarket.message.ItemFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemService implements ItemDao {
    @Autowired
    private ItemDaoOption daooption = new ItemDaoOption();

    @Override
    // 用于添加商品
    public int insertItem(Item item) {
        daooption.insertItem(item);
        return 0;
    }

    @Override
    // 用于删除商品
    public int deleteItem(int itemId) {
        daooption.deleteItem(itemId);
        return 0;
    }

    @Override
    // 用于更新商品
    public int updateItem(Item item) {
        daooption.updateItem(item);
        return 0;
    }

    @Override
    // 通过类型查询商品
    public List<Item> getItemByType(ItemType itemtype) {
        return daooption.getItemByType(itemtype);
    }

    @Override
    // 通过id查询商品
    public Item getItemById(int itemId) {
        return daooption.getItemById(itemId);
    }

    @Override
    // 通过类似商品名（关键字）查询商品
    public List<Item> getItemByKeyword(String keyword) {
        return daooption.getItemByKeyword(keyword);
    }

    @Override
    // 用于查询所有商品列表，默认顺序
    public List<Item> getItemList() {
        return daooption.getItemList();
    }

    //按价格升序或降序获得商品列表，输入ordering
    @Override
    public List<Item> getItemListOrderByPrice(Ordering ordering) {
        return daooption.getItemListOrderByPrice(ordering);
    }

    @Override
    public List<Item> getItemByFilter(ItemFilter itemFilter) {
        return daooption.getItemByFilter(itemFilter);
    }
}
