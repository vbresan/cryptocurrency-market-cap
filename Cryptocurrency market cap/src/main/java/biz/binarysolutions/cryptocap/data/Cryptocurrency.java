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
public class Cryptocurrency implements Comparable<Cryptocurrency> {

	@Id
	private String id;
	
	@Index
	private String date;
	private String symbol;
	private String name;
	private double cap;

	/**
	 * 
	 * @return
	 */
	private String getId() {
		
		StringBuffer sb = new StringBuffer();
		sb.append(date);
		sb.append(" ");
		sb.append(symbol);
		sb.append(" ");
		sb.append(name);
		
		return sb.toString();
	}
	
	/**
	 * 
	 */
	public Cryptocurrency() {
		// necessary for Objectify
	}

	/**
	 * 
	 * @param symbol
	 * @param name
	 * @param cap
	 */
	public Cryptocurrency(String symbol, String name, double cap) {

		this.date   = DateUtil.getFormattedDate(new Date());
		this.symbol = symbol;
		this.name   = name;
		this.cap    = cap;
		
		this.id = getId();
	}

	/**
	 * 
	 * @return
	 */
	public double getCap() {
		return cap;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Cryptocurrency other) {
		return (int) (other.cap - this.cap);
	}
}
