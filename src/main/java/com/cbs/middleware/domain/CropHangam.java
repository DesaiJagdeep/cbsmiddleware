package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A CropHangam.
 */
@Entity
@Table(name = "crop_hangam")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CropHangam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "hangam")
    private String hangam;

    @Column(name = "hangam_mr")
    private String hangamMr;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CropHangam id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHangam() {
        return this.hangam;
    }

    public CropHangam hangam(String hangam) {
        this.setHangam(hangam);
        return this;
    }

    public void setHangam(String hangam) {
        this.hangam = hangam;
    }

    public String getHangamMr() {
        return this.hangamMr;
    }

    public CropHangam hangamMr(String hangamMr) {
        this.setHangamMr(hangamMr);
        return this;
    }

    public void setHangamMr(String hangamMr) {
        this.hangamMr = hangamMr;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CropHangam)) {
            return false;
        }
        return id != null && id.equals(((CropHangam) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CropHangam{" +
            "id=" + getId() +
            ", hangam='" + getHangam() + "'" +
            ", hangamMr='" + getHangamMr() + "'" +
            "}";
    }
}
