package com.cbs.middleware.web.rest;

import com.cbs.middleware.config.ApplicationProperties;
import com.cbs.middleware.config.Constants;
import com.cbs.middleware.domain.ApplicationLog;
import com.cbs.middleware.domain.BatchData;
import com.cbs.middleware.domain.IssFileParser;
import com.cbs.middleware.repository.ApplicationLogRepository;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTProtectedRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller for managing {@link com.cbs.middleware.domain.IssFileParser}.
 */
@RestController
@RequestMapping("/api")
public class DownloadErrorInExcelResource {

    private final Logger log = LoggerFactory.getLogger(DownloadErrorInExcelResource.class);

    private static final String ENTITY_NAME = "DownloadErrorInExcelResource";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    ApplicationLogRepository applicationLogRepository;

    /**
     * Customize code
     *
     * @return
     *
     * @throws Exception
     */


    @GetMapping("/testExcel")
    public ResponseEntity<byte[]> testExcel() {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("data");

            Row row = sheet.createRow(0);
            int colNum = 0;
            Cell cell = row.createCell(colNum++);
            cell.setCellValue("Financial Year 1");

            CellStyle cellStyle = workbook.createCellStyle();
            if(cellStyle == null) {
                cellStyle = cell.getSheet().getWorkbook().createCellStyle();
            }
            cellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(cellStyle);



                System.out.println(cell.getAddress());
            System.out.println("Row = "+cell.getAddress().getRow());
            System.out.println("Column "+ cell.getAddress().getColumn());
            String pwd = "AB";
            byte[] decode = Hex.decodeHex(pwd.toCharArray());
            CTProtectedRange ctProtectedRange = sheet.getCTWorksheet().addNewProtectedRanges().addNewProtectedRange();
            ctProtectedRange.setName("protect");
            ctProtectedRange.setSqref(List.of("F1:J16"));


            System.out.println(ctProtectedRange); //get this string:<xml-fragment name="protect" password="AB"/>
            sheet.protectSheet("data");

            // Set up the HTTP headers for the response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);


            headers.setContentDispositionFormData("filename", "test-" + getUniqueName() + ".xlsx");

            List<String> contentDispositionList = new ArrayList<>();
            contentDispositionList.add("Content-Disposition");

            headers.setAccessControlExposeHeaders(contentDispositionList);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            byte[] excelContent = outputStream.toByteArray();
            outputStream.close();
            return new ResponseEntity<>(excelContent, headers, 200);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }
    }



    @GetMapping("/error-excel/{issPortalFileId}/error-type/{errorType}")
    public ResponseEntity<byte[]> errorExcel(@PathVariable Long issPortalFileId, @PathVariable String errorType) {
        List<ApplicationLog> ApplicationLogList = new ArrayList<>();
        if (Constants.downloadKccError.equalsIgnoreCase(errorType)) {
            ApplicationLogList =
                applicationLogRepository.findAllByIssPortalIdAndErrorTypeAndStatus(issPortalFileId, Constants.kccError, Constants.ERROR);
        } else {
            ApplicationLogList =
                applicationLogRepository.findAllByIssPortalIdAndErrorTypeAndStatus(
                    issPortalFileId,
                    Constants.validationError,
                    Constants.ERROR
                );
        }



        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Iss Portal Data for Pacs Member");
//        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
//            XSSFSheet sheet = workbook.createSheet("Iss Portal Data for Pacs Member");
            int rowNum = 0;
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            row.createCell(colNum++).setCellValue("Financial Year 1");
            row.createCell(colNum++).setCellValue("Bank Name 2");
            row.createCell(colNum++).setCellValue("Bank Code 3");
            row.createCell(colNum++).setCellValue("Branch Name 4");
            row.createCell(colNum++).setCellValue("Branch  Code 5");
            row.createCell(colNum++).setCellValue("Scheme Wise Branch Code 6");
            row.createCell(colNum++).setCellValue("IFSC 7");
            row.createCell(colNum++).setCellValue("Loan Account Number kcc 8");
            row.createCell(colNum++).setCellValue("Farmer Name 9");
            row.createCell(colNum++).setCellValue("Gender 10");
            row.createCell(colNum++).setCellValue("Aadhar  Number 11");
            row.createCell(colNum++).setCellValue("Dateof  Birth 12");
            row.createCell(colNum++).setCellValue("Age At Time Of Sanction 13");
            row.createCell(colNum++).setCellValue("Mobile No 14");
            row.createCell(colNum++).setCellValue("Farmers Category 15");
            row.createCell(colNum++).setCellValue("Farmer  Type 16");
            row.createCell(colNum++).setCellValue("Social Category 17");
            row.createCell(colNum++).setCellValue("Relative Type 18");
            row.createCell(colNum++).setCellValue("Relative Name 19");
            row.createCell(colNum++).setCellValue("State Name 20");
            row.createCell(colNum++).setCellValue("State Code 21");
            row.createCell(colNum++).setCellValue("District Name 22");
            row.createCell(colNum++).setCellValue("District code 23");
            row.createCell(colNum++).setCellValue("Block Code 24");
            row.createCell(colNum++).setCellValue("Block Name 25");
            row.createCell(colNum++).setCellValue("Village Code 26");
            row.createCell(colNum++).setCellValue("Village Name 27");
            row.createCell(colNum++).setCellValue("Address 28");
            row.createCell(colNum++).setCellValue("Pin Code 29");
            row.createCell(colNum++).setCellValue("Account Type 30");
            row.createCell(colNum++).setCellValue("Account Number 31");
            row.createCell(colNum++).setCellValue("Pacs Name 32");
            row.createCell(colNum++).setCellValue("Pacs Number 33");
            row.createCell(colNum++).setCellValue("Account Holder Type 34");
            row.createCell(colNum++).setCellValue("Primary occupation 35");
            row.createCell(colNum++).setCellValue("Loan Saction Date 36");
            row.createCell(colNum++).setCellValue("Loan Sanction Amount 37");
            row.createCell(colNum++).setCellValue("Tenure OF Loan 38");
            row.createCell(colNum++).setCellValue("Date Of Over Due Payment 39");
            row.createCell(colNum++).setCellValue("KCC Crop Code 40");
            row.createCell(colNum++).setCellValue("KCC Crop Name 41");
            row.createCell(colNum++).setCellValue("Crop Name 42");
            row.createCell(colNum++).setCellValue("survey No 43");
            row.createCell(colNum++).setCellValue("Sat Bara Subsurvey No 44");
            row.createCell(colNum++).setCellValue("Season name 45");
            row.createCell(colNum++).setCellValue("Activity Type 46");
            row.createCell(colNum++).setCellValue("Area Hect 47");
            row.createCell(colNum++).setCellValue("Land Type 48");
            row.createCell(colNum++).setCellValue("Disbursement Date 49");
            row.createCell(colNum++).setCellValue("Disburse Amount 50");
            row.createCell(colNum++).setCellValue("Maturity Loan Date 51");
            row.createCell(colNum++).setCellValue("Recovery Amount Principle 52");
            row.createCell(colNum++).setCellValue("Recovery Amount Interest 53");
            row.createCell(colNum++).setCellValue("Recovery Date 54");
            
            row.createCell(colNum++).setCellValue("Second Recovery Amount Principle 55");
            row.createCell(colNum++).setCellValue("Second Recovery Amount Interest 56");
            row.createCell(colNum++).setCellValue("Second Recovery Date 57");
            
            row.createCell(colNum++).setCellValue("Third Recovery Amount Principle 58");
            row.createCell(colNum++).setCellValue("Third Recovery Amount Interest 59");
            row.createCell(colNum++).setCellValue("Third Recovery Date 60");
            
            row.createCell(colNum++).setCellValue("Fourth Recovery Amount Principle 61");
            row.createCell(colNum++).setCellValue("Fourth Recovery Amount Interest 62");
            row.createCell(colNum++).setCellValue("Fourth Recovery Date 63");
            
            row.createCell(colNum++).setCellValue("Application Error");
            row.createCell(colNum++).setCellValue("ID");

            for (ApplicationLog applicationLog : ApplicationLogList) {
                colNum = 0;
                row = sheet.createRow(rowNum++);
                IssFileParser issFileParser = applicationLog.getIssFileParser();
                row.createCell(colNum++).setCellValue(issFileParser.getFinancialYear());
                row.createCell(colNum++).setCellValue(issFileParser.getBankName());
                row.createCell(colNum++).setCellValue(issFileParser.getBankCode());
                row.createCell(colNum++).setCellValue(issFileParser.getBranchName());
                row.createCell(colNum++).setCellValue(issFileParser.getBranchCode());
                row.createCell(colNum++).setCellValue(issFileParser.getSchemeWiseBranchCode());
                row.createCell(colNum++).setCellValue(issFileParser.getIfsc());
                row.createCell(colNum++).setCellValue(issFileParser.getLoanAccountNumberkcc());
                row.createCell(colNum++).setCellValue(issFileParser.getFarmerName());
                row.createCell(colNum++).setCellValue(issFileParser.getGender());
                row.createCell(colNum++).setCellValue(issFileParser.getAadharNumber());
                row.createCell(colNum++).setCellValue(issFileParser.getDateofBirth());
                row.createCell(colNum++).setCellValue(issFileParser.getAgeAtTimeOfSanction());

                row.createCell(colNum++).setCellValue(issFileParser.getMobileNo());

                row.createCell(colNum++).setCellValue(issFileParser.getFarmersCategory());
                row.createCell(colNum++).setCellValue(issFileParser.getFarmerType());
                row.createCell(colNum++).setCellValue(issFileParser.getSocialCategory());

                row.createCell(colNum++).setCellValue(issFileParser.getRelativeType());
                row.createCell(colNum++).setCellValue(issFileParser.getRelativeName());
                row.createCell(colNum++).setCellValue(issFileParser.getStateName());
                row.createCell(colNum++).setCellValue(issFileParser.getStateCode());
                row.createCell(colNum++).setCellValue(issFileParser.getDistrictName());
                row.createCell(colNum++).setCellValue(issFileParser.getDistrictCode());

                row.createCell(colNum++).setCellValue(issFileParser.getBlockCode());
                row.createCell(colNum++).setCellValue(issFileParser.getBlockName());
                row.createCell(colNum++).setCellValue(issFileParser.getVillageCode());
                row.createCell(colNum++).setCellValue(issFileParser.getVillageName());
                row.createCell(colNum++).setCellValue(issFileParser.getAddress());

                row.createCell(colNum++).setCellValue(issFileParser.getPinCode());
                row.createCell(colNum++).setCellValue(issFileParser.getAccountType());
                row.createCell(colNum++).setCellValue(issFileParser.getAccountNumber());
                row.createCell(colNum++).setCellValue(issFileParser.getPacsName());
                row.createCell(colNum++).setCellValue(issFileParser.getPacsNumber());
                row.createCell(colNum++).setCellValue(issFileParser.getAccountHolderType());
                row.createCell(colNum++).setCellValue(issFileParser.getPrimaryOccupation());

                row.createCell(colNum++).setCellValue(issFileParser.getLoanSactionDate());
                row.createCell(colNum++).setCellValue(issFileParser.getLoanSanctionAmount());
                row.createCell(colNum++).setCellValue(issFileParser.getTenureOFLoan());
                row.createCell(colNum++).setCellValue(issFileParser.getDateOfOverDuePayment());
                row.createCell(colNum++).setCellValue(issFileParser.getKccIssCropCode());
                row.createCell(colNum++).setCellValue(issFileParser.getKccIssCropName());
                row.createCell(colNum++).setCellValue(issFileParser.getCropName());
                row.createCell(colNum++).setCellValue(issFileParser.getSurveyNo());

                row.createCell(colNum++).setCellValue(issFileParser.getSatBaraSubsurveyNo());
                row.createCell(colNum++).setCellValue(issFileParser.getSeasonName());
                row.createCell(colNum++).setCellValue(issFileParser.getActivityType());
                row.createCell(colNum++).setCellValue(issFileParser.getAreaHect());
                row.createCell(colNum++).setCellValue(issFileParser.getLandType());
                row.createCell(colNum++).setCellValue(issFileParser.getDisbursementDate());

                row.createCell(colNum++).setCellValue(issFileParser.getDisburseAmount());
                row.createCell(colNum++).setCellValue(issFileParser.getMaturityLoanDate());
                row.createCell(colNum++).setCellValue(issFileParser.getRecoveryAmountPrinciple());
                row.createCell(colNum++).setCellValue(issFileParser.getRecoveryAmountInterest());
                row.createCell(colNum++).setCellValue(issFileParser.getRecoveryDate());
                
                
                row.createCell(colNum++).setCellValue(issFileParser.getSecondRecoveryAmountPrinciple());
                row.createCell(colNum++).setCellValue(issFileParser.getSecondRecoveryAmountInterest());
                row.createCell(colNum++).setCellValue(issFileParser.getSecondRecoveryDate());
                
                row.createCell(colNum++).setCellValue(issFileParser.getThirdRecoveryAmountPrinciple());
                row.createCell(colNum++).setCellValue(issFileParser.getThirdRecoveryAmountInterest());
                row.createCell(colNum++).setCellValue(issFileParser.getThirdRecoveryDate());
                
                
                row.createCell(colNum++).setCellValue(issFileParser.getFourthRecoveryAmountPrinciple());
                row.createCell(colNum++).setCellValue(issFileParser.getFourthRecoveryAmountInterest());
                row.createCell(colNum++).setCellValue(issFileParser.getFourthRecoveryDate());

                row.createCell(colNum++).setCellValue(applicationLog.getErrorMessage());
                row.createCell(colNum++).setCellValue(encryption(applicationLog.getIssFileParser().getId()));
            }

            // Write Excel to ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            outputStream.close();
            byte[] excelContent = outputStream.toByteArray();

            // Set up the HTTP headers for the response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            String fileNameWithOutExt = FilenameUtils.removeExtension(
                ApplicationLogList.get(0).getIssFileParser().getIssPortalFile().getFileName()
            );

            headers.setContentDispositionFormData("filename", fileNameWithOutExt + "-" + getUniqueName() + ".xlsx");

            List<String> contentDispositionList = new ArrayList<>();
            contentDispositionList.add("Content-Disposition");

            headers.setAccessControlExposeHeaders(contentDispositionList);
            return new ResponseEntity<>(excelContent, headers, 200);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    public String getUniqueName() {
        Calendar cal = new GregorianCalendar();
        return (
            "" +
            cal.get(Calendar.YEAR) +
            cal.get(Calendar.MONTH) +
            cal.get(Calendar.DAY_OF_MONTH) +
            cal.get(Calendar.HOUR) +
            cal.get(Calendar.MILLISECOND)
        );
    }

    public static byte[] objectToBytes(BatchData encDecObject) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(encDecObject);
            objectOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String encryption(Object encDecObject) {
        try {
            SecretKey secretKey = generateSecretKey(applicationProperties.getSecretKey(), applicationProperties.getKeySizeBits());
            IvParameterSpec ivParameterSpec = new IvParameterSpec(applicationProperties.getIv().getBytes(StandardCharsets.UTF_8));

            Cipher cipher = Cipher.getInstance(applicationProperties.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

            byte[] encryptedBytes = cipher.doFinal(encDecObject.toString().getBytes());
            return Hex.encodeHexString(encryptedBytes);
        } catch (Exception e) {
            return "error in encryption: " + e;
        }
    }

    public String decryption(String encDecObject) {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(applicationProperties.getIv().getBytes(StandardCharsets.UTF_8));
        final byte[] keyParse = applicationProperties.getSecretKey().getBytes();
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            SecretKeySpec key = new SecretKeySpec(keyParse, "AES");
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            byte[] val = new byte[encDecObject.length() / 2];
            for (int i = 0; i < val.length; i++) {
                int index = i * 2;
                int j = Integer.parseInt(encDecObject.substring(index, index + 2), 16);
                val[i] = (byte) j;
            }
            return new String(cipher.doFinal(val));
        } catch (Exception e) {
            log.error("Error while decrypting: " + e);
        }
        return null;
    }

    private static SecretKey generateSecretKey(String secretKey, int keySize) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        byte[] truncatedKey = new byte[keySize / 8];
        System.arraycopy(keyBytes, 0, truncatedKey, 0, truncatedKey.length);
        return new SecretKeySpec(truncatedKey, "AES");
    }
}
