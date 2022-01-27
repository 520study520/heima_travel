package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @title: CategoryDao
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 疾风剑豪
 * @date： 2022/1/6 下午 4:25
 */
public interface CategoryDao {

    /**
     *
     * 查询分类数据全部
     * @return 分类数据对象集合
     */
    List<Category> findAll();
}
