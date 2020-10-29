package biz.binarysolutions.cryptocap.util;

import com.googlecode.objectify.ObjectifyService;

import biz.binarysolutions.cryptocap.data.Cryptocurrency;
import biz.binarysolutions.cryptocap.data.Total;

/**
 * 
 *
 */
public class ObjectifyUtil {

	/**
	 * 
	 */
	public static void setup() {
		
		ObjectifyService.begin();
	    ObjectifyService.register(Cryptocurrency.class);
	    ObjectifyService.register(Total.class);
	}
}
