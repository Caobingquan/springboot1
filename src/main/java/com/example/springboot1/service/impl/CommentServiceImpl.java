package com.example.springboot1.service.impl;

import com.example.springboot1.mapper.CommentMapper;
import com.example.springboot1.pojo.Comment;
import com.example.springboot1.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author Binquan.Cao
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Override
    public int addComment(Comment comment) {
        return commentMapper.addComment(comment);
    }

    @Override
    public List<Comment> selectByCbId(int cbId) {
        return commentMapper.selectBycbId(cbId);
    }


}
