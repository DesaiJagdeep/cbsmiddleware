package com.cbs.middleware.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A CourtCaseDetails.
 */
@Entity
@Table(name = "court_case_details")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CourtCaseDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "kramank")
    private Long kramank;

    @Column(name = "dinank")
    private Instant dinank;

    @Column(name = "case_dinank")
    private Instant caseDinank;

    @Column(name = "sabhasad")
    private String sabhasad;

    @Column(name = "sabhasad_acc_no")
    private String sabhasadAccNo;

    @Column(name = "karj_prakar_type")
    private String karjPrakarType;

    @Column(name = "karj_prakar")
    private String karjPrakar;

    @Column(name = "certificate_milale")
    private String certificateMilale;

    @Column(name = "certificate_dinnank")
    private Instant certificateDinnank;

    @Column(name = "certificate_rakkam")
    private Double certificateRakkam;

    @Column(name = "yenebaki")
    private Double yenebaki;

    @Column(name = "vyaj")
    private Double vyaj;

    @Column(name = "etar")
    private Double etar;

    @Column(name = "dimmand_milale")
    private String dimmandMilale;

    @Column(name = "dimmand_dinnank")
    private Instant dimmandDinnank;

    @Column(name = "japti_aadhesh")
    private String japtiAadhesh;

    @Column(name = "japti_aadhesh_dinnank")
    private Instant japtiAadheshDinnank;

    @Column(name = "sthavr")
    private Double sthavr;

    @Column(name = "jangam")
    private Double jangam;

    @Column(name = "vikri_aadhesh")
    private String vikriAadhesh;

    @Column(name = "vikri_addhesh_dinnank")
    private Instant vikriAddheshDinnank;

    @Column(name = "etar_tapshil")
    private String etarTapshil;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CourtCaseDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKramank() {
        return this.kramank;
    }

    public CourtCaseDetails kramank(Long kramank) {
        this.setKramank(kramank);
        return this;
    }

    public void setKramank(Long kramank) {
        this.kramank = kramank;
    }

    public Instant getDinank() {
        return this.dinank;
    }

    public CourtCaseDetails dinank(Instant dinank) {
        this.setDinank(dinank);
        return this;
    }

    public void setDinank(Instant dinank) {
        this.dinank = dinank;
    }

    public Instant getCaseDinank() {
        return this.caseDinank;
    }

    public CourtCaseDetails caseDinank(Instant caseDinank) {
        this.setCaseDinank(caseDinank);
        return this;
    }

    public void setCaseDinank(Instant caseDinank) {
        this.caseDinank = caseDinank;
    }

    public String getSabhasad() {
        return this.sabhasad;
    }

    public CourtCaseDetails sabhasad(String sabhasad) {
        this.setSabhasad(sabhasad);
        return this;
    }

    public void setSabhasad(String sabhasad) {
        this.sabhasad = sabhasad;
    }

    public String getSabhasadAccNo() {
        return this.sabhasadAccNo;
    }

    public CourtCaseDetails sabhasadAccNo(String sabhasadAccNo) {
        this.setSabhasadAccNo(sabhasadAccNo);
        return this;
    }

    public void setSabhasadAccNo(String sabhasadAccNo) {
        this.sabhasadAccNo = sabhasadAccNo;
    }

    public String getKarjPrakarType() {
        return this.karjPrakarType;
    }

    public CourtCaseDetails karjPrakarType(String karjPrakarType) {
        this.setKarjPrakarType(karjPrakarType);
        return this;
    }

    public void setKarjPrakarType(String karjPrakarType) {
        this.karjPrakarType = karjPrakarType;
    }

    public String getKarjPrakar() {
        return this.karjPrakar;
    }

    public CourtCaseDetails karjPrakar(String karjPrakar) {
        this.setKarjPrakar(karjPrakar);
        return this;
    }

    public void setKarjPrakar(String karjPrakar) {
        this.karjPrakar = karjPrakar;
    }

    public String getCertificateMilale() {
        return this.certificateMilale;
    }

    public CourtCaseDetails certificateMilale(String certificateMilale) {
        this.setCertificateMilale(certificateMilale);
        return this;
    }

    public void setCertificateMilale(String certificateMilale) {
        this.certificateMilale = certificateMilale;
    }

    public Instant getCertificateDinnank() {
        return this.certificateDinnank;
    }

    public CourtCaseDetails certificateDinnank(Instant certificateDinnank) {
        this.setCertificateDinnank(certificateDinnank);
        return this;
    }

    public void setCertificateDinnank(Instant certificateDinnank) {
        this.certificateDinnank = certificateDinnank;
    }

    public Double getCertificateRakkam() {
        return this.certificateRakkam;
    }

    public CourtCaseDetails certificateRakkam(Double certificateRakkam) {
        this.setCertificateRakkam(certificateRakkam);
        return this;
    }

    public void setCertificateRakkam(Double certificateRakkam) {
        this.certificateRakkam = certificateRakkam;
    }

    public Double getYenebaki() {
        return this.yenebaki;
    }

    public CourtCaseDetails yenebaki(Double yenebaki) {
        this.setYenebaki(yenebaki);
        return this;
    }

    public void setYenebaki(Double yenebaki) {
        this.yenebaki = yenebaki;
    }

    public Double getVyaj() {
        return this.vyaj;
    }

    public CourtCaseDetails vyaj(Double vyaj) {
        this.setVyaj(vyaj);
        return this;
    }

    public void setVyaj(Double vyaj) {
        this.vyaj = vyaj;
    }

    public Double getEtar() {
        return this.etar;
    }

    public CourtCaseDetails etar(Double etar) {
        this.setEtar(etar);
        return this;
    }

    public void setEtar(Double etar) {
        this.etar = etar;
    }

    public String getDimmandMilale() {
        return this.dimmandMilale;
    }

    public CourtCaseDetails dimmandMilale(String dimmandMilale) {
        this.setDimmandMilale(dimmandMilale);
        return this;
    }

    public void setDimmandMilale(String dimmandMilale) {
        this.dimmandMilale = dimmandMilale;
    }

    public Instant getDimmandDinnank() {
        return this.dimmandDinnank;
    }

    public CourtCaseDetails dimmandDinnank(Instant dimmandDinnank) {
        this.setDimmandDinnank(dimmandDinnank);
        return this;
    }

    public void setDimmandDinnank(Instant dimmandDinnank) {
        this.dimmandDinnank = dimmandDinnank;
    }

    public String getJaptiAadhesh() {
        return this.japtiAadhesh;
    }

    public CourtCaseDetails japtiAadhesh(String japtiAadhesh) {
        this.setJaptiAadhesh(japtiAadhesh);
        return this;
    }

    public void setJaptiAadhesh(String japtiAadhesh) {
        this.japtiAadhesh = japtiAadhesh;
    }

    public Instant getJaptiAadheshDinnank() {
        return this.japtiAadheshDinnank;
    }

    public CourtCaseDetails japtiAadheshDinnank(Instant japtiAadheshDinnank) {
        this.setJaptiAadheshDinnank(japtiAadheshDinnank);
        return this;
    }

    public void setJaptiAadheshDinnank(Instant japtiAadheshDinnank) {
        this.japtiAadheshDinnank = japtiAadheshDinnank;
    }

    public Double getSthavr() {
        return this.sthavr;
    }

    public CourtCaseDetails sthavr(Double sthavr) {
        this.setSthavr(sthavr);
        return this;
    }

    public void setSthavr(Double sthavr) {
        this.sthavr = sthavr;
    }

    public Double getJangam() {
        return this.jangam;
    }

    public CourtCaseDetails jangam(Double jangam) {
        this.setJangam(jangam);
        return this;
    }

    public void setJangam(Double jangam) {
        this.jangam = jangam;
    }

    public String getVikriAadhesh() {
        return this.vikriAadhesh;
    }

    public CourtCaseDetails vikriAadhesh(String vikriAadhesh) {
        this.setVikriAadhesh(vikriAadhesh);
        return this;
    }

    public void setVikriAadhesh(String vikriAadhesh) {
        this.vikriAadhesh = vikriAadhesh;
    }

    public Instant getVikriAddheshDinnank() {
        return this.vikriAddheshDinnank;
    }

    public CourtCaseDetails vikriAddheshDinnank(Instant vikriAddheshDinnank) {
        this.setVikriAddheshDinnank(vikriAddheshDinnank);
        return this;
    }

    public void setVikriAddheshDinnank(Instant vikriAddheshDinnank) {
        this.vikriAddheshDinnank = vikriAddheshDinnank;
    }

    public String getEtarTapshil() {
        return this.etarTapshil;
    }

    public CourtCaseDetails etarTapshil(String etarTapshil) {
        this.setEtarTapshil(etarTapshil);
        return this;
    }

    public void setEtarTapshil(String etarTapshil) {
        this.etarTapshil = etarTapshil;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourtCaseDetails)) {
            return false;
        }
        return id != null && id.equals(((CourtCaseDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourtCaseDetails{" +
            "id=" + getId() +
            ", kramank=" + getKramank() +
            ", dinank='" + getDinank() + "'" +
            ", caseDinank='" + getCaseDinank() + "'" +
            ", sabhasad='" + getSabhasad() + "'" +
            ", sabhasadAccNo='" + getSabhasadAccNo() + "'" +
            ", karjPrakarType='" + getKarjPrakarType() + "'" +
            ", karjPrakar='" + getKarjPrakar() + "'" +
            ", certificateMilale='" + getCertificateMilale() + "'" +
            ", certificateDinnank='" + getCertificateDinnank() + "'" +
            ", certificateRakkam=" + getCertificateRakkam() +
            ", yenebaki=" + getYenebaki() +
            ", vyaj=" + getVyaj() +
            ", etar=" + getEtar() +
            ", dimmandMilale='" + getDimmandMilale() + "'" +
            ", dimmandDinnank='" + getDimmandDinnank() + "'" +
            ", japtiAadhesh='" + getJaptiAadhesh() + "'" +
            ", japtiAadheshDinnank='" + getJaptiAadheshDinnank() + "'" +
            ", sthavr=" + getSthavr() +
            ", jangam=" + getJangam() +
            ", vikriAadhesh='" + getVikriAadhesh() + "'" +
            ", vikriAddheshDinnank='" + getVikriAddheshDinnank() + "'" +
            ", etarTapshil='" + getEtarTapshil() + "'" +
            "}";
    }
}
