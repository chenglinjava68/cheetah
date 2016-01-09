package cheetah.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by Max on 2016/1/6.
 */
@Embeddable
public class Job {
    @Column(name="job_name")
    private String name;

    public String name() {
        return name;
    }
}
