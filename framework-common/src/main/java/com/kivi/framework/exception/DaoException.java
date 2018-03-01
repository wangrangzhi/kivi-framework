package com.kivi.framework.exception;

/**
 * @author Eric
 *
 */
public class DaoException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String            errorCode;

    public DaoException() {
        super();
    }

    public DaoException( String message ) {
        this(null, message, null);
    }

    public DaoException( String errorCode, String message ) {
        this(errorCode, message, null);
    }

    public DaoException( Throwable cause ) {
        this(null, null, cause);
    }

    public DaoException( String message, Throwable cause ) {
        this(null, message, cause);
    }

    public DaoException( String errorCode, String message, Throwable cause ) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode == null ? this.getMessage() : errorCode;
    }

    public void setErrorCode( String errorCode ) {
        this.errorCode = errorCode;
    }

}
