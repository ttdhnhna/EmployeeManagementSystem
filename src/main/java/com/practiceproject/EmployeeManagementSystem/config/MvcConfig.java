package com.practiceproject.EmployeeManagementSystem.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{
    @Override
    public void addResourceHandlers(@SuppressWarnings("null") ResourceHandlerRegistry registry){
        Path uploadDir=Paths.get("./anh");
        String uploadPath=uploadDir.toFile().getAbsolutePath();
        // if(dirName.startsWith("../")) dirName= dirName.replaceAll("../", "");
        registry.addResourceHandler("/anh/**").addResourceLocations("file:/"+uploadPath+"/");
    }
}
