package com.cbs.middleware.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A CourtCase.
 */
@Entity
@Table(name = "court_case")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CourtCase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private Long code;

    @Column(name = "case_dinank")
    private Instant caseDinank;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "taluka_name")
    private String talukaName;

    @Column(name = "taluka_code")
    private Long talukaCode;

    @Column(name = "sabasad_saving_acc_no")
    private String sabasadSavingAccNo;

    @Column(name = "sabasad_name")
    private String sabasadName;

    @Column(name = "sabasad_address")
    private String sabasadAddress;

    @Column(name = "karj_prakar")
    private String karjPrakar;

    @Column(name = "vasuli_adhikari")
    private String vasuliAdhikari;

    @Column(name = "ekun_jama")
    private Double ekunJama;

    @Column(name = "baki")
    private Double baki;

    @Column(name = "ar_office")
    private String arOffice;

    @Column(name = "ekun_vyaj")
    private Double ekunVyaj;

    @Column(name = "jama_vyaj")
    private Double jamaVyaj;

    @Column(name = "dand_vyaj")
    private Double dandVyaj;

    @Column(name = "karj_rakkam")
    private Double karjRakkam;

    @Column(name = "thak_dinnank")
    private Instant thakDinnank;

    @Column(name = "karj_dinnank")
    private Instant karjDinnank;

    @Column(name = "mudat_sampte_dinank")
    private Instant mudatSampteDinank;

    @Column(name = "mudat")
    private String mudat;

    @Column(name = "vyaj")
    private String vyaj;

    @Column(name = "hapta_rakkam")
    private Double haptaRakkam;

    @Column(name = "shakha_vevsthapak")
    private String shakhaVevsthapak;

    @Column(name = "suchak")
    private String suchak;

    @Column(name = "anumodak")
    private String anumodak;

    @Column(name = "dava")
    private Double dava;

    @Column(name = "vyaj_dar")
    private Float vyajDar;

    @Column(name = "sarcharge")
    private Double sarcharge;

    @Column(name = "jyada_vyaj")
    private Double jyadaVyaj;

    @Column(name = "yene_vyaj")
    private Double yeneVyaj;

    @Column(name = "vasuli_kharch")
    private Double vasuliKharch;

    @Column(name = "ethar_kharch")
    private Double etharKharch;

    @Column(name = "vima")
    private Double vima;

    @Column(name = "notice")
    private Double notice;

    @Column(name = "tharav_number")
    private Long tharavNumber;

    @Column(name = "tharav_dinank")
    private Instant tharavDinank;

    @Column(name = "vishay_kramank")
    private Integer vishayKramank;

    @Column(name = "notice_one")
    private Instant noticeOne;

    @Column(name = "notice_two")
    private Instant noticeTwo;

    @Column(name = "war")
    private String war;

    @Column(name = "vel")
    private String vel;

    @Column(name = "jamindar_one")
    private String jamindarOne;

    @Column(name = "jamindar_one_address")
    private String jamindarOneAddress;

    @Column(name = "jamindar_two")
    private String jamindarTwo;

    @Column(name = "jamindar_two_address")
    private String jamindarTwoAddress;

    @Column(name = "taran_type")
    private String taranType;

    @Column(name = "taran_details")
    private String taranDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CourtCase id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCode() {
        return this.code;
    }

    public CourtCase code(Long code) {
        this.setCode(code);
        return this;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Instant getCaseDinank() {
        return this.caseDinank;
    }

    public CourtCase caseDinank(Instant caseDinank) {
        this.setCaseDinank(caseDinank);
        return this;
    }

    public void setCaseDinank(Instant caseDinank) {
        this.caseDinank = caseDinank;
    }

    public String getBankName() {
        return this.bankName;
    }

    public CourtCase bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getTalukaName() {
        return this.talukaName;
    }

    public CourtCase talukaName(String talukaName) {
        this.setTalukaName(talukaName);
        return this;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }

    public Long getTalukaCode() {
        return this.talukaCode;
    }

    public CourtCase talukaCode(Long talukaCode) {
        this.setTalukaCode(talukaCode);
        return this;
    }

    public void setTalukaCode(Long talukaCode) {
        this.talukaCode = talukaCode;
    }

    public String getSabasadSavingAccNo() {
        return this.sabasadSavingAccNo;
    }

    public CourtCase sabasadSavingAccNo(String sabasadSavingAccNo) {
        this.setSabasadSavingAccNo(sabasadSavingAccNo);
        return this;
    }

    public void setSabasadSavingAccNo(String sabasadSavingAccNo) {
        this.sabasadSavingAccNo = sabasadSavingAccNo;
    }

    public String getSabasadName() {
        return this.sabasadName;
    }

    public CourtCase sabasadName(String sabasadName) {
        this.setSabasadName(sabasadName);
        return this;
    }

    public void setSabasadName(String sabasadName) {
        this.sabasadName = sabasadName;
    }

    public String getSabasadAddress() {
        return this.sabasadAddress;
    }

    public CourtCase sabasadAddress(String sabasadAddress) {
        this.setSabasadAddress(sabasadAddress);
        return this;
    }

    public void setSabasadAddress(String sabasadAddress) {
        this.sabasadAddress = sabasadAddress;
    }

    public String getKarjPrakar() {
        return this.karjPrakar;
    }

    public CourtCase karjPrakar(String karjPrakar) {
        this.setKarjPrakar(karjPrakar);
        return this;
    }

    public void setKarjPrakar(String karjPrakar) {
        this.karjPrakar = karjPrakar;
    }

    public String getVasuliAdhikari() {
        return this.vasuliAdhikari;
    }

    public CourtCase vasuliAdhikari(String vasuliAdhikari) {
        this.setVasuliAdhikari(vasuliAdhikari);
        return this;
    }

    public void setVasuliAdhikari(String vasuliAdhikari) {
        this.vasuliAdhikari = vasuliAdhikari;
    }

    public Double getEkunJama() {
        return this.ekunJama;
    }

    public CourtCase ekunJama(Double ekunJama) {
        this.setEkunJama(ekunJama);
        return this;
    }

    public void setEkunJama(Double ekunJama) {
        this.ekunJama = ekunJama;
    }

    public Double getBaki() {
        return this.baki;
    }

    public CourtCase baki(Double baki) {
        this.setBaki(baki);
        return this;
    }

    public void setBaki(Double baki) {
        this.baki = baki;
    }

    public String getArOffice() {
        return this.arOffice;
    }

    public CourtCase arOffice(String arOffice) {
        this.setArOffice(arOffice);
        return this;
    }

    public void setArOffice(String arOffice) {
        this.arOffice = arOffice;
    }

    public Double getEkunVyaj() {
        return this.ekunVyaj;
    }

    public CourtCase ekunVyaj(Double ekunVyaj) {
        this.setEkunVyaj(ekunVyaj);
        return this;
    }

    public void setEkunVyaj(Double ekunVyaj) {
        this.ekunVyaj = ekunVyaj;
    }

    public Double getJamaVyaj() {
        return this.jamaVyaj;
    }

    public CourtCase jamaVyaj(Double jamaVyaj) {
        this.setJamaVyaj(jamaVyaj);
        return this;
    }

    public void setJamaVyaj(Double jamaVyaj) {
        this.jamaVyaj = jamaVyaj;
    }

    public Double getDandVyaj() {
        return this.dandVyaj;
    }

    public CourtCase dandVyaj(Double dandVyaj) {
        this.setDandVyaj(dandVyaj);
        return this;
    }

    public void setDandVyaj(Double dandVyaj) {
        this.dandVyaj = dandVyaj;
    }

    public Double getKarjRakkam() {
        return this.karjRakkam;
    }

    public CourtCase karjRakkam(Double karjRakkam) {
        this.setKarjRakkam(karjRakkam);
        return this;
    }

    public void setKarjRakkam(Double karjRakkam) {
        this.karjRakkam = karjRakkam;
    }

    public Instant getThakDinnank() {
        return this.thakDinnank;
    }

    public CourtCase thakDinnank(Instant thakDinnank) {
        this.setThakDinnank(thakDinnank);
        return this;
    }

    public void setThakDinnank(Instant thakDinnank) {
        this.thakDinnank = thakDinnank;
    }

    public Instant getKarjDinnank() {
        return this.karjDinnank;
    }

    public CourtCase karjDinnank(Instant karjDinnank) {
        this.setKarjDinnank(karjDinnank);
        return this;
    }

    public void setKarjDinnank(Instant karjDinnank) {
        this.karjDinnank = karjDinnank;
    }

    public Instant getMudatSampteDinank() {
        return this.mudatSampteDinank;
    }

    public CourtCase mudatSampteDinank(Instant mudatSampteDinank) {
        this.setMudatSampteDinank(mudatSampteDinank);
        return this;
    }

    public void setMudatSampteDinank(Instant mudatSampteDinank) {
        this.mudatSampteDinank = mudatSampteDinank;
    }

    public String getMudat() {
        return this.mudat;
    }

    public CourtCase mudat(String mudat) {
        this.setMudat(mudat);
        return this;
    }

    public void setMudat(String mudat) {
        this.mudat = mudat;
    }

    public String getVyaj() {
        return this.vyaj;
    }

    public CourtCase vyaj(String vyaj) {
        this.setVyaj(vyaj);
        return this;
    }

    public void setVyaj(String vyaj) {
        this.vyaj = vyaj;
    }

    public Double getHaptaRakkam() {
        return this.haptaRakkam;
    }

    public CourtCase haptaRakkam(Double haptaRakkam) {
        this.setHaptaRakkam(haptaRakkam);
        return this;
    }

    public void setHaptaRakkam(Double haptaRakkam) {
        this.haptaRakkam = haptaRakkam;
    }

    public String getShakhaVevsthapak() {
        return this.shakhaVevsthapak;
    }

    public CourtCase shakhaVevsthapak(String shakhaVevsthapak) {
        this.setShakhaVevsthapak(shakhaVevsthapak);
        return this;
    }

    public void setShakhaVevsthapak(String shakhaVevsthapak) {
        this.shakhaVevsthapak = shakhaVevsthapak;
    }

    public String getSuchak() {
        return this.suchak;
    }

    public CourtCase suchak(String suchak) {
        this.setSuchak(suchak);
        return this;
    }

    public void setSuchak(String suchak) {
        this.suchak = suchak;
    }

    public String getAnumodak() {
        return this.anumodak;
    }

    public CourtCase anumodak(String anumodak) {
        this.setAnumodak(anumodak);
        return this;
    }

    public void setAnumodak(String anumodak) {
        this.anumodak = anumodak;
    }

    public Double getDava() {
        return this.dava;
    }

    public CourtCase dava(Double dava) {
        this.setDava(dava);
        return this;
    }

    public void setDava(Double dava) {
        this.dava = dava;
    }

    public Float getVyajDar() {
        return this.vyajDar;
    }

    public CourtCase vyajDar(Float vyajDar) {
        this.setVyajDar(vyajDar);
        return this;
    }

    public void setVyajDar(Float vyajDar) {
        this.vyajDar = vyajDar;
    }

    public Double getSarcharge() {
        return this.sarcharge;
    }

    public CourtCase sarcharge(Double sarcharge) {
        this.setSarcharge(sarcharge);
        return this;
    }

    public void setSarcharge(Double sarcharge) {
        this.sarcharge = sarcharge;
    }

    public Double getJyadaVyaj() {
        return this.jyadaVyaj;
    }

    public CourtCase jyadaVyaj(Double jyadaVyaj) {
        this.setJyadaVyaj(jyadaVyaj);
        return this;
    }

    public void setJyadaVyaj(Double jyadaVyaj) {
        this.jyadaVyaj = jyadaVyaj;
    }

    public Double getYeneVyaj() {
        return this.yeneVyaj;
    }

    public CourtCase yeneVyaj(Double yeneVyaj) {
        this.setYeneVyaj(yeneVyaj);
        return this;
    }

    public void setYeneVyaj(Double yeneVyaj) {
        this.yeneVyaj = yeneVyaj;
    }

    public Double getVasuliKharch() {
        return this.vasuliKharch;
    }

    public CourtCase vasuliKharch(Double vasuliKharch) {
        this.setVasuliKharch(vasuliKharch);
        return this;
    }

    public void setVasuliKharch(Double vasuliKharch) {
        this.vasuliKharch = vasuliKharch;
    }

    public Double getEtharKharch() {
        return this.etharKharch;
    }

    public CourtCase etharKharch(Double etharKharch) {
        this.setEtharKharch(etharKharch);
        return this;
    }

    public void setEtharKharch(Double etharKharch) {
        this.etharKharch = etharKharch;
    }

    public Double getVima() {
        return this.vima;
    }

    public CourtCase vima(Double vima) {
        this.setVima(vima);
        return this;
    }

    public void setVima(Double vima) {
        this.vima = vima;
    }

    public Double getNotice() {
        return this.notice;
    }

    public CourtCase notice(Double notice) {
        this.setNotice(notice);
        return this;
    }

    public void setNotice(Double notice) {
        this.notice = notice;
    }

    public Long getTharavNumber() {
        return this.tharavNumber;
    }

    public CourtCase tharavNumber(Long tharavNumber) {
        this.setTharavNumber(tharavNumber);
        return this;
    }

    public void setTharavNumber(Long tharavNumber) {
        this.tharavNumber = tharavNumber;
    }

    public Instant getTharavDinank() {
        return this.tharavDinank;
    }

    public CourtCase tharavDinank(Instant tharavDinank) {
        this.setTharavDinank(tharavDinank);
        return this;
    }

    public void setTharavDinank(Instant tharavDinank) {
        this.tharavDinank = tharavDinank;
    }

    public Integer getVishayKramank() {
        return this.vishayKramank;
    }

    public CourtCase vishayKramank(Integer vishayKramank) {
        this.setVishayKramank(vishayKramank);
        return this;
    }

    public void setVishayKramank(Integer vishayKramank) {
        this.vishayKramank = vishayKramank;
    }

    public Instant getNoticeOne() {
        return this.noticeOne;
    }

    public CourtCase noticeOne(Instant noticeOne) {
        this.setNoticeOne(noticeOne);
        return this;
    }

    public void setNoticeOne(Instant noticeOne) {
        this.noticeOne = noticeOne;
    }

    public Instant getNoticeTwo() {
        return this.noticeTwo;
    }

    public CourtCase noticeTwo(Instant noticeTwo) {
        this.setNoticeTwo(noticeTwo);
        return this;
    }

    public void setNoticeTwo(Instant noticeTwo) {
        this.noticeTwo = noticeTwo;
    }

    public String getWar() {
        return this.war;
    }

    public CourtCase war(String war) {
        this.setWar(war);
        return this;
    }

    public void setWar(String war) {
        this.war = war;
    }

    public String getVel() {
        return this.vel;
    }

    public CourtCase vel(String vel) {
        this.setVel(vel);
        return this;
    }

    public void setVel(String vel) {
        this.vel = vel;
    }

    public String getJamindarOne() {
        return this.jamindarOne;
    }

    public CourtCase jamindarOne(String jamindarOne) {
        this.setJamindarOne(jamindarOne);
        return this;
    }

    public void setJamindarOne(String jamindarOne) {
        this.jamindarOne = jamindarOne;
    }

    public String getJamindarOneAddress() {
        return this.jamindarOneAddress;
    }

    public CourtCase jamindarOneAddress(String jamindarOneAddress) {
        this.setJamindarOneAddress(jamindarOneAddress);
        return this;
    }

    public void setJamindarOneAddress(String jamindarOneAddress) {
        this.jamindarOneAddress = jamindarOneAddress;
    }

    public String getJamindarTwo() {
        return this.jamindarTwo;
    }

    public CourtCase jamindarTwo(String jamindarTwo) {
        this.setJamindarTwo(jamindarTwo);
        return this;
    }

    public void setJamindarTwo(String jamindarTwo) {
        this.jamindarTwo = jamindarTwo;
    }

    public String getJamindarTwoAddress() {
        return this.jamindarTwoAddress;
    }

    public CourtCase jamindarTwoAddress(String jamindarTwoAddress) {
        this.setJamindarTwoAddress(jamindarTwoAddress);
        return this;
    }

    public void setJamindarTwoAddress(String jamindarTwoAddress) {
        this.jamindarTwoAddress = jamindarTwoAddress;
    }

    public String getTaranType() {
        return this.taranType;
    }

    public CourtCase taranType(String taranType) {
        this.setTaranType(taranType);
        return this;
    }

    public void setTaranType(String taranType) {
        this.taranType = taranType;
    }

    public String getTaranDetails() {
        return this.taranDetails;
    }

    public CourtCase taranDetails(String taranDetails) {
        this.setTaranDetails(taranDetails);
        return this;
    }

    public void setTaranDetails(String taranDetails) {
        this.taranDetails = taranDetails;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourtCase)) {
            return false;
        }
        return id != null && id.equals(((CourtCase) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourtCase{" +
            "id=" + getId() +
            ", code=" + getCode() +
            ", caseDinank='" + getCaseDinank() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", talukaName='" + getTalukaName() + "'" +
            ", talukaCode=" + getTalukaCode() +
            ", sabasadSavingAccNo='" + getSabasadSavingAccNo() + "'" +
            ", sabasadName='" + getSabasadName() + "'" +
            ", sabasadAddress='" + getSabasadAddress() + "'" +
            ", karjPrakar='" + getKarjPrakar() + "'" +
            ", vasuliAdhikari='" + getVasuliAdhikari() + "'" +
            ", ekunJama=" + getEkunJama() +
            ", baki=" + getBaki() +
            ", arOffice='" + getArOffice() + "'" +
            ", ekunVyaj=" + getEkunVyaj() +
            ", jamaVyaj=" + getJamaVyaj() +
            ", dandVyaj=" + getDandVyaj() +
            ", karjRakkam=" + getKarjRakkam() +
            ", thakDinnank='" + getThakDinnank() + "'" +
            ", karjDinnank='" + getKarjDinnank() + "'" +
            ", mudatSampteDinank='" + getMudatSampteDinank() + "'" +
            ", mudat='" + getMudat() + "'" +
            ", vyaj='" + getVyaj() + "'" +
            ", haptaRakkam=" + getHaptaRakkam() +
            ", shakhaVevsthapak='" + getShakhaVevsthapak() + "'" +
            ", suchak='" + getSuchak() + "'" +
            ", anumodak='" + getAnumodak() + "'" +
            ", dava=" + getDava() +
            ", vyajDar=" + getVyajDar() +
            ", sarcharge=" + getSarcharge() +
            ", jyadaVyaj=" + getJyadaVyaj() +
            ", yeneVyaj=" + getYeneVyaj() +
            ", vasuliKharch=" + getVasuliKharch() +
            ", etharKharch=" + getEtharKharch() +
            ", vima=" + getVima() +
            ", notice=" + getNotice() +
            ", tharavNumber=" + getTharavNumber() +
            ", tharavDinank='" + getTharavDinank() + "'" +
            ", vishayKramank=" + getVishayKramank() +
            ", noticeOne='" + getNoticeOne() + "'" +
            ", noticeTwo='" + getNoticeTwo() + "'" +
            ", war='" + getWar() + "'" +
            ", vel='" + getVel() + "'" +
            ", jamindarOne='" + getJamindarOne() + "'" +
            ", jamindarOneAddress='" + getJamindarOneAddress() + "'" +
            ", jamindarTwo='" + getJamindarTwo() + "'" +
            ", jamindarTwoAddress='" + getJamindarTwoAddress() + "'" +
            ", taranType='" + getTaranType() + "'" +
            ", taranDetails='" + getTaranDetails() + "'" +
            "}";
    }
}
