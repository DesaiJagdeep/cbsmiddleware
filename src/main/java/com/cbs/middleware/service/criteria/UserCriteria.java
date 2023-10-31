package com.cbs.middleware.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.cbs.middleware.domain.IssFileParser}
 * entity. This class is used in
 * {@link com.cbs.middleware.web.rest.IssFileParserResource} to receive all the
 * possible filtering options from the Http GET request parameters. For example
 * the following could be a valid request:
 * {@code /iss-file-parsers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific
 * {@link Filter} class are used, we need to use fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserCriteria implements Serializable, Criteria {

	private static final long serialVersionUID = 1L;

	private LongFilter id;

	private StringFilter login;

	private StringFilter firstName;

	private StringFilter middleName;

	private StringFilter lastName;

	private StringFilter email;

	private StringFilter mobileNumber;

	private StringFilter branchName;

	private StringFilter pacsName;

	private Boolean distinct;

	public UserCriteria() {
	}

	public UserCriteria(UserCriteria other) {
		this.id = other.id == null ? null : other.id.copy();
		this.login = other.login == null ? null : other.login.copy();
		this.firstName = other.firstName == null ? null : other.firstName.copy();
		this.middleName = other.middleName == null ? null : other.middleName.copy();
		this.branchName = other.branchName == null ? null : other.branchName.copy();
		this.lastName = other.lastName == null ? null : other.lastName.copy();
		this.email = other.email == null ? null : other.email.copy();
		this.mobileNumber = other.mobileNumber == null ? null : other.mobileNumber.copy();
		this.branchName = other.branchName == null ? null : other.branchName.copy();
		this.pacsName = other.pacsName == null ? null : other.pacsName.copy();
		this.distinct = other.distinct;
	}

	@Override
	public UserCriteria copy() {
		return new UserCriteria(this);
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

	public StringFilter getLogin() {
		return login;
	}

	public StringFilter login() {
		if (login == null) {
			login = new StringFilter();
		}
		return login;
	}

	public void setLogin(StringFilter login) {
		this.login = login;
	}

	public StringFilter getFirstName() {
		return firstName;
	}

	public StringFilter firstName() {
		if (firstName == null) {
			firstName = new StringFilter();
		}
		return firstName;
	}

	public void setFirstName(StringFilter firstName) {
		this.firstName = firstName;
	}

	public StringFilter getMiddleName() {
		return middleName;
	}

	public StringFilter middleName() {
		if (middleName == null) {
			middleName = new StringFilter();
		}
		return middleName;
	}

	public void setMiddleName(StringFilter middleName) {
		this.middleName = middleName;
	}

	public StringFilter getBranchName() {
		return branchName;
	}

	public StringFilter branchName() {
		if (branchName == null) {
			branchName = new StringFilter();
		}
		return branchName;
	}

	public void setBranchName(StringFilter branchName) {
		this.branchName = branchName;
	}

	public StringFilter getLastName() {
		return lastName;
	}

	public StringFilter lastName() {
		if (lastName == null) {
			lastName = new StringFilter();
		}
		return lastName;
	}

	public void setLastName(StringFilter lastName) {
		this.lastName = lastName;
	}

	public StringFilter getMobileNumber() {
		return mobileNumber;
	}

	public StringFilter mobileNumber() {
		if (mobileNumber == null) {
			mobileNumber = new StringFilter();
		}
		return mobileNumber;
	}

	public void setMobileNumber(StringFilter mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public StringFilter getEmail() {
		return email;
	}

	public StringFilter email() {
		if (email == null) {
			email = new StringFilter();
		}
		return email;
	}

	public void setEmail(StringFilter email) {
		this.email = email;
	}

	public StringFilter getPacsName() {
		return pacsName;
	}

	public StringFilter pacsName() {
		if (pacsName == null) {
			pacsName = new StringFilter();
		}
		return pacsName;
	}

	public void setPacsName(StringFilter pacsName) {
		this.pacsName = pacsName;
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
		final UserCriteria that = (UserCriteria) o;
		return (Objects.equals(id, that.id) && Objects.equals(login, that.login)
				&& Objects.equals(firstName, that.firstName) && Objects.equals(middleName, that.middleName)
				&& Objects.equals(branchName, that.branchName) && Objects.equals(lastName, that.lastName)
				&& Objects.equals(email, that.email) && Objects.equals(mobileNumber, that.mobileNumber)
				&& Objects.equals(pacsName, that.pacsName) && Objects.equals(distinct, that.distinct));
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, login, firstName, middleName, branchName, lastName, email, mobileNumber, pacsName,
				distinct);
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "IssFileParserCriteria{" + (id != null ? "id=" + id + ", " : "")
				+ (login != null ? "login=" + login + ", " : "")
				+ (firstName != null ? "firstName=" + firstName + ", " : "")
				+ (middleName != null ? "middleName=" + middleName + ", " : "")
				+ (branchName != null ? "branchName=" + branchName + ", " : "")
				+ (lastName != null ? "branchCode=" + lastName + ", " : "")
				+ (email != null ? "email=" + email + ", " : "")
				+ (mobileNumber != null ? "mobileNumber=" + mobileNumber + ", " : "")
				+ (pacsName != null ? "pacsName=" + pacsName + ", " : "")
				+ (distinct != null ? "distinct=" + distinct + ", " : "") + "}";
	}
}
