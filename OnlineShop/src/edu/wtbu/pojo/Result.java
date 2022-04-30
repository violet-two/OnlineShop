package edu.wtbu.pojo;

public class Result {
	Object flag;
	Object data;
	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Result(Object flag, Object data) {
		super();
		this.flag = flag;
		this.data = data;
	}
	public Object getFlag() {
		return flag;
	}
	public void setFlag(Object flag) {
		this.flag = flag;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
