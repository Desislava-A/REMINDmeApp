package me.remind;

import org.joda.time.DateTime;

import java.util.Scanner;

public class TextNote extends Note implements Remindable
{
    private String text;
    
    public TextNote(String title)
    {
        super(title);
        initialize();
    }
    
    public TextNote(String title, DateTime deadline, Priority priority)
    {
        super(title, deadline, priority);
        initialize();
    }
    
    /**
     * Method to initialize the TextNote field
     */
    private void initialize()
    {
        Scanner input = new Scanner(System.in);
        
        System.out.print("\nText: ");
        text = input.nextLine();
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
    public String getTitleWithTypeAndPriority()
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
    public void showNote()
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
