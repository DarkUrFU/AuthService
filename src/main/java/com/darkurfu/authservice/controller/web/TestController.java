package com.darkurfu.authservice.controller.web;

import com.darkurfu.authservice.datamodels.user.UserAuthInfo;
import com.darkurfu.authservice.repository.mod.ModRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    ModRepository modRepository;

    @Autowired
    public TestController(ModRepository modRepository){
        this.modRepository = modRepository;
    }


    @GetMapping("/a")
    public String returnContext() {
        String s = SecurityContextHolder.getContext().getAuthentication().toString();
        UserAuthInfo s2 = (UserAuthInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "B\n" + s + "\n" + s2 ;
    }
}
