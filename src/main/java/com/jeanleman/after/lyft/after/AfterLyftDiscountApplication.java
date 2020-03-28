package com.jeanleman.after.lyft.after;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.jeanleman.after.lyft.after")
public class AfterLyftDiscountApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(AfterLyftDiscountApplication.class, args);
	}

}

