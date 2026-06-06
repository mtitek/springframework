package com.mtitek.spring;

import java.util.Arrays;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MtitekSpringStarterProjectApplication {
	private final static Logger LOG = LoggerFactory.getLogger(MtitekSpringStarterProjectApplication.class);

	public Integer value = 1;

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(MtitekSpringStarterProjectApplication.class, args);

		System.out.println("Welcome to MTITEK Spring Starter Project.");
		LOG.info("Welcome to MTITEK Spring Starter Project.");

		Arrays.stream(ctx.getBeanDefinitionNames()).sorted().forEach(bdn -> LOG.info("Spring Bean Definition Name: {}", bdn));

		MtitekSpringStarterProjectApplication mtitekSpringStarterProjectApplication = ctx
				.getBean(MtitekSpringStarterProjectApplication.class);
		System.out.println(mtitekSpringStarterProjectApplication.getValue());
		mtitekSpringStarterProjectApplication.setValue(2);

		mtitekSpringStarterProjectApplication = ctx.getBean("mtitekSpringStarterProjectApplication",
				MtitekSpringStarterProjectApplication.class);
		System.out.println(mtitekSpringStarterProjectApplication.getValue());

		String appUuid = ctx.getBean("appUuid", String.class);
		System.out.println(appUuid);
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Bean
	String appUuid() {
		return UUID.randomUUID().toString();
	}
}
