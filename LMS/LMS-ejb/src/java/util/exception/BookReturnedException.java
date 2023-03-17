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
public class BookReturnedException extends Exception {

    /**
     * Creates a new instance of <code>BookReturnedException</code> without
     * detail message.
     */
    public BookReturnedException() {
    }

    /**
     * Constructs an instance of <code>BookReturnedException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BookReturnedException(String msg) {
        super(msg);
    }
}
