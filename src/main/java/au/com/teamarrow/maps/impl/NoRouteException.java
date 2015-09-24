package au.com.teamarrow.maps.impl;

public class NoRouteException extends Exception {

	private static final long serialVersionUID = -899347694602786214L;

	public NoRouteException () {};
    
    public NoRouteException (String message) {
    	super (message);
    }

    public NoRouteException (Throwable cause) {
    	super (cause);
    }

    public NoRouteException (String message, Throwable cause) {
    	super (message, cause);
    }
}
