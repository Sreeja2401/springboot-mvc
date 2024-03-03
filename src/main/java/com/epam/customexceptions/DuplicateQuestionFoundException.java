package com.epam.customexceptions;

public class DuplicateQuestionFoundException extends Exception{
    public DuplicateQuestionFoundException (String message)
    {
        super(message);
    }
}
