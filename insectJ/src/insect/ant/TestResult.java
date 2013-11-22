package insect.ant;

import java.io.Serializable;

public class TestResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long time;
	private boolean isSuccess;
	
	public TestResult(long time, boolean isSuccess){
		this.time = time;
		this.isSuccess = isSuccess;
	}
	
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
}
