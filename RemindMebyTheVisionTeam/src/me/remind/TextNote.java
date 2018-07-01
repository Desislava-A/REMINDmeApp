package me.remind;

import java.util.Calendar;
import java.util.Scanner;

public class TextNote extends Note implements Remindable
{
    private String text;
    
    public TextNote(String title)
    {
        super(title);
        initialize();
    }
    
    public TextNote(String title, Calendar deadline, Priority priority)
    {
        super(title, deadline);
        initialize();
    }
    
    private void initialize()
    {
        Scanner input = new Scanner(System.in);
        
        System.out.print("\nText: ");
        text =  input.nextLine();
    }
    
    @Override
    public void showNote()
    {
        System.out.print(text);
    }
    
    @Override
    public void remind()
    {
        showNote();
        System.out.println(" [Reminder set in " + getDaysToDeadline() + " days]");
    }
}
