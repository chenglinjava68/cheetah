package cheetah.domain;

import cheetah.repository.PersonQueryRepoImpl;
import cheetah.repository.PersonRepoImpl;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 2015/12/31.
 */
@ContextConfiguration("classpath:META-INF/application.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PersonTest {
    @Autowired
    private PersonRepoImpl personRepoImpl;
    @Autowired
    private PersonQueryRepoImpl personQueryRepo;
    @Test
    public void save() {
        Person p = Person.newBuilder()
                .age(22)
                .name("huangfeng")
                .sex((short) 1)
                .build();
        personRepoImpl.put(p);
    }

    @Test
    public void list() {
        ArrayList<Object> notin = Lists.newArrayList();
        List<Object> in = Lists.newArrayList();
        in.add(11);
        notin.add("huang");
        PageRequest request = new PageRequest(0, 10);
        request.in("age", in);
        request.notIn("name", notin);
        request.gt("age", 11);
        request.lt("age", 1);
        request.le("age", 11);
        request.ge("age", 12);
        Page<Person> list = personQueryRepo.find(request);
    }

}