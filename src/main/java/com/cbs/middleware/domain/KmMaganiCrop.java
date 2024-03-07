package com.cbs.middleware.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A KmMaganiCrop.
 */
@Entity
@Table(name = "km_magani_crop")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class KmMaganiCrop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "crop_name")
    private String cropName;

    @ManyToOne
    private KmMagani kmMagani;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public KmMaganiCrop id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCropName() {
        return this.cropName;
    }

    public KmMaganiCrop cropName(String cropName) {
        this.setCropName(cropName);
        return this;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public KmMagani getKmMagani() {
        return this.kmMagani;
    }

    public void setKmMagani(KmMagani kmMagani) {
        this.kmMagani = kmMagani;
    }

    public KmMaganiCrop kmMagani(KmMagani kmMagani) {
        this.setKmMagani(kmMagani);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KmMaganiCrop)) {
            return false;
        }
        return id != null && id.equals(((KmMaganiCrop) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KmMaganiCrop{" +
            "id=" + getId() +
            ", cropName='" + getCropName() + "'" +
            "}";
    }
}
