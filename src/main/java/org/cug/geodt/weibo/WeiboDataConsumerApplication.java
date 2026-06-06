package org.cug.geodt.weibo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import com.github.jeffreyning.mybatisplus.conf.EnableMPP;

@EnableMPP
@SpringBootApplication
@EnableAsync // 启用异步支持
@EnableSwagger2
@MapperScan({"org.cug.geodt.weibo.mapper.**"})
@MapperScan(basePackages = {"org.cug.geodt.weibo.sos.mapper"})
@MapperScan(basePackages = {"org.cug.geodt.weibo.sos.engine.mapper"})
public class WeiboDataConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeiboDataConsumerApplication.class, args);
	}
}
