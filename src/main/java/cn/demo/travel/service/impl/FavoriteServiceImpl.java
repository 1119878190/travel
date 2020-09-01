package cn.demo.travel.service.impl;

import cn.demo.travel.dao.FavoriteDao;
import cn.demo.travel.dao.impl.FavoriteDaoImpl;
import cn.demo.travel.domain.Favorite;
import cn.demo.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     * 判断是收藏
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite =favoriteDao.findByRidAndUid(Integer.parseInt(rid),uid);
        if (favorite == null){
            return false;
        }else {
            return true;
        }

    }

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }

    /**
     * 取消收藏
     * @param rid
     * @param uid
     */

    @Override
    public void rem(String rid, int uid) {
        favoriteDao.rem(Integer.parseInt(rid),uid);
    }
}
