package com.zerone.secondhandmarket.service;

import com.zerone.secondhandmarket.dao.ImageDao;
import com.zerone.secondhandmarket.dao.ImageDaoOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ImageService implements ImageDao {
    @Autowired
    private ImageDaoOption daoOption = new ImageDaoOption();

    @Override
    // 用于添加图片信息
    public int insertImage(String imagePath) {
        return daoOption.insertImage(imagePath);
    }

    // 用于删除图片信息
    public int deleteImage(String imagePath) {
        return daoOption.deleteImage(imagePath);
    }
}
