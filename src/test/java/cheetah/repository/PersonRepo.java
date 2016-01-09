package cheetah.repository;

import cheetah.domain.Person;
import cheetah.domain.UUIDTrackingId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Max on 2015/12/31.
 */
public interface PersonRepo extends JpaRepository<Person, UUIDTrackingId> {

}
