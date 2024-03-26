package com.cbs.middleware.web.rest.utility;

import com.cbs.middleware.domain.IsCalculateTemp;
import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.repository.AccountHolderMasterRepository;
import com.cbs.middleware.repository.IsCalculateTempRepository;
import com.cbs.middleware.repository.IssFileParserRepository;
import com.cbs.middleware.service.dto.InterestSubventionDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
   public Long recoveryAmount = 0L;
    public  LocalDate recoveryDate;
    public  Long diffAmount=0L;
    public  Long above3Product=0L;
    public  Integer serialNo=0;
    public Long midBalanceAmt=0L;
    public String latsCreditDate;
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

    public Double interestStatePunjabrao3=0.00;  // state punjabrao 3 %
    public Double interestAbove3lakh=0.00; //Above 3 lakh interest
    public  LocalDate reportDate;
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Boolean insertLoanDetails=false;
    private static final DecimalFormat decfor = new DecimalFormat("0.00");


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

        //Check if loan is recover or not
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

        for (IssFileParser issFileParser:issFileParsers) {
            insertLoanDetails=false;
            Long loanDisbursementAmount = Long.valueOf(issFileParser.getDisburseAmount());
            LocalDate loanDisbursementDate = LocalDate.parse(issFileParser.getDisbursementDate(), inputFormatter);

            lastDate = loanDisbursementDate;
            recoveryDate = loanDisbursementDate;
            debitAmount = loanDisbursementAmount;
            midBalanceAmt = loanDisbursementAmount;
            diffAmount = 0L;
            serialNo = 0;

            loanMaturityDate = LocalDate.parse(issFileParser.getMaturityLoanDate());

            //Calculate the total disbursement of loan
            totalDebitAmount = totalDebitAmount + loanDisbursementAmount;

            //If total disbursement of loan is greater than 3 lakh, the find out difference amount & substract diff amount from total loan amount
            if (totalDebitAmount > interestCalAmount) {
                diffAmount = totalDebitAmount - interestCalAmount;
                debitAmount = debitAmount - diffAmount;
            }

            //bankDate calculated upto 365 days
            bankDate = ChronoUnit.DAYS.addTo(loanDisbursementDate, 364);
            System.out.println("Bank date:" + bankDate);

            reportDate = LocalDate.parse(interestSubventionDTO.getLastCreditDate());

            //Calculate Product Amount
            if (interestSubventionDTO.getReportCondition() == 1) {
                //center bank
                calculateProductAmountsCenter(issFileParser);
            } else {
                //state punjabrao
                calculateProductAmountsState(issFileParser);
            }

        }
        return null;
    }
    public void calculateProductAmountsCenter(IssFileParser issFileParser) {
        Long interestCalAmount = 300000L;

        //check for first recovery
        if (issFileParser.getRecoveryDate() != null && issFileParser.getRecoveryAmountPrinciple() != null) {
            recoveryDate = LocalDate.parse(issFileParser.getRecoveryDate(), inputFormatter);
            recoveryAmount = Long.parseLong(issFileParser.getRecoveryAmountPrinciple());
            Long day1 = 0L;
            Long productAmount1 = 0L;
            Long bankProductAmount1 = 0L;
            serialNo = 1;

            //check recovery date is Less than or equal to bankDate(365 days)
            int compareResult = recoveryDate.compareTo(bankDate);

            //If recovery date is greater than bank date find out days between loan date & bank date
            if (compareResult > 0) {
                day1 = ChronoUnit.DAYS.between(lastDate, bankDate);

                //Procedure to calculate days upto 31/03 as prev days & after 31/03 to bankdate or maturity date as present days
                int compareVasuliDate = recoveryDate.compareTo(reportDate);
                if (compareVasuliDate > 0) {
                    //If recovery date is greater than 31/03
                    prevDays = ChronoUnit.DAYS.between(lastDate, reportDate);
                    presentDays = ChronoUnit.DAYS.between(ChronoUnit.DAYS.addTo(reportDate, 1), bankDate);
                } else {
                    //If recovery date is less than 31/03
                    prevDays = ChronoUnit.DAYS.between(lastDate, recoveryDate);
                    presentDays = ChronoUnit.DAYS.between(ChronoUnit.DAYS.addTo(reportDate, 1), bankDate);

                }
                //If recovery date is less than bank date find out days between loan date & recovery date
            } else {
                day1 = ChronoUnit.DAYS.between(lastDate, recoveryDate);
                int compareVasuliDate = recoveryDate.compareTo(reportDate);
                if (compareVasuliDate > 0) {
                    prevDays = ChronoUnit.DAYS.between(lastDate, reportDate);
                    presentDays = ChronoUnit.DAYS.between(ChronoUnit.DAYS.addTo(reportDate, 1), recoveryDate);
                } else {
                    prevDays = ChronoUnit.DAYS.between(lastDate, recoveryDate);
                    presentDays = 0L;
                }
            }

            //For first cal productAmount is equal to loan amount
            productAmount1 = debitAmount;
            bankProductAmount1 = debitAmount;

            //Calculate balance loan amount substracting recovery amount. This amount is treated as product amount for next recovery
            debitAmount = debitAmount - recoveryAmount;
            midBalanceAmt = midBalanceAmt - recoveryAmount;

            //This date is calculated as per reducing balance formula,last date is defined for next days calculation
            lastDate = recoveryDate;

            //
            if (debitAmount < 0) {
                above3Product = diffAmount;
                bankProductAmount1 = diffAmount + debitAmount;
                diffAmount = diffAmount + debitAmount;
                debitAmount = 0L;
            }

            if (presentDays < 0) {
                presentDays = 0L;

            }
            if (prevDays < 0) {
                prevDays = 0L;
            }
            //Interest calculation
            //FirstReport-center bank - after 31/03
            interestFirst15 = (bankProductAmount1 * prevDays * interestSubventionDTO.getFromBankInterest()) / 36500;
            interestFirst25 = (bankProductAmount1 * prevDays * interestSubventionDTO.getToBankInterest()) / 36500;

            //SecondReport-center bank - after 01/04
            interestSecond15 = (bankProductAmount1 * presentDays * interestSubventionDTO.getFromBankInterest()) / 36500;
            interestSecond25 = (bankProductAmount1 * presentDays * interestSubventionDTO.getToBankInterest()) / 36500;

            if (loanRecover == 1) {
                interestFirst3 = (productAmount1 * prevDays * interestSubventionDTO.getFromInterest()) / 36500;
                interestSecond3 = (productAmount1 * presentDays * interestSubventionDTO.getToInterest()) / 36500;
            }

            //Above 3 lakh interest
            if (above3Product > 0) {
                interestAbove3lakh = (above3Product * day1 * interestSubventionDTO.getInterestAbove3Lakh()) / 36500;
            }


            //Save record into isCalculateTemp
            saveIntoIsCalculateTemp(serialNo, issFileParser, recoveryDate, recoveryAmount, issFileParser.getRecoveryAmountInterest(), midBalanceAmt, bankDate, prevDays, presentDays, day1, productAmount1, bankProductAmount1, above3Product, interestFirst3, interestSecond3, interestFirst15, interestFirst25, interestSecond15, interestSecond25, interestStatePunjabrao3,interestAbove3lakh, above3lakhAmt, upto50000, loanRecover);
            above3lakhAmt=0L;
        }
        //Above remarks specified in first recovery are the same for the second , third & fourth recovery
        //Check for Second recovery
        if (issFileParser.getSecondRecoveryDate() != null && issFileParser.getSecondRecoveryAmountPrinciple() != null) {
            recoveryDate = LocalDate.parse(issFileParser.getSecondRecoveryDate(), inputFormatter);
            recoveryAmount = Long.parseLong(issFileParser.getSecondRecoveryAmountPrinciple());

            Long day2 = 0L;
            Long productAmount2 = 0L;
            Long bankProductAmount2 = 0L;
            serialNo = 2;

            //check recovery date is Less than or eqaul to 365 days
            int compareResult = recoveryDate.compareTo(bankDate);
            if (compareResult > 0) {
                day2 = ChronoUnit.DAYS.between(lastDate, bankDate);
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
            } else {
                day2 = ChronoUnit.DAYS.between(lastDate, recoveryDate);
                int compareVasuliDate = recoveryDate.compareTo(reportDate);
                if (compareVasuliDate > 0) {
                    prevDays = ChronoUnit.DAYS.between(lastDate, reportDate);
                    presentDays = ChronoUnit.DAYS.between(ChronoUnit.DAYS.addTo(reportDate, 1), recoveryDate);
                } else {
                    prevDays = ChronoUnit.DAYS.between(lastDate, recoveryDate);
                    presentDays = 0L;
                }
            }

            productAmount2 = debitAmount;
            bankProductAmount2 = debitAmount;

            debitAmount = debitAmount - recoveryAmount;
            midBalanceAmt = midBalanceAmt - recoveryAmount;
            lastDate = recoveryDate;

            if (debitAmount < 0) {
                bankProductAmount2 = -debitAmount;
                above3Product = diffAmount;
                diffAmount = diffAmount + debitAmount;
                debitAmount = 0L;
            }
            if (presentDays < 0) {
                presentDays = 0L;

            }
            if (prevDays < 0) {
                prevDays = 0L;
            }

            //Interest calculation
            //FirstReport-center bank - after 31/03
            interestFirst15 = (bankProductAmount2 * prevDays * interestSubventionDTO.getFromBankInterest()) / 36500;
            interestFirst25 = (bankProductAmount2 * prevDays * interestSubventionDTO.getToBankInterest()) / 36500;

            //SecondReport-center bank - after 01/04
            interestSecond15 = (bankProductAmount2 * presentDays * interestSubventionDTO.getFromBankInterest()) / 36500;
            interestSecond25 = (bankProductAmount2 * presentDays * interestSubventionDTO.getToBankInterest()) / 36500;

            if (loanRecover == 1) {
                interestFirst3 = (productAmount2 * prevDays * interestSubventionDTO.getFromInterest()) / 36500;
                interestSecond3 = (productAmount2 * presentDays * interestSubventionDTO.getToInterest()) / 36500;
            }

            //Above 3 lakh interest
            if (above3Product > 0) {
                interestAbove3lakh = (above3Product * day2 * interestSubventionDTO.getInterestAbove3Lakh()) / 36500;
            }

            saveIntoIsCalculateTemp(serialNo, issFileParser, recoveryDate, recoveryAmount, issFileParser.getRecoveryAmountInterest(), midBalanceAmt, bankDate, prevDays, presentDays, day2, productAmount2, bankProductAmount2, above3Product, interestFirst3, interestSecond3, interestFirst15, interestFirst25, interestSecond15, interestSecond25, interestStatePunjabrao3,interestAbove3lakh, above3lakhAmt, upto50000, loanRecover);


        }

        //Check for Third recovery
        if (issFileParser.getThirdRecoveryDate() != null && issFileParser.getThirdRecoveryAmountPrinciple() != null) {
            recoveryDate = LocalDate.parse(issFileParser.getThirdRecoveryDate(), inputFormatter);
            recoveryAmount = Long.parseLong(issFileParser.getThirdRecoveryAmountPrinciple());

            Long day3 = 0L;
            Long productAmount3 = 0L;
            Long bankProductAmount3 = 0L;
            serialNo = 3;

            //check recovery date is Less than or eqaul to 365 days
            int compareResult = recoveryDate.compareTo(bankDate);
            if (compareResult > 0) {
                day3 = ChronoUnit.DAYS.between(lastDate, bankDate);
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
            } else {
                day3 = ChronoUnit.DAYS.between(lastDate, recoveryDate);
                int compareVasuliDate = recoveryDate.compareTo(reportDate);
                if (compareVasuliDate > 0) {
                    prevDays = ChronoUnit.DAYS.between(lastDate, reportDate);
                    presentDays = ChronoUnit.DAYS.between(ChronoUnit.DAYS.addTo(reportDate, 1), recoveryDate);
                } else {
                    prevDays = ChronoUnit.DAYS.between(lastDate, recoveryDate);
                    presentDays = 0L;
                }
            }

            productAmount3 = debitAmount;
            bankProductAmount3 = debitAmount;
            debitAmount = debitAmount - recoveryAmount;

            midBalanceAmt = midBalanceAmt - recoveryAmount;
            lastDate = recoveryDate;
            if (debitAmount < 0) {

                bankProductAmount3 = -debitAmount;
                above3Product = diffAmount;
                diffAmount = diffAmount + debitAmount;
                debitAmount = 0L;
            }
            if (presentDays < 0) {
                presentDays = 0L;

            }
            if (prevDays < 0) {
                prevDays = 0L;
            }
            //Interest calculation
            //FirstReport-center bank - after 31/03
            interestFirst15 = (bankProductAmount3 * prevDays * interestSubventionDTO.getFromBankInterest()) / 36500;
            interestFirst25 = (bankProductAmount3 * prevDays * interestSubventionDTO.getToBankInterest()) / 36500;

            //SecondReport-center bank - after 01/04
            interestSecond15 = (bankProductAmount3 * presentDays * interestSubventionDTO.getFromBankInterest()) / 36500;
            interestSecond25 = (bankProductAmount3 * presentDays * interestSubventionDTO.getToBankInterest()) / 36500;

            if (loanRecover == 1) {
                interestFirst3 = (productAmount3 * prevDays * interestSubventionDTO.getFromInterest()) / 36500;
                interestSecond3 = (productAmount3 * presentDays * interestSubventionDTO.getToInterest()) / 36500;
            }

            //Above 3 lakh interest
            if (above3Product > 0) {
                interestAbove3lakh = (above3Product * day3 * interestSubventionDTO.getInterestAbove3Lakh()) / 36500;
            }

            saveIntoIsCalculateTemp(serialNo, issFileParser, recoveryDate, recoveryAmount, issFileParser.getRecoveryAmountInterest(), midBalanceAmt, bankDate, prevDays, presentDays, day3, productAmount3, bankProductAmount3, above3Product, interestFirst3, interestSecond3, interestFirst15, interestFirst25, interestSecond15, interestSecond25, interestStatePunjabrao3,interestAbove3lakh, above3lakhAmt, upto50000, loanRecover);


        }
        //Check for Fourth recovery
        if (issFileParser.getFourthRecoveryDate() != null && issFileParser.getFourthRecoveryAmountPrinciple() != null) {
            recoveryDate = LocalDate.parse(issFileParser.getFourthRecoveryDate(), inputFormatter);
            recoveryAmount = Long.parseLong(issFileParser.getFourthRecoveryAmountPrinciple());

            Long day4 = 0L;
            Long productAmount4 = 0L;
            Long bankProductAmount4 = 0L;
            serialNo = 4;


            //check recovery date is Less than or eqaul to 365 days
            int compareResult = recoveryDate.compareTo(bankDate);
            if (compareResult > 0) {
                day4 = ChronoUnit.DAYS.between(lastDate, bankDate);
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
            } else {
                day4 = ChronoUnit.DAYS.between(lastDate, recoveryDate);
                int compareVasuliDate = recoveryDate.compareTo(reportDate);
                if (compareVasuliDate > 0) {
                    prevDays = ChronoUnit.DAYS.between(lastDate, reportDate);
                    presentDays = ChronoUnit.DAYS.between(ChronoUnit.DAYS.addTo(reportDate, 1), recoveryDate);
                } else {
                    prevDays = ChronoUnit.DAYS.between(lastDate, recoveryDate);
                    presentDays = 0L;
                }
            }

            productAmount4 = debitAmount;
            bankProductAmount4 = debitAmount;

            debitAmount = debitAmount - recoveryAmount;

            midBalanceAmt = midBalanceAmt - recoveryAmount;
            lastDate = recoveryDate;

            if (debitAmount < 0) {
                bankProductAmount4 = -debitAmount;
                above3Product = diffAmount;
                diffAmount = diffAmount + debitAmount;
                debitAmount = 0L;
            }

            if (presentDays < 0) {
                presentDays = 0L;

            }
            if (prevDays < 0) {
                prevDays = 0L;
            }

            //Interest calculation
            //FirstReport-center bank - after 31/03
            interestFirst15 = (bankProductAmount4 * prevDays * interestSubventionDTO.getFromBankInterest()) / 36500;
            interestFirst25 = (bankProductAmount4 * prevDays * interestSubventionDTO.getToBankInterest()) / 36500;

            //SecondReport-center bank - after 01/04
            interestSecond15 = (bankProductAmount4 * presentDays * interestSubventionDTO.getFromBankInterest()) / 36500;
            interestSecond25 = (bankProductAmount4 * presentDays * interestSubventionDTO.getToBankInterest()) / 36500;

            if (loanRecover == 1) {
                interestFirst3 = (productAmount4 * prevDays * interestSubventionDTO.getFromInterest()) / 36500;
                interestSecond3 = (productAmount4 * presentDays * interestSubventionDTO.getToInterest()) / 36500;
            }

            //Above 3 lakh interest
            if (above3Product > 0) {
                interestAbove3lakh = (above3Product * day4 * interestSubventionDTO.getInterestAbove3Lakh()) / 36500;
            }


            saveIntoIsCalculateTemp(serialNo, issFileParser, recoveryDate, recoveryAmount, issFileParser.getRecoveryAmountInterest(), midBalanceAmt, bankDate, prevDays, presentDays, day4, productAmount4, bankProductAmount4, above3Product, interestFirst3, interestSecond3, interestFirst15, interestFirst25, interestSecond15, interestSecond25,interestStatePunjabrao3, interestAbove3lakh, above3lakhAmt, upto50000, loanRecover);

        }

//After checking all the recovery, check if balance loan amount
        //If the loan amount is not totally paid or partially paid
        Long lastDays = 0L;
        Long lastProdAmount = 0L;
        Long memLastProdAmount = 0L;

        if (diffAmount > 0) {
            above3Product = diffAmount;
        } else {
            above3Product = 0L;
        }

        serialNo = 5;
        recoveryAmount=0L;

        if (debitAmount > 0) {

            lastProdAmount = debitAmount; // PACS PROD Amount

            int result = bankDate.compareTo(loanMaturityDate);

            //bank date is greater than maturity date
            if (result > 0) {
                lastDays = ChronoUnit.DAYS.between(lastDate, loanMaturityDate);
                //Procedure to calculate days upto 31/03 as prev days & after 31/03 to bankdate or maturity date as present days
                int compareVasuliDate = lastDate.compareTo(reportDate);
                if (compareVasuliDate > 0) {
                    //If recovery date is greater than 31/03
                    prevDays = 0L;
                    presentDays = ChronoUnit.DAYS.between(ChronoUnit.DAYS.addTo(reportDate, 1), loanMaturityDate) + 1;

                } else {
                    //If recovery date is less than 31/03
                    prevDays = ChronoUnit.DAYS.between(lastDate, reportDate) + 1;
                    presentDays = ChronoUnit.DAYS.between(ChronoUnit.DAYS.addTo(reportDate, 1), loanMaturityDate) + 1;
                }
            }

            //bank date is less than maturity date
            else {

                lastDays = ChronoUnit.DAYS.between(lastDate, bankDate);

                if (lastDays < 0) {
                    lastDays = 0L;
                }
                //Procedure to calculate days upto 31/03 as prev days & after 31/03 to bankdate or maturity date as present days
                int compareVasuliDate = lastDate.compareTo(reportDate);
                if (compareVasuliDate > 0) {
                    //If recovery date is greater than 31/03
                    prevDays = 0L;
                    presentDays = ChronoUnit.DAYS.between(ChronoUnit.DAYS.addTo(reportDate, 1), bankDate) + 1;
                } else {
                    //If recovery date is less than 31/03
                    prevDays = ChronoUnit.DAYS.between(lastDate, reportDate) + 1;
                    presentDays = ChronoUnit.DAYS.between(ChronoUnit.DAYS.addTo(reportDate, 1), bankDate) + 1;
                }

            }

            if (presentDays < 0) {
                presentDays = 0L;

            }
            if (prevDays < 0) {
                prevDays = 0L;
            }

            //Interest calculation
            //FirstReport-center bank - after 31/03
            interestFirst15 = (lastProdAmount * prevDays * interestSubventionDTO.getFromBankInterest()) / 36500;
            interestFirst25 = (lastProdAmount * prevDays * interestSubventionDTO.getToBankInterest()) / 36500;

            //SecondReport-center bank - after 01/04
            interestSecond15 = (lastProdAmount * presentDays * interestSubventionDTO.getFromBankInterest()) / 36500;
            interestSecond25 = (lastProdAmount * presentDays * interestSubventionDTO.getToBankInterest()) / 36500;

            if (loanRecover == 1) {
                //memLastProdAmount=lastProdAmount;
                interestFirst3 = (memLastProdAmount * prevDays * interestSubventionDTO.getFromInterest()) / 36500;
                interestSecond3 = (memLastProdAmount * presentDays * interestSubventionDTO.getToInterest()) / 36500;
            } else {
                interestFirst3 = 0.00;
                interestSecond3 = 0.00;
                memLastProdAmount = 0L;

            }

            //Above 3 lakh interest
            if (above3Product > 0) {
                interestAbove3lakh = (above3Product * lastDays * interestSubventionDTO.getInterestAbove3Lakh()) / 36500;
            }
            saveIntoIsCalculateTemp(serialNo, issFileParser, recoveryDate, recoveryAmount, issFileParser.getRecoveryAmountInterest(), midBalanceAmt, bankDate, prevDays, presentDays, lastDays, memLastProdAmount, lastProdAmount, above3Product, interestFirst3, interestSecond3, interestFirst15, interestFirst25, interestSecond15, interestSecond25, interestStatePunjabrao3, interestAbove3lakh, above3lakhAmt, upto50000, loanRecover);


        } else {
            debitAmount = 0L;
            lastProdAmount = -debitAmount;
        }

        //save previous loan amount
        previousDebitAmount = Long.valueOf(issFileParser.getDisburseAmount());
    }


    public void  calculateProductAmountsState(IssFileParser issFileParser) {
        Long interestCalAmount = 300000L;

        //check for first recovery
        if (issFileParser.getRecoveryDate() != null && issFileParser.getRecoveryAmountPrinciple() != null) {
            recoveryDate = LocalDate.parse(issFileParser.getRecoveryDate(), inputFormatter);
            recoveryAmount = Long.parseLong(issFileParser.getRecoveryAmountPrinciple());
            Long day1 = 0L;
            Long productAmount1 = 0L;
            Long bankProductAmount1 = 0L;
            serialNo = 1;

            //check recovery date is Less than or equal to bankDate(365 days)
            int compareResult = recoveryDate.compareTo(loanMaturityDate);

            //If recovery date is greater than maturity date find out days between loan date & bank date
            if (compareResult > 0) {

                day1=0L;

                //If recovery date is less than maturity date find out days between loan date & recovery date
            } else {
                day1 = ChronoUnit.DAYS.between(lastDate, recoveryDate);

            }

            //For first cal productAmount is equal to loan amount
            productAmount1 = debitAmount;

            //Calculate balance loan amount substracting recovery amount. This amount is treated as product amount for next recovery
            debitAmount = debitAmount - recoveryAmount;
            midBalanceAmt = midBalanceAmt - recoveryAmount;

            //This date is calculated as per reducing balance formula,last date is defined for next days calculation
            lastDate = recoveryDate;


            if (debitAmount < 0) {
                diffAmount = diffAmount + debitAmount;
                debitAmount = 0L;
            }

            //Interest calculation

            if (loanRecover == 1) {
                interestStatePunjabrao3 =  productAmount1 * day1 * interestSubventionDTO.getFromInterest() / 36500;

            }

            //Save record into isCalculateTemp
            saveIntoIsCalculateTemp(serialNo, issFileParser, recoveryDate, recoveryAmount, issFileParser.getRecoveryAmountInterest(), midBalanceAmt, bankDate, prevDays, presentDays, day1, productAmount1, bankProductAmount1, above3Product, interestFirst3, interestSecond3, interestFirst15, interestFirst25, interestSecond15, interestSecond25, interestStatePunjabrao3,interestAbove3lakh, above3lakhAmt, upto50000, loanRecover);
           above3lakhAmt=0L;

        }

        //Above remarks specified in first recovery are the same for the second , third & fourth recovery
        //Check for Second recovery
        if (issFileParser.getSecondRecoveryDate() != null && issFileParser.getSecondRecoveryAmountPrinciple() != null) {
            recoveryDate = LocalDate.parse(issFileParser.getSecondRecoveryDate(), inputFormatter);
            recoveryAmount = Long.parseLong(issFileParser.getSecondRecoveryAmountPrinciple());

            Long day2 = 0L;
            Long productAmount2 = 0L;
            Long bankProductAmount2 = 0L;
            serialNo = 2;

            //check recovery date is Less than or equal to Maturity date
            int compareResult = recoveryDate.compareTo(loanMaturityDate);

            //If recovery date is greater than maturity date mark days as zero
            if (compareResult > 0) {

                day2=0L;

                //If recovery date is less than maturity date find out days between loan date & recovery date
            } else {
                day2 = ChronoUnit.DAYS.between(lastDate, recoveryDate);

            }
            productAmount2 = debitAmount;


            debitAmount = debitAmount - recoveryAmount;
            midBalanceAmt = midBalanceAmt - recoveryAmount;
            lastDate = recoveryDate;

            if (debitAmount < 0) {
                diffAmount = diffAmount + debitAmount;
                debitAmount = 0L;
            }

            //Interest calculation

            if (loanRecover == 1) {
                interestStatePunjabrao3 =  productAmount2 * day2 * interestSubventionDTO.getFromInterest() / 36500;

            }

            saveIntoIsCalculateTemp(serialNo, issFileParser, recoveryDate, recoveryAmount, issFileParser.getRecoveryAmountInterest(), midBalanceAmt, bankDate, prevDays, presentDays, day2, productAmount2, bankProductAmount2, above3Product, interestFirst3, interestSecond3, interestFirst15, interestFirst25, interestSecond15, interestSecond25, interestStatePunjabrao3,interestAbove3lakh, above3lakhAmt, upto50000, loanRecover);


        }

        //Check for Third recovery
        if (issFileParser.getThirdRecoveryDate() != null && issFileParser.getThirdRecoveryAmountPrinciple() != null) {
            recoveryDate = LocalDate.parse(issFileParser.getThirdRecoveryDate(), inputFormatter);
            recoveryAmount = Long.parseLong(issFileParser.getThirdRecoveryAmountPrinciple());

            Long day3 = 0L;
            Long productAmount3 = 0L;
            Long bankProductAmount3 = 0L;
            serialNo = 3;

            //check recovery date is Less than or equal to Maturity date
            int compareResult = recoveryDate.compareTo(loanMaturityDate);

            //If recovery date is greater than maturity date find out days between loan date & bank date
            if (compareResult > 0) {

                day3=0L;

                //If recovery date is less than maturity date find out days between loan date & recovery date
            } else {
                day3 = ChronoUnit.DAYS.between(lastDate, recoveryDate);

            }

            productAmount3 = debitAmount;

            debitAmount = debitAmount - recoveryAmount;

            midBalanceAmt = midBalanceAmt - recoveryAmount;
            lastDate = recoveryDate;
            if (debitAmount < 0) {
                diffAmount = diffAmount + debitAmount;
                debitAmount = 0L;
            }

            //Interest calculation

            if (loanRecover == 1) {
                interestStatePunjabrao3 =  productAmount3 * day3 * interestSubventionDTO.getFromInterest() / 36500;

            }


            saveIntoIsCalculateTemp(serialNo, issFileParser, recoveryDate, recoveryAmount, issFileParser.getRecoveryAmountInterest(), midBalanceAmt, bankDate, prevDays, presentDays, day3, productAmount3, bankProductAmount3, above3Product, interestFirst3, interestSecond3, interestFirst15, interestFirst25, interestSecond15, interestSecond25, interestStatePunjabrao3,interestAbove3lakh, above3lakhAmt, upto50000, loanRecover);


        }
        //Check for Fourth recovery
        if (issFileParser.getFourthRecoveryDate() != null && issFileParser.getFourthRecoveryAmountPrinciple() != null) {
            recoveryDate = LocalDate.parse(issFileParser.getFourthRecoveryDate(), inputFormatter);
            recoveryAmount = Long.parseLong(issFileParser.getFourthRecoveryAmountPrinciple());

            Long day4 = 0L;
            Long productAmount4 = 0L;
            Long bankProductAmount4 = 0L;
            serialNo = 4;


            //check recovery date is Less than or equal to Maturity date
            int compareResult = recoveryDate.compareTo(loanMaturityDate);

            //If recovery date is greater than maturity date find out days between loan date & bank date
            if (compareResult > 0) {

                day4=0L;

                //If recovery date is less than maturity date find out days between loan date & recovery date
            } else {
                day4 = ChronoUnit.DAYS.between(lastDate, recoveryDate);

            }

            productAmount4 = debitAmount;

            debitAmount = debitAmount - recoveryAmount;

            midBalanceAmt = midBalanceAmt - recoveryAmount;
            lastDate = recoveryDate;

            if (debitAmount < 0) {

                diffAmount = diffAmount + debitAmount;
                debitAmount = 0L;
            }

            if (loanRecover == 1) {

                interestStatePunjabrao3 =  productAmount4 * day4 * interestSubventionDTO.getFromInterest() / 36500;
            }

            saveIntoIsCalculateTemp(serialNo, issFileParser, recoveryDate, recoveryAmount, issFileParser.getRecoveryAmountInterest(), midBalanceAmt, bankDate, prevDays, presentDays, day4, productAmount4, bankProductAmount4, above3Product, interestFirst3, interestSecond3, interestFirst15, interestFirst25, interestSecond15, interestSecond25, interestStatePunjabrao3,interestAbove3lakh, above3lakhAmt, upto50000, loanRecover);

        }

//After checking all the recovery, check if balance loan amount
        //If the loan amount is not totally paid or partially paid
        Long lastDays = 0L;
        Long lastProdAmount = 0L;
        Long memLastProdAmount = 0L;

        serialNo = 5;
        recoveryAmount=0L;

        if (debitAmount > 0) {

            lastProdAmount = debitAmount; // PACS PROD Amount

            lastDays = ChronoUnit.DAYS.between(lastDate, loanMaturityDate);

            //Interest calculation
            if (loanRecover == 1) {
                //memLastProdAmount=lastProdAmount;
                interestStatePunjabrao3 =  memLastProdAmount * lastDays * interestSubventionDTO.getFromInterest() / 36500;


            } else {
                interestStatePunjabrao3 = 0.00;
                memLastProdAmount = 0L;

            }

            saveIntoIsCalculateTemp(serialNo, issFileParser, recoveryDate, recoveryAmount, issFileParser.getRecoveryAmountInterest(), midBalanceAmt, bankDate, prevDays, presentDays, lastDays, memLastProdAmount, lastProdAmount, above3Product, interestFirst3, interestSecond3, interestFirst15, interestFirst25, interestSecond15, interestSecond25, interestStatePunjabrao3,interestAbove3lakh, above3lakhAmt, upto50000, loanRecover);


        } else {
            debitAmount = 0L;
            lastProdAmount = -debitAmount;
        }

        //save previous loan amount
        previousDebitAmount = Long.valueOf(issFileParser.getDisburseAmount());
    }

    public Double InterestUptoTwoDecimalPlaces(Double interestVal){
        decfor.setRoundingMode(RoundingMode.DOWN);
        Double intTwoDecimalPlaces= Double.valueOf(decfor.format(interestVal));
        System.out.println("\nAfter Rounding: " + decfor.format(intTwoDecimalPlaces));
        return intTwoDecimalPlaces;
    }

