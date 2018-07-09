package me.remind;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PhotoNote extends Note
{
    private static final int MIN_SHORTTXT_LENGHT = 3;
    private static final int MAX_SHORTTXT_LENGHT = 40;
    
    private BufferedImage image;
    private String fileName;
    private String description;
    
    public PhotoNote(String title, Priority priority, String fileName, String description)
    {
        this(title, priority, fileName);
        setDescription(description);
    }
    
    public PhotoNote(String title, Priority priority, String fileName)
    {
        super(title, priority);
        setFileName(fileName);
        setDescription(null);
    }
    
    protected void setDescription(String description)
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
    
    protected String getDescription()
    {
        return description;
    }
    
    @Override
    protected String getTitleWithTypeAndPriority()
    {
        return "[PhotoNote] {Priority: " +
                getPriority().toString().toLowerCase() + "}" +
                "\n\t\t" + getTitle();
    }
    
    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof PhotoNote &&
                ((PhotoNote) obj).getUid().equals(this.getUid());
    }
    
    @Override
    public int hashCode()
    {
        return getUid().hashCode();
    }
    
    @Override
    public String toString()
    {
        return "[PhotoNote]" + "\n\t" + getTitle();
    }
    
    @Override
    protected void showNote() throws IOException
    {
        // initializing the buffered image; files are located in /res/images
        image = ImageIO.read(this.getClass().getResource("/res/images/" + fileName));
        
        ImageIcon imageIcon = new ImageIcon(image);
        JLabel jLabel = new JLabel(imageIcon);
        JFrame frame = new JFrame(getTitle());
        frame.setSize(image.getWidth(), image.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        
        frame.add(jLabel);
        frame.setVisible(true);
    }
}
