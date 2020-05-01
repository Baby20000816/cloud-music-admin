package com.soft1851.cloud.music.admin.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.soft1851.cloud.music.admin.dto.SignDto;
import com.soft1851.cloud.music.admin.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/4/21
 * @Version 1.0
 */
@RestController
@Slf4j
@Validated
public class CaptchaController {
    @Resource
    private DefaultKaptcha defaultKaptcha;
    @Resource
    private RedisService redisService;

    @GetMapping("/captcha")
    public void defaultCaptcha(@Valid @Param("name") @NotNull String name) {
        //取得HttpServletResponse对象
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert sra != null;
        HttpServletResponse response = sra.getResponse();
        //生成验证码文本
        String text = defaultKaptcha.createText();
        log.info("参数" + name);
        //将验证码存入redis，配置的失效时间单位是分钟
        redisService.set(name, text, 2L);
        log.info("验证码" + redisService.getValue(name, String.class));
        //生成验证码图片，并通过response输出到客户端浏览器
        BufferedImage image = defaultKaptcha.createImage(text);
        //设置response的响应内容类型为图片格式
        assert response != null;
        response.setContentType("image/jpeg");
        response.setDateHeader("Expires", 0);
        try {
            //通过ImageIO将验证码图片通过response的字节输出流传回客户端
            ImageIO.write(image, "jpg", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
