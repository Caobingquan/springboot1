package com.example.springboot1.controller;

import com.example.springboot1.pojo.Blog;
import com.example.springboot1.pojo.Comment;
import com.example.springboot1.pojo.PageInfo;
import com.example.springboot1.pojo.User;
import com.example.springboot1.service.BlogService;
import com.example.springboot1.service.CommentService;
import com.example.springboot1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Binquan.Cao
 */
@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

    @RequestMapping("/selectbyBid")
    public  String selectbyBid(Model model,HttpServletRequest request, int bId){
        Blog blog= blogService.selectBlog(bId);
        if (null==blog){
            System.out.println("查询失败");
            request.setAttribute("msg", "no");
        }else {
            System.out.println("查询成功");
            List<Comment> comments = commentService.selectByCbId(bId);
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            request.setAttribute("msg", "ok");
            model.addAttribute("blog",blog);
            model.addAttribute("time",s.format(blog.getbDate()));
            model.addAttribute("comments",comments);
            request.setAttribute("blog",blog);
        }
        return "message";
    }

    /**
    添加微博
     */
    @RequestMapping("/add1")
    public String add1(HttpServletRequest request,String bPhone)throws Exception{
        request.setAttribute("msg",bPhone);
        return "add";
    }
    @RequestMapping("/add2")
    public String add2(HttpServletRequest request,MultipartFile upload,Blog blog,String phone)throws Exception{
        String imgName = upload.getOriginalFilename();
        if(imgName!=null &&upload!=null&&imgName.length()>0){
            //向对应项目地址中，上传文件
            String imgPath = "E:\\XM\\springboot1\\src\\main\\resources\\static\\images\\";
            String newFileName = UUID.randomUUID()+imgName.substring(imgName.lastIndexOf("."));
            File file = new File(imgPath+newFileName);
            upload.transferTo(file);
            blog.setbPhoto(newFileName);
            System.out.println(blog.getbPhoto());
        }
        User user = userService.selectUser2(phone);
        blog.setBuId(user.getuId());
        blog.setbDate(new Date());
        int a = blogService.add(blog);
        if (a>0){
            System.out.println("添加成功");
            request.setAttribute("msg", "ok!");
        }else {
            System.out.println("添加失败");
            request.setAttribute("msg", "no!");
        }
        return "forward:blogselect";
    }

    /**
    删除微博
     */
    @RequestMapping("/delete")
    public  String delete(HttpServletRequest request, int bId){
        int a = blogService.deleteBlog(bId);
        if (a>0){
            System.out.println("删除成功");
            request.setAttribute("msg", "ok");
        }else {
            System.out.println("删除失败");
            request.setAttribute("msg", "no");
        }
        return "forward:blogselect";
    }

    /**
    分页查询
     */
    @RequestMapping("/selectBcontent")
    public String selectBcontent(Model model,
                                 @RequestParam(defaultValue = "1")int pageNo,String bContent ){
        PageInfo<Blog> pageInfo = blogService.selectBcontent(pageNo, bContent);
        model.addAttribute("pageInfo",pageInfo);
        return "list";
    }

    /**
    分页查询
     */
    @RequestMapping("/blogselect")
    public String findUsersLimit(Model model,
                                   @RequestParam(defaultValue = "1")int pageNo){
        int pageSize = 3;
        PageInfo<Blog> pageInfo = blogService.findBlogsByLimit(pageNo,pageSize);
        model.addAttribute("pageInfo",pageInfo);
        return "list";
    }
}
