package com.cbs.middleware.web.rest.utility;

import com.cbs.middleware.domain.IsCalculateTemp;
import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.repository.AccountHolderMasterRepository;
import com.cbs.middleware.repository.IsCalculateTempRepository;
import com.cbs.middleware.repository.IssFileParserRepository;
import com.cbs.middleware.service.dto.InterestSubventionDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class InterestSubventionCalculator {

    private final List<IssFileParser> issFileParsers;

    private final IsCalculateTempRepository isCalculateTempRepository;
    private final InterestSubventionDTO interestSubventionDTO;
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
    public  LocalDate recoveryDate;
    public  Long diffAmount=0L;
    public  Long above3Product=0L;
    public  Integer serialNo=0;
    public Long midBalanceAmt=0L;
    public String firstVasuliDate;
    public Integer loanRecover;
    public  Long interestCalAmount=300000L;
    public   Long above3lakhAmt=0L;
    public  Integer upto50000=0;

    public Double interestFirst15=0.00;  //Center bank first 1.5%
    public Double interestFirst25=0.00; //Center bank  first 2.5%
    public Double interestSecond15=0.00; //Center bank second 1.5%
    public Double interestSecond25=0.00; //Center bank second 2.5%
    public Double interestFirst3=0.00;  //Center state first 3%
    public Double interestSecond3=0.00; //Center state second 3%
    public Double interestAbove3lakh=0.00; //Above 3 lakh interest

    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");




    public InterestSubventionCalculator(List<IssFileParser> issFileParsers, InterestSubventionDTO interestSubventionDTO, IsCalculateTempRepository isCalculateTempRepository) {
        this.issFileParsers = issFileParsers;
        this.interestSubventionDTO = interestSubventionDTO;
        this.isCalculateTempRepository = isCalculateTempRepository;
        loanRecover= checkLoanIsRecoverOrNot();
    }
    public Integer checkLoanIsRecoverOrNot(){
        Long totalLoan = 0L;
        Long totalRecovery = 0L;
        Long balanceRecovery = 0l;
        Integer firstRecoveryAmount = 0;
        Integer secondRecoveryAmount = 0;
        Integer thirdRecoveryAmount = 0;
        Integer fourthRecoveryAmount = 0;

        //Check if is recover or not
        for (IssFileParser issFileParser : issFileParsers) {
            Long loanDisbursementAmount = Long.valueOf(issFileParser.getDisburseAmount());

            if (issFileParser.getRecoveryAmountPrinciple() != null) {
                firstRecoveryAmount = Integer.parseInt(issFileParser.getRecoveryAmountPrinciple());
            }
            if (issFileParser.getSecondRecoveryAmountPrinciple() != null) {
                secondRecoveryAmount = Integer.parseInt(issFileParser.getSecondRecoveryAmountPrinciple());
            }
            if (issFileParser.getThirdRecoveryAmountPrinciple() != null) {
                thirdRecoveryAmount = Integer.parseInt(issFileParser.getThirdRecoveryAmountPrinciple());
            }
            if (issFileParser.getFourthRecoveryAmountPrinciple() != null) {
                fourthRecoveryAmount = Integer.parseInt(issFileParser.getFourthRecoveryAmountPrinciple());
            }
            totalLoan = totalLoan + loanDisbursementAmount;
            totalRecovery = totalRecovery + firstRecoveryAmount + secondRecoveryAmount + thirdRecoveryAmount + fourthRecoveryAmount;
        }

        balanceRecovery = totalLoan - totalRecovery;

        if (balanceRecovery == 0) {
            loanRecover = 1;
        } else {
            loanRecover = 0;
        }

        //Above 3 lakh maount
        above3lakhAmt = totalLoan - interestCalAmount;

        if (above3lakhAmt<0){
            above3lakhAmt=0L;
        }

        if (totalLoan<=50000){
            upto50000=1;
        }
        else if (totalLoan>=50000 && totalLoan<=interestCalAmount){
            upto50000=2;
        }
        else {
            upto50000=3;
        }

        return  loanRecover;
    }
    public List<IssFileParser> CalculateInterestForCenterState()

    {

        totalDebitAmount =0L;
        Long recoveryAmount=0L;
        previousDebitAmount=0L;

        firstVasuliDate = interestSubventionDTO.getLastCreditDate();

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
            Long day1 = 0L;
            Long productAmount1 = 0L;
            Long bankProductAmount1 = 0L;
            serialNo =1;

            //check recovery date is Less than or equal to bankDate(365 days)
            int compareResult = recoveryDate.compareTo(bankDate);

            //If recovery date is greater than bank date find out days between loan date & bank date
            if (compareResult > 0) {
                day1 = ChronoUnit.DAYS.between(lastDate, bankDate);

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
                day1 = ChronoUnit.DAYS.between(lastDate, recoveryDate);
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

            //
            if (debitAmount < 0) {
                above3Product = diffAmount;
                bankProductAmount1= diffAmount+debitAmount;
                diffAmount=diffAmount+debitAmount;
                debitAmount = 0L;
            }

            //Interest calculation
            //FirstReport-center bank - after 31/03
            interestFirst15=(bankProductAmount1*prevDays* interestSubventionDTO.getFromBankInterest())/36500;
            interestFirst25=(bankProductAmount1*prevDays* interestSubventionDTO.getToBankInterest())/36500;

            //SecondReport-center bank - after 01/04
            interestSecond15=(bankProductAmount1*presentDays* interestSubventionDTO.getFromBankInterest())/36500;
            interestSecond25=(bankProductAmount1*presentDays* interestSubventionDTO.getToBankInterest())/36500;

            if (loanRecover==1){
                interestFirst3=(productAmount1*prevDays* interestSubventionDTO.getFromInterest())/36500;
                interestSecond3=(productAmount1*presentDays* interestSubventionDTO.getToInterest())/36500;
            }

            //Above 3 lakh interest
            if (above3Product>0){
                interestAbove3lakh= (above3Product*day1*interestSubventionDTO.getInterestAbove3Lakh())/36500;
            }

            //Save record into isCalculateTemp
            saveIntoIsCalculateTemp(serialNo,issFileParser,recoveryDate,recoveryAmount,issFileParser.getRecoveryAmountInterest(),midBalanceAmt,bankDate,prevDays,presentDays,day1,productAmount1,bankProductAmount1,above3Product,interestFirst3,interestSecond3,interestFirst15,interestFirst25,interestSecond15,interestSecond25,interestAbove3lakh,above3lakhAmt,upto50000,loanRecover);


        }

        //Above remarks specified in first recovery are the same for the second , third & fourth recovery
        //Check for Second recovery
        if (issFileParser.getSecondRecoveryDate() != null && issFileParser.getSecondRecoveryAmountPrinciple() != null) {
            recoveryDate = LocalDate.parse(issFileParser.getSecondRecoveryDate(), inputFormatter);
            recoveryAmount = Long.parseLong(issFileParser.getSecondRecoveryAmountPrinciple());

            Long day2 = 0L;
            Long productAmount2 = 0L;
            Long bankProductAmount2 = 0L;
            serialNo =2;

            //check recovery date is Less than or eqaul to 365 days
            int compareResult = recoveryDate.compareTo(bankDate);
            if (compareResult > 0) {
                day2 = ChronoUnit.DAYS.between(lastDate, bankDate);
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
                day2 = ChronoUnit.DAYS.between(lastDate, recoveryDate);
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

            //Interest calculation
            //FirstReport-center bank - after 31/03
            interestFirst15=(bankProductAmount2*prevDays* interestSubventionDTO.getFromBankInterest())/36500;
            interestFirst25=(bankProductAmount2*prevDays* interestSubventionDTO.getToBankInterest())/36500;

            //SecondReport-center bank - after 01/04
            interestSecond15=(bankProductAmount2*presentDays* interestSubventionDTO.getFromBankInterest())/36500;
            interestSecond25=(bankProductAmount2*presentDays* interestSubventionDTO.getToBankInterest())/36500;

            if (loanRecover==1){
                interestFirst3=(productAmount2*prevDays* interestSubventionDTO.getFromInterest())/36500;
                interestSecond3=(productAmount2*presentDays* interestSubventionDTO.getToInterest())/36500;
            }

            //Above 3 lakh interest
            if (above3Product>0){
                interestAbove3lakh= (above3Product*day2*interestSubventionDTO.getInterestAbove3Lakh())/36500;
            }

            saveIntoIsCalculateTemp(serialNo,issFileParser,recoveryDate,recoveryAmount,issFileParser.getRecoveryAmountInterest(),midBalanceAmt,bankDate,prevDays,presentDays,day2,productAmount2,bankProductAmount2,above3Product,interestFirst3,interestSecond3,interestFirst15,interestFirst25,interestSecond15,interestSecond25,interestAbove3lakh,above3lakhAmt,upto50000,loanRecover);


        }

        //Check for Third recovery
        if (issFileParser.getThirdRecoveryDate() != null && issFileParser.getThirdRecoveryAmountPrinciple() != null) {
            recoveryDate = LocalDate.parse(issFileParser.getThirdRecoveryDate(), inputFormatter);
            recoveryAmount = Long.parseLong(issFileParser.getThirdRecoveryAmountPrinciple());

            Long day3 = 0L;
            Long productAmount3 = 0L;
            Long bankProductAmount3 = 0L;
            serialNo =3;

            //check recovery date is Less than or eqaul to 365 days
            int compareResult = recoveryDate.compareTo(bankDate);
            if (compareResult > 0) {
                day3 = ChronoUnit.DAYS.between(lastDate, bankDate);
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
                day3 = ChronoUnit.DAYS.between(lastDate, recoveryDate);
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

            //Interest calculation
            //FirstReport-center bank - after 31/03
            interestFirst15=(bankProductAmount3*prevDays* interestSubventionDTO.getFromBankInterest())/36500;
            interestFirst25=(bankProductAmount3*prevDays* interestSubventionDTO.getToBankInterest())/36500;

            //SecondReport-center bank - after 01/04
            interestSecond15=(bankProductAmount3*presentDays* interestSubventionDTO.getFromBankInterest())/36500;
            interestSecond25=(bankProductAmount3*presentDays* interestSubventionDTO.getToBankInterest())/36500;

            if (loanRecover==1){
                interestFirst3=(productAmount3*prevDays* interestSubventionDTO.getFromInterest())/36500;
                interestSecond3=(productAmount3*presentDays* interestSubventionDTO.getToInterest())/36500;
            }

            //Above 3 lakh interest
            if (above3Product>0){
                interestAbove3lakh= (above3Product*day3*interestSubventionDTO.getInterestAbove3Lakh())/36500;
            }

            saveIntoIsCalculateTemp(serialNo,issFileParser,recoveryDate,recoveryAmount,issFileParser.getRecoveryAmountInterest(),midBalanceAmt,bankDate,prevDays,presentDays,day3,productAmount3,bankProductAmount3,above3Product,interestFirst3,interestSecond3,interestFirst15,interestFirst25,interestSecond15,interestSecond25,interestAbove3lakh,above3lakhAmt,upto50000,loanRecover);


        }
        //Check for Fourth recovery
        if (issFileParser.getFourthRecoveryDate() != null && issFileParser.getFourthRecoveryAmountPrinciple() != null) {
            recoveryDate = LocalDate.parse(issFileParser.getFourthRecoveryDate(), inputFormatter);
            recoveryAmount = Long.parseLong(issFileParser.getFourthRecoveryAmountPrinciple());

            Long day4 = 0L;
            Long productAmount4 = 0L;
            Long bankProductAmount4 = 0L;
            serialNo=4;


            //check recovery date is Less than or eqaul to 365 days
            int compareResult = recoveryDate.compareTo(bankDate);
            if (compareResult > 0) {
                day4 = ChronoUnit.DAYS.between(lastDate, bankDate);
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
                day4 = ChronoUnit.DAYS.between(lastDate, recoveryDate);
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

            //Interest calculation
            //FirstReport-center bank - after 31/03
            interestFirst15=(bankProductAmount4*prevDays* interestSubventionDTO.getFromBankInterest())/36500;
            interestFirst25=(bankProductAmount4*prevDays* interestSubventionDTO.getToBankInterest())/36500;

            //SecondReport-center bank - after 01/04
            interestSecond15=(bankProductAmount4*presentDays* interestSubventionDTO.getFromBankInterest())/36500;
            interestSecond25=(bankProductAmount4*presentDays* interestSubventionDTO.getToBankInterest())/36500;

            if (loanRecover==1){
                interestFirst3=(productAmount4*prevDays* interestSubventionDTO.getFromInterest())/36500;
                interestSecond3=(productAmount4*presentDays* interestSubventionDTO.getToInterest())/36500;
            }

            //Above 3 lakh interest
            if (above3Product>0){
                interestAbove3lakh= (above3Product*day4*interestSubventionDTO.getInterestAbove3Lakh())/36500;
            }

            saveIntoIsCalculateTemp(serialNo,issFileParser,recoveryDate,recoveryAmount,issFileParser.getRecoveryAmountInterest(),midBalanceAmt,bankDate,prevDays,presentDays,day4,productAmount4,bankProductAmount4,above3Product,interestFirst3,interestSecond3,interestFirst15,interestFirst25,interestSecond15,interestSecond25,interestAbove3lakh,above3lakhAmt,upto50000,loanRecover);

        }

//After checking all the recovery, check if balance loan amount
        //If the loan amount is not totally paid or partially paid
        Long lastDays=0L;
        Long lastProdAmount=0L;
        Long memLastProdAmount=0L;


        if (debitAmount > 0) {
            serialNo=5;
            lastProdAmount=debitAmount; // PACS PROD Amount

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

                else {
                    //If recovery date is less than 31/03
                    prevDays= ChronoUnit.DAYS.between(lastDate,recoveryDate);
                    presentDays=0L;
                }
            }
            else {
                lastDays = ChronoUnit.DAYS.between(recoveryDate, bankDate);

                //Procedure to calculate days upto 31/03 as prev days & after 31/03 to bankdate or maturity date as present days
                int compareVasuliDate = recoveryDate.compareTo(reportDate);
                if (compareVasuliDate > 0) {
                    //If recovery date is greater than 31/03
                    prevDays = ChronoUnit.DAYS.between(lastDate, reportDate);
                    presentDays = ChronoUnit.DAYS.between(ChronoUnit.DAYS.addTo(reportDate, 1), bankDate);
                } else {
                    //If recovery date is less than 31/03
                    prevDays = ChronoUnit.DAYS.between(lastDate, recoveryDate);
                    presentDays = 0L;
                }

            }

            //Interest calculation
            //FirstReport-center bank - after 31/03
            interestFirst15=(lastProdAmount*prevDays* interestSubventionDTO.getFromBankInterest())/36500;
            interestFirst25=(lastProdAmount*prevDays* interestSubventionDTO.getToBankInterest())/36500;

            //SecondReport-center bank - after 01/04
            interestSecond15=(lastProdAmount*presentDays* interestSubventionDTO.getFromBankInterest())/36500;
            interestSecond25=(lastProdAmount*presentDays* interestSubventionDTO.getToBankInterest())/36500;

            if (loanRecover==1){
                //memLastProdAmount=lastProdAmount;
                interestFirst3=(memLastProdAmount*prevDays* interestSubventionDTO.getFromInterest())/36500;
                interestSecond3=(memLastProdAmount*presentDays* interestSubventionDTO.getToInterest())/36500;
            }
            else {
                interestFirst3=0.00;
                interestSecond3=0.00;
                memLastProdAmount=0L;

            }


        }
        else {
            lastProdAmount=-debitAmount;
        }

        if (diffAmount>0)
        {
            above3Product=diffAmount;
        }

        //Above 3 lakh interest
        if (above3Product>0){
            interestAbove3lakh= (above3Product*lastDays*interestSubventionDTO.getInterestAbove3Lakh())/36500;
        }

        //save previous loan amount
        previousDebitAmount = Long.valueOf(issFileParser.getDisburseAmount());

        //condition for calculating interest amount above 3 lakh for bank purpose
        saveIntoIsCalculateTemp(serialNo,issFileParser,recoveryDate,recoveryAmount,issFileParser.getRecoveryAmountInterest(),midBalanceAmt,bankDate,prevDays,presentDays,lastDays,memLastProdAmount,lastProdAmount,above3Product,interestFirst3,interestSecond3,interestFirst15,interestFirst25,interestSecond15,interestSecond25,interestAbove3lakh,above3lakhAmt,upto50000,loanRecover);


    }

public void saveIntoIsCalculateTemp(Integer serialNo,IssFileParser issFileParser,LocalDate recoveryDate,Long recoveryAmount,String recoveryInterest,Long midBalanceAmt,LocalDate bankDate,Long prevDays,Long presentDays,Long day,Long productAmount,Long bankProductAmount,Long above3Product,Double interestFirst3,Double interestSecond3,Double interestFirst15,Double interestFirst25,Double interestSecond15,Double interestSecond25,Double interestAbove3lakh, Long above3lakhAmt,Integer upto50000,Integer loanRecover){

     IsCalculateTemp isCalculateTemp =new IsCalculateTemp();
    isCalculateTemp.setSerialNo(serialNo);
    isCalculateTemp.setFinancialYear(issFileParser.getFinancialYear());
    isCalculateTemp.setIssFileParserId(issFileParser.getId());
    isCalculateTemp.setBranchCode(issFileParser.getBranchCode());
    isCalculateTemp.setLoanAccountNumberKcc(issFileParser.getLoanAccountNumberkcc());
    isCalculateTemp.setFarmerName(issFileParser.getFarmerName());
    isCalculateTemp.setGender(issFileParser.getGender());
    isCalculateTemp.setSocialCategory(issFileParser.getSocialCategory());
    isCalculateTemp.setAadharNumber(issFileParser.getAadharNumber());
    isCalculateTemp.setMobileNo(issFileParser.getMobileNo());
    isCalculateTemp.setFarmerType(issFileParser.getFarmerType());
    isCalculateTemp.setAccountNumber(issFileParser.getAccountNumber());
    isCalculateTemp.setLoanSanctionDate(issFileParser.getLoanSactionDate());
    isCalculateTemp.setLoanSanctionAmount(issFileParser.getLoanSanctionAmount());
    isCalculateTemp.setDisbursementDate(issFileParser.getDisbursementDate());
    isCalculateTemp.setDisburseAmount(issFileParser.getDisburseAmount());
    isCalculateTemp.setMaturityLoanDate(issFileParser.getMaturityLoanDate());
    isCalculateTemp.setBankDate(bankDate.format(inputFormatter));
    isCalculateTemp.setCropName(issFileParser.getCropName());
    isCalculateTemp.setRecoveryAmount(String.valueOf(recoveryAmount));
    isCalculateTemp.setRecoveryInterest(recoveryInterest);
    isCalculateTemp.setRecoveryDate(recoveryDate.format(inputFormatter));
    isCalculateTemp.setBalanceAmount(String.valueOf(midBalanceAmt));
    isCalculateTemp.setPrevDays(prevDays);
    isCalculateTemp.setPresDays(presentDays);
    isCalculateTemp.setActualDays(day);
    isCalculateTemp.setProductAmount(String.valueOf(productAmount));
    isCalculateTemp.setProductBank(String.valueOf(bankProductAmount));
    isCalculateTemp.setProductAbh3Lakh(String.valueOf(above3Product));
    isCalculateTemp.setInterestFirst15(interestFirst15);
    isCalculateTemp.setInterestFirst25(interestFirst25);
    isCalculateTemp.setInterestSecond25(interestSecond25);
    isCalculateTemp.setInterestSecond15(interestFirst25);
    isCalculateTemp.setInterestStateFirst3(interestFirst3);
    isCalculateTemp.setInterestStateSecond3(interestSecond3);
    isCalculateTemp.setInterestAbove3Lakh(interestAbove3lakh);
    isCalculateTemp.setAbh3LakhAmt(above3lakhAmt);
    isCalculateTemp.setIsRecover(loanRecover);
    isCalculateTemp.upto50000(upto50000);


  isCalculateTempRepository.save(isCalculateTemp);










}





    public List<IssFileParser> CalculateInterestForPunjabRao() {
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

