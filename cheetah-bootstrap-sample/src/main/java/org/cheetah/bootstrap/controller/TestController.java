package org.cheetah.bootstrap.controller;

import org.cheetah.fighter.DomainEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Max on 2016/3/4.
 */
@RequestMapping("/test")
@RestController
public class TestController {
    public static void main(String[] args) {
    }
    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping(method = RequestMethod.GET)
    public void test() {
        DomainEventPublisher.publish(new ApplicationEventTest("13e"));
    }

    @RequestMapping(value = "/on", method = RequestMethod.GET)
    public void test3() {
    }

    @RequestMapping(method = RequestMethod.POST)
    public User test2(@RequestBody User user) throws InterruptedException {
        System.out.println(user);
        if (user.getUsername().equals("username"))
            throw new RuntimeException();
        DomainEventPublisher.publish(new ApplicationEventTest("13e"));
        return user;
    }

    @RequestMapping(value = "timeout", method = RequestMethod.POST)
    public User timeout(@RequestBody User user) throws InterruptedException {
        System.out.println(user);
        DomainEventPublisher.publish(new ApplicationEventTest("13e"));
        return user;
    }

    @RequestMapping(value = "form", method = RequestMethod.POST)
    public User testForm(User user) {
        System.out.println(user);
        return new User();
    }
}
