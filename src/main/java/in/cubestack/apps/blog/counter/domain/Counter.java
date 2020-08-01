package in.cubestack.apps.blog.counter.domain;

import in.cubestack.apps.blog.base.domain.BaseModel;

import javax.persistence.*;


@Entity
@Table(name = "counter")
@SequenceGenerator(name = "default_gen", sequenceName = "counter_id_seq", allocationSize = 1)
public class Counter extends BaseModel {

    @Column
    private Long contentId;

    @Column
    @Enumerated(EnumType.STRING)
    private CounterType counterType;

    @Column
    private Long counterValue;

    public Counter() {}

    public Counter(Long contentId, CounterType counterType, Long counterValue) {
        this.contentId = contentId;
        this.counterType = counterType;
        this.counterValue = counterValue;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public CounterType getCounterType() {
        return counterType;
    }

    public void setCounterType(CounterType counterType) {
        this.counterType = counterType;
    }

    public Long getCounterValue() {
        return counterValue;
    }

    public void setCounterValue(Long counterValue) {
        this.counterValue = counterValue;
    }
}
