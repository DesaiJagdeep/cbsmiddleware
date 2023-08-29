package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.CourtCase} entity. This class is used
 * in {@link com.cbs.middleware.web.rest.CourtCaseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /court-cases?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CourtCaseCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter code;

    private InstantFilter caseDinank;

    private StringFilter bankName;

    private StringFilter talukaName;

    private LongFilter talukaCode;

    private StringFilter sabasadSavingAccNo;

    private StringFilter sabasadName;

    private StringFilter sabasadAddress;

    private StringFilter karjPrakar;

    private StringFilter vasuliAdhikari;

    private DoubleFilter ekunJama;

    private DoubleFilter baki;

    private StringFilter arOffice;

    private DoubleFilter ekunVyaj;

    private DoubleFilter jamaVyaj;

    private DoubleFilter dandVyaj;

    private DoubleFilter karjRakkam;

    private InstantFilter thakDinnank;

    private InstantFilter karjDinnank;

    private InstantFilter mudatSampteDinank;

    private StringFilter mudat;

    private StringFilter vyaj;

    private DoubleFilter haptaRakkam;

    private StringFilter shakhaVevsthapak;

    private StringFilter suchak;

    private StringFilter anumodak;

    private DoubleFilter dava;

    private FloatFilter vyajDar;

    private DoubleFilter sarcharge;

    private DoubleFilter jyadaVyaj;

    private DoubleFilter yeneVyaj;

    private DoubleFilter vasuliKharch;

    private DoubleFilter etharKharch;

    private DoubleFilter vima;

    private DoubleFilter notice;

    private LongFilter tharavNumber;

    private InstantFilter tharavDinank;

    private IntegerFilter vishayKramank;

    private InstantFilter noticeOne;

    private InstantFilter noticeTwo;

    private StringFilter war;

    private StringFilter vel;

    private StringFilter jamindarOne;

    private StringFilter jamindarOneAddress;

    private StringFilter jamindarTwo;

    private StringFilter jamindarTwoAddress;

    private StringFilter taranType;

    private StringFilter taranDetails;

    private Boolean distinct;

    public CourtCaseCriteria() {}

    public CourtCaseCriteria(CourtCaseCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.caseDinank = other.caseDinank == null ? null : other.caseDinank.copy();
        this.bankName = other.bankName == null ? null : other.bankName.copy();
        this.talukaName = other.talukaName == null ? null : other.talukaName.copy();
        this.talukaCode = other.talukaCode == null ? null : other.talukaCode.copy();
        this.sabasadSavingAccNo = other.sabasadSavingAccNo == null ? null : other.sabasadSavingAccNo.copy();
        this.sabasadName = other.sabasadName == null ? null : other.sabasadName.copy();
        this.sabasadAddress = other.sabasadAddress == null ? null : other.sabasadAddress.copy();
        this.karjPrakar = other.karjPrakar == null ? null : other.karjPrakar.copy();
        this.vasuliAdhikari = other.vasuliAdhikari == null ? null : other.vasuliAdhikari.copy();
        this.ekunJama = other.ekunJama == null ? null : other.ekunJama.copy();
        this.baki = other.baki == null ? null : other.baki.copy();
        this.arOffice = other.arOffice == null ? null : other.arOffice.copy();
        this.ekunVyaj = other.ekunVyaj == null ? null : other.ekunVyaj.copy();
        this.jamaVyaj = other.jamaVyaj == null ? null : other.jamaVyaj.copy();
        this.dandVyaj = other.dandVyaj == null ? null : other.dandVyaj.copy();
        this.karjRakkam = other.karjRakkam == null ? null : other.karjRakkam.copy();
        this.thakDinnank = other.thakDinnank == null ? null : other.thakDinnank.copy();
        this.karjDinnank = other.karjDinnank == null ? null : other.karjDinnank.copy();
        this.mudatSampteDinank = other.mudatSampteDinank == null ? null : other.mudatSampteDinank.copy();
        this.mudat = other.mudat == null ? null : other.mudat.copy();
        this.vyaj = other.vyaj == null ? null : other.vyaj.copy();
        this.haptaRakkam = other.haptaRakkam == null ? null : other.haptaRakkam.copy();
        this.shakhaVevsthapak = other.shakhaVevsthapak == null ? null : other.shakhaVevsthapak.copy();
        this.suchak = other.suchak == null ? null : other.suchak.copy();
        this.anumodak = other.anumodak == null ? null : other.anumodak.copy();
        this.dava = other.dava == null ? null : other.dava.copy();
        this.vyajDar = other.vyajDar == null ? null : other.vyajDar.copy();
        this.sarcharge = other.sarcharge == null ? null : other.sarcharge.copy();
        this.jyadaVyaj = other.jyadaVyaj == null ? null : other.jyadaVyaj.copy();
        this.yeneVyaj = other.yeneVyaj == null ? null : other.yeneVyaj.copy();
        this.vasuliKharch = other.vasuliKharch == null ? null : other.vasuliKharch.copy();
        this.etharKharch = other.etharKharch == null ? null : other.etharKharch.copy();
        this.vima = other.vima == null ? null : other.vima.copy();
        this.notice = other.notice == null ? null : other.notice.copy();
        this.tharavNumber = other.tharavNumber == null ? null : other.tharavNumber.copy();
        this.tharavDinank = other.tharavDinank == null ? null : other.tharavDinank.copy();
        this.vishayKramank = other.vishayKramank == null ? null : other.vishayKramank.copy();
        this.noticeOne = other.noticeOne == null ? null : other.noticeOne.copy();
        this.noticeTwo = other.noticeTwo == null ? null : other.noticeTwo.copy();
        this.war = other.war == null ? null : other.war.copy();
        this.vel = other.vel == null ? null : other.vel.copy();
        this.jamindarOne = other.jamindarOne == null ? null : other.jamindarOne.copy();
        this.jamindarOneAddress = other.jamindarOneAddress == null ? null : other.jamindarOneAddress.copy();
        this.jamindarTwo = other.jamindarTwo == null ? null : other.jamindarTwo.copy();
        this.jamindarTwoAddress = other.jamindarTwoAddress == null ? null : other.jamindarTwoAddress.copy();
        this.taranType = other.taranType == null ? null : other.taranType.copy();
        this.taranDetails = other.taranDetails == null ? null : other.taranDetails.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CourtCaseCriteria copy() {
        return new CourtCaseCriteria(this);
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

    public LongFilter getCode() {
        return code;
    }

    public LongFilter code() {
        if (code == null) {
            code = new LongFilter();
        }
        return code;
    }

    public void setCode(LongFilter code) {
        this.code = code;
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

    public StringFilter getBankName() {
        return bankName;
    }

    public StringFilter bankName() {
        if (bankName == null) {
            bankName = new StringFilter();
        }
        return bankName;
    }

    public void setBankName(StringFilter bankName) {
        this.bankName = bankName;
    }

    public StringFilter getTalukaName() {
        return talukaName;
    }

    public StringFilter talukaName() {
        if (talukaName == null) {
            talukaName = new StringFilter();
        }
        return talukaName;
    }

    public void setTalukaName(StringFilter talukaName) {
        this.talukaName = talukaName;
    }

    public LongFilter getTalukaCode() {
        return talukaCode;
    }

    public LongFilter talukaCode() {
        if (talukaCode == null) {
            talukaCode = new LongFilter();
        }
        return talukaCode;
    }

    public void setTalukaCode(LongFilter talukaCode) {
        this.talukaCode = talukaCode;
    }

    public StringFilter getSabasadSavingAccNo() {
        return sabasadSavingAccNo;
    }

    public StringFilter sabasadSavingAccNo() {
        if (sabasadSavingAccNo == null) {
            sabasadSavingAccNo = new StringFilter();
        }
        return sabasadSavingAccNo;
    }

    public void setSabasadSavingAccNo(StringFilter sabasadSavingAccNo) {
        this.sabasadSavingAccNo = sabasadSavingAccNo;
    }

    public StringFilter getSabasadName() {
        return sabasadName;
    }

    public StringFilter sabasadName() {
        if (sabasadName == null) {
            sabasadName = new StringFilter();
        }
        return sabasadName;
    }

    public void setSabasadName(StringFilter sabasadName) {
        this.sabasadName = sabasadName;
    }

    public StringFilter getSabasadAddress() {
        return sabasadAddress;
    }

    public StringFilter sabasadAddress() {
        if (sabasadAddress == null) {
            sabasadAddress = new StringFilter();
        }
        return sabasadAddress;
    }

    public void setSabasadAddress(StringFilter sabasadAddress) {
        this.sabasadAddress = sabasadAddress;
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

    public DoubleFilter getEkunJama() {
        return ekunJama;
    }

    public DoubleFilter ekunJama() {
        if (ekunJama == null) {
            ekunJama = new DoubleFilter();
        }
        return ekunJama;
    }

    public void setEkunJama(DoubleFilter ekunJama) {
        this.ekunJama = ekunJama;
    }

    public DoubleFilter getBaki() {
        return baki;
    }

    public DoubleFilter baki() {
        if (baki == null) {
            baki = new DoubleFilter();
        }
        return baki;
    }

    public void setBaki(DoubleFilter baki) {
        this.baki = baki;
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

    public DoubleFilter getEkunVyaj() {
        return ekunVyaj;
    }

    public DoubleFilter ekunVyaj() {
        if (ekunVyaj == null) {
            ekunVyaj = new DoubleFilter();
        }
        return ekunVyaj;
    }

    public void setEkunVyaj(DoubleFilter ekunVyaj) {
        this.ekunVyaj = ekunVyaj;
    }

    public DoubleFilter getJamaVyaj() {
        return jamaVyaj;
    }

    public DoubleFilter jamaVyaj() {
        if (jamaVyaj == null) {
            jamaVyaj = new DoubleFilter();
        }
        return jamaVyaj;
    }

    public void setJamaVyaj(DoubleFilter jamaVyaj) {
        this.jamaVyaj = jamaVyaj;
    }

    public DoubleFilter getDandVyaj() {
        return dandVyaj;
    }

    public DoubleFilter dandVyaj() {
        if (dandVyaj == null) {
            dandVyaj = new DoubleFilter();
        }
        return dandVyaj;
    }

    public void setDandVyaj(DoubleFilter dandVyaj) {
        this.dandVyaj = dandVyaj;
    }

    public DoubleFilter getKarjRakkam() {
        return karjRakkam;
    }

    public DoubleFilter karjRakkam() {
        if (karjRakkam == null) {
            karjRakkam = new DoubleFilter();
        }
        return karjRakkam;
    }

    public void setKarjRakkam(DoubleFilter karjRakkam) {
        this.karjRakkam = karjRakkam;
    }

    public InstantFilter getThakDinnank() {
        return thakDinnank;
    }

    public InstantFilter thakDinnank() {
        if (thakDinnank == null) {
            thakDinnank = new InstantFilter();
        }
        return thakDinnank;
    }

    public void setThakDinnank(InstantFilter thakDinnank) {
        this.thakDinnank = thakDinnank;
    }

    public InstantFilter getKarjDinnank() {
        return karjDinnank;
    }

    public InstantFilter karjDinnank() {
        if (karjDinnank == null) {
            karjDinnank = new InstantFilter();
        }
        return karjDinnank;
    }

    public void setKarjDinnank(InstantFilter karjDinnank) {
        this.karjDinnank = karjDinnank;
    }

    public InstantFilter getMudatSampteDinank() {
        return mudatSampteDinank;
    }

    public InstantFilter mudatSampteDinank() {
        if (mudatSampteDinank == null) {
            mudatSampteDinank = new InstantFilter();
        }
        return mudatSampteDinank;
    }

    public void setMudatSampteDinank(InstantFilter mudatSampteDinank) {
        this.mudatSampteDinank = mudatSampteDinank;
    }

    public StringFilter getMudat() {
        return mudat;
    }

    public StringFilter mudat() {
        if (mudat == null) {
            mudat = new StringFilter();
        }
        return mudat;
    }

    public void setMudat(StringFilter mudat) {
        this.mudat = mudat;
    }

    public StringFilter getVyaj() {
        return vyaj;
    }

    public StringFilter vyaj() {
        if (vyaj == null) {
            vyaj = new StringFilter();
        }
        return vyaj;
    }

    public void setVyaj(StringFilter vyaj) {
        this.vyaj = vyaj;
    }

    public DoubleFilter getHaptaRakkam() {
        return haptaRakkam;
    }

    public DoubleFilter haptaRakkam() {
        if (haptaRakkam == null) {
            haptaRakkam = new DoubleFilter();
        }
        return haptaRakkam;
    }

    public void setHaptaRakkam(DoubleFilter haptaRakkam) {
        this.haptaRakkam = haptaRakkam;
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

    public StringFilter getAnumodak() {
        return anumodak;
    }

    public StringFilter anumodak() {
        if (anumodak == null) {
            anumodak = new StringFilter();
        }
        return anumodak;
    }

    public void setAnumodak(StringFilter anumodak) {
        this.anumodak = anumodak;
    }

    public DoubleFilter getDava() {
        return dava;
    }

    public DoubleFilter dava() {
        if (dava == null) {
            dava = new DoubleFilter();
        }
        return dava;
    }

    public void setDava(DoubleFilter dava) {
        this.dava = dava;
    }

    public FloatFilter getVyajDar() {
        return vyajDar;
    }

    public FloatFilter vyajDar() {
        if (vyajDar == null) {
            vyajDar = new FloatFilter();
        }
        return vyajDar;
    }

    public void setVyajDar(FloatFilter vyajDar) {
        this.vyajDar = vyajDar;
    }

    public DoubleFilter getSarcharge() {
        return sarcharge;
    }

    public DoubleFilter sarcharge() {
        if (sarcharge == null) {
            sarcharge = new DoubleFilter();
        }
        return sarcharge;
    }

    public void setSarcharge(DoubleFilter sarcharge) {
        this.sarcharge = sarcharge;
    }

    public DoubleFilter getJyadaVyaj() {
        return jyadaVyaj;
    }

    public DoubleFilter jyadaVyaj() {
        if (jyadaVyaj == null) {
            jyadaVyaj = new DoubleFilter();
        }
        return jyadaVyaj;
    }

    public void setJyadaVyaj(DoubleFilter jyadaVyaj) {
        this.jyadaVyaj = jyadaVyaj;
    }

    public DoubleFilter getYeneVyaj() {
        return yeneVyaj;
    }

    public DoubleFilter yeneVyaj() {
        if (yeneVyaj == null) {
            yeneVyaj = new DoubleFilter();
        }
        return yeneVyaj;
    }

    public void setYeneVyaj(DoubleFilter yeneVyaj) {
        this.yeneVyaj = yeneVyaj;
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

    public DoubleFilter getEtharKharch() {
        return etharKharch;
    }

    public DoubleFilter etharKharch() {
        if (etharKharch == null) {
            etharKharch = new DoubleFilter();
        }
        return etharKharch;
    }

    public void setEtharKharch(DoubleFilter etharKharch) {
        this.etharKharch = etharKharch;
    }

    public DoubleFilter getVima() {
        return vima;
    }

    public DoubleFilter vima() {
        if (vima == null) {
            vima = new DoubleFilter();
        }
        return vima;
    }

    public void setVima(DoubleFilter vima) {
        this.vima = vima;
    }

    public DoubleFilter getNotice() {
        return notice;
    }

    public DoubleFilter notice() {
        if (notice == null) {
            notice = new DoubleFilter();
        }
        return notice;
    }

    public void setNotice(DoubleFilter notice) {
        this.notice = notice;
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

    public IntegerFilter getVishayKramank() {
        return vishayKramank;
    }

    public IntegerFilter vishayKramank() {
        if (vishayKramank == null) {
            vishayKramank = new IntegerFilter();
        }
        return vishayKramank;
    }

    public void setVishayKramank(IntegerFilter vishayKramank) {
        this.vishayKramank = vishayKramank;
    }

    public InstantFilter getNoticeOne() {
        return noticeOne;
    }

    public InstantFilter noticeOne() {
        if (noticeOne == null) {
            noticeOne = new InstantFilter();
        }
        return noticeOne;
    }

    public void setNoticeOne(InstantFilter noticeOne) {
        this.noticeOne = noticeOne;
    }

    public InstantFilter getNoticeTwo() {
        return noticeTwo;
    }

    public InstantFilter noticeTwo() {
        if (noticeTwo == null) {
            noticeTwo = new InstantFilter();
        }
        return noticeTwo;
    }

    public void setNoticeTwo(InstantFilter noticeTwo) {
        this.noticeTwo = noticeTwo;
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

    public StringFilter getJamindarOne() {
        return jamindarOne;
    }

    public StringFilter jamindarOne() {
        if (jamindarOne == null) {
            jamindarOne = new StringFilter();
        }
        return jamindarOne;
    }

    public void setJamindarOne(StringFilter jamindarOne) {
        this.jamindarOne = jamindarOne;
    }

    public StringFilter getJamindarOneAddress() {
        return jamindarOneAddress;
    }

    public StringFilter jamindarOneAddress() {
        if (jamindarOneAddress == null) {
            jamindarOneAddress = new StringFilter();
        }
        return jamindarOneAddress;
    }

    public void setJamindarOneAddress(StringFilter jamindarOneAddress) {
        this.jamindarOneAddress = jamindarOneAddress;
    }

    public StringFilter getJamindarTwo() {
        return jamindarTwo;
    }

    public StringFilter jamindarTwo() {
        if (jamindarTwo == null) {
            jamindarTwo = new StringFilter();
        }
        return jamindarTwo;
    }

    public void setJamindarTwo(StringFilter jamindarTwo) {
        this.jamindarTwo = jamindarTwo;
    }

    public StringFilter getJamindarTwoAddress() {
        return jamindarTwoAddress;
    }

    public StringFilter jamindarTwoAddress() {
        if (jamindarTwoAddress == null) {
            jamindarTwoAddress = new StringFilter();
        }
        return jamindarTwoAddress;
    }

    public void setJamindarTwoAddress(StringFilter jamindarTwoAddress) {
        this.jamindarTwoAddress = jamindarTwoAddress;
    }

    public StringFilter getTaranType() {
        return taranType;
    }

    public StringFilter taranType() {
        if (taranType == null) {
            taranType = new StringFilter();
        }
        return taranType;
    }

    public void setTaranType(StringFilter taranType) {
        this.taranType = taranType;
    }

    public StringFilter getTaranDetails() {
        return taranDetails;
    }

    public StringFilter taranDetails() {
        if (taranDetails == null) {
            taranDetails = new StringFilter();
        }
        return taranDetails;
    }

    public void setTaranDetails(StringFilter taranDetails) {
        this.taranDetails = taranDetails;
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
        final CourtCaseCriteria that = (CourtCaseCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(caseDinank, that.caseDinank) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(talukaName, that.talukaName) &&
            Objects.equals(talukaCode, that.talukaCode) &&
            Objects.equals(sabasadSavingAccNo, that.sabasadSavingAccNo) &&
            Objects.equals(sabasadName, that.sabasadName) &&
            Objects.equals(sabasadAddress, that.sabasadAddress) &&
            Objects.equals(karjPrakar, that.karjPrakar) &&
            Objects.equals(vasuliAdhikari, that.vasuliAdhikari) &&
            Objects.equals(ekunJama, that.ekunJama) &&
            Objects.equals(baki, that.baki) &&
            Objects.equals(arOffice, that.arOffice) &&
            Objects.equals(ekunVyaj, that.ekunVyaj) &&
            Objects.equals(jamaVyaj, that.jamaVyaj) &&
            Objects.equals(dandVyaj, that.dandVyaj) &&
            Objects.equals(karjRakkam, that.karjRakkam) &&
            Objects.equals(thakDinnank, that.thakDinnank) &&
            Objects.equals(karjDinnank, that.karjDinnank) &&
            Objects.equals(mudatSampteDinank, that.mudatSampteDinank) &&
            Objects.equals(mudat, that.mudat) &&
            Objects.equals(vyaj, that.vyaj) &&
            Objects.equals(haptaRakkam, that.haptaRakkam) &&
            Objects.equals(shakhaVevsthapak, that.shakhaVevsthapak) &&
            Objects.equals(suchak, that.suchak) &&
            Objects.equals(anumodak, that.anumodak) &&
            Objects.equals(dava, that.dava) &&
            Objects.equals(vyajDar, that.vyajDar) &&
            Objects.equals(sarcharge, that.sarcharge) &&
            Objects.equals(jyadaVyaj, that.jyadaVyaj) &&
            Objects.equals(yeneVyaj, that.yeneVyaj) &&
            Objects.equals(vasuliKharch, that.vasuliKharch) &&
            Objects.equals(etharKharch, that.etharKharch) &&
            Objects.equals(vima, that.vima) &&
            Objects.equals(notice, that.notice) &&
            Objects.equals(tharavNumber, that.tharavNumber) &&
            Objects.equals(tharavDinank, that.tharavDinank) &&
            Objects.equals(vishayKramank, that.vishayKramank) &&
            Objects.equals(noticeOne, that.noticeOne) &&
            Objects.equals(noticeTwo, that.noticeTwo) &&
            Objects.equals(war, that.war) &&
            Objects.equals(vel, that.vel) &&
            Objects.equals(jamindarOne, that.jamindarOne) &&
            Objects.equals(jamindarOneAddress, that.jamindarOneAddress) &&
            Objects.equals(jamindarTwo, that.jamindarTwo) &&
            Objects.equals(jamindarTwoAddress, that.jamindarTwoAddress) &&
            Objects.equals(taranType, that.taranType) &&
            Objects.equals(taranDetails, that.taranDetails) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            code,
            caseDinank,
            bankName,
            talukaName,
            talukaCode,
            sabasadSavingAccNo,
            sabasadName,
            sabasadAddress,
            karjPrakar,
            vasuliAdhikari,
            ekunJama,
            baki,
            arOffice,
            ekunVyaj,
            jamaVyaj,
            dandVyaj,
            karjRakkam,
            thakDinnank,
            karjDinnank,
            mudatSampteDinank,
            mudat,
            vyaj,
            haptaRakkam,
            shakhaVevsthapak,
            suchak,
            anumodak,
            dava,
            vyajDar,
            sarcharge,
            jyadaVyaj,
            yeneVyaj,
            vasuliKharch,
            etharKharch,
            vima,
            notice,
            tharavNumber,
            tharavDinank,
            vishayKramank,
            noticeOne,
            noticeTwo,
            war,
            vel,
            jamindarOne,
            jamindarOneAddress,
            jamindarTwo,
            jamindarTwoAddress,
            taranType,
            taranDetails,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourtCaseCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (caseDinank != null ? "caseDinank=" + caseDinank + ", " : "") +
            (bankName != null ? "bankName=" + bankName + ", " : "") +
            (talukaName != null ? "talukaName=" + talukaName + ", " : "") +
            (talukaCode != null ? "talukaCode=" + talukaCode + ", " : "") +
            (sabasadSavingAccNo != null ? "sabasadSavingAccNo=" + sabasadSavingAccNo + ", " : "") +
            (sabasadName != null ? "sabasadName=" + sabasadName + ", " : "") +
            (sabasadAddress != null ? "sabasadAddress=" + sabasadAddress + ", " : "") +
            (karjPrakar != null ? "karjPrakar=" + karjPrakar + ", " : "") +
            (vasuliAdhikari != null ? "vasuliAdhikari=" + vasuliAdhikari + ", " : "") +
            (ekunJama != null ? "ekunJama=" + ekunJama + ", " : "") +
            (baki != null ? "baki=" + baki + ", " : "") +
            (arOffice != null ? "arOffice=" + arOffice + ", " : "") +
            (ekunVyaj != null ? "ekunVyaj=" + ekunVyaj + ", " : "") +
            (jamaVyaj != null ? "jamaVyaj=" + jamaVyaj + ", " : "") +
            (dandVyaj != null ? "dandVyaj=" + dandVyaj + ", " : "") +
            (karjRakkam != null ? "karjRakkam=" + karjRakkam + ", " : "") +
            (thakDinnank != null ? "thakDinnank=" + thakDinnank + ", " : "") +
            (karjDinnank != null ? "karjDinnank=" + karjDinnank + ", " : "") +
            (mudatSampteDinank != null ? "mudatSampteDinank=" + mudatSampteDinank + ", " : "") +
            (mudat != null ? "mudat=" + mudat + ", " : "") +
            (vyaj != null ? "vyaj=" + vyaj + ", " : "") +
            (haptaRakkam != null ? "haptaRakkam=" + haptaRakkam + ", " : "") +
            (shakhaVevsthapak != null ? "shakhaVevsthapak=" + shakhaVevsthapak + ", " : "") +
            (suchak != null ? "suchak=" + suchak + ", " : "") +
            (anumodak != null ? "anumodak=" + anumodak + ", " : "") +
            (dava != null ? "dava=" + dava + ", " : "") +
            (vyajDar != null ? "vyajDar=" + vyajDar + ", " : "") +
            (sarcharge != null ? "sarcharge=" + sarcharge + ", " : "") +
            (jyadaVyaj != null ? "jyadaVyaj=" + jyadaVyaj + ", " : "") +
            (yeneVyaj != null ? "yeneVyaj=" + yeneVyaj + ", " : "") +
            (vasuliKharch != null ? "vasuliKharch=" + vasuliKharch + ", " : "") +
            (etharKharch != null ? "etharKharch=" + etharKharch + ", " : "") +
            (vima != null ? "vima=" + vima + ", " : "") +
            (notice != null ? "notice=" + notice + ", " : "") +
            (tharavNumber != null ? "tharavNumber=" + tharavNumber + ", " : "") +
            (tharavDinank != null ? "tharavDinank=" + tharavDinank + ", " : "") +
            (vishayKramank != null ? "vishayKramank=" + vishayKramank + ", " : "") +
            (noticeOne != null ? "noticeOne=" + noticeOne + ", " : "") +
            (noticeTwo != null ? "noticeTwo=" + noticeTwo + ", " : "") +
            (war != null ? "war=" + war + ", " : "") +
            (vel != null ? "vel=" + vel + ", " : "") +
            (jamindarOne != null ? "jamindarOne=" + jamindarOne + ", " : "") +
            (jamindarOneAddress != null ? "jamindarOneAddress=" + jamindarOneAddress + ", " : "") +
            (jamindarTwo != null ? "jamindarTwo=" + jamindarTwo + ", " : "") +
            (jamindarTwoAddress != null ? "jamindarTwoAddress=" + jamindarTwoAddress + ", " : "") +
            (taranType != null ? "taranType=" + taranType + ", " : "") +
            (taranDetails != null ? "taranDetails=" + taranDetails + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
