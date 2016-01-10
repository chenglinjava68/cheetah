package cheetah.repository;

import cheetah.domain.Person;
import cheetah.domain.UUIDTrackingId;
import cheetah.domain.hibernate.HibernateRepository;

/**
 * Created by Max on 2015/12/31.
 */
public class PersonRepoImpl extends HibernateRepository<UUIDTrackingId, Person> {
}
