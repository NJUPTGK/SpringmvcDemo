package com.tgk.springmvc.Controller;


import com.tgk.springmvc.RequestMapping;
import com.tgk.springmvc.handler.RequestParam;
import org.springframework.stereotype.Controller;

@Controller
public class HelloController {

        @RequestMapping("/test")
        public String test(@RequestParam("name") String name){
            System.out.println(".......RequestMapping");
            return "test";
        }
}
