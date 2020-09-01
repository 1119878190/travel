package cn.demo.travel.service;

import cn.demo.travel.domain.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 查询所有
     * @return
     */
    public List<Category> findAll();
}
