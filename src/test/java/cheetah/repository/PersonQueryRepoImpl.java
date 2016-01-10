package cheetah.repository;

import cheetah.domain.Person;
import cheetah.domain.UUIDTrackingId;
import cheetah.domain.jpa.PagingJpaRepository;

/**
 * Created by Max on 2016/1/9.
 */
public class PersonQueryRepoImpl extends PagingJpaRepository<UUIDTrackingId, Person> {
}
