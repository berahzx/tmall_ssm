package com.how2java.tmall.page;

public class Page {
	//偏移位置
	private int start;
	//每页数量
	private int count;
	//总的数量
	private int total;
	//参数
	private String param;
	public boolean isHasPreviouse(){
		if(this.start==0){
			return false;
		}return true;
	}
	public boolean isHasNext(){
		if(this.start==getLast()){
			return false;
		}
		return true;
	}
	public Page() {
		count=5;
	}
	public Page(int start, int count) {
		this();
		this.start = start;
		this.count = count;
	}
	public int getLast(){
		int last;
		if(total%count==0){
			last=total-count;
		}else{
			last=total-total%count;
		}
		return last<0?0:last;
	}
	public int getTotalPage(){
		int totalPage;
		if(total%count==0){
			totalPage=total/count;
		}else{
			totalPage=total/count+1;
		}
		return totalPage<0?1:totalPage;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
}
