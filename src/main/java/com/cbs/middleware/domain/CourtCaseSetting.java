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
public class CourtCaseSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
    private Double vasuliExpense;

    @Column(name = "other_expense")
    private Double otherExpense;

    @Column(name = "notice_expense")
    private Double noticeExpense;

    @Column(name = "meeting_no")
    private Long meetingNo;

    @Column(name = "meeting_date")
    private LocalDate meetingDate;

    @Column(name = "subject_no")
    private Long subjectNo;

    @Column(name = "meeting_day")
    private String meetingDay;

    @Column(name = "meeting_time")
    private String meetingTime;

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

    public CourtCaseSetting arOfficeName(String arOfficeName) {
        this.setArOfficeName(arOfficeName);
        return this;
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

    public Double getVasuliExpense() {
        return this.vasuliExpense;
    }

    public CourtCaseSetting vasuliExpense(Double vasuliExpense) {
        this.setVasuliExpense(vasuliExpense);
        return this;
    }

    public void setVasuliExpense(Double vasuliExpense) {
        this.vasuliExpense = vasuliExpense;
    }

    public Double getOtherExpense() {
        return this.otherExpense;
    }

    public CourtCaseSetting otherExpense(Double otherExpense) {
        this.setOtherExpense(otherExpense);
        return this;
    }

    public void setOtherExpense(Double otherExpense) {
        this.otherExpense = otherExpense;
    }

    public Double getNoticeExpense() {
        return this.noticeExpense;
    }

    public CourtCaseSetting noticeExpense(Double noticeExpense) {
        this.setNoticeExpense(noticeExpense);
        return this;
    }

    public void setNoticeExpense(Double noticeExpense) {
        this.noticeExpense = noticeExpense;
    }

    public Long getMeetingNo() {
        return this.meetingNo;
    }

    public CourtCaseSetting meetingNo(Long meetingNo) {
        this.setMeetingNo(meetingNo);
        return this;
    }

    public void setMeetingNo(Long meetingNo) {
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

    public Long getSubjectNo() {
        return this.subjectNo;
    }

    public CourtCaseSetting subjectNo(Long subjectNo) {
        this.setSubjectNo(subjectNo);
        return this;
    }

    public void setSubjectNo(Long subjectNo) {
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
