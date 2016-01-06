package jvddd.repository;

import jvddd.domain.Person;
import jvddd.domain.UUIDTrackingId;
import jvddd.domain.hibernate.PagingHibernateRepository;

/**
 * Created by Max on 2015/12/31.
 */
public class PersonRepoImpl extends PagingHibernateRepository<UUIDTrackingId, Person> {
}
