package biz.binarysolutions.cryptocap.util;

import java.util.List;

import com.googlecode.objectify.ObjectifyService;

import biz.binarysolutions.cryptocap.data.Cryptocurrency;
import biz.binarysolutions.cryptocap.data.Total;

/**
 * 
 *
 */
public class DatastoreUtil {

	/**
	 * 
	 * @return
	 */
	public static Total getLastTotal() {
		return ObjectifyService.ofy().load().type(Total.class).order("-date").first().now();
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public static List<Cryptocurrency> getItems(String date) {
		return ObjectifyService.ofy().load().type(Cryptocurrency.class).filter("date", date).list();
	}
}
