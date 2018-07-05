package me.remind;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main
{
    private static String title = null;
    private static String fileName = null;
    private static String description = null;
    private static Priority priority = null;
    private static Calendar deadline = null;
    private static DateTime dateTime = null;
    private static boolean flag = false;
    private static final Scanner input = new Scanner(System.in);
    
    private static void getTitle()
    {
        System.out.print("\nTitle: ");
        title = input.nextLine();
    }
    
    private static void getDeadline()
    {
        System.out.print("Deadline in days: ");
        deadline.add(Calendar.DATE, input.nextInt());
        input.nextLine();
    }
    
    private static void getFileName()
    {
        System.out.print("File name: ");
        fileName = input.nextLine();
    }
    
    private static void getImgDescription()
    {
        System.out.print("Image description: ");
        description = input.nextLine();
    }
    
    private static void getPriority()
    {
        System.out.print("Priority: ");
        String priorityStr = input.nextLine();
    
        switch (priorityStr.trim().toLowerCase())
        {
            case "critical":
                priority = Priority.CRITICAL;
                break;
            case "normal":
                priority = Priority.NORMAL;
                break;
            case "none":
                priority = Priority.NONE;
                break;
            default:
                break;
        }
    }
    
    public static void main(String[] args) throws InterruptedException
    {
        ClipBoard clipBoard = new ClipBoard();
        
        while (true)
        {
            System.out.println();
            switch (Menus.mainMenu())
            {
                case 1:
                    try
                    {
                        if (clipBoard.getAllNotes().size() == 0)
                            throw new IllegalStateException("Clipboard empty!");
    
                        clipBoard.showAllNotes();
    
                        System.out.print("\nSelect note number: ");
                        int index = input.nextInt();
                        input.nextLine();
    
                        System.out.println();
                        if (index < 1 || index > clipBoard.getAllNotes().size())
                            throw new IllegalArgumentException("Index not in boundaries!");
    
                        clipBoard.getAllNotes().get(index - 1).showNote();
                        input.nextLine();
                        System.out.println();
                    } catch (IllegalStateException stateex)
                    {
                        System.err.println("\n" + stateex.getMessage());
                        break;
                    } catch (IllegalArgumentException argex)
                    {
                        System.err.println("\n" + argex.getMessage());
                    }
    
                    Thread.sleep(400);
                    break;
                case 2:
                    try
                    {
                        if (clipBoard.getRemindableNotes().size() == 0)
                            throw new IllegalStateException("No reminders!");
                    } catch (IllegalStateException stateex)
                    {
                        System.err.println("\n" + stateex.getMessage());
                        Thread.sleep(400);
                        break;
                    }
                    
                    clipBoard.showReminders();
                    input.nextLine();
                    break;
                case 3:
                    try
                    {
                        if (clipBoard.getArchivedNotes().size() == 0)
                            throw new IllegalStateException("No archived notes!");
                    } catch (IllegalStateException stateex)
                    {
                        System.err.println("\n" + stateex.getMessage());
                        Thread.sleep(400);
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
                                    deadline = Calendar.getInstance();
                                    
                                    getTitle();
                                    getDeadline();
                                    getPriority();
                                    
                                    dateTime = new DateTime(deadline.getTimeInMillis());
                                    
                                    clipBoard.addTextNote(title, dateTime, priority);
                                    break;
                                }
                                case 2:
                                {
                                    getTitle();
                                    getPriority();
                                    
                                    clipBoard.addTextNote(title, null, priority);
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
                                    deadline = Calendar.getInstance();
                                    
                                    getTitle();
                                    getDeadline();
                                    getPriority();
    
                                    dateTime = new DateTime(deadline.getTimeInMillis());
                                    
                                    clipBoard.addListNote(title, dateTime, priority);
                                    break;
                                }
                                case 2:
                                {
                                    getTitle();
                                    getPriority();
                                    
                                    clipBoard.addListNote(title, null, priority);
                                    break;
                                }
                                case 0:
                                    break;
                            }
                            break;
                        }
                        case 3:
                            switch (Menus.hasReminderMenu())
                            {
                                case 1:
                                {
                                    deadline = Calendar.getInstance();
                                    
                                    getTitle();
                                    getDeadline();
                                    getPriority();
                                    getFileName();
    
                                    dateTime = new DateTime(deadline.getTimeInMillis());
                                    
                                    clipBoard.addVoiceNote(title, dateTime, priority, fileName);
                                    break;
                                }
                                case 2:
                                {
                                    getTitle();
                                    getPriority();
                                    getFileName();
                                    
                                    clipBoard.addVoiceNote(title, null,
                                            priority, fileName);
                                    break;
                                }
                                case 0:
                                    break;
                            }
                            break;
                        case 4:
                            switch (Menus.hasReminderMenu())
                            {
                                case 1:
                                {
                                    deadline = Calendar.getInstance();
                                    
                                    getTitle();
                                    getDeadline();
                                    getPriority();
                                    getFileName();
                                    getImgDescription();
    
                                    dateTime = new DateTime(deadline.getTimeInMillis());
                                    
                                    clipBoard.addPhotoNote(title, dateTime, priority,
                                            fileName, description);
                                    break;
                                }
                                case 2:
                                {
                                    getTitle();
                                    getPriority();
                                    getFileName();
                                    getImgDescription();
                                    
                                    clipBoard.addPhotoNote(title, null, priority,
                                            fileName, description);
                                    break;
                                }
                                case 0:
                                    break;
                            }
                    }
                    break;
                case 5:
                    try
                    {
                        if (clipBoard.getAllNotes().size() == 0)
                            throw new IllegalStateException("Clipboard empty!");
                        
                        System.out.print("\nTitle to search: ");
                        title = input.nextLine();
                        
                        clipBoard.search(title);
                    } catch (IllegalStateException stateex)
                    {
                        System.err.print("\n" + stateex.getMessage());
                    } catch (NoSuchElementException notfoundex)
                    {
                        System.err.println("Note not found!");
                    }
                    
                    Thread.sleep(400);
                    break;
                case 6:
                    System.out.println();
                    try
                    {
                        if (clipBoard.getListNotes().size() == 0)
                            throw new IllegalStateException("No list notes!");
                        
                        clipBoard.showListTitles();
                        
                        System.out.println("\nWhich title to edit: ");
                        title = input.nextLine();
                        
                        clipBoard.promptToCheckListItems(title);
                    } catch (IllegalStateException stateex)
                    {
                        System.err.println(stateex.getMessage());
                    } catch (NoSuchElementException notfoundex)
                    {
                        System.err.println("Note not found!");
                    }
    
                    Thread.sleep(400);
                    break;
                case 7:
                    System.out.println();
                    try
                    {
                        if (clipBoard.getAllNotes().size() == 0)
                            throw new IllegalStateException("Clipboard empty!");
                        
                        clipBoard.showTitles();
                        
                        System.out.print("\nTitle of note to be pinned: ");
                        String titleToBePinned = input.nextLine();
                        
                        clipBoard.pinNote(titleToBePinned);
                        
                        if (!clipBoard.isNotePinned(titleToBePinned))
                            throw new FailedPinException("Pin failed");
                    } catch (IllegalStateException stateex)
                    {
                        System.err.println(stateex.getMessage());
                    } catch (NoSuchElementException notfoundex)
                    {
                        System.err.println("Note not found!");
                    } catch (FailedPinException pinex)
                    {
                        System.err.println(pinex.getMessage());
                    }
    
                    Thread.sleep(400);
                    break;
                case 8:
                    try
                    {
                        if (clipBoard.getPinnedNotes().size() == 0)
                            throw new IllegalStateException("There aren't any pinned notes!");
                        
                        clipBoard.showPinned();
                        
                        System.out.println("\nTitle of note to be unpinned: ");
                        String titleToBeUnpinned = input.nextLine();
                        
                        clipBoard.unpinNote(titleToBeUnpinned);
                    } catch (IllegalStateException stateex)
                    {
                        System.err.println("\n" + stateex.getMessage());
                    } catch (NoSuchElementException notfoundex)
                    {
                        System.err.println("Note not found!");
                    }
    
                    Thread.sleep(400);
                    break;
                case 9:
                    System.out.println();
                    try
                    {
                        if (clipBoard.getAllNotes().size() == 0)
                            throw new IllegalStateException("Clipboard empty!");
                        
                        clipBoard.showTitles();
                        
                        System.out.print("\nTitle of note to be archived: ");
                        String titleToBeArchived = input.nextLine();
                        
                        clipBoard.archiveNote(titleToBeArchived);
                        
                        if (clipBoard.isNoteArchived(titleToBeArchived))
                            System.out.print("\nNote archived");
                        else
                            throw new FailedArchiveException("\nArchive failed");
                    } catch (IllegalStateException stateex)
                    {
                        System.err.println(stateex.getMessage());
                    } catch (FailedArchiveException farchex)
                    {
                        System.err.println(farchex.getMessage());
                    }
    
                    Thread.sleep(400);
                    break;
                case 10:
                    try
                    {
                        if (clipBoard.getAllNotes().size() == 0)
                            throw new IllegalStateException("\nClipboard empty!\n");
                        
                        clipBoard.showTitles();
                        
                        System.out.print("\nTitle of note to be deleted: ");
                        String titleToBeDeleted = input.nextLine();
                        
                        clipBoard.deleteNote(titleToBeDeleted);
                    } catch (NoSuchElementException notfoundex)
                    {
                        System.err.println("Note not found!");
                    } catch (IllegalStateException stateex)
                    {
                        System.err.print(stateex.getMessage());
                    }
    
                    Thread.sleep(400);
                    break;
                case 11:
                    flag = false;
                    
                    try
                    {
                        clipBoard.clearAllNotes();
                    } catch (IllegalStateException stateex)
                    {
                        System.err.println("\n" + stateex.getMessage());
                        flag = true;
                        Thread.sleep(400);
                        break;
                    }
                    
                    if (clipBoard.getAllNotes().size() == 0 && flag)
                        System.out.println("\nNotes cleared");
                    
                    break;
                case 12:
                    flag = false;
                    
                    try
                    {
                        clipBoard.clearArchive();
                    } catch (IllegalStateException stateex)
                    {
                        System.err.println("\n" + stateex.getMessage());
                        flag = true;
                        Thread.sleep(400);
                        break;
                    }
                    
                    if (clipBoard.getArchivedNotes().size() == 0 && flag)
                        System.out.println("\nArchive cleared");
                    
                    break;
                case 0:
                    if (input != null)
                        input.close();
                    
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }
}