package com.cbs.middleware.web.rest.utility;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//import org.apache.commons.text.NumberToWords;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TranslationServiceUtility {


    private static TranslationServiceUtility translationServiceUtility;

    public static TranslationServiceUtility getInstance() {
        return translationServiceUtility != null ? translationServiceUtility : new TranslationServiceUtility();
    }

    public static String numberTOMarathiNumber(String str) {
        if (str == null || "".equals(str.trim()) || str.equals("0") || str.equals("0.00")) return "";
        String answer = str;
        answer = answer.replace("1", "१");
        answer = answer.replace("2", "२");
        answer = answer.replace("3", "३");
        answer = answer.replace("4", "४");
        answer = answer.replace("5", "५");
        answer = answer.replace("6", "६");
        answer = answer.replace("7", "७");
        answer = answer.replace("8", "८");
        answer = answer.replace("9", "९");
        answer = answer.replace("0", "०");

        return answer;

    }


    public static String translationText(String text) {
        String returnText = "";

        RestTemplate restTemplate = new RestTemplate();

        String apiKey = "AIzaSyBo5y_UGSaPWLVwcfyl2Y6304tURv4ND6k";

        Map<String, String> queryParameters = new HashMap<String, String>();
        queryParameters.put("key", apiKey);
        queryParameters.put("source", "en");
        queryParameters.put("target", "mr");
        queryParameters.put("q", text);

        String url = "https://www.googleapis.com/language/translate/" + "v2?key={key}&source={source}&target={target}&q={q}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-HTTP-Method-Override", "GET");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<String>(null, headers);

        ResponseEntity<TranslationResponse> translationResponse = restTemplate.exchange(
            url,
            HttpMethod.GET,
            request,
            TranslationResponse.class,
            queryParameters
        );

        if (translationResponse.getStatusCode().is2xxSuccessful()) {
            returnText = translationResponse.getBody().getData().getTranslations().get(0).getTranslatedText();
        }

        return returnText;
    }

    public String translationTextMrToEn(String text) {
        String returnText = "";

        try {
            RestTemplate restTemplate = new RestTemplate();

            String apiKey = "AIzaSyBo5y_UGSaPWLVwcfyl2Y6304tURv4ND6k";

            Map<String, String> queryParameters = new HashMap<String, String>();
            queryParameters.put("key", apiKey);
            queryParameters.put("source", "mr");
            queryParameters.put("target", "en");
            queryParameters.put("q", text);

            String url = "https://www.googleapis.com/language/translate/" + "v2?key={key}&source={source}&target={target}&q={q}";

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-HTTP-Method-Override", "GET");
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<String> request = new HttpEntity<String>(null, headers);

            ResponseEntity<TranslationResponse> translationResponse = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                TranslationResponse.class,
                queryParameters
            );

            if (translationResponse.getStatusCode().is2xxSuccessful()) {
                returnText = translationResponse.getBody().getData().getTranslations().get(0).getTranslatedText();
            }
        } catch (Exception e) {
        }

        return returnText;
    }


    public String oneZeroOneDateMr(LocalDate loanDate) {
        if (loanDate == null) {
            return "";
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = loanDate.format(formatter);


            return numberTOMarathiNumber(formattedDate);
        } catch (Exception e) {
            return "Error in translation";
        }
    }

    public static String totalAmountWord(Double amount) {

        if (amount == null || amount.isNaN()) {
            return "";
        }
        try {
            String format = String.format("%.2f", amount);
            double v = Double.parseDouble(format);
            String convertDoubleToText = EnglishNumberToWords.convertDoubleToText(v);
            return translationText(convertDoubleToText);
        } catch (Exception e) {
            return "Error in translation";
        }
    }


    //======================================================================================================================


