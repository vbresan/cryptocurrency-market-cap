package biz.binarysolutions.cryptocap.cron;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;

import biz.binarysolutions.cryptocap.data.Cryptocurrency;
import biz.binarysolutions.cryptocap.data.Total;
import biz.binarysolutions.cryptocap.util.ObjectifyUtil;



@SuppressWarnings("serial")
@WebServlet(
    name = "FetchData",
    urlPatterns = {"/cron/fetchData"}
)
public class FetchData extends HttpServlet {
	
	private static final String FETCH_URL = 
		"https://coinmarketcap.com/all/views/all/";
	
	private static final String SYMBOL_TAG = 
		"<span class=\"currency-symbol\">";
	
	private static final String NAME_TAG = 
		"<a class=\"currency-name-container";
	
	private static final String CAP_TAG = 
		"<td class=\"no-wrap market-cap text-right\"";
	
	private static final String TOTAL_CAP_TAG = "total-marketcap";
	private static final int ITEM_MAX_COUNT = 10;
	
	/**
	 * 
	 * @param line
	 * @return
	 */
	private String fetchSymbol(String line) {

		String symbol = line.substring(0, line.indexOf("</a>"));
		symbol = symbol.substring(symbol.lastIndexOf(">") + 1);
		
		return symbol;
	}

	/**
	 * 
	 * @param line
	 * @return
	 */
	private String fetchName(String line) {

		String name = line.substring(0, line.indexOf("</a>"));
		name = name.substring(name.lastIndexOf(">") + 1);
		
		return name;
	}

	/**
	 * 
	 * @param line
	 * @return
	 */
	private String fetchCap(String line) {
		
		int beginIndex = line.indexOf("data-usd=\"") + "data-usd=\"".length();
		String cap = line.substring(beginIndex);
		cap = cap.substring(0, cap.indexOf("\""));
		
		return cap;
	}

	/**
	 * 
	 * @param line
	 * @return
	 */
	private double fetchTotalCap(String line) {
		return Double.valueOf(fetchCap(line)).doubleValue();
	}

	/**
	 * 
	 * @param total
	 */
	private void saveTotal(double amount) {

		Total total = new Total(amount);
		ObjectifyService.ofy().save().entity(total);
	}

	/**
	 * 
	 * @param list
	 */
	private void saveItems(List<Cryptocurrency> list) {
		ObjectifyService.ofy().save().entities(list);
	}

	/**
	 * 
	 * @param list 
	 * @return
	 * @throws IOException 
	 */
	private double getData(List<Cryptocurrency> list) throws IOException {
		
		URL url = new URL(FETCH_URL);
		BufferedReader reader = 
			new BufferedReader(new InputStreamReader(url.openStream()));
		
		String symbol = "";
		String name   = "";
		String cap    = "?";
		double total  = 0;
		
		int count = 0;

		String line;
		while ((line = reader.readLine()) != null) {
			if (line.contains(SYMBOL_TAG)) {
				symbol = fetchSymbol(line);
			} else if (line.contains(NAME_TAG)) {
				name = fetchName(line);
			} else if (line.contains(CAP_TAG)) {
				cap = fetchCap(line);
			} else if (line.contains(TOTAL_CAP_TAG)) {
				total = fetchTotalCap(line);
			}
			
			if (!cap.equals("?") && count < ITEM_MAX_COUNT) {
				
				double cap_double = Double.valueOf(cap).doubleValue();
				list.add(new Cryptocurrency(symbol, name, cap_double));
				
				cap = "?";
				count++;
			}
		}
		
		reader.close();
		return total;
	}
	

	@Override
	protected void doPost
		(
			HttpServletRequest  request, 
			HttpServletResponse response
		) 
		throws ServletException, IOException {
		
		ObjectifyUtil.setup();
		
		List<Cryptocurrency> list = new ArrayList<Cryptocurrency>();
		double total = getData(list);
		
		saveTotal(total);
		saveItems(list);
	}

	@Override
	protected void doGet
		(
			HttpServletRequest  request, 
			HttpServletResponse response
		) 
		throws ServletException, IOException {
		doPost(request, response);
	}
}