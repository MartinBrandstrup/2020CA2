/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Brandstrup
 */
public class DatabaseException extends Exception
{
    public DatabaseException(String msg)
    {
        super(msg);
    }
    
}
