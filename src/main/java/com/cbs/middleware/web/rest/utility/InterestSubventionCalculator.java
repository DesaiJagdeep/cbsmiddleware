package com.cbs.middleware.web.rest.utility;

import com.cbs.middleware.domain.IssFileParser;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class InterestSubventionCalculator {


    private final List<IssFileParser> issFileParsers;

    public Long totalDebitAmount;
    public Long creditAmount;
    public Long debitAmount;
    public LocalDate creditDate;
    public Long prevDays;
    public Long presentDays;
    public Integer typeOfReport;
    public Integer reportFor;
    public LocalDate bankDate;
    public  LocalDate lastDate;
    public  LocalDate loanMaturityDate;

    public Long previousDebitAmount;

    public   LocalDate recoveryDate;

    public  Long diffAmount=0L;

    public  Long above3Product=0L;

    public  Integer serialNo=0;

    public Long midBalanceAmt=0L;

    public String firstVasuliDate="2023-03-31";




    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public InterestSubventionCalculator(List<IssFileParser> issFileParsers){
        this.issFileParsers =issFileParsers;


    }
// InterestSubvention  isb = new InterestSubvention();
    // geteligibility() // process array and set is_elible Y/N
    // boolean isEligible = GetEligibility();
//        if (isEligible){
//
//
//        }

    // split amount below and above 3L
    // calculateInterest() // loop through
    // InterestSubvention
    // is_eligible
    // State - 3% - 100% recovery
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

    public List<IssFileParser> CalculateInterest()

    {
        Long interestCalAmount=300000L;
        totalDebitAmount =0L;
        Long recoveryAmount=0L;
        previousDebitAmount=0L;

        for (IssFileParser issFileParser:issFileParsers){

            Long loanDisbursementAmount = Long.valueOf(issFileParser.getDisburseAmount());
            LocalDate loanDisbursementDate = LocalDate.parse(issFileParser.getDisbursementDate(), inputFormatter);

            lastDate=loanDisbursementDate;
            recoveryDate=loanDisbursementDate;
            debitAmount=loanDisbursementAmount;
            midBalanceAmt=loanDisbursementAmount;
            diffAmount=0L;
            serialNo=0;

            loanMaturityDate=LocalDate.parse(issFileParser.getMaturityLoanDate());

            //Calculate the total disbursement of loan
            totalDebitAmount = totalDebitAmount + loanDisbursementAmount;

            //If total disbursement of loan is greater than 3 lakh, the find out difference amount & substract diff amount from total loan amount
            if (totalDebitAmount>interestCalAmount){
                diffAmount = totalDebitAmount - interestCalAmount;
                debitAmount= debitAmount- diffAmount;
            }

            //bankDate calculated upto 365 days
            bankDate = ChronoUnit.DAYS.addTo(loanDisbursementDate,364);
            System.out.println("Bank date:" + bankDate);

            //Calculate Product Amount
           calculateProductAmounts(issFileParser);
        }

        return null;
    }


    public void calculateProductAmounts(IssFileParser issFileParser) {
        Long interestCalAmount = 300000L;

        Long recoveryAmount = 0L;
        LocalDate reportDate = LocalDate.parse(firstVasuliDate);

        //check for first recovery
        if (issFileParser.getRecoveryDate() != null && issFileParser.getRecoveryAmountPrinciple() != null) {
            recoveryDate = LocalDate.parse(issFileParser.getRecoveryDate(), inputFormatter);
            recoveryAmount = Long.parseLong(issFileParser.getRecoveryAmountPrinciple());
            Long days1 = 0L;
            Long productAmount1 = 0L;
            Long bankProductAmount1 = 0L;
            serialNo =1;

            //check recovery date is Less than or equal to bankDate(365 days)
            int compareResult = recoveryDate.compareTo(bankDate);

            //If recovery date is greater than bank date find out days between loan date & bank date
            if (compareResult > 0) {
                //days1 = ChronoUnit.DAYS.between(lastDate, bankDate);

                //Procedure to calculate days upto 31/03 as prev days & after 31/03 to bankdate or maturity date as present days
                int compareVasuliDate = recoveryDate.compareTo(reportDate);
                if (compareVasuliDate>0){
                    //If recovery date is greater than 31/03
                    prevDays= ChronoUnit.DAYS.between(lastDate,reportDate);
                    presentDays=ChronoUnit.DAYS.between(ChronoUnit.DAYS.addTo(reportDate,1),bankDate);
                }

                else {
                    //If recovery date is less than 31/03
                    prevDays= ChronoUnit.DAYS.between(lastDate,recoveryDate);
                    presentDays=0L;
                }

                //If recovery date is less than bank date find out days between loan date & recovery date
            } else {
                //days1 = ChronoUnit.DAYS.between(lastDate, recoveryDate);
                int compareVasuliDate = recoveryDate.compareTo(reportDate);
                if (compareVasuliDate>0){
                    prevDays= ChronoUnit.DAYS.between(lastDate,reportDate);
                    presentDays=ChronoUnit.DAYS.between(ChronoUnit.DAYS.addTo(reportDate,1),recoveryDate);
                }

                else {
                    prevDays= ChronoUnit.DAYS.between(lastDate,recoveryDate);
                    presentDays=0L;
                }
            }


            //For first cal productAmount is equal to loan amount
            productAmount1 = debitAmount;
            bankProductAmount1=debitAmount;

            //Calculate balance loan amount substracting recovery amount. This amount is treated as product amount for next recovery
            debitAmount = debitAmount - recoveryAmount;

            midBalanceAmt = midBalanceAmt-recoveryAmount;

            //This date is calculated as per reducing balance formula,last date is defined for next days calculation
            lastDate = recoveryDate;

            if (debitAmount < 0) {
                above3Product = diffAmount;
                bankProductAmount1= diffAmount+debitAmount;
                diffAmount=diffAmount+debitAmount;
                debitAmount = 0L;
            }

        }

        //Above remarks specified in first recovery are the same for the second , third & fourth recovery
        //Check for Second recovery
        if (issFileParser.getSecondRecoveryDate() != null && issFileParser.getSecondRecoveryAmountPrinciple() != null) {
            recoveryDate = LocalDate.parse(issFileParser.getSecondRecoveryDate(), inputFormatter);
            recoveryAmount = Long.parseLong(issFileParser.getSecondRecoveryAmountPrinciple());

            Long days2 = 0L;
            Long productAmount2 = 0L;
            Long bankProductAmount2 = 0L;
            serialNo =2;

            //check recovery date is Less than or eqaul to 365 days
            int compareResult = recoveryDate.compareTo(bankDate);
            if (compareResult > 0) {
                days2 = ChronoUnit.DAYS.between(lastDate, bankDate);
                //Procedure to calculate days upto 31/03 as prev days & after 31/03 to bankdate or maturity date as present days
                int compareVasuliDate = recoveryDate.compareTo(reportDate);
                if (compareVasuliDate>0){
                    //If recovery date is greater than 31/03
                    prevDays= ChronoUnit.DAYS.between(lastDate,reportDate);
                    presentDays=ChronoUnit.DAYS.between(ChronoUnit.DAYS.addTo(reportDate,1),bankDate);
                }

                else {
                    //If recovery date is less than 31/03
                    prevDays= ChronoUnit.DAYS.between(lastDate,recoveryDate);
                    presentDays=0L;
                }
            } else {
                days2 = ChronoUnit.DAYS.between(lastDate, recoveryDate);
                int compareVasuliDate = recoveryDate.compareTo(reportDate);
                if (compareVasuliDate>0){
                    prevDays= ChronoUnit.DAYS.between(lastDate,reportDate);
                    presentDays=ChronoUnit.DAYS.between(ChronoUnit.DAYS.addTo(reportDate,1),recoveryDate);
                }

                else {
                    prevDays= ChronoUnit.DAYS.between(lastDate,recoveryDate);
                    presentDays=0L;
                }
            }

            productAmount2 = debitAmount;
            debitAmount = debitAmount - recoveryAmount;

            midBalanceAmt = midBalanceAmt-recoveryAmount;
            lastDate = recoveryDate;
            if (debitAmount < 0) {
                bankProductAmount2 =-debitAmount;
                above3Product=diffAmount;
                diffAmount=diffAmount+debitAmount;
                debitAmount = 0L;
            }


        }

        //Check for Third recovery
        if (issFileParser.getThirdRecoveryDate() != null && issFileParser.getThirdRecoveryAmountPrinciple() != null) {
            recoveryDate = LocalDate.parse(issFileParser.getThirdRecoveryDate(), inputFormatter);
            recoveryAmount = Long.parseLong(issFileParser.getThirdRecoveryAmountPrinciple());

            Long days3 = 0L;
            Long productAmount3 = 0L;
            Long bankProductAmount3 = 0L;
            serialNo =3;


            //check recovery date is Less than or eqaul to 365 days
            int compareResult = recoveryDate.compareTo(bankDate);
            if (compareResult > 0) {
                days3 = ChronoUnit.DAYS.between(lastDate, bankDate);
                //Procedure to calculate days upto 31/03 as prev days & after 31/03 to bankdate or maturity date as present days
                int compareVasuliDate = recoveryDate.compareTo(reportDate);
                if (compareVasuliDate>0){
                    //If recovery date is greater than 31/03
                    prevDays= ChronoUnit.DAYS.between(lastDate,reportDate);
                    presentDays=ChronoUnit.DAYS.between(ChronoUnit.DAYS.addTo(reportDate,1),bankDate);
                }

                else {
                    //If recovery date is less than 31/03
                    prevDays= ChronoUnit.DAYS.between(lastDate,recoveryDate);
                    presentDays=0L;
                }
            } else {
                days3 = ChronoUnit.DAYS.between(lastDate, recoveryDate);
                int compareVasuliDate = recoveryDate.compareTo(reportDate);
                if (compareVasuliDate>0){
                    prevDays= ChronoUnit.DAYS.between(lastDate,reportDate);
                    presentDays=ChronoUnit.DAYS.between(ChronoUnit.DAYS.addTo(reportDate,1),recoveryDate);
                }

                else {
                    prevDays= ChronoUnit.DAYS.between(lastDate,recoveryDate);
                    presentDays=0L;
                }
            }

            productAmount3 = debitAmount;
            debitAmount = debitAmount - recoveryAmount;

            midBalanceAmt = midBalanceAmt-recoveryAmount;
            lastDate = recoveryDate;
            if (debitAmount < 0) {

                bankProductAmount3=-debitAmount;
                above3Product=diffAmount;
                diffAmount=diffAmount+debitAmount;
                debitAmount = 0L;
            }

        }
        //Check for Fourth recovery
        if (issFileParser.getFourthRecoveryDate() != null && issFileParser.getFourthRecoveryAmountPrinciple() != null) {
            recoveryDate = LocalDate.parse(issFileParser.getFourthRecoveryDate(), inputFormatter);
            recoveryAmount = Long.parseLong(issFileParser.getFourthRecoveryAmountPrinciple());

            Long days4 = 0L;
            Long productAmount4 = 0L;
            Long bankProductAmount4 = 0L;
            serialNo=4;


            //check recovery date is Less than or eqaul to 365 days
            int compareResult = recoveryDate.compareTo(bankDate);
            if (compareResult > 0) {
                days4 = ChronoUnit.DAYS.between(lastDate, bankDate);
                //Procedure to calculate days upto 31/03 as prev days & after 31/03 to bankdate or maturity date as present days
                int compareVasuliDate = recoveryDate.compareTo(reportDate);
                if (compareVasuliDate>0){
                    //If recovery date is greater than 31/03
                    prevDays= ChronoUnit.DAYS.between(lastDate,reportDate);
                    presentDays=ChronoUnit.DAYS.between(ChronoUnit.DAYS.addTo(reportDate,1),bankDate);
                }

                else {
                    //If recovery date is less than 31/03
                    prevDays= ChronoUnit.DAYS.between(lastDate,recoveryDate);
                    presentDays=0L;
                }
            } else {
                days4 = ChronoUnit.DAYS.between(lastDate, recoveryDate);
                int compareVasuliDate = recoveryDate.compareTo(reportDate);
                if (compareVasuliDate>0){
                    prevDays= ChronoUnit.DAYS.between(lastDate,reportDate);
                    presentDays=ChronoUnit.DAYS.between(ChronoUnit.DAYS.addTo(reportDate,1),recoveryDate);
                }

                else {
                    prevDays= ChronoUnit.DAYS.between(lastDate,recoveryDate);
                    presentDays=0L;
                }
            }

            productAmount4 = debitAmount;
            debitAmount = debitAmount - recoveryAmount;

            midBalanceAmt = midBalanceAmt-recoveryAmount;
            lastDate = recoveryDate;


            if (debitAmount < 0) {
                bankProductAmount4=-debitAmount;
                above3Product=diffAmount;
                diffAmount=diffAmount+debitAmount;
                debitAmount = 0L;
            }
        }

//After checking all the recovery, check if balance loan amount
        //If the loan amount is not totally paid or partially paid
        Long lastDays=0L;
        Long lastProdAmount=0L;

        int result = bankDate.compareTo(loanMaturityDate);
        if (result > 0)
        {
            lastDays= ChronoUnit.DAYS.between(recoveryDate,loanMaturityDate);
            //Procedure to calculate days upto 31/03 as prev days & after 31/03 to bankdate or maturity date as present days
            int compareVasuliDate = recoveryDate.compareTo(reportDate);
            if (compareVasuliDate>0){
                //If recovery date is greater than 31/03
                prevDays= ChronoUnit.DAYS.between(lastDate,reportDate);
                presentDays=ChronoUnit.DAYS.between(ChronoUnit.DAYS.addTo(reportDate,1),loanMaturityDate);
            }
`
            else {
                //If recovery date is less than 31/03
                prevDays= ChronoUnit.DAYS.between(lastDate,recoveryDate);
                presentDays=0L;
            }
        }
        else {
            lastDays= ChronoUnit.DAYS.between(recoveryDate,bankDate);

            //Procedure to calculate days upto 31/03 as prev days & after 31/03 to bankdate or maturity date as present days
            int compareVasuliDate = recoveryDate.compareTo(reportDate);
            if (compareVasuliDate>0){
                //If recovery date is greater than 31/03
                prevDays= ChronoUnit.DAYS.between(lastDate,reportDate);
                presentDays=ChronoUnit.DAYS.between(ChronoUnit.DAYS.addTo(reportDate,1),bankDate);
            }

            else {
                //If recovery date is less than 31/03
                prevDays= ChronoUnit.DAYS.between(lastDate,recoveryDate);
                presentDays=0L;
            }

        }

        if (debitAmount > 0) {

            lastProdAmount=debitAmount; // PACS PROD Amount
        }
        else {
            lastProdAmount=-debitAmount;
        }
        if (diffAmount>0)
        {
            above3Product=diffAmount;
        }
        serialNo=5;
        //save previous loan amount
        previousDebitAmount = Long.valueOf(issFileParser.getDisburseAmount());

        //condition for calculating interest amount above 3 lakh for bank purpose

    }


//    public boolean GetEligibility(){
//        boolean isEligible =false;
//        Integer firstRecoveryAmount=0;
//        Integer secondRecoveryAmount=0;
//        Integer thirdRecoveryAmount=0;
//        Integer fourthRecoveryAmount=0;
//
//        //loop through application
//        for (IssFileParser issFileParser:issFileParsers){
//            //First to check total loan is repaid or not
//            //sum of all the recovered amount
//
//            if(issFileParser.getRecoveryAmountPrinciple() != null){
//                firstRecoveryAmount = Integer.parseInt(issFileParser.getRecoveryAmountPrinciple());
//            }
//            if(issFileParser.getSecondRecoveryAmountPrinciple() != null){
//                secondRecoveryAmount = Integer.parseInt(issFileParser.getSecondRecoveryAmountPrinciple());
//            }
//            if(issFileParser.getThirdRecoveryAmountPrinciple() != null){
//                thirdRecoveryAmount = Integer.parseInt(issFileParser.getThirdRecoveryAmountPrinciple());
//            }
//            if(issFileParser.getFourthRecoveryAmountPrinciple() != null){
//                fourthRecoveryAmount = Integer.parseInt(issFileParser.getFourthRecoveryAmountPrinciple());
//            }
//
//            Integer SumOfRecoveredAmount  = firstRecoveryAmount + secondRecoveryAmount + thirdRecoveryAmount + fourthRecoveryAmount;
//            System.out.println("SumOfRecoveredAmount in calculator:" + SumOfRecoveredAmount);
//
//
//            if (Integer.parseInt(issFileParser.getDisburseAmount())==SumOfRecoveredAmount){
//                //find the days upto last recovery date, no of days should be less than or equal to 365 days
//
//                //Convert date string into LocalDate to calculate days between two dates
//
//                LocalDate disbursementDate ,secondRecoveryDate,ThirdRecoveryDate,FourthRecoveryDate = null;
//                LocalDate firstRecoveryDate = null;
//                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                disbursementDate = LocalDate.parse(issFileParser.getDisbursementDate(), inputFormatter);
//
//                long daysBetween=0;
//
//                //check for last recovery date
//                if (issFileParser.getRecoveryDate() != null) {
//
//                    firstRecoveryDate = LocalDate.parse(issFileParser.getRecoveryDate(), inputFormatter);
//
//                    daysBetween = ChronoUnit.DAYS.between(disbursementDate,firstRecoveryDate);
//                }
//                else if (issFileParser.getSecondRecoveryDate() != null) {
//                    secondRecoveryDate = LocalDate.parse(issFileParser.getSecondRecoveryDate(), inputFormatter);
//                    daysBetween = daysBetween + (ChronoUnit.DAYS.between(firstRecoveryDate,secondRecoveryDate));
//                }
//              else  if (issFileParser.getFourthRecoveryDate() != null) {
//                    FourthRecoveryDate = LocalDate.parse(issFileParser.getFourthRecoveryDate(), inputFormatter);
//
//                }
//                else if (issFileParser.getThirdRecoveryDate() != null) {
//                    ThirdRecoveryDate = LocalDate.parse(issFileParser.getThirdRecoveryDate(), inputFormatter);
//
//                }
//
//                // daysBetween = ChronoUnit.DAYS.between(disbursementDate, lastRecoveryDate);
//               if (daysBetween<=365){
//                isEligible=true;
//                         }
//
//               System.out.println("disbursementDate: " + disbursementDate + " lastRecoveryDate: "+ lastRecoveryDate +"Days upto last recovery: " + daysBetween );
//
//
//            }
//
//        }
//
//        return  isEligible;
//
//    }


}
