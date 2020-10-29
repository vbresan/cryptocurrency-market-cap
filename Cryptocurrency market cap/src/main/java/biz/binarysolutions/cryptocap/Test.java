package biz.binarysolutions.cryptocap;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.binarysolutions.cryptocap.data.Cryptocurrency;
import biz.binarysolutions.cryptocap.data.Total;
import biz.binarysolutions.cryptocap.util.DatastoreUtil;
import biz.binarysolutions.cryptocap.util.ObjectifyUtil;

/**
 * 
 *
 */
@SuppressWarnings("serial")
@WebServlet(
    name = "Test",
    urlPatterns = {"/test"}
)
public class Test extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ObjectifyUtil.setup();
		
		Total total = DatastoreUtil.getLastTotal();
		String date = total.getId();
		
		List<Cryptocurrency> items = DatastoreUtil.getItems(date);
		Collections.sort(items);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
