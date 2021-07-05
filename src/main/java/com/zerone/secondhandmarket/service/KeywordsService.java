package com.zerone.secondhandmarket.service;

import com.zerone.secondhandmarket.dao.AdminDaoOption;
import com.zerone.secondhandmarket.dao.ItemKeywordDao;
import com.zerone.secondhandmarket.dao.ItemKeywordDaoOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class KeywordsService implements ItemKeywordDao {
    @Autowired
    private ItemKeywordDaoOption daoOption;

    // 用于添加商品关键词
    @Override
    public int insertKeyword(int item_id, String keyword) {
        return daoOption.insertKeyword(item_id, keyword);
    }

    @Override
    // 用于删除关键词
    public int deleteItem(int itemId, String keyword) {
        return daoOption.deleteItem(itemId, keyword);
    }

    @Override
    // 通过类型查询商品
    public List<String> getKeywordsByItemId(int itemId) {
        return getKeywordsByItemId(itemId);
    }
}
