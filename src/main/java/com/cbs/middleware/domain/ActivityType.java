package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A ActivityType.
 */
@Entity
@Table(name = "activity_type")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ActivityType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "activity_type")
    private String activityType;

    @Column(name = "activity_type_code")
    private Integer activityTypeCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ActivityType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityType() {
        return this.activityType;
    }

    public ActivityType activityType(String activityType) {
        this.setActivityType(activityType);
        return this;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public Integer getActivityTypeCode() {
        return this.activityTypeCode;
    }

    public ActivityType activityTypeCode(Integer activityTypeCode) {
        this.setActivityTypeCode(activityTypeCode);
        return this;
    }

    public void setActivityTypeCode(Integer activityTypeCode) {
        this.activityTypeCode = activityTypeCode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActivityType)) {
            return false;
        }
        return id != null && id.equals(((ActivityType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActivityType{" +
            "id=" + getId() +
            ", activityType='" + getActivityType() + "'" +
            ", activityTypeCode=" + getActivityTypeCode() +
            "}";
    }
}
