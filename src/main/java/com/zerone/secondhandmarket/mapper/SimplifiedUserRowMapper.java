package com.zerone.secondhandmarket.mapper;
import com.zerone.secondhandmarket.entity.SimplifiedUser;
import com.zerone.secondhandmarket.enums.UserHead;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 实现RowMapper接口，返回simplifiedUser对象
 * */
public class SimplifiedUserRowMapper implements RowMapper<SimplifiedUser>{

    @Override
    public SimplifiedUser mapRow(ResultSet rs, int i) throws SQLException {
//        获取结果集中的数据
        SimplifiedUser temp = new SimplifiedUser();
        temp.setUserID(rs.getInt("user_id"));
        temp.setNickName(rs.getString("Nickname"));
        temp.setHead(UserHead.valueOf(rs.getString("head_portrait")));
        return temp;
    }
}

