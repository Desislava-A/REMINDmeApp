package me.remind;

import java.awt.*;
import java.util.Calendar;

public class PhotoNote extends Note
{
    public static final int MIN_SHORTTXT_LENGHT = 3;
    public static final int MAX_SHORTTXT_LENGHT = 40;
    
    private Image photo;
    private String shortText;
    
    public PhotoNote(String title, Calendar deadline, Image photo, Priority priority, String shortText)
    {
        this(title, deadline, photo, priority);
        this.photo = photo;
        setShortText(shortText);
    }
    
    public PhotoNote(String title, Calendar deadline, Image photo, Priority priority)
    {
        super(title, deadline, priority);
        this.photo = photo;
        setShortText(null);
    }
    
    private void setShortText(String shortText)
    {
        if (shortText == null)
            return;
        
        if (shortText.length() < MIN_SHORTTXT_LENGHT || shortText.length() > MAX_SHORTTXT_LENGHT)
            return;
        
        this.shortText = shortText;
    }
    
    @Override
    public void showNote()
    {
        // different implementation for image type
    }
}
