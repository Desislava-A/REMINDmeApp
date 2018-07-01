package me.remind;

import java.util.Calendar;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        
        ClipBoard clipBoard = new ClipBoard();
        
        while (true)
        {
            System.out.println("\n");
            switch (Menus.mainMenu())
            {
                case 1:
                    clipBoard.showAllNotes();
                    input.nextLine();
                    break;
                case 2:
                    clipBoard.showReminders();
                    input.nextLine();
                    break;
                case 3:
                    System.out.println();
                    switch (Menus.addMenu())
                    {
                        case 1:
                        {
                            switch (Menus.hasReminderMenu())
                            {
                                case 1:
                                {
                                    String title;
                                    
                                    Calendar deadline = Calendar.getInstance();
                                    
                                    System.out.print("\nTitle: ");
                                    title = input.nextLine();
                                    System.out.print("Deadline in days: ");
                                    deadline.add(Calendar.DATE, input.nextInt());
                                    input.nextLine();
                                    
                                    clipBoard.addTextNote(title, deadline, Priority.NONE);
                                    break;
                                }
                                case 2:
                                {
                                    String title;
                                    
                                    System.out.print("\nTitle: ");
                                    title = input.nextLine();
                                    
                                    clipBoard.addTextNote(title, null, Priority.NONE);
                                    break;
                                }
                            }
                            break;
                        }
                        case 2:
                        {
                            switch (Menus.hasReminderMenu())
                            {
                                case 1:
                                {
                                    String title;
    
                                    Calendar deadline = Calendar.getInstance();
    
                                    System.out.print("\nTitle: ");
                                    title = input.nextLine();
                                    System.out.print("Deadline in days: ");
                                    deadline.add(Calendar.DATE, input.nextInt());
                                    input.nextLine();
    
                                    clipBoard.addListNote(title, deadline, Priority.NONE);
                                    break;
                                }
                                case 2:
                                    String title;
    
                                    System.out.print("\nTitle: ");
                                    title = input.nextLine();
    
                                    clipBoard.addListNote(title, null, Priority.NONE);
                                    break;
                                case 0:
                                    break;
                            }
                            break;
                        }
                        case 4:
                            break;
                        case 0:
                            break;
                    }
                    break;
                    case 4:
                    clipBoard.showTitlesForPin();
    
                    System.out.print("\nTitle of note to be pinned: ");
                    String titleToBePinned = input.nextLine();
                    clipBoard.pinNote(titleToBePinned);
    
                    try
                    {
                        if (clipBoard.isNotePinned(titleToBePinned))
                            System.out.println("The note was pinned!");
                        else
                            throw new FailedPinException("Pin failed");
                    } catch (FailedPinException fpex)
                    {
                        System.err.println(fpex.getMessage());
                    }
                    
                    break;
                case 5:
                    break;
                case 0:
                    System.exit(0);
                    break;
            }
        }
    }
}
