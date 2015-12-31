package jvddd.domain;

import jvddd.repository.PersonRepo;
import jvddd.repository.PersonRepoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Created by Max on 2015/12/31.
 */
@ContextConfiguration("classpath:META-INF/application.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class PersonTest {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private PersonRepoImpl personRepoImpl;
    @Autowired
    private PersonRepo personRepo;
    @Test
    public void save() {
        Person p = em.find(Person.class,
                QueryHelper.createQueryTrackingId("1ae50a1e-3c87-48b4-b4ed-cc4922de68d0"));
        Person p2 = Person.newBuilder()
                .age(33)
                .trackingId(p.trackingId())
                .name(p.name())
                .sex(p.sex())
                .timist(p.timist())
                .version(p.version())
                .build();
        em.merge(p2);
    }

    @Test
    public void find() {
        personRepo.findOne(QueryHelper.createQueryTrackingId("1ae50a1e-3c87-48b4-b4ed-cc4922de68d0"));
        personRepoImpl.find();
    }

}