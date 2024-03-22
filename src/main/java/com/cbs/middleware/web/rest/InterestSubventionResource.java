package com.cbs.middleware.web.rest;

import com.cbs.middleware.domain.*;
import com.cbs.middleware.repository.*;
import com.cbs.middleware.service.dto.InterestSubventionDTO;
import com.cbs.middleware.service.dto.TalukaApplicationDTO;
import com.cbs.middleware.service.dto.SummaryReportUpdateDTO;
import com.cbs.middleware.web.rest.utility.InterestSubventionCalculator;
import org.apache.commons.math3.stat.descriptive.summary.Sum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class InterestSubventionResource {

    private final IssFileParserRepository issFileParserRepository;

    @Autowired
    IsCalculateTempRepository isCalculateTempRepository;

    @Autowired
    CenterReportMarchRepository centerReportMarchRepository;
    @Autowired
    CenterReportJuneRepository centerReportJuneRepository;

    @Autowired
    StateReportPanjabraoRepository stateReportPanjabraoRepository;

    @Autowired
    SummaryReportRepository summaryReportRepository;

    public InterestSubventionResource(IssFileParserRepository issFileParserRepository) {

        this.issFileParserRepository = issFileParserRepository;
    }

    //Process records
    @PostMapping("/interestSubvention")
    public void ProcessRecordsToCalInterestSubvention (@RequestBody InterestSubventionDTO interestSubventionDTO){

        //delete record from ISCalculateTemp
        deleteFromIsCalculateTemp(interestSubventionDTO);

       //get distinct aadhar numbers from parser by pacscode & financial year
        List<String> distinctAadhars = issFileParserRepository.findDistinctFarmerByPacsNumberAndFinancialYear(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());

        //loop through aadhar numbers
        for (String aadharNumber : distinctAadhars) {
            //get the records from parser order by disbursementDate ASC
            List<IssFileParser> issFileParsers = issFileParserRepository.findByAadharNumber(aadharNumber, interestSubventionDTO.getFinancialYear());
            System.out.println("IssFileParsers:" + issFileParsers);

            //Calculate interest for report 1 & 2
            List<IssFileParser> issSubvention = new InterestSubventionCalculator(issFileParsers,interestSubventionDTO,isCalculateTempRepository).CalculateInterestForCenterState();

        }

        //Insert data into Center March & Center June & State Panjabrao
        saveDataIntoCenterMarchJuneReport(interestSubventionDTO);

    }
 public void saveDataIntoCenterMarchJuneReport(InterestSubventionDTO interestSubventionDTO){

        //Insert data to center march table
        if (interestSubventionDTO.getReportType()==1 && interestSubventionDTO.getReportCondition()==1){

            //delete record from center report march table
            deleteFromCenterReportMarch(interestSubventionDTO);

            //select records from IsCalculateTemp
            List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.SelectFromIsCalculateTemp(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());

            //save records into center report march table
            List<CenterReportMarch> centerReportMarchList =convertToCenterReportMarch(isCalculateTempList);
            centerReportMarchRepository.saveAll(centerReportMarchList);

            CenterReportMarch centerReportMarch= new CenterReportMarch();
            if (!centerReportMarchList.isEmpty()){
                 centerReportMarch= centerReportMarchList.get(0);
            }

            GenerateSummaryReportCenterMarch(interestSubventionDTO,centerReportMarch);

        }
        //Insert data to center june table
        else if (interestSubventionDTO.getReportType()==2 && interestSubventionDTO.getReportCondition()==1){

             //delete record from center report june table
            deleteFromCenterReportJune(interestSubventionDTO);

            //select records from IsCalculateTemp
            List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.SelectFromIsCalculateTemp(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());

            //save records into center report june table
            List<CenterReportJune> centerReportJuneList =convertToCenterReportJune(isCalculateTempList);
            centerReportJuneRepository.saveAll(centerReportJuneList);

            CenterReportJune centerReportJune= new CenterReportJune();
            if (!centerReportJuneList.isEmpty()){
                centerReportJune= centerReportJuneList.get(0);
            }

            GenerateSummaryReportCenterJune(interestSubventionDTO,centerReportJune);

            }
        //Insert data to state panjabrao table
        else if (interestSubventionDTO.getReportType()==3 && interestSubventionDTO.getReportCondition()==2){

            //delete record from state report panjabrao table
            deleteFromStateReportPanjabrao(interestSubventionDTO);

            //select records from IsCalculateTemp
            List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.SelectFromIsCalculateTemp(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());

            //save records into state report panjabrao table
            List<StateReportPanjabro> stateReportPanjabroList =convertToStateReportPanjabrao(isCalculateTempList);
            stateReportPanjabraoRepository.saveAll(stateReportPanjabroList);

            StateReportPanjabro stateReportPanjabrao= new StateReportPanjabro();
            if (!stateReportPanjabroList.isEmpty()){
                stateReportPanjabrao= stateReportPanjabroList.get(0);
            }

            GenerateSummaryReportStatePanjabrao(interestSubventionDTO,stateReportPanjabrao);

            }

         deleteFromIsCalculateTemp(interestSubventionDTO);

    }
    ///.....CENTER REPORT MARCH......///
    public void GenerateSummaryReportCenterMarch(InterestSubventionDTO interestSubventionDTO, CenterReportMarch centerReportMarch){
        //Summary Report: March

        //1-delete from summary report table as per FY, PACS, Report type, report condition
        deleteFromSummaryReport(interestSubventionDTO);

        //2-Before calculation insert default records(1,2,3 - Upto 5000, >5000 to <=3 lakh, above 3 lakh)
        for (int j=1;j<=3;j++){
            for(int i=0;i<3;i++){
                addDefaultRecordsIntoSummaryReport(interestSubventionDTO,i+1,j,centerReportMarch.getBranchCode(), centerReportMarch.getTalukaCode());
            }
        }

        //3-Calculate total no of loan acc, loan amt, recovery amt as per above condition 2
        List<Object[]> totalLoanAmtandAccs = centerReportMarchRepository.FindTotalLoansAndAccounts(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());
        //convert Total loan amounts & accs object into SummaryReportUpdateDTO
        List<SummaryReportUpdateDTO> totalLoanAccDTOList= ConvertResultIntoSummaryReportDTO(totalLoanAmtandAccs,"totalLoan",false);
         if (!totalLoanAccDTOList.isEmpty()){
            UpdateTotalLoanAccsInSummaryReport(interestSubventionDTO,totalLoanAccDTOList);
         }


        //4-Find recovery accounts as per condition 2
        List<Object[]> totalRecoveryAccounts = centerReportMarchRepository.FindRecoveryAccounts(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());
        List<SummaryReportUpdateDTO> totalRecoveryACCList= ConvertResultIntoSummaryReportDTO(totalRecoveryAccounts,"recoveryAccount",false);
       if (!totalRecoveryACCList.isEmpty()){
              UpdateTotalRecoveryAccsInSummaryReport(interestSubventionDTO,totalRecoveryACCList);
       }

        //5-find social category interest amt & accounts as per condition 2
        List<Object[]> castWiseInterestAmtAndAccs = centerReportMarchRepository.FindCastWiseInterestAmtAndAccs(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());
        List<SummaryReportUpdateDTO> castWiseInterestAmtAndAccsList= ConvertResultIntoSummaryReportDTO(castWiseInterestAmtAndAccs,"cast",false);
        if (!castWiseInterestAmtAndAccsList.isEmpty()){
            UpdateCastWiseInterestAmtandAccsInSummaryReport(interestSubventionDTO,castWiseInterestAmtAndAccsList);
        }

       //6- find gender interest amt & accounts as per condition 2
        List<Object[]> womensInterestAmtAndAccs = centerReportMarchRepository.FindWomensInterestAmtAndAccs(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());
        List<SummaryReportUpdateDTO> womensInterestAmtAndAccsList= ConvertResultIntoSummaryReportDTO(womensInterestAmtAndAccs,"gender",false);
        if (!womensInterestAmtAndAccsList.isEmpty()){
            UpdateGenderWiseInterestAmtandAccsInSummaryReport(interestSubventionDTO,castWiseInterestAmtAndAccsList);
        }

        //7-find farmerType interest amt & accounts as per condition 2
        List<Object[]> farmerTypeWiseInterestAmtAndAccs = centerReportMarchRepository.FindFarmerTypeWiseInterestAmtAndAccs(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());
        List<SummaryReportUpdateDTO> farmerTypeWiseInterestAmtAndAccsList= ConvertResultIntoSummaryReportDTO(farmerTypeWiseInterestAmtAndAccs,"farmerType",false);
        if (!farmerTypeWiseInterestAmtAndAccsList.isEmpty()){
            UpdateFarmerTypeWiseInterestAmtandAccsInSummaryReport(interestSubventionDTO,farmerTypeWiseInterestAmtAndAccsList);
        }

        //Generate report for center march 3%
     GenerateSummaryReportCenterMarchState3(interestSubventionDTO);

    }

    ///.....CENTER REPORT MARCH STATE 3......///
    public void GenerateSummaryReportCenterMarchState3(InterestSubventionDTO interestSubventionDTO){
    //5-find social category interest amt & accounts as per condition 2
        List<Object[]> castWiseInterestAmtAndAccs = centerReportMarchRepository.FindCastWiseInterestAmtAndAccsOnlyRecover(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());
        List<SummaryReportUpdateDTO> castWiseInterestAmtAndAccsList= ConvertResultIntoSummaryReportDTO(castWiseInterestAmtAndAccs,"cast",true);
        if (!castWiseInterestAmtAndAccsList.isEmpty()){
            UpdateCastWiseInterestAmtandAccsInSummaryState3(interestSubventionDTO,castWiseInterestAmtAndAccsList);
        }

        //6- find gender interest amt & accounts as per condition 2
        List<Object[]> womensInterestAmtAndAccs = centerReportMarchRepository.FindWomensInterestAmtAndAccsOnlyRecover(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());
        List<SummaryReportUpdateDTO> womensInterestAmtAndAccsList= ConvertResultIntoSummaryReportDTO(womensInterestAmtAndAccs,"gender",true);
        if (!womensInterestAmtAndAccsList.isEmpty()){
            UpdateGenderWiseInterestAmtandAccsInSummaryState3(interestSubventionDTO,castWiseInterestAmtAndAccsList);
        }

        //7-find farmerType interest amt & accounts as per condition 2
        List<Object[]> farmerTypeWiseInterestAmtAndAccs = centerReportMarchRepository.FindFarmerTypeWiseInterestAmtAndAccsOnlyRecover(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());
        List<SummaryReportUpdateDTO> farmerTypeWiseInterestAmtAndAccsList= ConvertResultIntoSummaryReportDTO(farmerTypeWiseInterestAmtAndAccs,"farmerType",true);
        if (!farmerTypeWiseInterestAmtAndAccsList.isEmpty()){
            UpdateFarmerTypeWiseInterestAmtandAccsInSummaryState3(interestSubventionDTO,farmerTypeWiseInterestAmtAndAccsList);
        }

    }


    ///.....CENTER REPORT JUNE......///
    public void GenerateSummaryReportCenterJune(InterestSubventionDTO interestSubventionDTO, CenterReportJune centerReportJune){
        //Summary Report: June

        //1-delete from summary report table as per FY, PACS, Report type, report condition
        deleteFromSummaryReport(interestSubventionDTO);

        //2-Before calculation insert default records(1,2,3 - Upto 5000, >5000 to <=3 lakh, above 3 lakh)

        //2-Before calculation insert default records(1,2,3 - Upto 5000, >5000 to <=3 lakh, above 3 lakh)
        for (int j=1;j<=3;j++){
            for(int i=0;i<3;i++){
                addDefaultRecordsIntoSummaryReport(interestSubventionDTO,i+1,j,centerReportJune.getBranchCode(), centerReportJune.getTalukaCode());
            }
        }


        //3-Calculate total no of loan acc, loan amt, recovery amt as per above condition 2
        List<Object[]> totalLoanAmtandAccs = centerReportJuneRepository.FindTotalLoansAndAccounts(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());
        //convert Total loan amounts & accs object into SummaryReportUpdateDTO
        List<SummaryReportUpdateDTO> totalLoanAccDTOList= ConvertResultIntoSummaryReportDTO(totalLoanAmtandAccs,"totalLoan",false);
        if (!totalLoanAccDTOList.isEmpty()){
            UpdateTotalLoanAccsInSummaryReport(interestSubventionDTO,totalLoanAccDTOList);
        }

        //4-Find recovery accounts as per condition 2
        List<Object[]> totalRecoveryAccounts = centerReportJuneRepository.FindRecoveryAccounts(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());
        List<SummaryReportUpdateDTO> totalRecoveryACCList= ConvertResultIntoSummaryReportDTO(totalRecoveryAccounts,"recoveryAccount",false);
        if (!totalRecoveryACCList.isEmpty()){
            UpdateTotalRecoveryAccsInSummaryReport(interestSubventionDTO,totalRecoveryACCList);
        }

        //5-find social category interest amt & accounts as per condition 2
        List<Object[]> castWiseInterestAmtAndAccs = centerReportJuneRepository.FindCastWiseInterestAmtAndAccs(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());
        List<SummaryReportUpdateDTO> castWiseInterestAmtAndAccsList= ConvertResultIntoSummaryReportDTO(castWiseInterestAmtAndAccs,"cast",false);
        if (!castWiseInterestAmtAndAccsList.isEmpty()){
            UpdateCastWiseInterestAmtandAccsInSummaryReport(interestSubventionDTO,castWiseInterestAmtAndAccsList);
        }

        //6- find gender interest amt & accounts as per condition 2
        List<Object[]> womensInterestAmtAndAccs = centerReportJuneRepository.FindWomensInterestAmtAndAccs(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());
        List<SummaryReportUpdateDTO> womensInterestAmtAndAccsList= ConvertResultIntoSummaryReportDTO(womensInterestAmtAndAccs,"gender",false);
        if (!womensInterestAmtAndAccsList.isEmpty()){
            UpdateGenderWiseInterestAmtandAccsInSummaryReport(interestSubventionDTO,castWiseInterestAmtAndAccsList);
        }

        //7-find farmerType interest amt & accounts as per condition 2
        List<Object[]> farmerTypeWiseInterestAmtAndAccs = centerReportJuneRepository.FindFarmerTypeWiseInterestAmtAndAccs(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());
        List<SummaryReportUpdateDTO> farmerTypeWiseInterestAmtAndAccsList= ConvertResultIntoSummaryReportDTO(farmerTypeWiseInterestAmtAndAccs,"farmerType",false);
        if (!farmerTypeWiseInterestAmtAndAccsList.isEmpty()){
            UpdateFarmerTypeWiseInterestAmtandAccsInSummaryReport(interestSubventionDTO,farmerTypeWiseInterestAmtAndAccsList);
        }

        //Generate report for center june 3%
        GenerateSummaryReportCenterJuneState3(interestSubventionDTO);

    }

    ///.....CENTER REPORT JUNE STATE 3......///
    public void GenerateSummaryReportCenterJuneState3(InterestSubventionDTO interestSubventionDTO){
        //5-find social category interest amt & accounts as per condition 2
        List<Object[]> castWiseInterestAmtAndAccs = centerReportJuneRepository.FindCastWiseInterestAmtAndAccsOnlyRecover(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());
        List<SummaryReportUpdateDTO> castWiseInterestAmtAndAccsList= ConvertResultIntoSummaryReportDTO(castWiseInterestAmtAndAccs,"cast",true);
        if (!castWiseInterestAmtAndAccsList.isEmpty()){
            UpdateCastWiseInterestAmtandAccsInSummaryState3(interestSubventionDTO,castWiseInterestAmtAndAccsList);
        }

        //6- find gender interest amt & accounts as per condition 2
        List<Object[]> womensInterestAmtAndAccs = centerReportJuneRepository.FindWomensInterestAmtAndAccsOnlyRecover(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());
        List<SummaryReportUpdateDTO> womensInterestAmtAndAccsList= ConvertResultIntoSummaryReportDTO(womensInterestAmtAndAccs,"gender",true);
        if (!womensInterestAmtAndAccsList.isEmpty()){
            UpdateGenderWiseInterestAmtandAccsInSummaryState3(interestSubventionDTO,castWiseInterestAmtAndAccsList);
        }

        //7-find farmerType interest amt & accounts as per condition 2
        List<Object[]> farmerTypeWiseInterestAmtAndAccs = centerReportJuneRepository.FindFarmerTypeWiseInterestAmtAndAccsOnlyRecover(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());
        List<SummaryReportUpdateDTO> farmerTypeWiseInterestAmtAndAccsList= ConvertResultIntoSummaryReportDTO(farmerTypeWiseInterestAmtAndAccs,"farmerType",true);
        if (!farmerTypeWiseInterestAmtAndAccsList.isEmpty()){
            UpdateFarmerTypeWiseInterestAmtandAccsInSummaryState3(interestSubventionDTO,farmerTypeWiseInterestAmtAndAccsList);
        }

    }
///..........////

    ///.....STATE PUNJABRAO......///
    public void GenerateSummaryReportStatePanjabrao(InterestSubventionDTO interestSubventionDTO, StateReportPanjabro stateReportPanjabro){
        //Summary Report: Panjabrao

        //1-delete from summary report table as per FY, PACS, Report type, report condition
        deleteFromSummaryReport(interestSubventionDTO);

        //2-Before calculation insert default records(1,2,3 - Upto 5000, >5000 to <=3 lakh, above 3 lakh)
        for (int j=1;j<=1;j++){
            for(int i=0;i<3;i++){
                addDefaultRecordsIntoSummaryReport(interestSubventionDTO,i+1,4,stateReportPanjabro.getBranchCode(), stateReportPanjabro.getTalukaCode());
            }
        }

        //3-Calculate total no of loan acc, loan amt, recovery amt as per above condition 2
        List<Object[]> totalLoanAmtandAccs = stateReportPanjabraoRepository.FindTotalLoansAndAccounts(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());
        //convert Total loan amounts & accs object into SummaryReportUpdateDTO
        List<SummaryReportUpdateDTO> totalLoanAccDTOList= ConvertResultIntoSummaryReportDTO(totalLoanAmtandAccs,"totalLoan",true);
        if (!totalLoanAccDTOList.isEmpty()){
            UpdateTotalLoanAccsInSummaryReport(interestSubventionDTO,totalLoanAccDTOList);
        }

        //4-Find recovery accounts as per condition 2
        List<Object[]> totalRecoveryAccounts = stateReportPanjabraoRepository.FindRecoveryAccounts(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());
        List<SummaryReportUpdateDTO> totalRecoveryACCList= ConvertResultIntoSummaryReportDTO(totalRecoveryAccounts,"recoveryAccount",true);
        if (!totalRecoveryACCList.isEmpty()){
            UpdateTotalRecoveryAccsInSummaryReport(interestSubventionDTO,totalRecoveryACCList);
        }

        //5-find social category interest amt & accounts as per condition 2
        List<Object[]> castWiseInterestAmtAndAccs = stateReportPanjabraoRepository.FindCastWiseInterestAmtAndAccs(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());
        List<SummaryReportUpdateDTO> castWiseInterestAmtAndAccsList= ConvertResultIntoSummaryReportDTO(castWiseInterestAmtAndAccs,"cast",true);
        if (!castWiseInterestAmtAndAccsList.isEmpty()){
            UpdateCastWiseInterestAmtandAccsInSummaryState3(interestSubventionDTO,castWiseInterestAmtAndAccsList);
        }

        //6- find gender interest amt & accounts as per condition 2
        List<Object[]> womensInterestAmtAndAccs = stateReportPanjabraoRepository.FindWomensInterestAmtAndAccs(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());
        List<SummaryReportUpdateDTO> womensInterestAmtAndAccsList= ConvertResultIntoSummaryReportDTO(womensInterestAmtAndAccs,"gender",true);
        if (!womensInterestAmtAndAccsList.isEmpty()){
            UpdateGenderWiseInterestAmtandAccsInSummaryState3(interestSubventionDTO,castWiseInterestAmtAndAccsList);
        }

        //7-find farmerType interest amt & accounts as per condition 2
        List<Object[]> farmerTypeWiseInterestAmtAndAccs = stateReportPanjabraoRepository.FindFarmerTypeWiseInterestAmtAndAccs(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());
        List<SummaryReportUpdateDTO> farmerTypeWiseInterestAmtAndAccsList= ConvertResultIntoSummaryReportDTO(farmerTypeWiseInterestAmtAndAccs,"farmerType",true);
        if (!farmerTypeWiseInterestAmtAndAccsList.isEmpty()){
            UpdateFarmerTypeWiseInterestAmtandAccsInSummaryState3(interestSubventionDTO,farmerTypeWiseInterestAmtAndAccsList);
        }

    }



    ///Add defaults records into Summary Report
    public void addDefaultRecordsIntoSummaryReport(InterestSubventionDTO interestSubventionDTO,Integer upto5000,Integer interestType,String branchCode,String talukaCode){
        SummaryReport summaryReport= new SummaryReport();
        summaryReport.setFinancialYear(interestSubventionDTO.getFinancialYear());
        summaryReport.setPacsNumber(interestSubventionDTO.getPacsNumber());
        summaryReport.setBranchCode(branchCode);
        summaryReport.setTalukaCode(talukaCode);
        summaryReport.setReportType(interestSubventionDTO.getReportType());
        summaryReport.setReportCondition(interestSubventionDTO.getReportCondition());
        summaryReport.setInterestType(interestType);
        summaryReport.setUpto50000(upto5000);
        summaryReportRepository.save(summaryReport);

    }


    //Convert Result into SummaryReport DTO
    public List<SummaryReportUpdateDTO> ConvertResultIntoSummaryReportDTO(List<Object[]> totalLoanAccs,String convertDatafor,Boolean Interest3) {

        List<SummaryReportUpdateDTO> summaryReportUpdateDTOList = new ArrayList<>();
        for (Object[] branch : totalLoanAccs) {

            switch (convertDatafor) {
                case "totalLoan": {
                    Integer aadharCount = ((BigInteger) branch[0]).intValue();
                    Long totalLoanAmount = ((BigDecimal) branch[1]).longValue();
                    Long totalRecoveryAmount = ((BigDecimal) branch[2]).longValue();
                    Integer upto50000 = (Integer) branch[3];
                    SummaryReportUpdateDTO summaryReportUpdateDTO = new SummaryReportUpdateDTO(aadharCount, totalLoanAmount, totalRecoveryAmount, upto50000);
                    summaryReportUpdateDTOList.add(summaryReportUpdateDTO);
                    break;
                }
                case "recoveryAccount": {
                    Integer aadharCount = ((BigInteger) branch[0]).intValue();
                    Integer upto50000 = (Integer) branch[1];
                    SummaryReportUpdateDTO summaryReportUpdateDTO = new SummaryReportUpdateDTO(aadharCount, upto50000);
                    summaryReportUpdateDTOList.add(summaryReportUpdateDTO);
                    break;
                }
                case "cast": {

                    if (Interest3){
                        Integer aadharCount = ((BigInteger) branch[0]).intValue();
                        Double interest3 = ((Double) branch[1]);
                        Integer upto50000 = (Integer) branch[2];
                        String socialCategory = (String) branch[3];
                        SummaryReportUpdateDTO summaryReportUpdateDTO = new SummaryReportUpdateDTO(aadharCount, null, null, interest3,  socialCategory,upto50000);
                        summaryReportUpdateDTOList.add(summaryReportUpdateDTO);
                    }
                    else {
                        Integer aadharCount = ((BigInteger) branch[0]).intValue();
                        Double interest15 = ((Double) branch[1]);
                        Double interest25 = ((Double) branch[2]);
                        Integer upto50000 = (Integer) branch[3];
                        String socialCategory = (String) branch[4];
                        SummaryReportUpdateDTO summaryReportUpdateDTO = new SummaryReportUpdateDTO(aadharCount, interest15, interest25, null,  socialCategory,upto50000);
                        summaryReportUpdateDTOList.add(summaryReportUpdateDTO);
                    }

                    break;
                }
                case "gender": {

                    if (Interest3){
                        Integer aadharCount = ((BigInteger) branch[0]).intValue();
                        Double interest3 = ((Double) branch[1]);
                        Integer upto50000 = (Integer) branch[2];
                        String gender = (String) branch[3];
                        SummaryReportUpdateDTO summaryReportUpdateDTO = new SummaryReportUpdateDTO(aadharCount, null, null, interest3, upto50000,gender);
                        summaryReportUpdateDTOList.add(summaryReportUpdateDTO);
                    }
                    else {
                        Integer aadharCount = ((BigInteger) branch[0]).intValue();
                        Double interest15 = ((Double) branch[1]);
                        Double interest25 = ((Double) branch[2]);
                        Integer upto50000 = (Integer) branch[3];
                        String gender = (String) branch[4];
                        SummaryReportUpdateDTO summaryReportUpdateDTO = new SummaryReportUpdateDTO(aadharCount, interest15, interest25, null,  upto50000,gender);
                        summaryReportUpdateDTOList.add(summaryReportUpdateDTO);
                    }

                    break;
                }
                case "farmerType": {
                    if (Interest3){
                        Integer aadharCount = ((BigInteger) branch[0]).intValue();
                        Double interest3 = ((Double) branch[1]);
                        Integer upto50000 = (Integer) branch[2];
                        SummaryReportUpdateDTO summaryReportUpdateDTO = new SummaryReportUpdateDTO(aadharCount, null, null, interest3, upto50000);
                        summaryReportUpdateDTOList.add(summaryReportUpdateDTO);
                    }
                    else {
                        Integer aadharCount = ((BigInteger) branch[0]).intValue();
                        Double interest15 = ((Double) branch[1]);
                        Double interest25 = ((Double) branch[2]);
                         Integer upto50000 = (Integer) branch[3];
                        SummaryReportUpdateDTO summaryReportUpdateDTO = new SummaryReportUpdateDTO(aadharCount, interest15, interest25, null, upto50000);
                        summaryReportUpdateDTOList.add(summaryReportUpdateDTO);
                    }

                    break;
                }
            }

        }

        return summaryReportUpdateDTOList;

    }

    //Update total loan amounts & accounts in summary report
    public void UpdateTotalLoanAccsInSummaryReport(InterestSubventionDTO interestSubventionDTO, List<SummaryReportUpdateDTO> totalLoanAmtandAccs){

        List<SummaryReport> summaryReportList = summaryReportRepository.SelectFromSummaryReport(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear(),interestSubventionDTO.getReportType(),interestSubventionDTO.getReportCondition());

        for (SummaryReport summaryReport:summaryReportList){
            for(SummaryReportUpdateDTO totalLoanAmtAcc:totalLoanAmtandAccs){

                if (summaryReport.getUpto50000().equals(totalLoanAmtAcc.getUpto50000())){
                    summaryReport.setTotalLoanAmount(totalLoanAmtAcc.getTotalDisburseAmount());
                    summaryReport.setNoOfLoanAccounts(totalLoanAmtAcc.getDistinctAadharCount());
                    summaryReport.setTotalRecoveryAmount(totalLoanAmtAcc.getTotalRecoveryAmount());
                    summaryReportRepository.save(summaryReport);
                }

            }
        }

    }

    //Update total recovery accounts in summary report
    public void UpdateTotalRecoveryAccsInSummaryReport(InterestSubventionDTO interestSubventionDTO, List<SummaryReportUpdateDTO> totalRecoveryAccs){

        List<SummaryReport> summaryReportList = summaryReportRepository.SelectFromSummaryReport(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear(),interestSubventionDTO.getReportType(),interestSubventionDTO.getReportCondition());

        for (SummaryReport summaryReport:summaryReportList){
            for(SummaryReportUpdateDTO recoveryAccs:totalRecoveryAccs){
                if (summaryReport.getUpto50000().equals(recoveryAccs.getUpto50000())){
                    summaryReport.setNoOfRecoveryAccounts(recoveryAccs.getDistinctAadharCount());
                    summaryReportRepository.save(summaryReport);
                }

            }
        }

    }


    //Update cast wise total interest & accounts in summary report:1.5 & 2.5
    public void UpdateCastWiseInterestAmtandAccsInSummaryReport(InterestSubventionDTO interestSubventionDTO, List<SummaryReportUpdateDTO> castWiseInterestAmtAndAccs){

        List<SummaryReport> summaryReportList = summaryReportRepository.SelectFromSummaryReport(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear(),interestSubventionDTO.getReportType(),interestSubventionDTO.getReportCondition());

        for (SummaryReport summaryReport:summaryReportList){
            for(SummaryReportUpdateDTO castWise:castWiseInterestAmtAndAccs) {

                //Interest rate 1.5%
                if (summaryReport.getUpto50000().equals(castWise.getUpto50000()) && summaryReport.getInterestType() == 1) {

                    if (castWise.getSocialCategory().equals("GEN") || castWise.getSocialCategory().equals("OBC")) {
                        summaryReport.setNoOfGeneralAccounts(castWise.getDistinctAadharCount());
                        summaryReport.setGeneralAmount(castWise.getTotalInterest15());

                    }
                    if (castWise.getSocialCategory().equals("SC")) {

                        summaryReport.setNoOfSCAccounts(castWise.getDistinctAadharCount());
                        summaryReport.setScAmount(castWise.getTotalInterest15());

                    }
                    if (castWise.getSocialCategory().equals("ST")) {

                        summaryReport.setNoOfSTAccounts(castWise.getDistinctAadharCount());
                        summaryReport.setStAmount(castWise.getTotalInterest15());

                    }
                    summaryReportRepository.save(summaryReport);
                }
                //Interest rate 2.5%
                if (summaryReport.getUpto50000().equals(castWise.getUpto50000()) && summaryReport.getInterestType() == 2) {
                    if (castWise.getSocialCategory().equals("GEN") || castWise.getSocialCategory().equals("OBC")) {
                        summaryReport.setNoOfGeneralAccounts(castWise.getDistinctAadharCount());
                        summaryReport.setGeneralAmount(castWise.getTotalInterest25());
                    }
                    if (castWise.getSocialCategory().equals("SC")) {
                        summaryReport.setNoOfSCAccounts(castWise.getDistinctAadharCount());
                        summaryReport.setScAmount(castWise.getTotalInterest25());
                    }
                    if (castWise.getSocialCategory().equals("ST")) {
                        summaryReport.setNoOfSTAccounts(castWise.getDistinctAadharCount());
                        summaryReport.setStAmount(castWise.getTotalInterest25());
                    }
                    summaryReportRepository.save(summaryReport);
                }
            }

        }
    }

    //Update cast wise total interest & accounts in summary report:3
    public void UpdateCastWiseInterestAmtandAccsInSummaryState3(InterestSubventionDTO interestSubventionDTO, List<SummaryReportUpdateDTO> castWiseInterestAmtAndAccs){

        List<SummaryReport> summaryReportList = new ArrayList<>();
         summaryReportList = summaryReportRepository.SelectFromSummaryReport(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear(),interestSubventionDTO.getReportType(),interestSubventionDTO.getReportCondition());

        for (SummaryReport summaryReport:summaryReportList){
            for(SummaryReportUpdateDTO castWise:castWiseInterestAmtAndAccs){

                //Interest rate CenterState & Punjabrao 3%
                if ((summaryReport.getUpto50000().equals(castWise.getUpto50000()) && summaryReport.getInterestType()==3) || (summaryReport.getUpto50000().equals(castWise.getUpto50000()) && summaryReport.getInterestType()==4)){
                    if (castWise.getSocialCategory().equals("GEN") || castWise.getSocialCategory().equals("OBC")){
                        summaryReport.setNoOfGeneralAccounts(castWise.getDistinctAadharCount());
                        summaryReport.setGeneralAmount(castWise.getTotalInterest3());
                    }
                    if (castWise.getSocialCategory().equals("SC")){
                        summaryReport.setNoOfSCAccounts(castWise.getDistinctAadharCount());
                        summaryReport.setScAmount(castWise.getTotalInterest3());
                    }
                    if (castWise.getSocialCategory().equals("ST")){
                        summaryReport.setNoOfSTAccounts(castWise.getDistinctAadharCount());
                        summaryReport.setStAmount(castWise.getTotalInterest3());

                    }
                    summaryReportRepository.save(summaryReport);
                }

            }
        }
    }


    //Update gender wise total interest & accounts in summary report:1.5 & 2.5
    public void UpdateGenderWiseInterestAmtandAccsInSummaryReport(InterestSubventionDTO interestSubventionDTO, List<SummaryReportUpdateDTO> totalLoanAmtandAccs){

        List<SummaryReport> summaryReportList = summaryReportRepository.SelectFromSummaryReport(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear(),interestSubventionDTO.getReportType(),interestSubventionDTO.getReportCondition());
        Integer reportType= interestSubventionDTO.getReportType();
        Integer reportCondition = interestSubventionDTO.getReportCondition();
        for (SummaryReport summaryReport:summaryReportList){
            for(SummaryReportUpdateDTO genderWise:totalLoanAmtandAccs){

                //Interest rate 1.5%
                if (summaryReport.getUpto50000().equals(genderWise.getUpto50000()) && summaryReport.getInterestType()==1){

                    summaryReport.setNoOfWomenAccounts(genderWise.getDistinctAadharCount());
                    summaryReport.setWomenAmount(genderWise.getTotalInterest15());
                    summaryReportRepository.save(summaryReport);
                }
                //Interest rate 2.5%
                if (summaryReport.getUpto50000().equals(genderWise.getUpto50000()) && summaryReport.getInterestType()==2) {

                    summaryReport.setNoOfWomenAccounts(genderWise.getDistinctAadharCount());
                    summaryReport.setWomenAmount(genderWise.getTotalInterest25());
                    summaryReportRepository.save(summaryReport);

                }


            }
        }

    }

    //Update gender wise total interest & accounts in summary report:3
    public void UpdateGenderWiseInterestAmtandAccsInSummaryState3(InterestSubventionDTO interestSubventionDTO, List<SummaryReportUpdateDTO> totalLoanAmtandAccs){
        List<SummaryReport> summaryReportList = new ArrayList<>();
        summaryReportList  = summaryReportRepository.SelectFromSummaryReport(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear(),interestSubventionDTO.getReportType(),interestSubventionDTO.getReportCondition());
        Integer reportType= interestSubventionDTO.getReportType();
        Integer reportCondition = interestSubventionDTO.getReportCondition();
        for (SummaryReport summaryReport:summaryReportList){
            for(SummaryReportUpdateDTO genderWise:totalLoanAmtandAccs){

                //Interest rate CenterState & Punjabrao 3%
                if (summaryReport.getUpto50000().equals(genderWise.getUpto50000()) && summaryReport.getInterestType()==3 || summaryReport.getUpto50000().equals(genderWise.getUpto50000()) && summaryReport.getInterestType()==4){
                    summaryReport.setNoOfWomenAccounts(genderWise.getDistinctAadharCount());
                    summaryReport.setWomenAmount(genderWise.getTotalInterest3());
                    summaryReportRepository.save(summaryReport);

                }

            }
        }

    }


    //Update farmer type wise total interest & accounts in summary report: 1.5 & 2.5
    public void UpdateFarmerTypeWiseInterestAmtandAccsInSummaryReport(InterestSubventionDTO interestSubventionDTO, List<SummaryReportUpdateDTO> farmerTypeWiseInterestAmtAndAccsList){

        List<SummaryReport> summaryReportList = summaryReportRepository.SelectFromSummaryReport(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear(),interestSubventionDTO.getReportType(),interestSubventionDTO.getReportCondition());

        for (SummaryReport summaryReport:summaryReportList){
            for(SummaryReportUpdateDTO farmerTypeWise:farmerTypeWiseInterestAmtAndAccsList){

                //Interest rate 1.5%
                if (summaryReport.getUpto50000().equals(farmerTypeWise.getUpto50000()) && summaryReport.getInterestType()==1){
                    summaryReport.setNoOfSmallMediumAccounts(farmerTypeWise.getDistinctAadharCount());
                    summaryReport.setSmallMediumAmount(farmerTypeWise.getTotalInterest15());
                    summaryReportRepository.save(summaryReport);
                }
                //Interest rate 2.5%
                if (summaryReport.getUpto50000().equals(farmerTypeWise.getUpto50000()) && summaryReport.getInterestType()==2){
                    summaryReport.setNoOfSmallMediumAccounts(farmerTypeWise.getDistinctAadharCount());
                    summaryReport.setSmallMediumAmount(farmerTypeWise.getTotalInterest25());
                    summaryReportRepository.save(summaryReport);
                }


            }
        }

    }

    //Update farmer type wise total interest & accounts in summary report: 3
    public void UpdateFarmerTypeWiseInterestAmtandAccsInSummaryState3(InterestSubventionDTO interestSubventionDTO, List<SummaryReportUpdateDTO> farmerTypeWiseInterestAmtAndAccsList){

        List<SummaryReport> summaryReportList = new ArrayList<>();
     summaryReportList = summaryReportRepository.SelectFromSummaryReport(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear(),interestSubventionDTO.getReportType(),interestSubventionDTO.getReportCondition());

        for (SummaryReport summaryReport:summaryReportList){
            for(SummaryReportUpdateDTO farmerTypeWise:farmerTypeWiseInterestAmtAndAccsList){

                //Interest rate CenterState & Punjabrao 3%
                if (summaryReport.getUpto50000().equals(farmerTypeWise.getUpto50000()) && summaryReport.getInterestType()==3 || summaryReport.getUpto50000().equals(farmerTypeWise.getUpto50000()) && summaryReport.getInterestType()==4){
                    summaryReport.setNoOfSmallMediumAccounts(farmerTypeWise.getDistinctAadharCount());
                    summaryReport.setSmallMediumAmount(farmerTypeWise.getTotalInterest3());
                    summaryReportRepository.save(summaryReport);
                }

            }
        }

    }

    private List<CenterReportMarch> convertToCenterReportMarch(List<IsCalculateTemp> isCalculateTempList) {
        List<CenterReportMarch> centerReportMarchList = new ArrayList<>();

        for (IsCalculateTemp isCalculateTemp : isCalculateTempList) {
            CenterReportMarch  centerReportMarch = new CenterReportMarch();
            // Copy properties from ISCalculateTemp to CenterReportMarch
            BeanUtils.copyProperties(isCalculateTemp, centerReportMarch);
            centerReportMarchList.add(centerReportMarch);
        }

        return centerReportMarchList;
    }

    private List<CenterReportJune> convertToCenterReportJune(List<IsCalculateTemp> isCalculateTempList) {
        List<CenterReportJune> centerReportJuneList = new ArrayList<>();

        for (IsCalculateTemp isCalculateTemp : isCalculateTempList) {
            CenterReportJune  centerReportJune = new CenterReportJune();
            // Copy properties from ISCalculateTemp to CenterReportJune
            BeanUtils.copyProperties(isCalculateTemp, centerReportJune);
            centerReportJuneList.add(centerReportJune);
        }

        return centerReportJuneList;
    }

    private List<StateReportPanjabro> convertToStateReportPanjabrao(List<IsCalculateTemp> isCalculateTempList) {
        List<StateReportPanjabro> stateReportPanjabroList = new ArrayList<>();

        for (IsCalculateTemp isCalculateTemp : isCalculateTempList) {
            StateReportPanjabro  stateReportPanjabro = new StateReportPanjabro();
            // Copy properties from ISCalculateTemp to CenterReportJune
            BeanUtils.copyProperties(isCalculateTemp, stateReportPanjabro);
            stateReportPanjabroList.add(stateReportPanjabro);
        }

        return stateReportPanjabroList;
    }


    //Delete records from ISCalculateTemp before record insertion
    public void deleteFromIsCalculateTemp(InterestSubventionDTO interestSubventionDTO){
        List<IsCalculateTemp> isCalculateTempList = isCalculateTempRepository.SelectFromIsCalculateTemp(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());

        if (isCalculateTempList!=null){
            isCalculateTempRepository.deleteAll(isCalculateTempList);
        }

    }

    //Delete records from CenterReportMarch
    public void deleteFromCenterReportMarch(InterestSubventionDTO interestSubventionDTO){
        List<CenterReportMarch> centerReportMarchList = centerReportMarchRepository.FindFromCenterReportMarch(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());

        if (!centerReportMarchList.isEmpty()){
            centerReportMarchRepository.deleteAll(centerReportMarchList);
        }

    }

    //Delete records from CenterReportJune
    public void deleteFromCenterReportJune(InterestSubventionDTO interestSubventionDTO){
        List<CenterReportJune> centerReportJuneList = centerReportJuneRepository.SelectFromCenterReportJune(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());

        if (!centerReportJuneList.isEmpty()){
            centerReportJuneRepository.deleteAll(centerReportJuneList);
        }

    }

    //Delete records from StateReportPanjabrao
    public void deleteFromStateReportPanjabrao(InterestSubventionDTO interestSubventionDTO){
        List<StateReportPanjabro> stateReportPanjabroList = stateReportPanjabraoRepository.SelectFromStateReportPanjabro(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear());

        if (!stateReportPanjabroList.isEmpty()){
            stateReportPanjabraoRepository.deleteAll(stateReportPanjabroList);
        }

    }


//delete from summary report
    public void deleteFromSummaryReport(InterestSubventionDTO interestSubventionDTO){
        List<SummaryReport> summaryReportList = summaryReportRepository.SelectFromSummaryReport(interestSubventionDTO.getPacsNumber(), interestSubventionDTO.getFinancialYear(),interestSubventionDTO.getReportType(),interestSubventionDTO.getReportCondition());

        if (summaryReportList!=null){
            summaryReportRepository.deleteAll(summaryReportList);
        }

    }

}
