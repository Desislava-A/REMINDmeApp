package me.remind;

import org.joda.time.DateTime;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PhotoNote extends Note
{
    public static final int MIN_SHORTTXT_LENGHT = 3;
    public static final int MAX_SHORTTXT_LENGHT = 40;
    
    private BufferedImage image;
    private String fileName;
    private String description;
    
    public PhotoNote(String title, DateTime deadline, Priority priority,
                     String fileName, String description)
    {
        this(title, deadline, priority, fileName);
        setDescription(description);
    }
    
    public PhotoNote(String title, DateTime deadline, Priority priority, String fileName)
    {
        super(title, deadline, priority);
        setFileName(fileName);
        setDescription(null);
    }
    
    private void setDescription(String description)
    {
        if (description == null)
            return;
        
        if (description.length() < MIN_SHORTTXT_LENGHT ||
                description.length() > MAX_SHORTTXT_LENGHT)
            return;
        
        this.description = description;
    }
    
    private void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    
    protected String getFileName()
    {
        return fileName;
    }
    
    @Override
    protected String getTitleWithTypeAndPriority()
    {
        return "[PhotoNote] {Priority: " + getPriority().toString().toLowerCase() + "}" +
                "\n\t\t" + getTitle();
    }
    
    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof PhotoNote &&
                ((PhotoNote) obj).getTitle().equals(getTitle()) &&
                ((PhotoNote) obj).fileName.equals(fileName) &&
                ((PhotoNote) obj).getPriority().equals(getPriority()) &&
                ((PhotoNote) obj).description.equals(description);
    }
    
    @Override
    public int hashCode()
    {
        return fileName.hashCode();
    }
    
    @Override
    public String toString()
    {
        return "[PhotoNote]" + "\n\t" + getTitle();
    }
    
    @Override
    protected void showNote()
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
