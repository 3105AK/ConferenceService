package com.brawlstars.ConferenceService.configurers;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/enter").setViewName("enter");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/createRoom").setViewName("createRoom");
        //registry.addViewController("/room").setViewName("room");
        //registry.addViewController("/room/{id}").setViewName("room");
        registry.addViewController("/room/{id}/add").setViewName("room");
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/admin/reports").setViewName("reports");
        registry.addViewController("/admin/{id}").setViewName("admin");
        registry.addViewController("/hello").setViewName("hello");//will delete, need to check
    }

}
