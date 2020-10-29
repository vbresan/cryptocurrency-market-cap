package biz.binarysolutions.cryptocap.data;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import biz.binarysolutions.cryptocap.util.DateUtil;

/**
 * 
 *
 */
@Entity
public class Total {

	@Id
	private String id;
	@Index
	private Date   date;
	private double amount;
	
	/**
	 * 
	 */
	public Total() {
		// necessary for Objectify
	}

	/**
	 * 
	 * @param amount
	 */
	public Total(double amount) {
		
		this.date   = new Date();
		this.amount = amount;
		this.id     = DateUtil.getFormattedDate(date);
	}

	/**
	 * 
	 * @return
	 */
	public String getId() {
		return id; 
	}

	/**
	 * 
	 * @return
	 */
	public double getAmount() {
		return amount;
	}
	
	/**
	 * 
	 * @return
	 */
	public Date getDate() {
		return date;
	}
}
