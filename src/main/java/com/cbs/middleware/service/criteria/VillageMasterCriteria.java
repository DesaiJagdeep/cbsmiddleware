package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.VillageMaster} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.VillageMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /village-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VillageMasterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter villageName;

    private StringFilter villageNameMr;

    private LongFilter villageCode;

    private StringFilter villageCodeMr;

    private LongFilter talukaMasterId;

    private Boolean distinct;

    public VillageMasterCriteria() {}

    public VillageMasterCriteria(VillageMasterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.villageName = other.villageName == null ? null : other.villageName.copy();
        this.villageNameMr = other.villageNameMr == null ? null : other.villageNameMr.copy();
        this.villageCode = other.villageCode == null ? null : other.villageCode.copy();
        this.villageCodeMr = other.villageCodeMr == null ? null : other.villageCodeMr.copy();
        this.talukaMasterId = other.talukaMasterId == null ? null : other.talukaMasterId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public VillageMasterCriteria copy() {
        return new VillageMasterCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getVillageName() {
        return villageName;
    }

    public StringFilter villageName() {
        if (villageName == null) {
            villageName = new StringFilter();
        }
        return villageName;
    }

    public void setVillageName(StringFilter villageName) {
        this.villageName = villageName;
    }

    public StringFilter getVillageNameMr() {
        return villageNameMr;
    }

    public StringFilter villageNameMr() {
        if (villageNameMr == null) {
            villageNameMr = new StringFilter();
        }
        return villageNameMr;
    }

    public void setVillageNameMr(StringFilter villageNameMr) {
        this.villageNameMr = villageNameMr;
    }

    public LongFilter getVillageCode() {
        return villageCode;
    }

    public LongFilter villageCode() {
        if (villageCode == null) {
            villageCode = new LongFilter();
        }
        return villageCode;
    }

    public void setVillageCode(LongFilter villageCode) {
        this.villageCode = villageCode;
    }

    public StringFilter getVillageCodeMr() {
        return villageCodeMr;
    }

    public StringFilter villageCodeMr() {
        if (villageCodeMr == null) {
            villageCodeMr = new StringFilter();
        }
        return villageCodeMr;
    }

    public void setVillageCodeMr(StringFilter villageCodeMr) {
        this.villageCodeMr = villageCodeMr;
    }

    public LongFilter getTalukaMasterId() {
        return talukaMasterId;
    }

    public LongFilter talukaMasterId() {
        if (talukaMasterId == null) {
            talukaMasterId = new LongFilter();
        }
        return talukaMasterId;
    }

    public void setTalukaMasterId(LongFilter talukaMasterId) {
        this.talukaMasterId = talukaMasterId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VillageMasterCriteria that = (VillageMasterCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(villageName, that.villageName) &&
            Objects.equals(villageNameMr, that.villageNameMr) &&
            Objects.equals(villageCode, that.villageCode) &&
            Objects.equals(villageCodeMr, that.villageCodeMr) &&
            Objects.equals(talukaMasterId, that.talukaMasterId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, villageName, villageNameMr, villageCode, villageCodeMr, talukaMasterId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VillageMasterCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (villageName != null ? "villageName=" + villageName + ", " : "") +
            (villageNameMr != null ? "villageNameMr=" + villageNameMr + ", " : "") +
            (villageCode != null ? "villageCode=" + villageCode + ", " : "") +
            (villageCodeMr != null ? "villageCodeMr=" + villageCodeMr + ", " : "") +
            (talukaMasterId != null ? "talukaMasterId=" + talukaMasterId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
