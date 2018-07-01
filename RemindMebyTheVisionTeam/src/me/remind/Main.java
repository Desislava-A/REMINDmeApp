package me.remind;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws InterruptedException
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
                        // WIP
                        case 3:
                            break;
                        case 4:
                            break;
                        case 0:
                            break;
                    }
                    break;
                case 4:
                    clipBoard.showTitles();
                    
                    System.out.print("\nTitle of note to be pinned: ");
                    String titleToBePinned = input.nextLine();
                    
                    clipBoard.pinNote(titleToBePinned);
    
                    Thread.sleep(250);
                    try
                    {
                        if (clipBoard.isNotePinned(titleToBePinned))
                            System.out.print("\nThe note was pinned!");
                        else
                            throw new FailedPinException("\nPin failed");
                    } catch (FailedPinException fpex)
                    {
                        System.err.println(fpex.getMessage());
                    }
                    break;
                case 5:
                    clipBoard.showPinnedTitles();
    
                    try
                    {
                        if (clipBoard.getPinnedNotes().size() == 0)
                            throw new IllegalArgumentException();
                    } catch (IllegalArgumentException iargex)
                    {
                        Thread.sleep(250);
                        System.err.println("\nPinned notes list empty");
                        break;
                    }
                    
                    System.out.print("\nTitle of note to be unpinned: ");
                    String titleToBeUnpinned = input.nextLine();
                    clipBoard.unpinNote(titleToBeUnpinned);
                    break;
                case 6:
                    clipBoard.showTitles();
    
                    System.out.print("\nTitle of note to be archived: ");
                    String titleToBeArchived = input.nextLine();
                    
                    clipBoard.archiveNote(titleToBeArchived);
    
                    Thread.sleep(300);
                    try
                    {
                        if (clipBoard.isNoteArchived(titleToBeArchived))
                            System.out.println("Note archived");
                        else
                            throw new FailedArchiveException("\nArchive failed");
                    } catch (FailedArchiveException farchex)
                    {
                        System.err.println(farchex.getMessage());
                    }
                    break;
                case 7:
                    System.out.print("\nTitle of note to be deleted: ");
                    String titleToBeDeleted = input.nextLine();
                    
                    clipBoard.deleteNote(titleToBeDeleted);
                    break;
                case 8:
                    clipBoard.clearAllNotes();
    
                    Thread.sleep(300);
                    if (clipBoard.getAllNotes().size() == 0)
                        System.out.println("\nNotes cleared");
                    break;
                case 9:
                    clipBoard.clearArchive();
    
                    Thread.sleep(300);
                    if (clipBoard.getArchivedNotes().size() == 0)
                        System.out.println("\nArchive cleared");
                    break;
                case 0:
                    System.exit(0);
                    break;
            }
        }
    }
}