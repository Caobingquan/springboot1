package com.example.springboot1.service;

import com.example.springboot1.pojo.Comment;

import java.util.List;

/**
 * @author Binquan.Cao
 */
public interface CommentService {
    /**
     * 添加评论
     */
    int addComment(Comment comment);
    /**
     * 查询评论
     */
    List<Comment> selectByCbId(int cbId);
}