/*
    public static String enWordsToMrWords(Double amount) {

        if (amount == null || amount.isNaN()) {
            return "";
        }
        try {
            String formatted = String.format("%.2f", amount);
            String enAmountInWords = convertToWords(formatted);
            return translationText(enAmountInWords);
        } catch (Exception e) {
            return "Error in translation";
        }
    }



    public static String convertToWords(String num) {

    BigDecimal bd = new BigDecimal(num);
    long number = bd.longValue();
    long no = bd.longValue();
    int decimal = (int) (bd.remainder(BigDecimal.ONE).doubleValue() * 100);
    int digits_length = String.valueOf(no).length();
    int i = 0;
    ArrayList<String> str = new ArrayList<>();
    HashMap<Integer, String> words = new HashMap<>();
        words.put(0,"");
        words.put(1,"One");
        words.put(2,"Two");
        words.put(3,"Three");
        words.put(4,"Four");
        words.put(5,"Five");
        words.put(6,"Six");
        words.put(7,"Seven");
        words.put(8,"Eight");
        words.put(9,"Nine");
        words.put(10,"Ten");
        words.put(11,"Eleven");
        words.put(12,"Twelve");
        words.put(13,"Thirteen");
        words.put(14,"Fourteen");
        words.put(15,"Fifteen");
        words.put(16,"Sixteen");
        words.put(17,"Seventeen");
        words.put(18,"Eighteen");
        words.put(19,"Nineteen");
        words.put(20,"Twenty");
        words.put(30,"Thirty");
        words.put(40,"Forty");
        words.put(50,"Fifty");
        words.put(60,"Sixty");
        words.put(70,"Seventy");
        words.put(80,"Eighty");
        words.put(90,"Nintey");
    String digits[] = {"", "Hundred", "Thousand", "Lakh", "Crore"};
        while(i<digits_length)

    {
        int divider = (i == 2) ? 10 : 100;
        number = no % divider;
        no = no / divider;
        i += divider == 10 ? 1 : 2;
        if (number > 0) {
            int counter = str.size();
            String plural = (counter > 0 && number > 9) ? "s" : "";
            String tmp = (number < 21) ? words.get(Integer.valueOf((int) number)) + " " + digits[counter] + plural
                : words.get(Integer.valueOf((int) Math.floor(number / 10) * 10)) + " "
                + words.get(Integer.valueOf((int) (number % 10))) + " " + digits[counter] + plural;
            str.add(tmp);
        } else {
            str.add("");
        }
    }

        Collections.reverse(str);
    String Rupees = String.join(" ", str).trim();

    String paise = (decimal) > 0
        ? " And " + words.get(Integer.valueOf((int) (decimal - decimal % 10))) + " "
        + words.get(Integer.valueOf((int) (decimal % 10))) + " Paise "
        : "";
        return""+Rupees + "  Rupees "+paise +" Only";

}
*/


    //ConvertAmountToMarathiAmountInWord

    public static final Map<Integer, String> marathiDigits = new HashMap<>();

    static {
        marathiDigits.put(0, "शून्य");
        marathiDigits.put(1, "एक");
        marathiDigits.put(2, "दोन");
        marathiDigits.put(3, "तीन");
        marathiDigits.put(4, "चार");
        marathiDigits.put(5, "पाच");
        marathiDigits.put(6, "सहा");
        marathiDigits.put(7, "सात");
        marathiDigits.put(8, "आठ");
        marathiDigits.put(9, "नऊ");
        marathiDigits.put(10, "दहा");
        marathiDigits.put(11, "अकरा");
        marathiDigits.put(12, "बारा");
        marathiDigits.put(13, "तेरा");
        marathiDigits.put(14, "चौदा");
        marathiDigits.put(15, "पंधरा");
        marathiDigits.put(16, "सोळा");
        marathiDigits.put(17, "सतरा");
        marathiDigits.put(18, "अठरा");
        marathiDigits.put(19, "एकोणीस");
        marathiDigits.put(20, "वीस");
        marathiDigits.put(21, "एकवीस");
        marathiDigits.put(22, "बावीस");
        marathiDigits.put(23, "तेवीस");
        marathiDigits.put(24, "चोवीस");
        marathiDigits.put(25, "पंचवीस");
        marathiDigits.put(26, "सव्वीस");
        marathiDigits.put(27, "सत्तावीस");
        marathiDigits.put(28, "अठ्ठावीस");
        marathiDigits.put(29, "एकोणतीस");
        marathiDigits.put(30, "तीस");
        marathiDigits.put(31, "एकतीस");
        marathiDigits.put(32, "बत्तीस");
        marathiDigits.put(33, "तेहेतीस");
        marathiDigits.put(34, "चौतीस");
        marathiDigits.put(35, "पस्तीस");
        marathiDigits.put(36, "छत्तीस");
        marathiDigits.put(37, "सदतीस");
        marathiDigits.put(38, "अडतीस");
        marathiDigits.put(39, "एकोणचाळीस");
        marathiDigits.put(40, "चाळीस");
        marathiDigits.put(41, "एक्केचाळीस");
        marathiDigits.put(42, "बेचाळीस");
        marathiDigits.put(43, "त्रेचाळीस");
        marathiDigits.put(44, "चव्वेचाळीस");
        marathiDigits.put(45, "पंचेचाळीस");
        marathiDigits.put(46, "सेहेचाळीस");
        marathiDigits.put(47, "सत्तेचाळीस");
        marathiDigits.put(48, "अठ्ठेचाळीस");
        marathiDigits.put(49, "एकोणपन्नास");
        marathiDigits.put(50, "पन्नास");
        marathiDigits.put(51, "एक्कावन्न");
        marathiDigits.put(52, "बावन्न");
        marathiDigits.put(53, "त्रेपन्न");
        marathiDigits.put(54, "चोपन्न");
        marathiDigits.put(55, "पंचावन्न");
        marathiDigits.put(56, "छप्पन्न");
        marathiDigits.put(57, "सत्तावन्न");
        marathiDigits.put(58, "अठ्ठावन्न");
        marathiDigits.put(59, "एकोणसाठ");
        marathiDigits.put(60, "साठ");
        marathiDigits.put(61, "एकसष्ठ");
        marathiDigits.put(62, "बासष्ठ");
        marathiDigits.put(63, "त्रेसष्ठ");
        marathiDigits.put(64, "चौसष्ठ");
        marathiDigits.put(65, "पासष्ठ");
        marathiDigits.put(66, "सहासष्ठ");
        marathiDigits.put(67, "सदुसष्ठ");
        marathiDigits.put(68, "अडुसष्ठ");
        marathiDigits.put(69, "एकोणसत्तर");
        marathiDigits.put(70, "सत्तर");
        marathiDigits.put(71, "एक्काहत्तर");
        marathiDigits.put(72, "बाहत्तर");
        marathiDigits.put(73, "त्र्याहत्तर");
        marathiDigits.put(74, "चौर्‍याहत्तर");
        marathiDigits.put(75, "पंच्याहत्तर");
        marathiDigits.put(76, "शहात्तर");
        marathiDigits.put(77, "सत्याहत्तर");
        marathiDigits.put(78, "अठ्ठ्याहत्तर");
        marathiDigits.put(79, "एकोणऐंशी");
        marathiDigits.put(80, "ऐंशी");
        marathiDigits.put(81, "एक्क्याऐंशी");
        marathiDigits.put(82, "ब्याऐंशी");
        marathiDigits.put(83, "त्र्याऐंशी");
        marathiDigits.put(84, "चौऱ्याऐंशी");
        marathiDigits.put(85, "पंच्याऐंशी");
        marathiDigits.put(86, "शहाऐंशी");
        marathiDigits.put(87, "सत्त्याऐंशी");
        marathiDigits.put(88, "अठ्ठ्याऐंशी");
        marathiDigits.put(89, "एकोणनव्वद");
        marathiDigits.put(90, "नव्वद");
        marathiDigits.put(91, "एक्क्याण्णव");
        marathiDigits.put(92, "ब्याण्णव");
        marathiDigits.put(93, "त्र्याण्णव");
        marathiDigits.put(94, "चौऱ्याण्णव");
        marathiDigits.put(95, "पंच्याण्णव");
        marathiDigits.put(96, "शहाण्णव");
        marathiDigits.put(97, "सत्त्याण्णव");
        marathiDigits.put(98, "अठ्ठ्याण्णव");
        marathiDigits.put(99, "नव्व्याण्णव");
        marathiDigits.put(100, "एकशे");
    }


    public static String amountToMarathiWords(Double amount){
        if (amount == null || amount.isNaN()) {
            return "";
        }
        String formatted = String.format("%.2f", amount);
        String a1[] = formatted.split("\\.");

        Long number = Long.parseLong(a1[0]);
        int dec = Integer.parseInt(a1[1]);

        String marathiAmountWord = convertToMarathi(number, dec);
        return  marathiAmountWord;
    }
    static String convertToMarathi(long number, int dec) {

        StringBuilder marathiText = new StringBuilder();
        String num_str = String.valueOf(number);
        System.out.println (num_str);
        long division = 0;
        long num = 0;

        marathiText.append ("रुपये ");

        if (number == 0) {
            marathiText.append(marathiDigits.get((int) number));
            marathiText.append(" फक्त");
            return marathiText.toString().trim();
        }

        // Convert billions
        if (number > 10000000) {
            division = number / 10000000;
            num = 0;

            if (division > 1000) {
                num = division / 1000;
                marathiText.append (marathiDigits.get((int) num));
                marathiText.append(" हजार");
                division %= 1000;
            }

            if (division > 100) {
                num = division / 100;
                marathiText.append (marathiDigits.get((int) num));
                marathiText.append("शे ");
                division %= 100;
            }

            if (division > 0) {
                marathiText.append(marathiDigits.get((int) division));

            }

            marathiText.append(" कोटी ");
        }

        number %= 10000000;

        if (number > 100000) {
            division = number / 100000;

            if (division > 0) {
                marathiText.append (marathiDigits.get((int) division));
                marathiText.append(" लाख ");
            }
        }
        number %= 100000;

        if (number > 1000) {
            division = number / 1000;

            if (division > 0) {
                marathiText.append (marathiDigits.get((int) division));
                marathiText.append("  हजार ");
            }
        }
        number %= 1000;

        if (number > 100) {
            division = number / 100;

            if (division > 0) {
                marathiText.append (marathiDigits.get((int) division));
                marathiText.append("शे ");
            }
        }
        number %= 100;

        if (number > 0) {

            marathiText.append(marathiDigits.get((int) number));

        }

        if (dec > 0) {

            marathiText.append(" आणि पैसे ");
            marathiText.append(marathiDigits.get((int) dec));

        }

        marathiText.append(" फक्त");
        return marathiText.toString().trim();
    }
    //======================================================================================================================



}

