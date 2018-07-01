package me.remind;

public class FailedArchiveException extends Exception
{
    private String message;
    
    public FailedArchiveException(String message)
    {
        this.message = message;
    }
    
    @Override
    public String getMessage()
    {
        return message;
    }
}
