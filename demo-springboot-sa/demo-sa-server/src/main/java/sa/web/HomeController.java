package sa.web;

import demo.sa.sdk.dto.Insterest;
import demo.sa.sdk.dto.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class HomeController {

    private Random random = new Random();

    @GetMapping("/sa/hello")
    public String hello(){
        return "Sa-Service Hello";
    }

    @GetMapping("/sa/user")
    public User getUser(@RequestParam("uid") long id){
        System.out.println("===========request uid=============");
        List<Insterest> insterests = new ArrayList<>();
        insterests.add(new Insterest("足球",1));
        insterests.add(new Insterest("游泳",2));
        User user = new User();
        user.setUid(id);
        user.setName("张三");
        user.setInsterests(insterests);
        return user;
    }

    @PostMapping("/sa/user/add")
    public long addUser(@RequestBody User user){
        System.out.println("user is "+user);
        return random.nextLong();
    }

}
