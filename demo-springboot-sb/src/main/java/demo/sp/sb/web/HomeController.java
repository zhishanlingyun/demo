package demo.sp.sb.web;

import demo.sa.sdk.dto.Insterest;
import demo.sa.sdk.dto.User;
import demo.sp.sb.client.SaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private SaClient saClient;

    @GetMapping("/sb/hello")
    public String hello(){
        return "Sa-Service Hello";
    }


    @GetMapping("/sb/user")
    public User getSaUser(){
        return saClient.getUser(123123L);
    }

    @GetMapping("/sb/user/add")
    public long addSaUser(){
        List<Insterest> insterests = new ArrayList<>();
        insterests.add(new Insterest("足球",1));
        insterests.add(new Insterest("游泳",2));
        User user = new User();
        user.setName("jack");
        user.setInsterests(insterests);
        return saClient.addUser(user);

    }

}
