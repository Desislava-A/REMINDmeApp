package me.remind;

import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main
{
    private static String title = null;
    private static String fileName = null;
    private static String description = null;
    private static Calendar deadline = null;
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
    
    public static void main(String[] args) throws InterruptedException
    {
        ClipBoard clipBoard = new ClipBoard();
        
        while (true)
        {
            System.out.println();
            switch (Menus.mainMenu())
            {
                case 1:
                    System.out.println();
                    try
                    {
                        if (clipBoard.getAllNotes().size() == 0)
                            throw new IllegalStateException("Clipboard empty!");
                    } catch (IllegalStateException stateex)
                    {
                        System.err.println(stateex.getMessage());
                        Thread.sleep(400);
                        break;
                    }
                    
                    clipBoard.showAllNotes();
                    
                    System.out.print("\nSelect note number: ");
                    int index = input.nextInt();
                    input.nextLine();
                    
                    clipBoard.getAllNotes().get(index - 1).showNote();
                    break;
                case 2:
                    System.out.println();
                    try
                    {
                        if (clipBoard.getRemindableNotes().size() == 0)
                            throw new IllegalStateException("No reminders!");
                    } catch (IllegalStateException stateex)
                    {
                        System.err.println(stateex.getMessage());
                        Thread.sleep(400);
                        break;
                    }
                    
                    clipBoard.showReminders();
                    input.nextLine();
                    break;
                case 3:
                    System.out.println();
                    try
                    {
                        if (clipBoard.getArchivedNotes().size() == 0)
                            throw new IllegalStateException("No archived notes!");
                    } catch (IllegalStateException stateex)
                    {
                        System.err.println(stateex.getMessage());
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
                                    getTitle();
                                    getDeadline();
                                    
                                    clipBoard.addTextNote(title, deadline.getTime(),
                                            Priority.NONE);
                                    break;
                                }
                                case 2:
                                {
                                    getTitle();
                                    
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
                                    deadline = Calendar.getInstance();
                                    
                                    getTitle();
                                    getDeadline();
                                    
                                    clipBoard.addListNote(title, deadline.getTime(),
                                            Priority.NONE);
                                    break;
                                }
                                case 2:
                                {
                                    getTitle();
                                    
                                    clipBoard.addListNote(title, null,
                                            Priority.NONE);
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
                                    getFileName();
                                    
                                    clipBoard.addVoiceNote(title, deadline.getTime(),
                                            Priority.NONE, fileName);
                                    break;
                                }
                                case 2:
                                {
                                    getTitle();
                                    getFileName();
                                    
                                    clipBoard.addVoiceNote(title, null,
                                            Priority.NONE, fileName);
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
                                    getFileName();
                                    getImgDescription();
                                    
                                    clipBoard.addPhotoNote(title, null, Priority.NONE,
                                            fileName, description);
                                    break;
                                }
                                case 2:
                                {
                                    getTitle();
                                    getFileName();
                                    getImgDescription();
                                    
                                    clipBoard.addPhotoNote(title, null, Priority.NONE,
                                            fileName, description);
                                    break;
                                }
                                case 0:
                                    break;
                            }
                    }
                    break;
                case 5:
                    System.out.println();
                    try
                    {
                        if (clipBoard.getAllNotes().size() == 0)
                            throw new IllegalStateException("Clipboard empty!");
                        
                        System.out.print("\nTitle to search: ");
                        title = input.nextLine();
                        
                        clipBoard.search(title);
                    } catch (IllegalStateException stateex)
                    {
                        System.err.print(stateex.getMessage());
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
                    System.out.println();
                    try
                    {
                        if (clipBoard.getPinnedNotes().size() == 0)
                            throw new IllegalStateException("\nThere aren't any pinned notes!");
                        
                        clipBoard.showPinned();
                        
                        System.out.println("\nTitle of note to be unpinned: ");
                        String titleToBeUnpinned = input.nextLine();
                        
                        clipBoard.unpinNote(titleToBeUnpinned);
                    } catch (IllegalStateException stateex)
                    {
                        System.err.println(stateex.getMessage());
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