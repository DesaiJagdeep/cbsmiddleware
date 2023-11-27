package com.cbs.middleware.domain.domainUtil;

import org.springframework.stereotype.Component;

@Component
public class TalukaWiseDataReport {

	private Integer srNo=0;
	private String talukaName;
    private Long talukaId;
	private Integer noOfSocieties=0;
	private Integer completed=0;
	private Integer inProgress=0;
	private Integer yetToStart=0;
	private Integer pendingForApproval=0;

    public Long getTalukaId() {
        return talukaId;
    }

    public void setTalukaId(Long talukaId) {
        this.talukaId = talukaId;
    }

    public Integer getSrNo() {
		return srNo;
	}
	public void setSrNo(Integer srNo) {
		this.srNo = srNo;
	}
	public String getTalukaName() {
		return talukaName;
	}
	public void setTalukaName(String talukaName) {
		this.talukaName = talukaName;
	}
	public Integer getNoOfSocieties() {
		return noOfSocieties;
	}
	public void setNoOfSocieties(Integer noOfSocieties) {
		this.noOfSocieties = noOfSocieties;
	}
	public Integer getCompleted() {
		return completed;
	}
	public void setCompleted(Integer completed) {
		this.completed = completed;
	}
	public Integer getInProgress() {
		return inProgress;
	}
	public void setInProgress(Integer inProgress) {
		this.inProgress = inProgress;
	}
	public Integer getYetToStart() {
		return yetToStart;
	}
	public void setYetToStart(Integer yetToStart) {
		this.yetToStart = yetToStart;
	}
	public Integer getPendingForApproval() {
		return pendingForApproval;
	}

	public void setPendingForApproval(Integer pendingForApproval) {
		this.pendingForApproval = pendingForApproval;
	}



}
