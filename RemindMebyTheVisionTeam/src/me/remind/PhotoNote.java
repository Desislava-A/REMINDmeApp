package me.remind;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

public class PhotoNote extends Note
{
    public static final int MIN_SHORTTXT_LENGHT = 3;
    public static final int MAX_SHORTTXT_LENGHT = 40;
    
    private BufferedImage image;
    private String fileName;
    private String text;
    
    public PhotoNote(String title, Date deadline, Priority priority,
                     String shortText, String fileName)
    {
        this(title, deadline, priority, fileName);
        setText(shortText);
    }
    
    public PhotoNote(String title, Date deadline, Priority priority, String fileName)
    {
        super(title, deadline, priority);
        setFileName(fileName);
        setText(null);
    }
    
    private void setText(String shortText)
    {
        if (shortText == null)
            return;
        
        if (shortText.length() < MIN_SHORTTXT_LENGHT || shortText.length() > MAX_SHORTTXT_LENGHT)
            return;
        
        this.text = shortText;
    }
    
    private void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    
    public String getFileName()
    {
        return fileName;
    }
    
    @Override
    public String getTitleWithType()
    {
        return "[PhotoNote]" + "\n\t" + getTitle();
    }
    
    @Override
    public String toString()
    {
        return "[PhotoNote]" + "\n\t" + getTitle();
    }
    
    @Override
    public void showNote()
    {
        try
        {
            image = ImageIO.read(this.getClass().getResource("/res/images/" + fileName));
        } catch (IOException ioex)
        {
            System.err.println(ioex.getMessage());
            ioex.printStackTrace();
        }
        
        ImageIcon imageIcon = new ImageIcon(image);
        JLabel jLabel = new JLabel(imageIcon);
        JFrame frame = new JFrame(getTitle());
        frame.setSize(image.getWidth(), image.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.add(jLabel);
        frame.setVisible(true);
    }
}
