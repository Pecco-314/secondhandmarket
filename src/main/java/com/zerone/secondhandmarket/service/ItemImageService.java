package com.zerone.secondhandmarket.service;

import com.zerone.secondhandmarket.dao.ItemImageDao;
import com.zerone.secondhandmarket.dao.ItemImageDaoOption;
import com.zerone.secondhandmarket.dao.ItemTagsDao;
import com.zerone.secondhandmarket.dao.ItemTagsDaoOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemImageService implements ItemImageDao {
    @Autowired
    private ItemImageDaoOption daoOption;

    @Override
    // 用于添加商品图片
    public int insertItemImage(int itemId, String imagePath) {
        return daoOption.insertItemImage(itemId, imagePath);
    }

    @Override
    // 用于删除商品图片
    public int deleteItemImage(int itemId, String imagePath) {
        return daoOption.deleteItemImage(itemId, imagePath);
    }

    @Override
    // 通过类型查询商品图片
    public List<String> getImagesByItemId(int itemId) {
        return daoOption.getImagesByItemId(itemId);
    }
}
