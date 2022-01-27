package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @title: CategoryServiceImpl
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 疾风剑豪  分类展示服务类
 * @date： 2022/1/6 下午 4:33
 */
public interface CategoryService {

    /**
     * 查询分类数据全部
     * @return
     */
    List<Category> queryAll();
}
