package cn.demo.travel.dao.impl;

import cn.demo.travel.dao.SellerDao;
import cn.demo.travel.domain.Seller;
import cn.demo.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 根据sid查询卖家对象
     * @param sid
     * @return
     */
    @Override
    public Seller findById(int sid) {
        String sql = " select * from tab_seller where sid = ? ";
        return  template.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),sid);
    }
}
