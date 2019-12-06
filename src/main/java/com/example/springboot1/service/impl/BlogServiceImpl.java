package com.example.springboot1.service.impl;

import com.example.springboot1.mapper.BlogMapper;
import com.example.springboot1.mapper.UserMapper;
import com.example.springboot1.pojo.Blog;
import com.example.springboot1.pojo.PageInfo;
import com.example.springboot1.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Binquan.Cao
 */
@Service("blogService")
public class BlogServiceImpl implements BlogService {
    @Autowired
    BlogMapper blogMapper;
    @Autowired
    UserMapper userMapper;
    @Override
    public Blog selectBlog(int bId) {
        Blog blog = blogMapper.selectBlog(bId);
        blog.setBuName(userMapper.selectUser(blog.getBuId()).getuName());
        return blog;
    }

    @Override
    public int add(Blog blog) {
        return blogMapper.addBlog(blog);
    }

    @Override
    public int deleteBlog(int bId) {
        return blogMapper.deleteBlog(bId);
    }

    @Override
    public PageInfo<Blog> selectBcontent(int pageNo,String bContent) {
        PageInfo<Blog> pageInfo = new PageInfo<>();
        Map<String,Object> map = new HashMap<>(5);
        map.put("pageNo", 3*(pageNo-1));
        map.put("pageSize", 3);
        map.put("bContent",bContent);
        List<Blog> blogs = blogMapper.selectBcontent(map);
        for (Blog b:blogs) {
            String content = b.getbContent().substring(0,3);
            b.setbContent(content);
        }
        //将查询到的数据放入pageInfo对象
        pageInfo.setDatas(blogs);
        //将页码放入当前页码
        pageInfo.setCurrentPage(pageNo);
        //最大页码处理
        //a.查询总数据条目数
        int count = blogMapper.countBlogs();
        int maxPage = 0;
        //b.判断数据与每页显示条目数关系  例如:总共7条记录每页显示3条   总共需要3页   业务逻辑如下
        if(count%3==0){
            maxPage = count/3;
        }else{
            maxPage = count/3+1;
        }
        //将最大页码放入pageInfo
        pageInfo.setMaxPage(maxPage);
        return pageInfo;
    }
    @Cacheable(value = "blogs",key = "'a'+#pageNo+#pageSize")
    @Override
    public PageInfo<Blog> findBlogsByLimit(int pageNo, int pageSize) {
        PageInfo<Blog> pageInfo = new PageInfo<>();
        Map<String,Object> map = new HashMap<>(5);
        map.put("pageNo", pageSize*(pageNo-1));
        map.put("pageSize", pageSize);
        List<Blog> blogs = blogMapper.findBlogsByLimit(map);
        for (Blog b:blogs) {
            String content = b.getbContent().substring(0,3);
            b.setbContent(content);
            b.setBuName(userMapper.selectUser(b.getBuId()).getuName());
        }
        //将查询到的数据放入pageInfo对象
        pageInfo.setDatas(blogs);
        //将页码放入当前页码
        pageInfo.setCurrentPage(pageNo);
        //最大页码处理
        //a.查询总数据条目数
        int count = blogMapper.countBlogs();
        int maxPage = 0;
        //b.判断数据与每页显示条目数关系  例如:总共7条记录每页显示3条   总共需要3页   业务逻辑如下
        if(count%pageSize==0){
            maxPage = count/pageSize;
        }else{
            maxPage = count/pageSize+1;
        }
        //将最大页码放入pageInfo
        pageInfo.setMaxPage(maxPage);
        return pageInfo;
    }
}