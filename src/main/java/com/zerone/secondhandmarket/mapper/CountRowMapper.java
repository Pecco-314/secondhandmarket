package com.zerone.secondhandmarket.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountRowMapper implements RowMapper<Integer> {
    @Override
    public Integer mapRow(ResultSet rs, int i) throws SQLException {
        return rs.getInt("_count");
    }
}
