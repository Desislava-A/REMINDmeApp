package me.remind;

public class FailedArchiveException extends Exception
{
    public FailedArchiveException()
    {
        super("Note archiving was unsuccessful");
    }
}
