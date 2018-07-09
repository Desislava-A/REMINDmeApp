package me.remind;

import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextNote extends RemindableNote
{
    private String text;
    
    public TextNote(String title, DateTime deadline, Priority priority)
            throws IOException
    {
        super(title, priority);
        setDeadline(deadline);
        initialize();
    }
    
    protected void setText(String text)
    {
        this.text = text;
    }
    
    protected String getText()
    {
        return text;
    }
    
    /**
     * Method to initialize the TextNote field
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
        return obj instanceof TextNote && ((TextNote) obj).getText().equals(getText());
    }
    
    @Override
    public int hashCode()
    {
        return getUid().hashCode();
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
}
