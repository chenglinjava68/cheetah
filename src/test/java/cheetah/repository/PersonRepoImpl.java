package cheetah.repository;

import cheetah.domain.Person;
import cheetah.domain.UUIDTrackingId;
import cheetah.domain.hibernate.PagingHibernateRepository;

/**
 * Created by Max on 2015/12/31.
 */
public class PersonRepoImpl extends PagingHibernateRepository<UUIDTrackingId, Person> {
}
