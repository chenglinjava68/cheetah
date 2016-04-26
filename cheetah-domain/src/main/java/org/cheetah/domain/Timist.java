package org.cheetah.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * 模型-关心时间值对象
 * Created by Max on 2015/12/25.
 */
//@Embeddable
public class Timist implements ValueObject<Timist> {
//    @Column(name = "create_time")
    private Long createTime = System.currentTimeMillis();
//    @Column(name = "modify_time")
    private Long modifyTime = System.currentTimeMillis();

    Timist() {
    }

    public Timist(Long createTime, Long modifyTime) {
        this.createTime = createTime;
        this.modifyTime = modifyTime;
    }

    public Long createTime() {
        return createTime;
    }

    public Long modifyTime() {
        return modifyTime;
    }

    public Timist modify() {
        return new Timist(this.createTime, System.currentTimeMillis());
    }

    @Override
    public boolean sameAs(Timist other) {
        return new EqualsBuilder().append(this.createTime, other.createTime)
                .append(this.modifyTime, other.modifyTime)
                .isEquals();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Timist other = (Timist) o;

        return sameAs(other);
    }

    @Override
    public String toString() {
        return "[createTime: " + createTime + ", modifyTime: " + modifyTime + "]";
    }
}
