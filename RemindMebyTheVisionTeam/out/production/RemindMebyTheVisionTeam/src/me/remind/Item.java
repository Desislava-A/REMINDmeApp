package me.remind;

import java.io.Serializable;

public class Item implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public static final int MIN_TEXT_LENGTH = 3;
    public static final int MAX_TEXT_LENGTH = 60;
    
    private String itemText;
    private Check check;
    
    public Item()
    {
    }
    
    public Item(String itemText, Check check)
    {
        setItemText(itemText);
        setCheck(check);
    }
    
    protected Check getCheck()
    {
        return check;
    }
    
    protected void setCheck(Check check)
    {
        this.check = check;
    }
    
    protected void setItemText(String itemText)
    {
        if (itemText == null)
            return;
        
        if (itemText.length() < MIN_TEXT_LENGTH || itemText.length() > MAX_TEXT_LENGTH)
            return;
        
        this.itemText = itemText;
    }
    
    protected String getItemText()
    {
        return itemText;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Item && ((Item) obj).itemText.equals(itemText);
    }
    
    @Override
    public int hashCode()
    {
        return itemText.hashCode();
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append(itemText);
        
        if (check == Check.CHECKED)
            sb.append(" 1");
        else
            sb.append(" 0");
        
        return sb.toString();
    }
}
