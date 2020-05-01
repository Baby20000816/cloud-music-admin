package com.soft1851.cloud.music.admin.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Description Captcha
 * @Author wf
 * @Date 2020/4/21
 * @Version 1.0
 */
@Configuration
public class CaptchaConfig {
    @Bean
    public DefaultKaptcha defaultKaptcha() {
        //构造器注入
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        //set注入
        Properties properties = new Properties();
        //添加边框
        properties.setProperty("kaptcha.textproducer.char.length", "6");
        properties.setProperty("kaptcha.border", "yes");
        properties.setProperty("kaptcha.border.color", "205,150,110");
        properties.setProperty("kaptcha.textproducer.font.color", "red");
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        properties.setProperty("kaptcha.textproducer.font.names", "微软雅黑, 楷体, 宋体");
        properties.setProperty("kaptcha.image.width", "120");
        properties.setProperty("kaptcha.image.height", "45");
        properties.setProperty("kaptcha.session.key", "code");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
