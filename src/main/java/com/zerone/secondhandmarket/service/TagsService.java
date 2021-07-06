package com.zerone.secondhandmarket.service;

import com.zerone.secondhandmarket.dao.ItemTagsDao;
import com.zerone.secondhandmarket.dao.ItemTagsDaoOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagsService implements ItemTagsDao {
    @Autowired
    private ItemTagsDaoOption daoOption;

    // 用于添加商品关键词
    @Override
    public int insertTag(int itemId, String keyword) {
        return daoOption.insertTag(itemId, keyword);
    }

    @Override
    // 用于删除关键词
    public int deleteItem(int itemId, String keyword) {
        return daoOption.deleteItem(itemId, keyword);
    }

    @Override
    // 通过类型查询商品
    public List<String> getTagsByItemId(int itemId) {
        return daoOption.getTagsByItemId(itemId);
    }
}
