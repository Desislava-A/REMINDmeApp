package me.remind;

public class Item
{
    public static final int MIN_TEXT_LENGTH = 3;
    public static final int MAX_TEXT_LENGTH = 60;
    
    private String itemText;
    private Check check;
    
    public Item(String itemText, Check check)
    {
        setItemText(itemText);
        setCheck(check);
    }
    
    public Check getCheck()
    {
        return check;
    }
    
    private void setCheck(Check check)
    {
        this.check = check;
    }
    
    private void setItemText(String itemText)
    {
        if (itemText == null)
            return;
        
        if (itemText.length() < MIN_TEXT_LENGTH|| itemText.length() > MAX_TEXT_LENGTH)
            return;
        
        this.itemText = itemText;
    }
    
    public String getItemText()
    {
        return itemText;
    }
}
