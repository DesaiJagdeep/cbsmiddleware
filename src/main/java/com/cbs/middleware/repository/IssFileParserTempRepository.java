package com.cbs.middleware.repository;

import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.domain.IssFileParserTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssFileParserTempRepository extends JpaRepository<IssFileParserTemp, Long>, JpaSpecificationExecutor<IssFileParser> {

/*    @Query(value = "select * from iss_file_parser_temp where financial_year=:fYear and " +
        "account_number=:AccountNumber and " +
        "loan_saction_date=:LoanSactionDate and " +
        "kcc_iss_crop_code=:KccIssCropCode and " +
        "disbursement_date=:DisbursementDate and " +
        "maturity_loan_date=:maturityLoanDate ", nativeQuery = true)
    Optional<IssFileParser> findOneByFinancialYearAndAccountNumberAndLoanSactionDateAndKccIssCropCodeAndDisbursementDateAndMaturityLoanDate(
        @Param("fYear") String fYear,
        @Param("AccountNumber") String AccountNumber,
        @Param("LoanSactionDate") String LoanSactionDate,
        @Param("KccIssCropCode") String KccIssCropCode,
        @Param("DisbursementDate") String DisbursementDate,
        @Param("maturityLoanDate") String maturityLoanDate
    );*/

    @Query(value = "select * from iss_file_parser_temp where pacs_number=:pacsNumber and financial_year=:fYear ",nativeQuery = true)
    List<IssFileParserTemp> findByPacsNumberAndFinancialYear(@Param("pacsNumber") String pacsNumber, @Param("fYear") String fYear);

    @Query(value = "select * from iss_file_parser_temp where pacs_number=:pacsNumber and uploading_user=:login ",nativeQuery = true)
    List<IssFileParserTemp> findByPacsNumberAndUploadingUser(@Param("pacsNumber") String pacsNumber, @Param("login") String login);


    Optional<IssFileParserTemp> findByFinancialYearEqualsAndAccountNumberEqualsAndLoanSactionDateEqualsAndKccIssCropCodeEqualsAndDisbursementDateEqualsAndMaturityLoanDateEquals(
        String financialYear,
        String accountNumber,
        String loanSactionDate,
        String kccIssCropCode,
        String disbursementDate,
        String maturityLoanDate);

   /* @Modifying
    @Query(value = "delete from IssFileParserTemp ifpt where ifpt.uploadingUser=:login")
    void deleteTempData(@Param("login") String login);
*/


}
