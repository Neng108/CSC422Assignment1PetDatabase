/*
Exception for when user's input of pet is not 2 values: the name and the age. 
 */
package com.mycompany.csc422assignment1petdatabase;

public class InvalidInputException extends Exception{
    InvalidInputException(){
        super();
    }
}