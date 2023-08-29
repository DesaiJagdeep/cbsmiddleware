package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.CourtCaseDetails} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.CourtCaseDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /court-case-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CourtCaseDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter kramank;

    private InstantFilter dinank;

    private InstantFilter caseDinank;

    private StringFilter sabhasad;

    private StringFilter sabhasadAccNo;

    private StringFilter karjPrakarType;

    private StringFilter karjPrakar;

    private StringFilter certificateMilale;

    private InstantFilter certificateDinnank;

    private DoubleFilter certificateRakkam;

    private DoubleFilter yenebaki;

    private DoubleFilter vyaj;

    private DoubleFilter etar;

    private StringFilter dimmandMilale;

    private InstantFilter dimmandDinnank;

    private StringFilter japtiAadhesh;

    private InstantFilter japtiAadheshDinnank;

    private DoubleFilter sthavr;

    private DoubleFilter jangam;

    private StringFilter vikriAadhesh;

    private InstantFilter vikriAddheshDinnank;

    private StringFilter etarTapshil;

    private Boolean distinct;

    public CourtCaseDetailsCriteria() {}

    public CourtCaseDetailsCriteria(CourtCaseDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.kramank = other.kramank == null ? null : other.kramank.copy();
        this.dinank = other.dinank == null ? null : other.dinank.copy();
        this.caseDinank = other.caseDinank == null ? null : other.caseDinank.copy();
        this.sabhasad = other.sabhasad == null ? null : other.sabhasad.copy();
        this.sabhasadAccNo = other.sabhasadAccNo == null ? null : other.sabhasadAccNo.copy();
        this.karjPrakarType = other.karjPrakarType == null ? null : other.karjPrakarType.copy();
        this.karjPrakar = other.karjPrakar == null ? null : other.karjPrakar.copy();
        this.certificateMilale = other.certificateMilale == null ? null : other.certificateMilale.copy();
        this.certificateDinnank = other.certificateDinnank == null ? null : other.certificateDinnank.copy();
        this.certificateRakkam = other.certificateRakkam == null ? null : other.certificateRakkam.copy();
        this.yenebaki = other.yenebaki == null ? null : other.yenebaki.copy();
        this.vyaj = other.vyaj == null ? null : other.vyaj.copy();
        this.etar = other.etar == null ? null : other.etar.copy();
        this.dimmandMilale = other.dimmandMilale == null ? null : other.dimmandMilale.copy();
        this.dimmandDinnank = other.dimmandDinnank == null ? null : other.dimmandDinnank.copy();
        this.japtiAadhesh = other.japtiAadhesh == null ? null : other.japtiAadhesh.copy();
        this.japtiAadheshDinnank = other.japtiAadheshDinnank == null ? null : other.japtiAadheshDinnank.copy();
        this.sthavr = other.sthavr == null ? null : other.sthavr.copy();
        this.jangam = other.jangam == null ? null : other.jangam.copy();
        this.vikriAadhesh = other.vikriAadhesh == null ? null : other.vikriAadhesh.copy();
        this.vikriAddheshDinnank = other.vikriAddheshDinnank == null ? null : other.vikriAddheshDinnank.copy();
        this.etarTapshil = other.etarTapshil == null ? null : other.etarTapshil.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CourtCaseDetailsCriteria copy() {
        return new CourtCaseDetailsCriteria(this);
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

    public LongFilter getKramank() {
        return kramank;
    }

    public LongFilter kramank() {
        if (kramank == null) {
            kramank = new LongFilter();
        }
        return kramank;
    }

    public void setKramank(LongFilter kramank) {
        this.kramank = kramank;
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

    public InstantFilter getCaseDinank() {
        return caseDinank;
    }

    public InstantFilter caseDinank() {
        if (caseDinank == null) {
            caseDinank = new InstantFilter();
        }
        return caseDinank;
    }

    public void setCaseDinank(InstantFilter caseDinank) {
        this.caseDinank = caseDinank;
    }

    public StringFilter getSabhasad() {
        return sabhasad;
    }

    public StringFilter sabhasad() {
        if (sabhasad == null) {
            sabhasad = new StringFilter();
        }
        return sabhasad;
    }

    public void setSabhasad(StringFilter sabhasad) {
        this.sabhasad = sabhasad;
    }

    public StringFilter getSabhasadAccNo() {
        return sabhasadAccNo;
    }

    public StringFilter sabhasadAccNo() {
        if (sabhasadAccNo == null) {
            sabhasadAccNo = new StringFilter();
        }
        return sabhasadAccNo;
    }

    public void setSabhasadAccNo(StringFilter sabhasadAccNo) {
        this.sabhasadAccNo = sabhasadAccNo;
    }

    public StringFilter getKarjPrakarType() {
        return karjPrakarType;
    }

    public StringFilter karjPrakarType() {
        if (karjPrakarType == null) {
            karjPrakarType = new StringFilter();
        }
        return karjPrakarType;
    }

    public void setKarjPrakarType(StringFilter karjPrakarType) {
        this.karjPrakarType = karjPrakarType;
    }

    public StringFilter getKarjPrakar() {
        return karjPrakar;
    }

    public StringFilter karjPrakar() {
        if (karjPrakar == null) {
            karjPrakar = new StringFilter();
        }
        return karjPrakar;
    }

    public void setKarjPrakar(StringFilter karjPrakar) {
        this.karjPrakar = karjPrakar;
    }

    public StringFilter getCertificateMilale() {
        return certificateMilale;
    }

    public StringFilter certificateMilale() {
        if (certificateMilale == null) {
            certificateMilale = new StringFilter();
        }
        return certificateMilale;
    }

    public void setCertificateMilale(StringFilter certificateMilale) {
        this.certificateMilale = certificateMilale;
    }

    public InstantFilter getCertificateDinnank() {
        return certificateDinnank;
    }

    public InstantFilter certificateDinnank() {
        if (certificateDinnank == null) {
            certificateDinnank = new InstantFilter();
        }
        return certificateDinnank;
    }

    public void setCertificateDinnank(InstantFilter certificateDinnank) {
        this.certificateDinnank = certificateDinnank;
    }

    public DoubleFilter getCertificateRakkam() {
        return certificateRakkam;
    }

    public DoubleFilter certificateRakkam() {
        if (certificateRakkam == null) {
            certificateRakkam = new DoubleFilter();
        }
        return certificateRakkam;
    }

    public void setCertificateRakkam(DoubleFilter certificateRakkam) {
        this.certificateRakkam = certificateRakkam;
    }

    public DoubleFilter getYenebaki() {
        return yenebaki;
    }

    public DoubleFilter yenebaki() {
        if (yenebaki == null) {
            yenebaki = new DoubleFilter();
        }
        return yenebaki;
    }

    public void setYenebaki(DoubleFilter yenebaki) {
        this.yenebaki = yenebaki;
    }

    public DoubleFilter getVyaj() {
        return vyaj;
    }

    public DoubleFilter vyaj() {
        if (vyaj == null) {
            vyaj = new DoubleFilter();
        }
        return vyaj;
    }

    public void setVyaj(DoubleFilter vyaj) {
        this.vyaj = vyaj;
    }

    public DoubleFilter getEtar() {
        return etar;
    }

    public DoubleFilter etar() {
        if (etar == null) {
            etar = new DoubleFilter();
        }
        return etar;
    }

    public void setEtar(DoubleFilter etar) {
        this.etar = etar;
    }

    public StringFilter getDimmandMilale() {
        return dimmandMilale;
    }

    public StringFilter dimmandMilale() {
        if (dimmandMilale == null) {
            dimmandMilale = new StringFilter();
        }
        return dimmandMilale;
    }

    public void setDimmandMilale(StringFilter dimmandMilale) {
        this.dimmandMilale = dimmandMilale;
    }

    public InstantFilter getDimmandDinnank() {
        return dimmandDinnank;
    }

    public InstantFilter dimmandDinnank() {
        if (dimmandDinnank == null) {
            dimmandDinnank = new InstantFilter();
        }
        return dimmandDinnank;
    }

    public void setDimmandDinnank(InstantFilter dimmandDinnank) {
        this.dimmandDinnank = dimmandDinnank;
    }

    public StringFilter getJaptiAadhesh() {
        return japtiAadhesh;
    }

    public StringFilter japtiAadhesh() {
        if (japtiAadhesh == null) {
            japtiAadhesh = new StringFilter();
        }
        return japtiAadhesh;
    }

    public void setJaptiAadhesh(StringFilter japtiAadhesh) {
        this.japtiAadhesh = japtiAadhesh;
    }

    public InstantFilter getJaptiAadheshDinnank() {
        return japtiAadheshDinnank;
    }

    public InstantFilter japtiAadheshDinnank() {
        if (japtiAadheshDinnank == null) {
            japtiAadheshDinnank = new InstantFilter();
        }
        return japtiAadheshDinnank;
    }

    public void setJaptiAadheshDinnank(InstantFilter japtiAadheshDinnank) {
        this.japtiAadheshDinnank = japtiAadheshDinnank;
    }

    public DoubleFilter getSthavr() {
        return sthavr;
    }

    public DoubleFilter sthavr() {
        if (sthavr == null) {
            sthavr = new DoubleFilter();
        }
        return sthavr;
    }

    public void setSthavr(DoubleFilter sthavr) {
        this.sthavr = sthavr;
    }

    public DoubleFilter getJangam() {
        return jangam;
    }

    public DoubleFilter jangam() {
        if (jangam == null) {
            jangam = new DoubleFilter();
        }
        return jangam;
    }

    public void setJangam(DoubleFilter jangam) {
        this.jangam = jangam;
    }

    public StringFilter getVikriAadhesh() {
        return vikriAadhesh;
    }

    public StringFilter vikriAadhesh() {
        if (vikriAadhesh == null) {
            vikriAadhesh = new StringFilter();
        }
        return vikriAadhesh;
    }

    public void setVikriAadhesh(StringFilter vikriAadhesh) {
        this.vikriAadhesh = vikriAadhesh;
    }

    public InstantFilter getVikriAddheshDinnank() {
        return vikriAddheshDinnank;
    }

    public InstantFilter vikriAddheshDinnank() {
        if (vikriAddheshDinnank == null) {
            vikriAddheshDinnank = new InstantFilter();
        }
        return vikriAddheshDinnank;
    }

    public void setVikriAddheshDinnank(InstantFilter vikriAddheshDinnank) {
        this.vikriAddheshDinnank = vikriAddheshDinnank;
    }

    public StringFilter getEtarTapshil() {
        return etarTapshil;
    }

    public StringFilter etarTapshil() {
        if (etarTapshil == null) {
            etarTapshil = new StringFilter();
        }
        return etarTapshil;
    }

    public void setEtarTapshil(StringFilter etarTapshil) {
        this.etarTapshil = etarTapshil;
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
        final CourtCaseDetailsCriteria that = (CourtCaseDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(kramank, that.kramank) &&
            Objects.equals(dinank, that.dinank) &&
            Objects.equals(caseDinank, that.caseDinank) &&
            Objects.equals(sabhasad, that.sabhasad) &&
            Objects.equals(sabhasadAccNo, that.sabhasadAccNo) &&
            Objects.equals(karjPrakarType, that.karjPrakarType) &&
            Objects.equals(karjPrakar, that.karjPrakar) &&
            Objects.equals(certificateMilale, that.certificateMilale) &&
            Objects.equals(certificateDinnank, that.certificateDinnank) &&
            Objects.equals(certificateRakkam, that.certificateRakkam) &&
            Objects.equals(yenebaki, that.yenebaki) &&
            Objects.equals(vyaj, that.vyaj) &&
            Objects.equals(etar, that.etar) &&
            Objects.equals(dimmandMilale, that.dimmandMilale) &&
            Objects.equals(dimmandDinnank, that.dimmandDinnank) &&
            Objects.equals(japtiAadhesh, that.japtiAadhesh) &&
            Objects.equals(japtiAadheshDinnank, that.japtiAadheshDinnank) &&
            Objects.equals(sthavr, that.sthavr) &&
            Objects.equals(jangam, that.jangam) &&
            Objects.equals(vikriAadhesh, that.vikriAadhesh) &&
            Objects.equals(vikriAddheshDinnank, that.vikriAddheshDinnank) &&
            Objects.equals(etarTapshil, that.etarTapshil) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            kramank,
            dinank,
            caseDinank,
            sabhasad,
            sabhasadAccNo,
            karjPrakarType,
            karjPrakar,
            certificateMilale,
            certificateDinnank,
            certificateRakkam,
            yenebaki,
            vyaj,
            etar,
            dimmandMilale,
            dimmandDinnank,
            japtiAadhesh,
            japtiAadheshDinnank,
            sthavr,
            jangam,
            vikriAadhesh,
            vikriAddheshDinnank,
            etarTapshil,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourtCaseDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (kramank != null ? "kramank=" + kramank + ", " : "") +
            (dinank != null ? "dinank=" + dinank + ", " : "") +
            (caseDinank != null ? "caseDinank=" + caseDinank + ", " : "") +
            (sabhasad != null ? "sabhasad=" + sabhasad + ", " : "") +
            (sabhasadAccNo != null ? "sabhasadAccNo=" + sabhasadAccNo + ", " : "") +
            (karjPrakarType != null ? "karjPrakarType=" + karjPrakarType + ", " : "") +
            (karjPrakar != null ? "karjPrakar=" + karjPrakar + ", " : "") +
            (certificateMilale != null ? "certificateMilale=" + certificateMilale + ", " : "") +
            (certificateDinnank != null ? "certificateDinnank=" + certificateDinnank + ", " : "") +
            (certificateRakkam != null ? "certificateRakkam=" + certificateRakkam + ", " : "") +
            (yenebaki != null ? "yenebaki=" + yenebaki + ", " : "") +
            (vyaj != null ? "vyaj=" + vyaj + ", " : "") +
            (etar != null ? "etar=" + etar + ", " : "") +
            (dimmandMilale != null ? "dimmandMilale=" + dimmandMilale + ", " : "") +
            (dimmandDinnank != null ? "dimmandDinnank=" + dimmandDinnank + ", " : "") +
            (japtiAadhesh != null ? "japtiAadhesh=" + japtiAadhesh + ", " : "") +
            (japtiAadheshDinnank != null ? "japtiAadheshDinnank=" + japtiAadheshDinnank + ", " : "") +
            (sthavr != null ? "sthavr=" + sthavr + ", " : "") +
            (jangam != null ? "jangam=" + jangam + ", " : "") +
            (vikriAadhesh != null ? "vikriAadhesh=" + vikriAadhesh + ", " : "") +
            (vikriAddheshDinnank != null ? "vikriAddheshDinnank=" + vikriAddheshDinnank + ", " : "") +
            (etarTapshil != null ? "etarTapshil=" + etarTapshil + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
