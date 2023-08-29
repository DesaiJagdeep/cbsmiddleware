package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.CourtCaseSetting} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.CourtCaseSettingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /court-case-settings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CourtCaseSettingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter dinank;

    private StringFilter shakhaVevsthapak;

    private StringFilter suchak;

    private StringFilter aanumodak;

    private StringFilter vasuliAdhikari;

    private StringFilter arOffice;

    private LongFilter tharavNumber;

    private InstantFilter tharavDinank;

    private InstantFilter karjFedNotice;

    private InstantFilter oneZeroOneNoticeOne;

    private InstantFilter oneZeroOneNoticeTwo;

    private StringFilter vishayKramank;

    private StringFilter war;

    private StringFilter vel;

    private InstantFilter maganiNotice;

    private DoubleFilter etarKharch;

    private DoubleFilter noticeKharch;

    private DoubleFilter vasuliKharch;

    private Boolean distinct;

    public CourtCaseSettingCriteria() {}

    public CourtCaseSettingCriteria(CourtCaseSettingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dinank = other.dinank == null ? null : other.dinank.copy();
        this.shakhaVevsthapak = other.shakhaVevsthapak == null ? null : other.shakhaVevsthapak.copy();
        this.suchak = other.suchak == null ? null : other.suchak.copy();
        this.aanumodak = other.aanumodak == null ? null : other.aanumodak.copy();
        this.vasuliAdhikari = other.vasuliAdhikari == null ? null : other.vasuliAdhikari.copy();
        this.arOffice = other.arOffice == null ? null : other.arOffice.copy();
        this.tharavNumber = other.tharavNumber == null ? null : other.tharavNumber.copy();
        this.tharavDinank = other.tharavDinank == null ? null : other.tharavDinank.copy();
        this.karjFedNotice = other.karjFedNotice == null ? null : other.karjFedNotice.copy();
        this.oneZeroOneNoticeOne = other.oneZeroOneNoticeOne == null ? null : other.oneZeroOneNoticeOne.copy();
        this.oneZeroOneNoticeTwo = other.oneZeroOneNoticeTwo == null ? null : other.oneZeroOneNoticeTwo.copy();
        this.vishayKramank = other.vishayKramank == null ? null : other.vishayKramank.copy();
        this.war = other.war == null ? null : other.war.copy();
        this.vel = other.vel == null ? null : other.vel.copy();
        this.maganiNotice = other.maganiNotice == null ? null : other.maganiNotice.copy();
        this.etarKharch = other.etarKharch == null ? null : other.etarKharch.copy();
        this.noticeKharch = other.noticeKharch == null ? null : other.noticeKharch.copy();
        this.vasuliKharch = other.vasuliKharch == null ? null : other.vasuliKharch.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CourtCaseSettingCriteria copy() {
        return new CourtCaseSettingCriteria(this);
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

    public InstantFilter getDinank() {
        return dinank;
    }

    public InstantFilter dinank() {
        if (dinank == null) {
            dinank = new InstantFilter();
        }
        return dinank;
    }

    public void setDinank(InstantFilter dinank) {
        this.dinank = dinank;
    }

    public StringFilter getShakhaVevsthapak() {
        return shakhaVevsthapak;
    }

    public StringFilter shakhaVevsthapak() {
        if (shakhaVevsthapak == null) {
            shakhaVevsthapak = new StringFilter();
        }
        return shakhaVevsthapak;
    }

    public void setShakhaVevsthapak(StringFilter shakhaVevsthapak) {
        this.shakhaVevsthapak = shakhaVevsthapak;
    }

    public StringFilter getSuchak() {
        return suchak;
    }

    public StringFilter suchak() {
        if (suchak == null) {
            suchak = new StringFilter();
        }
        return suchak;
    }

    public void setSuchak(StringFilter suchak) {
        this.suchak = suchak;
    }

    public StringFilter getAanumodak() {
        return aanumodak;
    }

    public StringFilter aanumodak() {
        if (aanumodak == null) {
            aanumodak = new StringFilter();
        }
        return aanumodak;
    }

    public void setAanumodak(StringFilter aanumodak) {
        this.aanumodak = aanumodak;
    }

    public StringFilter getVasuliAdhikari() {
        return vasuliAdhikari;
    }

    public StringFilter vasuliAdhikari() {
        if (vasuliAdhikari == null) {
            vasuliAdhikari = new StringFilter();
        }
        return vasuliAdhikari;
    }

    public void setVasuliAdhikari(StringFilter vasuliAdhikari) {
        this.vasuliAdhikari = vasuliAdhikari;
    }

    public StringFilter getArOffice() {
        return arOffice;
    }

    public StringFilter arOffice() {
        if (arOffice == null) {
            arOffice = new StringFilter();
        }
        return arOffice;
    }

    public void setArOffice(StringFilter arOffice) {
        this.arOffice = arOffice;
    }

    public LongFilter getTharavNumber() {
        return tharavNumber;
    }

    public LongFilter tharavNumber() {
        if (tharavNumber == null) {
            tharavNumber = new LongFilter();
        }
        return tharavNumber;
    }

    public void setTharavNumber(LongFilter tharavNumber) {
        this.tharavNumber = tharavNumber;
    }

    public InstantFilter getTharavDinank() {
        return tharavDinank;
    }

    public InstantFilter tharavDinank() {
        if (tharavDinank == null) {
            tharavDinank = new InstantFilter();
        }
        return tharavDinank;
    }

    public void setTharavDinank(InstantFilter tharavDinank) {
        this.tharavDinank = tharavDinank;
    }

    public InstantFilter getKarjFedNotice() {
        return karjFedNotice;
    }

    public InstantFilter karjFedNotice() {
        if (karjFedNotice == null) {
            karjFedNotice = new InstantFilter();
        }
        return karjFedNotice;
    }

    public void setKarjFedNotice(InstantFilter karjFedNotice) {
        this.karjFedNotice = karjFedNotice;
    }

    public InstantFilter getOneZeroOneNoticeOne() {
        return oneZeroOneNoticeOne;
    }

    public InstantFilter oneZeroOneNoticeOne() {
        if (oneZeroOneNoticeOne == null) {
            oneZeroOneNoticeOne = new InstantFilter();
        }
        return oneZeroOneNoticeOne;
    }

    public void setOneZeroOneNoticeOne(InstantFilter oneZeroOneNoticeOne) {
        this.oneZeroOneNoticeOne = oneZeroOneNoticeOne;
    }

    public InstantFilter getOneZeroOneNoticeTwo() {
        return oneZeroOneNoticeTwo;
    }

    public InstantFilter oneZeroOneNoticeTwo() {
        if (oneZeroOneNoticeTwo == null) {
            oneZeroOneNoticeTwo = new InstantFilter();
        }
        return oneZeroOneNoticeTwo;
    }

    public void setOneZeroOneNoticeTwo(InstantFilter oneZeroOneNoticeTwo) {
        this.oneZeroOneNoticeTwo = oneZeroOneNoticeTwo;
    }

    public StringFilter getVishayKramank() {
        return vishayKramank;
    }

    public StringFilter vishayKramank() {
        if (vishayKramank == null) {
            vishayKramank = new StringFilter();
        }
        return vishayKramank;
    }

    public void setVishayKramank(StringFilter vishayKramank) {
        this.vishayKramank = vishayKramank;
    }

    public StringFilter getWar() {
        return war;
    }

    public StringFilter war() {
        if (war == null) {
            war = new StringFilter();
        }
        return war;
    }

    public void setWar(StringFilter war) {
        this.war = war;
    }

    public StringFilter getVel() {
        return vel;
    }

    public StringFilter vel() {
        if (vel == null) {
            vel = new StringFilter();
        }
        return vel;
    }

    public void setVel(StringFilter vel) {
        this.vel = vel;
    }

    public InstantFilter getMaganiNotice() {
        return maganiNotice;
    }

    public InstantFilter maganiNotice() {
        if (maganiNotice == null) {
            maganiNotice = new InstantFilter();
        }
        return maganiNotice;
    }

    public void setMaganiNotice(InstantFilter maganiNotice) {
        this.maganiNotice = maganiNotice;
    }

    public DoubleFilter getEtarKharch() {
        return etarKharch;
    }

    public DoubleFilter etarKharch() {
        if (etarKharch == null) {
            etarKharch = new DoubleFilter();
        }
        return etarKharch;
    }

    public void setEtarKharch(DoubleFilter etarKharch) {
        this.etarKharch = etarKharch;
    }

    public DoubleFilter getNoticeKharch() {
        return noticeKharch;
    }

    public DoubleFilter noticeKharch() {
        if (noticeKharch == null) {
            noticeKharch = new DoubleFilter();
        }
        return noticeKharch;
    }

    public void setNoticeKharch(DoubleFilter noticeKharch) {
        this.noticeKharch = noticeKharch;
    }

    public DoubleFilter getVasuliKharch() {
        return vasuliKharch;
    }

    public DoubleFilter vasuliKharch() {
        if (vasuliKharch == null) {
            vasuliKharch = new DoubleFilter();
        }
        return vasuliKharch;
    }

    public void setVasuliKharch(DoubleFilter vasuliKharch) {
        this.vasuliKharch = vasuliKharch;
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
        final CourtCaseSettingCriteria that = (CourtCaseSettingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dinank, that.dinank) &&
            Objects.equals(shakhaVevsthapak, that.shakhaVevsthapak) &&
            Objects.equals(suchak, that.suchak) &&
            Objects.equals(aanumodak, that.aanumodak) &&
            Objects.equals(vasuliAdhikari, that.vasuliAdhikari) &&
            Objects.equals(arOffice, that.arOffice) &&
            Objects.equals(tharavNumber, that.tharavNumber) &&
            Objects.equals(tharavDinank, that.tharavDinank) &&
            Objects.equals(karjFedNotice, that.karjFedNotice) &&
            Objects.equals(oneZeroOneNoticeOne, that.oneZeroOneNoticeOne) &&
            Objects.equals(oneZeroOneNoticeTwo, that.oneZeroOneNoticeTwo) &&
            Objects.equals(vishayKramank, that.vishayKramank) &&
            Objects.equals(war, that.war) &&
            Objects.equals(vel, that.vel) &&
            Objects.equals(maganiNotice, that.maganiNotice) &&
            Objects.equals(etarKharch, that.etarKharch) &&
            Objects.equals(noticeKharch, that.noticeKharch) &&
            Objects.equals(vasuliKharch, that.vasuliKharch) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            dinank,
            shakhaVevsthapak,
            suchak,
            aanumodak,
            vasuliAdhikari,
            arOffice,
            tharavNumber,
            tharavDinank,
            karjFedNotice,
            oneZeroOneNoticeOne,
            oneZeroOneNoticeTwo,
            vishayKramank,
            war,
            vel,
            maganiNotice,
            etarKharch,
            noticeKharch,
            vasuliKharch,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourtCaseSettingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dinank != null ? "dinank=" + dinank + ", " : "") +
            (shakhaVevsthapak != null ? "shakhaVevsthapak=" + shakhaVevsthapak + ", " : "") +
            (suchak != null ? "suchak=" + suchak + ", " : "") +
            (aanumodak != null ? "aanumodak=" + aanumodak + ", " : "") +
            (vasuliAdhikari != null ? "vasuliAdhikari=" + vasuliAdhikari + ", " : "") +
            (arOffice != null ? "arOffice=" + arOffice + ", " : "") +
            (tharavNumber != null ? "tharavNumber=" + tharavNumber + ", " : "") +
            (tharavDinank != null ? "tharavDinank=" + tharavDinank + ", " : "") +
            (karjFedNotice != null ? "karjFedNotice=" + karjFedNotice + ", " : "") +
            (oneZeroOneNoticeOne != null ? "oneZeroOneNoticeOne=" + oneZeroOneNoticeOne + ", " : "") +
            (oneZeroOneNoticeTwo != null ? "oneZeroOneNoticeTwo=" + oneZeroOneNoticeTwo + ", " : "") +
            (vishayKramank != null ? "vishayKramank=" + vishayKramank + ", " : "") +
            (war != null ? "war=" + war + ", " : "") +
            (vel != null ? "vel=" + vel + ", " : "") +
            (maganiNotice != null ? "maganiNotice=" + maganiNotice + ", " : "") +
            (etarKharch != null ? "etarKharch=" + etarKharch + ", " : "") +
            (noticeKharch != null ? "noticeKharch=" + noticeKharch + ", " : "") +
            (vasuliKharch != null ? "vasuliKharch=" + vasuliKharch + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
