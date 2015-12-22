package com.example.SEPM;

public class ItemsActivity {

	private String date;
	private String area;
	private int no;
	
	
	 public ItemsActivity (String date,String area,int no) {
		// TODO Auto-generated constructor stub
		super();
		this.date = date;
		this.area = area;
		this.no=no;
	}
	 
	 
	 public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getPlace() {
			return area;
		}
		public void setArea(String area) {
			this.area = area;
		}
		public int getNo() {
			return no;
		}
		public void setNo(int no) {
			this.no = no;
		}
		
		
}
