package me.remind;

public class TextNote extends Note implements Remindable
{
    private String text;
    
    TextNote(String title, String deadline, String text, Priority priority)
    {
        super(title, deadline,priority);
        this.text = text;
    }
    
    @Override
    public void showNote()
    {
        System.out.printf("%s: %s \n", super.toString(), text);
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
