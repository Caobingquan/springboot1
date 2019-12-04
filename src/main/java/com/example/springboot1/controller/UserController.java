package com.example.springboot1.controller;

import com.example.springboot1.pojo.User;
import com.example.springboot1.service.UserService;
import com.example.springboot1.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @Title ${NAME}
 * @Description
 * @author {你的名字}
 * @date ${DATE}
 */


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RedisUtils redisUtils;

    public static String getMd5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/index")
    public String rh()throws Exception{
        System.out.println("访问templates下index.html");
        return "index";
    }

    /**
    登录
     */
    @PostMapping("/login")
    public String login(HttpServletRequest request, User user) {
            String s = getMd5(user.getuPassword());
            user.setuPassword(s);
            user.setuErrorDate(new Date());
            //进行 用户登录操作
            String result = userService.login(user);
            switch (result) {
                case "ok":
                    HttpSession session = request.getSession();
                    session.setAttribute("Phone",user.getuPhone());
//                    return "redirect:blog/blogselect";
                    return "forward:../blog/blogselect";
                case "uNameErr":
                    request.setAttribute("msg", "输入的手机号不存在!");
                    return "index";
                case "uPasswordErr":
                    request.setAttribute("msg", "输入的密码错误!");
                    return "index";
                case "DateErr":
                    request.setAttribute("msg", "你的账号被锁定!");
                    return "index";
                default:
                    break;
            }
            return "welcome";
    }

    /**
    用户注册
     */
    @RequestMapping("/register")
    public  String add(HttpServletRequest request, User user){
        String s = getMd5(user.getuPassword());
        user.setuPassword(s);
        int a = userService.add(user);
        if (a>0){
            System.out.println("注册成功");
            request.setAttribute("msg", "ok!");
        }else {
            System.out.println("注册失败");
            request.setAttribute("msg", "no!");
        }
        return "index";
    }

    /**
     * 手机验证码
     */
    @RequestMapping("/checkcode1")
    @ResponseBody
    public String checkcode1(HttpServletRequest request, String checkcode1){
        System.out.println("yh");
        Object a = redisUtils.get("phoneCode");
        String sessionCode = (String) request.getSession().getAttribute("phoneCode");
        if (null == a || "".equals(a)) {
            return "{\"msg\":\"m1\"}";
        } else if (null == checkcode1 || "".equals(checkcode1)) {
            return "{\"msg\":\"m2\"}";
        } else if (checkcode1.equalsIgnoreCase(sessionCode)){
            return "{\"msg\":\"m3\"}";
        } else {
            return "{\"msg\":\"m4\"}";
        }
    }

    /**
     * 手机图片验证码
     */
    @RequestMapping("/checkcode")
    @ResponseBody
    public String checkcode(HttpServletRequest request, String checkcode){
        System.out.println("yhdd");
        String sessionCode = (String) request.getSession().getAttribute("imgCode");
        if (null == sessionCode || "".equals(sessionCode) || null == checkcode || "".equals(checkcode)) {
            return "{\"msg\":\"m1\"}";
        } else if (checkcode.equalsIgnoreCase(sessionCode)){
            return "{\"msg\":\"m3\"}";
        } else {
            return "{\"msg\":\"m2\"}";
        }
    }

    /**
     * 手机是否重复
     */
    @RequestMapping("/checkUphone")
    @ResponseBody
    public String checkUphone(String uPhone){
        System.out.println("yhmm");
        User user1=userService.selectCheckUphone(uPhone);
        if (null==user1){
            return "{\"msg\":\"ok\"}";
        }else{
            return "{\"msg\":\"no\"}";
        }
    }


    /**
    更新管理员
     */
    @RequestMapping("/update1")
    public String update1(Model model,int uId){
        User user = userService.selectUser(uId);
        model.addAttribute("user",user);
        //return "user/userupdate";
        return  "user/user-edit";
    }

    @ResponseBody
    @RequestMapping("/update2")
    public void update2(Model model,User user){
        int a = userService.updateUser(user);
        if (a>0){
            System.out.println("更新成功！");
            model.addAttribute("msg","更新成功");
        }else {
            System.out.println("更新失败！");
            model.addAttribute("msg","更新失败");
        }
//        return "user/welcome";
    }

    /**
    上传图片
     */
    @RequestMapping("/upload1")
    public String upload1(MultipartFile upload)throws Exception{
        String imgName = upload.getOriginalFilename();
        if(imgName!=null &&upload!=null&&imgName.length()>0){
            //向对应项目地址中，上传文件
            String imgPath = "E:\\IDEA\\homemaking\\src\\main\\resources\\static\\img\\";
            String newFileName = UUID.randomUUID()+imgName.substring(imgName.lastIndexOf("."));
            File file = new File(imgPath+newFileName);
            //文件写入磁盘E:\IDEA\homemaking
            upload.transferTo(file);
            //将生成的图片名给User对象    用于存在数据库表中  User表的 U_IMAGE 字段中
            User user = new User();
            user.setuPhoto(newFileName);
            System.out.println(user.getuPhoto());
            return "{\"msg\":\"ok\"}";
        }
        return "{\"msg\":\"no\"}";
    }

    /**
    手机验证码
     */
    @ResponseBody
    @RequestMapping("/createcode1")
    public void createcode1(HttpServletRequest request){
        char[] chars="0123456789".toCharArray();
        Random random=new Random();
        StringBuilder stringBuilder=new StringBuilder();
        for (int i=0;i<4;i++){
            //随机位置根据自己定义的chars数组而定
            int n=random.nextInt(chars.length);
            //将chars数组中对应位置内容 放入到sb 对象
            stringBuilder.append(chars[n]);
        }
        String code = stringBuilder.toString();
        System.out.println(code);
        redisUtils.set("phoneCode",code,60);
        HttpSession session=request.getSession();
        session.setAttribute("phoneCode",code);
        session.setMaxInactiveInterval(60);
    }

    /**
    图片验证码
     */
    @RequestMapping("/createImage")
    public void code(HttpServletRequest request, HttpServletResponse response){
        //1.规定验证码范围
        System.out.println("createImage");
        char[] chars="qwertyuiopasdfghjklzxcvbnm148523690".toCharArray();
        //2.获取一个Image 对象 用于存放图片
        BufferedImage image =new BufferedImage(80,20,BufferedImage.TYPE_3BYTE_BGR);
        //3.获取画笔能够将写入图片中
        Graphics graphics=image.getGraphics();
        //4.图片背景设置
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0,0,80,20);
        //5.边框设置
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0,0,79,19);
        //6.字体设置
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("黑体",Font.BOLD,18));
        //7.随机生成4个字符，作为验证码
        Random random=new Random();
            StringBuilder stringBuilder=new StringBuilder();
            for (int i=0;i<4;i++){
                //随机位置根据自己定义的chars数组而定
                int n=random.nextInt(chars.length);
                //将chars数组中对应位置内容 放入到sb 对象
                stringBuilder.append(chars[n]+" ");
            }
        //8.随机数添加到画布中
        graphics.drawString(stringBuilder.toString(),3,15);
        //9.添加干扰点
        graphics.setColor(Color.BLUE);
        for (int i=0;i<100;i++){
            int x= random.nextInt(80);
            int y= random.nextInt(20);
            graphics.drawOval(x,y,1,1);
        }
        //10.显示拼装图片
        HttpSession session=request.getSession();
        try {
            ImageIO.write(image,"jpeg",response.getOutputStream());
        } catch (IOException e){
            e.printStackTrace();
        }

        String[] strings=stringBuilder.toString().split(" ");
        String code="";
        for (int i=0;i<strings.length;i++){
            code+=strings[i];
        }
        session.setAttribute("imgCode",code);

    }
}
