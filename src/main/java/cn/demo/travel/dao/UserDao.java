package cn.demo.travel.dao;

import cn.demo.travel.domain.User;

public interface UserDao {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 用户保存
     * @param user
     */
    public void save(User user);

    /**
     * 查找激活码
     * @param code
     * @return
     */
    User findByCode(String code);

    /**
     * 更改用户激活状态
     * @param user
     */
    void updateStatus(User user);

    /**
     * 根据用户名 和密码查询
     * @param username
     * @param password
     */
    User findByUsernameAndPassword(String username, String password);
}
