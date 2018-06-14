package br.com.challenge.rhmoreira.tests.functional;

public class ExceptionCatcher {
	
	private Throwable exception;
	
	public static ExceptionCatcher doTry(TryBlock function) {
		ExceptionCatcher catcher = new ExceptionCatcher();
		try {
			function.doTry();
		}catch (Exception e) {
			catcher.exception = e;
		}
		
		return catcher;
	}
	
	public Throwable doCatch() {
		return exception;
	}
	

}
