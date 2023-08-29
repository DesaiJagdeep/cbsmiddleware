package com.cbs.middleware.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A CourtCaseSetting.
 */
@Entity
@Table(name = "court_case_setting")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CourtCaseSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "dinank")
    private Instant dinank;

    @Column(name = "shakha_vevsthapak")
    private String shakhaVevsthapak;

    @Column(name = "suchak")
    private String suchak;

    @Column(name = "aanumodak")
    private String aanumodak;

    @Column(name = "vasuli_adhikari")
    private String vasuliAdhikari;

    @Column(name = "ar_office")
    private String arOffice;

    @Column(name = "tharav_number")
    private Long tharavNumber;

    @Column(name = "tharav_dinank")
    private Instant tharavDinank;

    @Column(name = "karj_fed_notice")
    private Instant karjFedNotice;

    @Column(name = "one_zero_one_notice_one")
    private Instant oneZeroOneNoticeOne;

    @Column(name = "one_zero_one_notice_two")
    private Instant oneZeroOneNoticeTwo;

    @Column(name = "vishay_kramank")
    private String vishayKramank;

    @Column(name = "war")
    private String war;

    @Column(name = "vel")
    private String vel;

    @Column(name = "magani_notice")
    private Instant maganiNotice;

    @Column(name = "etar_kharch")
    private Double etarKharch;

    @Column(name = "notice_kharch")
    private Double noticeKharch;

    @Column(name = "vasuli_kharch")
    private Double vasuliKharch;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CourtCaseSetting id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDinank() {
        return this.dinank;
    }

    public CourtCaseSetting dinank(Instant dinank) {
        this.setDinank(dinank);
        return this;
    }

    public void setDinank(Instant dinank) {
        this.dinank = dinank;
    }

    public String getShakhaVevsthapak() {
        return this.shakhaVevsthapak;
    }

    public CourtCaseSetting shakhaVevsthapak(String shakhaVevsthapak) {
        this.setShakhaVevsthapak(shakhaVevsthapak);
        return this;
    }

    public void setShakhaVevsthapak(String shakhaVevsthapak) {
        this.shakhaVevsthapak = shakhaVevsthapak;
    }

    public String getSuchak() {
        return this.suchak;
    }

    public CourtCaseSetting suchak(String suchak) {
        this.setSuchak(suchak);
        return this;
    }

    public void setSuchak(String suchak) {
        this.suchak = suchak;
    }

    public String getAanumodak() {
        return this.aanumodak;
    }

    public CourtCaseSetting aanumodak(String aanumodak) {
        this.setAanumodak(aanumodak);
        return this;
    }

    public void setAanumodak(String aanumodak) {
        this.aanumodak = aanumodak;
    }

    public String getVasuliAdhikari() {
        return this.vasuliAdhikari;
    }

    public CourtCaseSetting vasuliAdhikari(String vasuliAdhikari) {
        this.setVasuliAdhikari(vasuliAdhikari);
        return this;
    }

    public void setVasuliAdhikari(String vasuliAdhikari) {
        this.vasuliAdhikari = vasuliAdhikari;
    }

    public String getArOffice() {
        return this.arOffice;
    }

    public CourtCaseSetting arOffice(String arOffice) {
        this.setArOffice(arOffice);
        return this;
    }

    public void setArOffice(String arOffice) {
        this.arOffice = arOffice;
    }

    public Long getTharavNumber() {
        return this.tharavNumber;
    }

    public CourtCaseSetting tharavNumber(Long tharavNumber) {
        this.setTharavNumber(tharavNumber);
        return this;
    }

    public void setTharavNumber(Long tharavNumber) {
        this.tharavNumber = tharavNumber;
    }

    public Instant getTharavDinank() {
        return this.tharavDinank;
    }

    public CourtCaseSetting tharavDinank(Instant tharavDinank) {
        this.setTharavDinank(tharavDinank);
        return this;
    }

    public void setTharavDinank(Instant tharavDinank) {
        this.tharavDinank = tharavDinank;
    }

    public Instant getKarjFedNotice() {
        return this.karjFedNotice;
    }

    public CourtCaseSetting karjFedNotice(Instant karjFedNotice) {
        this.setKarjFedNotice(karjFedNotice);
        return this;
    }

    public void setKarjFedNotice(Instant karjFedNotice) {
        this.karjFedNotice = karjFedNotice;
    }

    public Instant getOneZeroOneNoticeOne() {
        return this.oneZeroOneNoticeOne;
    }

    public CourtCaseSetting oneZeroOneNoticeOne(Instant oneZeroOneNoticeOne) {
        this.setOneZeroOneNoticeOne(oneZeroOneNoticeOne);
        return this;
    }

    public void setOneZeroOneNoticeOne(Instant oneZeroOneNoticeOne) {
        this.oneZeroOneNoticeOne = oneZeroOneNoticeOne;
    }

    public Instant getOneZeroOneNoticeTwo() {
        return this.oneZeroOneNoticeTwo;
    }

    public CourtCaseSetting oneZeroOneNoticeTwo(Instant oneZeroOneNoticeTwo) {
        this.setOneZeroOneNoticeTwo(oneZeroOneNoticeTwo);
        return this;
    }

    public void setOneZeroOneNoticeTwo(Instant oneZeroOneNoticeTwo) {
        this.oneZeroOneNoticeTwo = oneZeroOneNoticeTwo;
    }

    public String getVishayKramank() {
        return this.vishayKramank;
    }

    public CourtCaseSetting vishayKramank(String vishayKramank) {
        this.setVishayKramank(vishayKramank);
        return this;
    }

    public void setVishayKramank(String vishayKramank) {
        this.vishayKramank = vishayKramank;
    }

    public String getWar() {
        return this.war;
    }

    public CourtCaseSetting war(String war) {
        this.setWar(war);
        return this;
    }

    public void setWar(String war) {
        this.war = war;
    }

    public String getVel() {
        return this.vel;
    }

    public CourtCaseSetting vel(String vel) {
        this.setVel(vel);
        return this;
    }

    public void setVel(String vel) {
        this.vel = vel;
    }

    public Instant getMaganiNotice() {
        return this.maganiNotice;
    }

    public CourtCaseSetting maganiNotice(Instant maganiNotice) {
        this.setMaganiNotice(maganiNotice);
        return this;
    }

    public void setMaganiNotice(Instant maganiNotice) {
        this.maganiNotice = maganiNotice;
    }

    public Double getEtarKharch() {
        return this.etarKharch;
    }

    public CourtCaseSetting etarKharch(Double etarKharch) {
        this.setEtarKharch(etarKharch);
        return this;
    }

    public void setEtarKharch(Double etarKharch) {
        this.etarKharch = etarKharch;
    }

    public Double getNoticeKharch() {
        return this.noticeKharch;
    }

    public CourtCaseSetting noticeKharch(Double noticeKharch) {
        this.setNoticeKharch(noticeKharch);
        return this;
    }

    public void setNoticeKharch(Double noticeKharch) {
        this.noticeKharch = noticeKharch;
    }

    public Double getVasuliKharch() {
        return this.vasuliKharch;
    }

    public CourtCaseSetting vasuliKharch(Double vasuliKharch) {
        this.setVasuliKharch(vasuliKharch);
        return this;
    }

    public void setVasuliKharch(Double vasuliKharch) {
        this.vasuliKharch = vasuliKharch;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourtCaseSetting)) {
            return false;
        }
        return id != null && id.equals(((CourtCaseSetting) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourtCaseSetting{" +
            "id=" + getId() +
            ", dinank='" + getDinank() + "'" +
            ", shakhaVevsthapak='" + getShakhaVevsthapak() + "'" +
            ", suchak='" + getSuchak() + "'" +
            ", aanumodak='" + getAanumodak() + "'" +
            ", vasuliAdhikari='" + getVasuliAdhikari() + "'" +
            ", arOffice='" + getArOffice() + "'" +
            ", tharavNumber=" + getTharavNumber() +
            ", tharavDinank='" + getTharavDinank() + "'" +
            ", karjFedNotice='" + getKarjFedNotice() + "'" +
            ", oneZeroOneNoticeOne='" + getOneZeroOneNoticeOne() + "'" +
            ", oneZeroOneNoticeTwo='" + getOneZeroOneNoticeTwo() + "'" +
            ", vishayKramank='" + getVishayKramank() + "'" +
            ", war='" + getWar() + "'" +
            ", vel='" + getVel() + "'" +
            ", maganiNotice='" + getMaganiNotice() + "'" +
            ", etarKharch=" + getEtarKharch() +
            ", noticeKharch=" + getNoticeKharch() +
            ", vasuliKharch=" + getVasuliKharch() +
            "}";
    }
}
