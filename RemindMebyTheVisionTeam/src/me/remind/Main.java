package me.remind;

public class Main {

    public static void main(String[] args)
    {
        System.out.println("This is Main");
    
        ClipBoard clipBoard = new ClipBoard();
        
        Note textNote = new TextNote("Maths exam", "07.09.2018", "room 1152");
        
        Note list = new ListNote("Urgent meeting","20:00" , Priority.CRITICAL);
        
        
    }
}
