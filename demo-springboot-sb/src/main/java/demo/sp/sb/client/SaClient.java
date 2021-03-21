package demo.sp.sb.client;

import demo.sa.sdk.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("sa")
public interface SaClient {

    @RequestMapping(method = RequestMethod.GET,value = "/sa/user")
    User getUser(@RequestParam("uid")long uid);

    @RequestMapping(method = RequestMethod.POST,value = "/sa/user/add")
    long addUser(@RequestBody User user);

}
