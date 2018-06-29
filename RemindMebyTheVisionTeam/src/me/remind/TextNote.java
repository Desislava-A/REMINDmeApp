package me.remind;

public class TextNote extends Note implements Remindable
{
    private String text;
    
    TextNote(String title, String deadline, String text)
    {
        super(title, deadline);
        this.text = text;
    }
    
    @Override
    public void makeNote()
    {
    
    }
    
    @Override
    public void previewNote()
    {
    
    }
    
    @Override
    public void remind()
    {
    
    }
}
