package cn.demo.travel.dao.impl;

import cn.demo.travel.dao.RouteImgDao;
import cn.demo.travel.domain.RouteImg;
import cn.demo.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

//根据rid查询RouteImg
public class RouteImgDaoImpl implements RouteImgDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<RouteImg> findByRid(int rid) {
        String  sql = " select * from tab_route_img where rid = ? ";
        return template.query(sql,new BeanPropertyRowMapper<>(RouteImg.class),rid);
    }
}
