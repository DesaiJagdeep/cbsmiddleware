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

    private StringFilter vasuliAdhikariName;
    
    private StringFilter branchOrPacsCode;
    
    private StringFilter settingCode;
    
    private StringFilter financialYear;

    private StringFilter arOfficeName;

    private StringFilter chairmanName;

    private StringFilter sachivName;

    private StringFilter suchakName;

    private StringFilter anumodakName;

    private StringFilter vasuliExpense;

    private StringFilter otherExpense;

    private StringFilter noticeExpense;

    private StringFilter meetingNo;

    private LocalDateFilter meetingDate;

    private StringFilter subjectNo;

    private StringFilter meetingDay;

    private StringFilter meetingTime;

    private Boolean distinct;

    public CourtCaseSettingCriteria() {}

    public CourtCaseSettingCriteria(CourtCaseSettingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.settingCode = other.settingCode == null ? null : other.settingCode.copy();
        this.branchOrPacsCode = other.branchOrPacsCode == null ? null : other.branchOrPacsCode.copy();
        this.vasuliAdhikariName = other.vasuliAdhikariName == null ? null : other.vasuliAdhikariName.copy();
        this.financialYear = other.financialYear == null ? null : other.financialYear.copy();
        this.arOfficeName = other.arOfficeName == null ? null : other.arOfficeName.copy();
        this.chairmanName = other.chairmanName == null ? null : other.chairmanName.copy();
        this.sachivName = other.sachivName == null ? null : other.sachivName.copy();
        this.suchakName = other.suchakName == null ? null : other.suchakName.copy();
        this.anumodakName = other.anumodakName == null ? null : other.anumodakName.copy();
        this.vasuliExpense = other.vasuliExpense == null ? null : other.vasuliExpense.copy();
        this.otherExpense = other.otherExpense == null ? null : other.otherExpense.copy();
        this.noticeExpense = other.noticeExpense == null ? null : other.noticeExpense.copy();
        this.meetingNo = other.meetingNo == null ? null : other.meetingNo.copy();
        this.meetingDate = other.meetingDate == null ? null : other.meetingDate.copy();
        this.subjectNo = other.subjectNo == null ? null : other.subjectNo.copy();
        this.meetingDay = other.meetingDay == null ? null : other.meetingDay.copy();
        this.meetingTime = other.meetingTime == null ? null : other.meetingTime.copy();
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
    
    
    
    public StringFilter getSettingCode() {
        return settingCode;
    }

    public StringFilter settingCode() {
        if (settingCode == null) {
        	settingCode = new StringFilter();
        }
        return settingCode;
    }

    public void setSettingCode(StringFilter settingCode) {
        this.settingCode = settingCode;
    }
    
    
    
    public StringFilter getBranchOrPacsCode() {
        return branchOrPacsCode;
    }

    public StringFilter branchOrPacsCode() {
        if (branchOrPacsCode == null) {
        	branchOrPacsCode = new StringFilter();
        }
        return branchOrPacsCode;
    }

    public void setBranchOrPacsCode(StringFilter branchOrPacsCode) {
        this.branchOrPacsCode = branchOrPacsCode;
    }
    
    

    public StringFilter getVasuliAdhikariName() {
        return vasuliAdhikariName;
    }

    public StringFilter vasuliAdhikariName() {
        if (vasuliAdhikariName == null) {
            vasuliAdhikariName = new StringFilter();
        }
        return vasuliAdhikariName;
    }

    public void setVasuliAdhikariName(StringFilter vasuliAdhikariName) {
        this.vasuliAdhikariName = vasuliAdhikariName;
    }
    
    
    
    public StringFilter getFinancialYear() {
        return financialYear;
    }

    public StringFilter financialYear() {
        if (financialYear == null) {
        	financialYear = new StringFilter();
        }
        return financialYear;
    }

    public void setFinancialYear(StringFilter financialYear) {
        this.financialYear = financialYear;
    }
    
    

    public StringFilter getArOfficeName() {
        return arOfficeName;
    }

    public StringFilter arOfficeName() {
        if (arOfficeName == null) {
            arOfficeName = new StringFilter();
        }
        return arOfficeName;
    }

    public void setArOfficeName(StringFilter arOfficeName) {
        this.arOfficeName = arOfficeName;
    }

    public StringFilter getChairmanName() {
        return chairmanName;
    }

    public StringFilter chairmanName() {
        if (chairmanName == null) {
            chairmanName = new StringFilter();
        }
        return chairmanName;
    }

    public void setChairmanName(StringFilter chairmanName) {
        this.chairmanName = chairmanName;
    }

    public StringFilter getSachivName() {
        return sachivName;
    }

    public StringFilter sachivName() {
        if (sachivName == null) {
            sachivName = new StringFilter();
        }
        return sachivName;
    }

    public void setSachivName(StringFilter sachivName) {
        this.sachivName = sachivName;
    }

    public StringFilter getSuchakName() {
        return suchakName;
    }

    public StringFilter suchakName() {
        if (suchakName == null) {
            suchakName = new StringFilter();
        }
        return suchakName;
    }

    public void setSuchakName(StringFilter suchakName) {
        this.suchakName = suchakName;
    }

    public StringFilter getAnumodakName() {
        return anumodakName;
    }

    public StringFilter anumodakName() {
        if (anumodakName == null) {
            anumodakName = new StringFilter();
        }
        return anumodakName;
    }

    public void setAnumodakName(StringFilter anumodakName) {
        this.anumodakName = anumodakName;
    }

    public StringFilter getVasuliExpense() {
        return vasuliExpense;
    }

    public StringFilter vasuliExpense() {
        if (vasuliExpense == null) {
            vasuliExpense = new StringFilter();
        }
        return vasuliExpense;
    }

    public void setVasuliExpense(StringFilter vasuliExpense) {
        this.vasuliExpense = vasuliExpense;
    }

    public StringFilter getOtherExpense() {
        return otherExpense;
    }

    public StringFilter otherExpense() {
        if (otherExpense == null) {
            otherExpense = new StringFilter();
        }
        return otherExpense;
    }

    public void setOtherExpense(StringFilter otherExpense) {
        this.otherExpense = otherExpense;
    }

    public StringFilter getNoticeExpense() {
        return noticeExpense;
    }

    public StringFilter noticeExpense() {
        if (noticeExpense == null) {
            noticeExpense = new StringFilter();
        }
        return noticeExpense;
    }

    public void setNoticeExpense(StringFilter noticeExpense) {
        this.noticeExpense = noticeExpense;
    }

    public StringFilter getMeetingNo() {
        return meetingNo;
    }

    public StringFilter meetingNo() {
        if (meetingNo == null) {
            meetingNo = new StringFilter();
        }
        return meetingNo;
    }

    public void setMeetingNo(StringFilter meetingNo) {
        this.meetingNo = meetingNo;
    }

    public LocalDateFilter getMeetingDate() {
        return meetingDate;
    }

    public LocalDateFilter meetingDate() {
        if (meetingDate == null) {
            meetingDate = new LocalDateFilter();
        }
        return meetingDate;
    }

    public void setMeetingDate(LocalDateFilter meetingDate) {
        this.meetingDate = meetingDate;
    }

    public StringFilter getSubjectNo() {
        return subjectNo;
    }

    public StringFilter subjectNo() {
        if (subjectNo == null) {
            subjectNo = new StringFilter();
        }
        return subjectNo;
    }

    public void setSubjectNo(StringFilter subjectNo) {
        this.subjectNo = subjectNo;
    }

    public StringFilter getMeetingDay() {
        return meetingDay;
    }

    public StringFilter meetingDay() {
        if (meetingDay == null) {
            meetingDay = new StringFilter();
        }
        return meetingDay;
    }

    public void setMeetingDay(StringFilter meetingDay) {
        this.meetingDay = meetingDay;
    }

    public StringFilter getMeetingTime() {
        return meetingTime;
    }

    public StringFilter meetingTime() {
        if (meetingTime == null) {
            meetingTime = new StringFilter();
        }
        return meetingTime;
    }

    public void setMeetingTime(StringFilter meetingTime) {
        this.meetingTime = meetingTime;
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
            Objects.equals(settingCode, that.settingCode) &&
            Objects.equals(vasuliAdhikariName, that.vasuliAdhikariName) &&
            Objects.equals(financialYear, that.financialYear) &&
            Objects.equals(arOfficeName, that.arOfficeName) &&
            Objects.equals(chairmanName, that.chairmanName) &&
            Objects.equals(sachivName, that.sachivName) &&
            Objects.equals(suchakName, that.suchakName) &&
            Objects.equals(anumodakName, that.anumodakName) &&
            Objects.equals(vasuliExpense, that.vasuliExpense) &&
            Objects.equals(otherExpense, that.otherExpense) &&
            Objects.equals(noticeExpense, that.noticeExpense) &&
            Objects.equals(meetingNo, that.meetingNo) &&
            Objects.equals(meetingDate, that.meetingDate) &&
            Objects.equals(subjectNo, that.subjectNo) &&
            Objects.equals(meetingDay, that.meetingDay) &&
            Objects.equals(meetingTime, that.meetingTime) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            settingCode,
            vasuliAdhikariName,
            financialYear,
            arOfficeName,
            chairmanName,
            sachivName,
            suchakName,
            anumodakName,
            vasuliExpense,
            otherExpense,
            noticeExpense,
            meetingNo,
            meetingDate,
            subjectNo,
            meetingDay,
            meetingTime,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourtCaseSettingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (settingCode != null ? "settingCode=" + settingCode + ", " : "") +
            (vasuliAdhikariName != null ? "vasuliAdhikariName=" + vasuliAdhikariName + ", " : "") +
            (financialYear != null ? "financialYear=" + financialYear + ", " : "") +
            (arOfficeName != null ? "arOfficeName=" + arOfficeName + ", " : "") +
            (chairmanName != null ? "chairmanName=" + chairmanName + ", " : "") +
            (sachivName != null ? "sachivName=" + sachivName + ", " : "") +
            (suchakName != null ? "suchakName=" + suchakName + ", " : "") +
            (anumodakName != null ? "anumodakName=" + anumodakName + ", " : "") +
            (vasuliExpense != null ? "vasuliExpense=" + vasuliExpense + ", " : "") +
            (otherExpense != null ? "otherExpense=" + otherExpense + ", " : "") +
            (noticeExpense != null ? "noticeExpense=" + noticeExpense + ", " : "") +
            (meetingNo != null ? "meetingNo=" + meetingNo + ", " : "") +
            (meetingDate != null ? "meetingDate=" + meetingDate + ", " : "") +
            (subjectNo != null ? "subjectNo=" + subjectNo + ", " : "") +
            (meetingDay != null ? "meetingDay=" + meetingDay + ", " : "") +
            (meetingTime != null ? "meetingTime=" + meetingTime + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
