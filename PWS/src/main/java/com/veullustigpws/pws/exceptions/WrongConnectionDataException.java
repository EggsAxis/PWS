package com.veullustigpws.pws.exceptions;

public class WrongConnectionDataException extends Exception {
	private static final long serialVersionUID = 9099975457275746940L;
	
	public static final int WRONG_CODE_FORMAT = 1;
	public static final int INVALID_CODE = 2;
	public static final int UNKNOWN_HOST = 3;
	public static final int WRONG_PASSWORD = 4;
	
	private int type;
	
	public WrongConnectionDataException(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
}
