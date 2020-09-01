package cn.demo.travel.dao.impl;

import cn.demo.travel.dao.FavoriteDao;
import cn.demo.travel.domain.Favorite;
import cn.demo.travel.util.JDBCUtils;
import com.sun.xml.internal.xsom.XSUnionSimpleType;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 根据rid uid查询收藏线路
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        Favorite favorite = null;
        try {
            String sql = " select * from tab_favorite where rid = ? and uid = ? ";
            favorite = template.queryForObject(sql,new BeanPropertyRowMapper<Favorite>(Favorite.class),rid,uid);
        } catch (DataAccessException e) {
            System.out.println("没有改用户");
        }
        return favorite;
    }

    /**
     * 根据rid查询线路收藏次数
     * @param rid
     * @return
     */

    @Override
    public int findCountByRid(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";
        return template.queryForObject(sql,Integer.class,rid);
    }

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    @Override
    public void add(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";
        template.update(sql,rid,new Date(),uid);
    }

    /**
     * 取消收藏
     * @param rid
     * @param uid
     */
    @Override
    public void rem(int rid, int uid) {
        String sql = " delete from tab_favorite where rid = ? and uid = ? ";
        template.update(sql,rid,uid);
    }
}
