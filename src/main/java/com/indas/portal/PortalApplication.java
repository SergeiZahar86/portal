package com.indas.portal;

import com.indas.portal.configure.PartDataBaseConfiguration;
import com.indas.portal.configure.DataBaseSecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
		PartDataBaseConfiguration.class,
		DataBaseSecurityConfiguration.class,
		//WebConfig.class
})
@ComponentScans({
		@ComponentScan("com.indas.portal.service"),
		@ComponentScan("com.indas.portal.configure"),
		@ComponentScan("com.indas.portal.rest"),
		@ComponentScan("com.indas.portal.controllers"),
		@ComponentScan("com.indas.portal.repositories"),
		@ComponentScan("com.indas.portal.security"),
		@ComponentScan("com.indas.portal.box"),
		@ComponentScan("com.indas.portal.export"),
		@ComponentScan("com.indas.portal.media"),
})
public class PortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortalApplication.class, args);
	}

}
