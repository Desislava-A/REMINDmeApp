package me.remind;

import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextNote extends Note implements Remindable
{
    private String text;
    
    public TextNote(String title, DateTime deadline, Priority priority) throws IOException
    {
        super(title, deadline, priority);
        initialize();
    }
    
    /**
     * Method to initialize the TextNote field
     *
     */
    private void initialize() throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.print("\nText: ");
        text = br.readLine();
    }
    
    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof TextNote &&
                ((TextNote) obj).getTitle().equals(getTitle()) &&
                ((TextNote) obj).text.equals(text) &&
                ((TextNote) obj).getPriority().equals(getPriority());
    }
    
    @Override
    public int hashCode()
    {
        return text.hashCode();
    }
    
    @Override
    protected String getTitleWithTypeAndPriority()
    {
        return "[TextNote] {Priority: " + getPriority().toString().toLowerCase() + "}" +
                "\n\t\t" + getTitle();
    }
    
    @Override
    public String toString()
    {
        return getTitle();
    }
    
    @Override
    protected void showNote()
    {
        System.out.println("[TextNote]");
        System.out.print("\t" + text);
    }
    
    @Override
    public void remind()
    {
        System.out.print(" ->[Reminder set in " + getHoursToDeadline() + " hours]\n");
    }
}
