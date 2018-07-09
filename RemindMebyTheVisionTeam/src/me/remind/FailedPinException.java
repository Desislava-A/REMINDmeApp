package me.remind;

public class FailedPinException extends Exception
{
    public FailedPinException()
    {
        super("Note pinning was unsuccessful");
    }
}
