/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wintrisstech.erik.iaroc;

/**
 *
 * @author Rob
 */
public interface JackState {

    JackState bothBump();

    JackState leftBump();

    JackState rightBump();
    
    JackState noBump();
    
    JackState action();
    
}
