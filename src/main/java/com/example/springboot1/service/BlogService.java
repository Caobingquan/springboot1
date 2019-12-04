package com.example.springboot1.service;


import com.example.springboot1.pojo.Blog;
import com.example.springboot1.pojo.PageInfo;

/**
 * @author Binquan.Cao
 */
public interface BlogService {
    /**
     * 根据编号查询微博信息
     */
    Blog selectBlog(int bId);

    /**
     添加微博
     */
    int add(Blog blog);

    /**
    删除微博
     */
    int deleteBlog(int bId);

    /**
     * 查询条件
     */
    PageInfo<Blog> selectBcontent(int pageNo,String bContent);

    /**
    分页业务逻辑
     */
    PageInfo<Blog> findBlogsByLimit(int pageNo, int pageSize);
}
