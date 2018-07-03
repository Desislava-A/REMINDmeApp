package me.remind;

public class FailedPinException extends Exception
{
    private String message;
    
    public FailedPinException(String message)
    {
        this.message = message;
    }
    
    @Override
    public String getMessage()
    {
        return message;
    }
}