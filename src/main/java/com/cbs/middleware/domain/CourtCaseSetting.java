package com.cbs.middleware.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A CourtCaseSetting.
 */
@Entity
@Table(name = "court_case_setting")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CourtCaseSetting extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "unique_file_name")
    private String uniqueFileName;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "taluka_name")
    private String talukaName;

    @Column(name = "vasuli_adhikari_name")
    private String vasuliAdhikariName;

    @Column(name = "ar_office_name")
    private String arOfficeName;

    @Column(name = "chairman_name")
    private String chairmanName;

    @Column(name = "sachiv_name")
    private String sachivName;

    @Column(name = "suchak_name")
    private String suchakName;

    @Column(name = "anumodak_name")
    private String anumodakName;

    @Column(name = "vasuli_expense")
    private String vasuliExpense;

    @Column(name = "other_expense")
    private String otherExpense;

    @Column(name = "notice_expense")
    private String noticeExpense;

    @Column(name = "meeting_no")
    private String meetingNo;

    @Column(name = "meeting_date")
    private LocalDate meetingDate;

    @Column(name = "subject_no")
    private String subjectNo;

    @Column(name = "meeting_day")
    private String meetingDay;

    @Column(name = "meeting_time")
    private String meetingTime;

    //English Data

    @Column(name = "bank_name_en")
    private String bankNameEn;

    @Column(name = "taluka_name_en")
    private String talukaNameEn;

    @Column(name = "vasuli_adhikari_name_en")
    private String vasuliAdhikariNameEn;

    @Column(name = "ar_office_name_en")
    private String arOfficeNameEn;

    @Column(name = "chairman_name_en")
    private String chairmanNameEn;

    @Column(name = "sachiv_name_en")
    private String sachivNameEn;

    @Column(name = "suchak_name_en")
    private String suchakNameEn;

    @Column(name = "anumodak_name_en")
    private String anumodakNameEn;

    @Column(name = "vasuli_expense_en")
    private Double vasuliExpenseEn;

    @Column(name = "other_expense_en")
    private Double otherExpenseEn;

    @Column(name = "notice_expense_en")
    private Double noticeExpenseEn;

    @Column(name = "meeting_no_en")
    private Long meetingNoEn;

    @Column(name = "subject_no_en")
    private Long subjectNoEn;

    @Column(name = "meeting_day_en")
    private String meetingDayEn;

    @Column(name = "meeting_time_en")
    private String meetingTimeEn;

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

    public String getVasuliAdhikariName() {
        return this.vasuliAdhikariName;
    }

    public CourtCaseSetting vasuliAdhikariName(String vasuliAdhikariName) {
        this.setVasuliAdhikariName(vasuliAdhikariName);
        return this;
    }

    public void setVasuliAdhikariName(String vasuliAdhikariName) {
        this.vasuliAdhikariName = vasuliAdhikariName;
    }

    public String getArOfficeName() {
        return this.arOfficeName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUniqueFileName() {
        return uniqueFileName;
    }

    public void setUniqueFileName(String uniqueFileName) {
        this.uniqueFileName = uniqueFileName;
    }

    public CourtCaseSetting arOfficeName(String arOfficeName) {
        this.setArOfficeName(arOfficeName);
        return this;
    }

    public String getVasuliAdhikariNameEn() {
        return vasuliAdhikariNameEn;
    }

    public void setVasuliAdhikariNameEn(String vasuliAdhikariNameEn) {
        this.vasuliAdhikariNameEn = vasuliAdhikariNameEn;
    }

    public String getArOfficeNameEn() {
        return arOfficeNameEn;
    }

    public void setArOfficeNameEn(String arOfficeNameEn) {
        this.arOfficeNameEn = arOfficeNameEn;
    }

    public String getChairmanNameEn() {
        return chairmanNameEn;
    }

    public void setChairmanNameEn(String chairmanNameEn) {
        this.chairmanNameEn = chairmanNameEn;
    }

    public String getSachivNameEn() {
        return sachivNameEn;
    }

    public void setSachivNameEn(String sachivNameEn) {
        this.sachivNameEn = sachivNameEn;
    }

    public String getSuchakNameEn() {
        return suchakNameEn;
    }

    public void setSuchakNameEn(String suchakNameEn) {
        this.suchakNameEn = suchakNameEn;
    }

    public String getAnumodakNameEn() {
        return anumodakNameEn;
    }

    public void setAnumodakNameEn(String anumodakNameEn) {
        this.anumodakNameEn = anumodakNameEn;
    }

    public Double getVasuliExpenseEn() {
        return vasuliExpenseEn;
    }

    public void setVasuliExpenseEn(Double vasuliExpenseEn) {
        this.vasuliExpenseEn = vasuliExpenseEn;
    }

    public Double getOtherExpenseEn() {
        return otherExpenseEn;
    }

    public void setOtherExpenseEn(Double otherExpenseEn) {
        this.otherExpenseEn = otherExpenseEn;
    }

    public Double getNoticeExpenseEn() {
        return noticeExpenseEn;
    }

    public void setNoticeExpenseEn(Double noticeExpenseEn) {
        this.noticeExpenseEn = noticeExpenseEn;
    }

    public Long getMeetingNoEn() {
        return meetingNoEn;
    }

    public void setMeetingNoEn(Long meetingNoEn) {
        this.meetingNoEn = meetingNoEn;
    }

    public Long getSubjectNoEn() {
        return subjectNoEn;
    }

    public void setSubjectNoEn(Long subjectNoEn) {
        this.subjectNoEn = subjectNoEn;
    }

    public String getMeetingDayEn() {
        return meetingDayEn;
    }

    public void setMeetingDayEn(String meetingDayEn) {
        this.meetingDayEn = meetingDayEn;
    }

    public String getMeetingTimeEn() {
        return meetingTimeEn;
    }

    public void setMeetingTimeEn(String meetingTimeEn) {
        this.meetingTimeEn = meetingTimeEn;
    }

    public void setArOfficeName(String arOfficeName) {
        this.arOfficeName = arOfficeName;
    }

    public String getChairmanName() {
        return this.chairmanName;
    }

    public CourtCaseSetting chairmanName(String chairmanName) {
        this.setChairmanName(chairmanName);
        return this;
    }

    public void setChairmanName(String chairmanName) {
        this.chairmanName = chairmanName;
    }

    public String getSachivName() {
        return this.sachivName;
    }

    public CourtCaseSetting sachivName(String sachivName) {
        this.setSachivName(sachivName);
        return this;
    }

    public void setSachivName(String sachivName) {
        this.sachivName = sachivName;
    }

    public String getSuchakName() {
        return this.suchakName;
    }

    public CourtCaseSetting suchakName(String suchakName) {
        this.setSuchakName(suchakName);
        return this;
    }

    public void setSuchakName(String suchakName) {
        this.suchakName = suchakName;
    }

    public String getAnumodakName() {
        return this.anumodakName;
    }

    public CourtCaseSetting anumodakName(String anumodakName) {
        this.setAnumodakName(anumodakName);
        return this;
    }

    public void setAnumodakName(String anumodakName) {
        this.anumodakName = anumodakName;
    }

    public String getVasuliExpense() {
        return this.vasuliExpense;
    }

    public CourtCaseSetting vasuliExpense(String vasuliExpense) {
        this.setVasuliExpense(vasuliExpense);
        return this;
    }

    public void setVasuliExpense(String vasuliExpense) {
        this.vasuliExpense = vasuliExpense;
    }

    public String getOtherExpense() {
        return this.otherExpense;
    }

    public CourtCaseSetting otherExpense(String otherExpense) {
        this.setOtherExpense(otherExpense);
        return this;
    }

    public void setOtherExpense(String otherExpense) {
        this.otherExpense = otherExpense;
    }

    public String getNoticeExpense() {
        return this.noticeExpense;
    }

    public CourtCaseSetting noticeExpense(String noticeExpense) {
        this.setNoticeExpense(noticeExpense);
        return this;
    }

    public void setNoticeExpense(String noticeExpense) {
        this.noticeExpense = noticeExpense;
    }

    public String getMeetingNo() {
        return this.meetingNo;
    }

    public CourtCaseSetting meetingNo(String meetingNo) {
        this.setMeetingNo(meetingNo);
        return this;
    }

    public void setMeetingNo(String meetingNo) {
        this.meetingNo = meetingNo;
    }

    public LocalDate getMeetingDate() {
        return this.meetingDate;
    }

    public CourtCaseSetting meetingDate(LocalDate meetingDate) {
        this.setMeetingDate(meetingDate);
        return this;
    }

    public void setMeetingDate(LocalDate meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getSubjectNo() {
        return this.subjectNo;
    }

    public CourtCaseSetting subjectNo(String subjectNo) {
        this.setSubjectNo(subjectNo);
        return this;
    }

    public void setSubjectNo(String subjectNo) {
        this.subjectNo = subjectNo;
    }

    public String getMeetingDay() {
        return this.meetingDay;
    }

    public CourtCaseSetting meetingDay(String meetingDay) {
        this.setMeetingDay(meetingDay);
        return this;
    }

    public void setMeetingDay(String meetingDay) {
        this.meetingDay = meetingDay;
    }

    public String getMeetingTime() {
        return this.meetingTime;
    }

    public CourtCaseSetting meetingTime(String meetingTime) {
        this.setMeetingTime(meetingTime);
        return this;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getTalukaName() {
        return talukaName;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }

    public String getBankNameEn() {
        return bankNameEn;
    }

    public void setBankNameEn(String bankNameEn) {
        this.bankNameEn = bankNameEn;
    }

    public String getTalukaNameEn() {
        return talukaNameEn;
    }

    public void setTalukaNameEn(String talukaNameEn) {
        this.talukaNameEn = talukaNameEn;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
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
            ", vasuliAdhikariName='" + getVasuliAdhikariName() + "'" +
            ", arOfficeName='" + getArOfficeName() + "'" +
            ", chairmanName='" + getChairmanName() + "'" +
            ", sachivName='" + getSachivName() + "'" +
            ", suchakName='" + getSuchakName() + "'" +
            ", anumodakName='" + getAnumodakName() + "'" +
            ", vasuliExpense=" + getVasuliExpense() +
            ", otherExpense=" + getOtherExpense() +
            ", noticeExpense=" + getNoticeExpense() +
            ", meetingNo=" + getMeetingNo() +
            ", meetingDate='" + getMeetingDate() + "'" +
            ", subjectNo=" + getSubjectNo() +
            ", meetingDay='" + getMeetingDay() + "'" +
            ", meetingTime='" + getMeetingTime() + "'" +
            "}";
    }
}
