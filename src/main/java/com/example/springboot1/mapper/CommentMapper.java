package com.example.springboot1.mapper;

import com.example.springboot1.pojo.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author Binquan.Cao
 */
@Repository("commentMapper")
public interface CommentMapper {

    /**
     * 添加评论
     * @param comment
     * @return
     */
    int addComment(Comment comment);
    /**
     * 根据微博编号查询
     */
    List<Comment> selectBycbId(int cbId);


}
