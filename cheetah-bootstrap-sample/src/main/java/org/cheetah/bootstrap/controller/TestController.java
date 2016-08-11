package org.cheetah.bootstrap.controller;

import org.cheetah.fighter.api.DomainEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

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
    ApplicationListenerTest applicationListenerTest = new ApplicationListenerTest();
    private AtomicLong atomicLong = new AtomicLong();

    @RequestMapping(method = RequestMethod.GET)
    public void test() {
        DomainEventPublisher.publish(new ApplicationEventTest("13e"));
    }

    @RequestMapping(value = "s", method = RequestMethod.GET)
    public void test2() {
        applicationContext.publishEvent(new SpringEvent("asdf"));
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
    public void testForm(User user) {
        System.out.println(user);
    }
}
