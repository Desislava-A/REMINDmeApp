package me.remind;

import java.util.Calendar;
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
                    System.out.println();
                    Thread.sleep(300);
                    try
                    {
                        if (clipBoard.getAllNotes().size() == 0)
                            throw new IllegalArgumentException("Clipboard empty!");
                    } catch (IllegalArgumentException argex)
                    {
                        System.err.print(argex.getMessage());
                        break;
                    }
                    
                    clipBoard.showAllNotes();
                    
                    System.out.print("\nShow index : ");
                    int index = input.nextInt();
                    input.nextLine();
                    
                    clipBoard.getAllNotes().get(index - 1).showNote();
                    break;
                case 2:
                    System.out.println();
                    Thread.sleep(300);
                    try
                    {
                        if (clipBoard.getRemindableNotes().size() == 0)
                            throw new IllegalArgumentException("No reminders!");
                    } catch (IllegalArgumentException argex)
                    {
                        System.err.print(argex.getMessage());
                        break;
                    }
                    
                    clipBoard.showReminders();
                    input.nextLine();
                    break;
                case 3:
                    System.out.println();
                    Thread.sleep(300);
                    try
                    {
                        if (clipBoard.getArchivedNotes().size() == 0)
                            throw new IllegalArgumentException("No archived notes!");
                    } catch (IllegalArgumentException argex)
                    {
                        System.err.print(argex.getMessage());
                        break;
                    }
                    
                    clipBoard.showArchive();
                    input.nextLine();
                    break;
                case 4:
                    System.out.println();
                    switch (Menus.addSubMenu())
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
                                    
                                    clipBoard.addTextNote(title, deadline.getTime(),
                                            Priority.NONE);
                                    break;
                                }
                                case 2:
                                {
                                    String title;
    
                                    System.out.print("\nTitle: ");
                                    title = input.nextLine();
    
                                    clipBoard.addTextNote(title, null,
                                            Priority.NONE);
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
    
                                    clipBoard.addListNote(title, deadline.getTime(),
                                            Priority.NONE);
                                    break;
                                }
                                case 2:
                                {
                                    String title;
    
                                    System.out.print("\nTitle: ");
                                    title = input.nextLine();
    
                                    clipBoard.addListNote(title, null,
                                            Priority.NONE);
                                    break;
                                }
                                default:
                                    System.out.println("Going back a menu...");
                                    Thread.sleep(300);
                                    break;
                            }
                            break;
                        }
                        case 3:
                            switch (Menus.hasReminderMenu())
                            {
                                case 1:
                                {
                                    String title;
                                    String fileName;
    
                                    Calendar deadline = Calendar.getInstance();
    
                                    System.out.print("\nTitle: ");
                                    title = input.nextLine();
                                    System.out.print("Deadline in days: ");
                                    deadline.add(Calendar.DATE, input.nextInt());
                                    input.nextLine();
                                    System.out.print("File name: ");
                                    fileName = input.nextLine();
    
                                    clipBoard.addVoiceNote(title, deadline.getTime(),
                                            Priority.NONE, fileName);
                                    break;
                                }
                                case 2:
                                {
                                    String title;
                                    String fileName;
    
                                    System.out.print("\nTitle: ");
                                    title = input.nextLine();
                                    System.out.print("File name: ");
                                    fileName = input.nextLine();
    
                                    clipBoard.addVoiceNote(title, null,
                                            Priority.NONE, fileName);
                                    break;
                                }
                                default:
                                    System.out.println("Going back a menu...");
                                    Thread.sleep(150);
                                    break;
                            }
                            break;
                        case 4:
                            switch (Menus.hasReminderMenu())
                            {
                                case 1:
                                {
                                    String title;
                                    String fileName;
                                    String description;
    
                                    Calendar deadline1 = Calendar.getInstance();
    
                                    System.out.print("\nTitle: ");
                                    title = input.nextLine();
                                    System.out.print("Deadline in days: ");
                                    deadline1.add(Calendar.DATE, input.nextInt());
                                    input.nextLine();
                                    System.out.print("File name: ");
                                    fileName = input.nextLine();
                                    System.out.print("Image description: ");
                                    description = input.nextLine();
    
                                    clipBoard.addPhotoNote(title, null, Priority.NONE,
                                            fileName, description);
                                    break;
                                }
                                case 2:
                                {
                                    String title;
                                    String fileName;
                                    String description;
    
                                    System.out.print("\nTitle: ");
                                    title = input.nextLine();
                                    System.out.print("File name: ");
                                    fileName = input.nextLine();
                                    System.out.print("Image description: ");
                                    description = input.nextLine();
    
                                    clipBoard.addPhotoNote(title, null, Priority.NONE,
                                            fileName, description);
                                    break;
                                }
                                default:
                                    System.out.println("Going back a menu...");
                                    Thread.sleep(150);
                                    break;
                            }
                    }
                    break;
                case 5:
                    System.out.println();
                    Thread.sleep(300);
                    try
                    {
                        if (clipBoard.getListNotes().size() == 0)
                            throw new IllegalArgumentException("No list notes!");
                    } catch (IllegalArgumentException argex)
                    {
                        System.err.print(argex.getMessage());
                        break;
                    }
                    
                    clipBoard.showListTitles();
    
                    System.out.print("\nWhich title to edit: ");
                    String title = input.nextLine();
    
                    clipBoard.promptToCheckListItems(title);
                    break;
                case 6:
                    System.out.println();
                    Thread.sleep(300);
                    try
                    {
                        if (clipBoard.getAllNotes().size() == 0)
                            throw new IllegalArgumentException("Clipboard empty!");
                    } catch (IllegalArgumentException argex)
                    {
                        System.err.print(argex.getMessage());
                        break;
                    }
                    
                    clipBoard.showTitles();
                    
                    System.out.print("\nTitle of note to be pinned: ");
                    String titleToBePinned = input.nextLine();
                    clipBoard.pinNote(titleToBePinned);
                    
                    try
                    {
                        if (!clipBoard.isNotePinned(titleToBePinned))
                            throw new FailedPinException("Pin failed");
                    } catch (FailedPinException ex)
                    {
                        System.err.print(ex.getMessage());
                        ex.printStackTrace();
                    }
                    
                    break;
                case 7:
                    System.out.println();
                    Thread.sleep(300);
                    try
                    {
                        if (clipBoard.getPinnedNotes().size() == 0)
                            throw new IllegalArgumentException();
                    } catch (IllegalArgumentException iargex)
                    {
                        System.err.print("\nNo pinned notes");
                        break;
                    }
    
                    clipBoard.showPinned();
                    
                    System.out.print("\nTitle of note to be unpinned: ");
                    String titleToBeUnpinned = input.nextLine();
                    clipBoard.unpinNote(titleToBeUnpinned);
                    break;
                case 8:
                    System.out.println();
                    Thread.sleep(300);
                    try
                    {
                        if (clipBoard.getAllNotes().size() == 0)
                            throw new IllegalArgumentException("Clipboard empty!");
                    } catch (IllegalArgumentException argex)
                    {
                        System.err.print(argex.getMessage());
                        break;
                    }
                    
                    clipBoard.showTitles();
                    
                    System.out.print("\nTitle of note to be archived: ");
                    String titleToBeArchived = input.nextLine();
                    
                    clipBoard.archiveNote(titleToBeArchived);
                    
                    Thread.sleep(300);
                    try
                    {
                        if (clipBoard.isNoteArchived(titleToBeArchived))
                            System.out.print("\nNote archived");
                        else
                            throw new FailedArchiveException("\nArchive failed");
                    } catch (FailedArchiveException farchex)
                    {
                        System.err.print(farchex.getMessage());
                    }
                    
                    break;
                case 9:
                    System.out.print("\nTitle of note to be deleted: ");
                    String titleToBeDeleted = input.nextLine();
                    
                    clipBoard.deleteNote(titleToBeDeleted);
                    break;
                case 10:
                    clipBoard.clearAllNotes();
                    
                    Thread.sleep(300);
                    if (clipBoard.getAllNotes().size() == 0)
                        System.out.print("\nNotes cleared");
                    break;
                case 11:
                    clipBoard.clearArchive();
                    
                    Thread.sleep(300);
                    if (clipBoard.getArchivedNotes().size() == 0)
                        System.out.print("\nArchive cleared");
                    break;
                case 0:
                    System.exit(0);
                    break;
            }
        }
    }
}