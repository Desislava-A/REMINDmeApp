package me.remind;

public class FailedPinException extends Exception
{
    String message;
    
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
