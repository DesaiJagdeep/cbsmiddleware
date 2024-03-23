package com.cbs.middleware.web.rest.utility;

import java.text.DecimalFormat;

public class EnglishNumberToWords {
	private static final String[] tensNames = {
		    "",
		    " ten",
		    " twenty",
		    " thirty",
		    " forty",
		    " fifty",
		    " sixty",
		    " seventy",
		    " eighty",
		    " ninety"
		  };

		  private static final String[] numNames = {
		    "",
		    " one",
		    " two",
		    " three",
		    " four",
		    " five",
		    " six",
		    " seven",
		    " eight",
		    " nine",
		    " ten",
		    " eleven",
		    " twelve",
		    " thirteen",
		    " fourteen",
		    " fifteen",
		    " sixteen",
		    " seventeen",
		    " eighteen",
		    " nineteen"
		  };

		  private EnglishNumberToWords() {}

		  private static String convertLessThanOneThousand(int number) {
		    String soFar;

		    if (number % 100 < 20){
		      soFar = numNames[number % 100];
		      number /= 100;
		    }
		    else {
		      soFar = numNames[number % 10];
		      number /= 10;

		      soFar = tensNames[number % 10] + soFar;
		      number /= 10;
		    }
		    if (number == 0) return soFar;
		    return numNames[number] + " hundred" + soFar;
		  }



		  public static String convertDoubleToText(Double number)
		  {
              DecimalFormat df = new DecimalFormat("#.###########");
              String largeNumber = df.format(number);


			  String[] div = largeNumber.toString().split("\\.");


			  StringBuilder numberToWord=new StringBuilder();


			  if(div[0].length()>1)
			  {
				  String rupee=div[0];

				  numberToWord.append(EnglishNumberToWords.convert(Long.parseLong(rupee)));
				  numberToWord.append(" Rupees");
			  }


			  if(div.length >1 && div[1].length()>1)
			  {
				  String paise= div[1];
				  numberToWord.append(" ");
				  numberToWord.append(EnglishNumberToWords.convert(Long.parseLong(paise)));
				  numberToWord.append(" Paise");
			  }

			  numberToWord.append(" Only");




			return numberToWord.toString();

		  }


		  static String convert(long number) {
		    // 0 to 999 999 999 999
		    if (number == 0) { return "zero"; }

		    String snumber = Long.toString(number);

		    // pad with "0"
		    String mask = "000000000000";
		    DecimalFormat df = new DecimalFormat(mask);
		    snumber = df.format(number);

		    // XXXnnnnnnnnn
		    int billions = Integer.parseInt(snumber.substring(0,3));
		    // nnnXXXnnnnnn
		    int millions  = Integer.parseInt(snumber.substring(3,6));
		    // nnnnnnXXXnnn
		    int hundredThousands = Integer.parseInt(snumber.substring(6,9));
		    // nnnnnnnnnXXX
		    int thousands = Integer.parseInt(snumber.substring(9,12));

		    String tradBillions;
		    switch (billions) {
		    case 0:
		      tradBillions = "";
		      break;
		    case 1 :
		      tradBillions = convertLessThanOneThousand(billions)
		      + " billion ";
		      break;
		    default :
		      tradBillions = convertLessThanOneThousand(billions)
		      + " billion ";
		    }
		    String result =  tradBillions;

		    String tradMillions;
		    switch (millions) {
		    case 0:
		      tradMillions = "";
		      break;
		    case 1 :
		      tradMillions = convertLessThanOneThousand(millions)
		         + " million ";
		      break;
		    default :
		      tradMillions = convertLessThanOneThousand(millions)
		         + " million ";
		    }
		    result =  result + tradMillions;

		    String tradHundredThousands;
		    switch (hundredThousands) {
		    case 0:
		      tradHundredThousands = "";
		      break;
		    case 1 :
		      tradHundredThousands = "one thousand ";
		      break;
		    default :
		      tradHundredThousands = convertLessThanOneThousand(hundredThousands)
		         + " thousand ";
		    }
		    result =  result + tradHundredThousands;

		    String tradThousand;
		    tradThousand = convertLessThanOneThousand(thousands);
		    result =  result + tradThousand;

		    // remove extra spaces!
		    return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
		  }

		  /**
		   * testing
		   * @param args
		   */
		 // public static void main(String[] args) {
		   // System.out.println("*** " + EnglishNumberToWords.convertDoubleToText(51240.01));


		    /*
		     *** zero
		     *** one
		     *** sixteen
		     *** one hundred
		     *** one hundred eighteen
		     *** two hundred
		     *** two hundred nineteen
		     *** eight hundred
		     *** eight hundred one
		     *** one thousand three hundred sixteen
		     *** one million
		     *** two millions
		     *** three millions two hundred
		     *** seven hundred thousand
		     *** nine millions
		     *** nine millions one thousand
		     *** one hundred twenty three millions four hundred
		     **      fifty six thousand seven hundred eighty nine
		     *** two billion one hundred forty seven millions
		     **      four hundred eighty three thousand six hundred forty seven
		     *** three billion ten
		     **/
		//  }


}
