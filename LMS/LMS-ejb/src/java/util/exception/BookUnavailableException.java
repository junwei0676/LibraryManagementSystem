/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author junwe
 */
public class BookUnavailableException extends Exception{

    /**
     * Creates a new instance of <code>BookUnavailableException</code> without
     * detail message.
     */
    public BookUnavailableException() {
    }

    /**
     * Constructs an instance of <code>BookUnavailableException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BookUnavailableException(String msg) {
        super(msg);
    }
}
