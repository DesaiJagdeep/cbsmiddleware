package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.repository.IsCalculateTempRepository;
import com.cbs.middleware.repository.IssFileParserRepository;
import com.cbs.middleware.service.dto.InterestSubventionDTO;
import com.cbs.middleware.web.rest.utility.InterestSubventionCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class InterestSubventionResource {

    private final IssFileParserRepository issFileParserRepository;

    @Autowired
    IsCalculateTempRepository isCalculateTempRepository;
    public InterestSubventionResource(IssFileParserRepository issFileParserRepository) {

        this.issFileParserRepository = issFileParserRepository;
    }

    //Process records
    @PostMapping("/interestSubvention")
    public void ProcessRecordsToCalInterestSubvention (@RequestBody InterestSubventionDTO interestSubventionDTO){
        //get distinct aadhar numbers from parser by pacscode & financial year
        List<String> distinctAadhars = issFileParserRepository.findDistinctFarmerByPacsNumberAndFinancialYear(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());

        //loop through aadhar numbers
        for (String aadharNumber : distinctAadhars) {
            //get the records from parser order by disbursementDate ASC
            List<IssFileParser> issFileParsers = issFileParserRepository.findByAadharNumber(aadharNumber, interestSubventionDTO.getFinancialYear());
            System.out.println("IssFileParsers:" + issFileParsers);


            //Calculate interest for report 1 & 2
            List<IssFileParser> issSubvention = new InterestSubventionCalculator(issFileParsers,interestSubventionDTO,isCalculateTempRepository).CalculateInterestForCenterState();


            //Calculate interest for punjab rao 3
           // issSubvention = new InterestSubventionCalculator(issFileParsers,interestSubventionDTO).CalculateInterestForPunjabRao();

        }


        //end loop through aadhar numbers



    }

    // InterestSubvention
    // is_eligible
    // State - 3%
    // State - 1.5%
    // Center - 3%
    // Center 2.5%
    // split loan amount upto 3 L aand above 3L [
    // Applicationnumber, totalamount,eligible amount, excess amount, disbursement date
    // disbursement amount, maturity date, recovery1.2.3, recovery interest, recovery interest amount]
    // [ {"123456","150000", "150000","0",
    // rec1 date, rev1 amount, rev1 duration in days,
    // rev1 interest eligible_percentage, rev1 interest ligible_mount
    // , rec1 interest extra_percentage, rev1 interest extra_mount,
    // 3%centerAmount, 2.5%centerAmount, 3%stateAmount, 1.5% state amount
    //
    // total eligilble_interest, total extra_interest},
    // {"67666666","400000","150000","250000"},
    // {"999999","100000","0","100000"}]
    // Interest amount


    // recovery1 50,000 2 months  4,00,000 used for 2 months (1,50,000 with 6% + 2,50,000 with 11%)
    // recovery2 1,25,000 4 months 75,000 350000 used for next 2 months (1,00,000 with 6%  250000 with 11%)
    // recovery3 2,00,000 8 months 2,25,000 for 8mths (all with 11%)
    // recovery4 25,000 11 months 25000 for 11months (all with 11%)

    public void calculate(List<IssFileParser> issFileParser){
        // InterestSubvention  isb = new InterestSubvention();
        // geteligibility() // process array and set is_elible Y/N
        // split amount below and above 3L
        // calculateInterest() // loop through
    }

    public void checkEligibility() {


    }

    public void Calculate3PerecentInterest() {

    }
    public void CalculateOneandHalfPerecentInterest() {

    }
    public void calculateTwoandHalfPerecentInterest() {

    }


}