public void saveIntoIsCalculateTemp(Integer serialNo,IssFileParser issFileParser,LocalDate recoveryDate,Long recoveryAmount,String recoveryInterest,Long midBalanceAmt,LocalDate bankDate,Long prevDays,Long presentDays,Long day,Long productAmount,Long bankProductAmount,Long above3Product,Double interestFirst3,Double interestSecond3,Double interestFirst15,Double interestFirst25,Double interestSecond15,Double interestSecond25,Double interestStatePunjabrao3,Double interestAbove3lakh, Long above3lakhAmt,Integer upto50000,Integer loanRecover){

        IsCalculateTemp isCalculateTemp =new IsCalculateTemp();
    isCalculateTemp.setSerialNo(serialNo);
    isCalculateTemp.setFinancialYear(issFileParser.getFinancialYear());
    isCalculateTemp.setIssFileParserId(issFileParser.getId());
    isCalculateTemp.setBranchCode(issFileParser.getBranchCode());
    isCalculateTemp.setTalukaCode(this.interestSubventionDTO.getTalukaCode());
    isCalculateTemp.setPacsNumber(issFileParser.getPacsNumber());
    isCalculateTemp.setLoanAccountNumberKcc(issFileParser.getLoanAccountNumberkcc());
    isCalculateTemp.setFarmerName(issFileParser.getFarmerName());
    isCalculateTemp.setGender(issFileParser.getGender());
    isCalculateTemp.setSocialCategory(issFileParser.getSocialCategory());
    isCalculateTemp.setAadharNumber(issFileParser.getAadharNumber());
    isCalculateTemp.setMobileNo(issFileParser.getMobileNo());
    isCalculateTemp.setFarmerType(issFileParser.getFarmerType());
    isCalculateTemp.setAccountNumber(issFileParser.getAccountNumber());

    if (!insertLoanDetails){
        isCalculateTemp.setLoanSanctionDate(LocalDate.parse(issFileParser.getLoanSactionDate(), inputFormatter));
        isCalculateTemp.setLoanSanctionAmount(Long.valueOf(issFileParser.getLoanSanctionAmount()));
        isCalculateTemp.setDisbursementDate(LocalDate.parse(issFileParser.getDisbursementDate(), inputFormatter));
        isCalculateTemp.setDisburseAmount(Long.valueOf(issFileParser.getDisburseAmount()));
    }

    isCalculateTemp.setMaturityLoanDate(LocalDate.parse(issFileParser.getMaturityLoanDate(),inputFormatter));
    isCalculateTemp.setBankDate(bankDate);
    isCalculateTemp.setCropName(issFileParser.getCropName());
    isCalculateTemp.setRecoveryAmount(recoveryAmount);
    isCalculateTemp.setRecoveryInterest(Double.valueOf(recoveryInterest));
    isCalculateTemp.setRecoveryDate(recoveryDate);
    isCalculateTemp.setBalanceAmount(midBalanceAmt);
    isCalculateTemp.setPrevDays(prevDays);
    isCalculateTemp.setPresDays(presentDays);
    isCalculateTemp.setActualDays(day);
    isCalculateTemp.setProductAmount(productAmount);
    isCalculateTemp.setProductBank(bankProductAmount);
    isCalculateTemp.setProductAbh3Lakh(above3Product);
    isCalculateTemp.setInterestFirst15(InterestUptoTwoDecimalPlaces( interestFirst15));
    isCalculateTemp.setInterestFirst25( InterestUptoTwoDecimalPlaces(interestFirst25));
    isCalculateTemp.setInterestSecond15( InterestUptoTwoDecimalPlaces(interestSecond15));
    isCalculateTemp.setInterestSecond25( InterestUptoTwoDecimalPlaces(interestSecond25));
    isCalculateTemp.setInterestStateFirst3( InterestUptoTwoDecimalPlaces(interestFirst3));
    isCalculateTemp.setInterestStateSecond3( InterestUptoTwoDecimalPlaces(interestSecond3));
    isCalculateTemp.setPanjabraoInt3( InterestUptoTwoDecimalPlaces(interestStatePunjabrao3));
    isCalculateTemp.setInterestAbove3Lakh( InterestUptoTwoDecimalPlaces(interestAbove3lakh));
    isCalculateTemp.setAbh3LakhAmt(above3lakhAmt);
    isCalculateTemp.setIsRecover(loanRecover);
    isCalculateTemp.setUpto50000(upto50000);


  isCalculateTempRepository.save(isCalculateTemp);

  insertLoanDetails=true;

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

