package cn.demo.travel.dao;

import cn.demo.travel.domain.Favorite;

public interface FavoriteDao {
    /**
     * 根据rid uid查询收藏信息
     * @param rid
     * @param uid
     * @return
     */
    public Favorite findByRidAndUid(int rid, int uid);

    /**
     * 根据rid查询线路收藏次数
     * @param rid
     * @return
     */

    public int findCountByRid(int rid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    public void add(int rid, int uid);

    /**
     * 取消收藏
     * @param rid
     * @param uid
     */
    public void rem(int rid, int uid);
}
