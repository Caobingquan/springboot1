package com.example.springboot1.controller;

import com.example.springboot1.pojo.Comment;
import com.example.springboot1.pojo.User;
import com.example.springboot1.service.CommentService;
import com.example.springboot1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Binquan.Cao
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    /**
    添加微博
     */
    @RequestMapping("/add1")
    public String add1(Model model,int cbId)throws Exception{
        model.addAttribute("cbId",cbId);
        return "add1";
    }
    @RequestMapping("/add2")
    public String add2(HttpServletRequest request,MultipartFile upload,Comment comment,String phone)throws Exception{
        String imgName = upload.getOriginalFilename();
        if(imgName!=null &&upload!=null&&imgName.length()>0){
            //向对应项目地址中，上传文件
            String imgPath = "E:\\XM\\springboot1\\src\\main\\resources\\static\\images\\";
            String newFileName = UUID.randomUUID()+imgName.substring(imgName.lastIndexOf("."));
            File file = new File(imgPath+newFileName);
            upload.transferTo(file);
            comment.setcPhoto(newFileName);
            System.out.println(comment.getcPhoto());
        }
        User user = userService.selectUser2(phone);
        comment.setCuId(user.getuId());
        comment.setcDate(new Date());
        int a = commentService.addComment(comment);
        if (a>0){
            System.out.println("添加成功");
            request.setAttribute("msg", "ok!");
        }else {
            System.out.println("添加失败");
            request.setAttribute("msg", "no!");
        }
        return "forward:../blog/selectbyBid?bId="+comment.getCbId();
    }

    @RequestMapping("/select/{cBId}")
    @ResponseBody
    public List<Comment> select(@PathVariable("cBId") int cbId){
        List<Comment> comments = commentService.selectByCbId(cbId);
        return comments;
    }

}
