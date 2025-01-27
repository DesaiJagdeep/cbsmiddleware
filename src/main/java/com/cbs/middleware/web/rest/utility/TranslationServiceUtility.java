package com.cbs.middleware.web.rest.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	
	
//	public String convertNumberToWords(int number) {
//        return NumberToWords.toWords(number);
//    }
//	
	
	
	
	public static String numberTOMarathiNumber( String str)
	{
		 
			    String answer = str;
			    answer = answer.replace("1","१");
			    answer = answer.replace("2","२");
			    answer = answer.replace("3","३");
			    answer = answer.replace("4","४");
			    answer = answer.replace("5","५");
			    answer = answer.replace("6","६");
			    answer = answer.replace("7","७");
			    answer = answer.replace("8","८");
			    answer = answer.replace("9","९");
			    answer = answer.replace("0","०");
			    
			    return answer;
		
	}
	
	
	
	

    public String translationText(String text) {
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
        } catch (Exception e) {}

        return returnText;
    }
    
    
    
    public static String oneZeroOneDateMr(LocalDate loanDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = loanDate.format(formatter);

            
            return numberTOMarathiNumber(formattedDate);
        } catch (Exception e) {
            return "Error in translation";
        }
    }
    
    
    
    
   
}
