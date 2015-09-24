package au.com.teamarrow.maps.impl;

public class NoRouteNodeException extends Exception {

	private static final long serialVersionUID = -4229796380344583630L;

	public NoRouteNodeException () {};
    
    public NoRouteNodeException (String message) {
    	super (message);
    }

    public NoRouteNodeException (Throwable cause) {
    	super (cause);
    }

    public NoRouteNodeException (String message, Throwable cause) {
    	super (message, cause);
    }
}
