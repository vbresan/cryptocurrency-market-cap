package biz.binarysolutions.cryptocap.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 *
 */
public class DateUtil {
	
	private static final String FORMAT = "yyyy_MM_dd";

	/**
	 * 
	 * @return
	 */
	public static String getFormattedDate(Date date) {

		DateFormat df = new SimpleDateFormat(FORMAT);
		return df.format(date);
	}	
}
