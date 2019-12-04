package com.example.springboot1.mapper;

import com.example.springboot1.pojo.Blog;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
/**
 * @author Binquan.Cao
 */
@Repository("blogMapper")
public interface BlogMapper {

    Blog selectBlog(int bId);


    /**
     * 添加微博
     * @param blog
     * @return
     */
    int addBlog(Blog blog);
    /**
     * 根据内容查询（参数为bContent）
     */
    public List<Blog> selectBcontent(Map map);

    /**
     * 删除微博
     * @param bId
     * @return
     */
    @Delete("delete from blog where B_ID = #{bId}")
    int deleteBlog(int bId);

    /**
     * 分页查询数据总条目数
     * @return
     */
    public int countBlogs();

    /**
     * 分页查询用户的方法（不包含条件）
     * @param map pageNo  当前页码  pageSize  每页显示条目数
     * @return
     */
    public List<Blog> findBlogsByLimit(Map map);
}
