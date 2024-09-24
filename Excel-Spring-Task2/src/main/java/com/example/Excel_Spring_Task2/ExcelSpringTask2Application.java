package com.example.Excel_Spring_Task2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ExcelSpringTask2Application {

	public static void main(String[] args)
	{
		SpringApplication.run(ExcelSpringTask2Application.class, args);
	}

}
