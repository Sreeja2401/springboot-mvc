package com.epam.customexceptions;

public class DuplicateQuizFoundException extends Exception{
    public DuplicateQuizFoundException (String message)
    {
        super(message);
    }
}
