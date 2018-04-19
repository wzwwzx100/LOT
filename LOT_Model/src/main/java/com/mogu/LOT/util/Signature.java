package com.mogu.LOT.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;






/**
 * @author ma_junliang
 *
 */
public class Signature{
	private Signature() {}

	private static Signature instance = null;
	
	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F' };
	
	private static final String apiKey="asdfasdf";
	
	public static synchronized Signature getInstance() {
		if (instance == null) {
			instance = new Signature();
		}		
		return instance;
	}
	
	
	
	
	public String sign(List<NameValue> list) throws NullPointerException{
		if(list==null||list.size()==0) {
			throw new NullPointerException("params list must be not null");
		}
		Collections.sort(list);
		String str="";
		Collections.sort(list);
		for (NameValue nameValue : list) {
			str+=nameValue.getName()+nameValue.getValue();
		}
		return this.str2MD532(str+apiKey);
	}

	
	public String str2MD532(String ste) {
		try {
			byte[] strTemp = ste.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	public class NameValue implements Comparable<NameValue>{
		
		private String name;
		
		private String value;

		
		
		public NameValue(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		@Override
		public int compareTo(NameValue o) {
			String thisStr=this.name+this.value;
			String str=o.name+o.value;
			if(thisStr.hashCode()>str.hashCode()) {
				return 1;
			}
			if(thisStr.hashCode()<str.hashCode()) {
				return -1;
			}
			return 0;
		}
		
		
		@Override
		public String toString() {
			return this.name+this.value;
		}

	}
	
	
	public static void main(String[] args) throws Exception{
		Signature s=Signature.getInstance();
		List<Signature.NameValue> list=new ArrayList<Signature.NameValue>();
		list.add(s.new NameValue("name2", "value2"));
		list.add(s.new NameValue("name1", "value1")); 
		
		list.add(s.new NameValue("name3", "value3"));
		String signString=s.sign(list);
		System.out.println(signString);
	}
	
	

}
