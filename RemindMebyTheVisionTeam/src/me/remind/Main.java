package me.remind;

public class Main
{
    public static void main(String[] args)
    {
        ClipBoard clipBoard = new ClipBoard();
        
        Note textNote = new TextNote("Maths exam", "07.09.2018", "room 1152",Priority.CRITICAL);
        
        ListNote list = new ListNote("Urgent meeting","20:00" , Priority.CRITICAL);

        textNote.showNote(textNote);
        
        list.showNote(list);
        list.promptToCheck();
        list.showNote(list);
    }
}
