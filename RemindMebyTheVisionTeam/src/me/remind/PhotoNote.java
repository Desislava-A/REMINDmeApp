package me.remind;

import java.awt.*;

public class PhotoNote extends Note
{
    public static final int MIN_SHORTTXT_LENGHT = 3;
    public static final int MAX_SHORTTXT_LENGHT = 40;
    
    private Image photo;
    private String shortText;
    
    public PhotoNote(String title, String deadline, Image photo, Priority priority, String shortText)
    {
        this(title, deadline, photo, priority);
        this.photo = photo;
        setShortText(shortText);
    }
    
    public PhotoNote(String title, String deadline, Image photo, Priority priority)
    {
        super(title, deadline, priority);
        this.photo = photo;
        setShortText(null);
    }
    
    private void setShortText(String shortText)
    {
        if (shortText == null)
        {
            System.err.println("Text should be between 3 and 40 symbols");
            return;
        }
        
        if (shortText.length() < MIN_SHORTTXT_LENGHT || shortText.length() > MAX_SHORTTXT_LENGHT)
        {
            System.err.println("Text should be between 3 and 40 symbols");
            return;
        }
        
        
        this.shortText = shortText;
    }
    
    @Override
    public void showNote(Note note)
    {
        // different implementation for image type
    }
    
    @Override
    public void previewNote()
    {
    
    }
}
