package jvddd.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Max on 2015/12/31.
 */
@Entity
@Table(name="persons")
@Access(AccessType.FIELD)
public class Person extends UUIDKeyEntity {
    private String name;
    private short sex;
    private int age;
    Person() {

    }

    public Person(Builder builder) {
        super(builder);
        this.name= builder.name;
        this.sex = builder.sex;
        this.age = builder.age;
    }

    public String name() {
        return name;
    }

    public short sex() {
        return sex;
    }

    public int age() {
        return age;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder extends EntityBuilder<UUIDTrackingId, Person> {
        private String name;
        private short sex;
        private int age;

        @Override
        public Person build() {
            return new Person(this);
        }

        @Override
        public Builder trackingId(UUIDTrackingId trackingId) {
            return (Builder) super.trackingId(trackingId);
        }

        @Override
        public Builder timist(Timist timist) {
            return (Builder) super.timist(timist);
        }

        @Override
        public Builder version(Long version) {
            return (Builder) super.version(version);
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }


        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder sex(short sex) {
            this.sex = sex;
            return this;
        }
    }
}
