package me.remind;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class PhotoNote extends Note
{
    public static final int MIN_SHORTTXT_LENGHT = 3;
    public static final int MAX_SHORTTXT_LENGHT = 40;
    
    private Image photo;
    private String shortText;
    
    public PhotoNote(String title, Date deadline, Image photo, Priority priority, String shortText)
    {
        this(title, deadline, photo, priority);
        //this.photo = photo;
        initializeImage(); // reading the photo by file path input from user
        setShortText(shortText);
    }
    
    public PhotoNote(String title, Date deadline, Image photo, Priority priority)
    {
        super(title, deadline, priority);
        //this.photo = photo;
        initializeImage(); // reading the photo by file path input from user
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

    /**
     * Method to initialize the Image photo field
     */
    private void initializeImage()
    {
        Scanner input = new Scanner(System.in);
        System.out.print("\nFile path: ");
        String filePath= input.nextLine();
        try{
            photo = ImageIO.read(new File(filePath));
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
    
    @Override
    public void showNote()
    {
        // different implementation for image type
    }
}
