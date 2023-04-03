package me.veeraya.ISO8583.field;

public class PackException extends RuntimeException{
	   
	public PackException(String message) {
	      super(message);
    }
    public PackException(String message, Throwable cause) {
	      super(message, cause);
	}
}
