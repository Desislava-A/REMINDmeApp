package me.remind;

import org.joda.time.DateTime;

import java.io.*;
import java.util.*;

public class Main
{
    private static StringBuilder title = new StringBuilder();
    private static StringBuilder fileName = new StringBuilder();
    private static StringBuilder description = new StringBuilder();
    private static Priority priority = null;
    private static Calendar deadline = null;
    private static DateTime dateTime = null;
    private static boolean flag = false;
    private static ClipboardFileManager cfm = new ClipboardFileManager();
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    private static void getTitle() throws IOException
    {
        System.out.print("\nTitle: ");
        title.append(br.readLine());
    }
    
    private static void getDeadline() throws IOException
    {
        System.out.print("Deadline in days: ");
        deadline.add(Calendar.DATE, Integer.parseInt(br.readLine()));
    }
    
    private static void getFileName() throws IOException
    {
        System.out.print("File name: ");
        fileName.append(br.readLine());
    }
    
    private static void getImgDescription() throws IOException
    {
        System.out.print("Image description: ");
        description.append(br.readLine());
    }
    
    private static void getPriority() throws IOException
    {
        String testPriorityInput = null;
        boolean incorrectPriorityInput = true;
        
        do
        {
            System.out.println("\n[Priority choice]");
            System.out.println("Please choose one of the following: ");
            System.out.println("\t->[none, normal or critical]\n");
            System.out.print("Priority: ");
            String priorityStr = br.readLine();
            
            testPriorityInput = priorityStr.trim().toLowerCase();
            
            switch (testPriorityInput)
            {
                case "critical":
                    priority = Priority.CRITICAL;
                    incorrectPriorityInput = false;
                    break;
                case "normal":
                    priority = Priority.NORMAL;
                    incorrectPriorityInput = false;
                    break;
                case "none":
                    priority = Priority.NONE;
                    incorrectPriorityInput = false;
                    break;
                default:
                    break;
            }
        } while (incorrectPriorityInput);
    }
    
    public static void main(String[] args) throws Exception
    {
        ClipBoard clipBoard = cfm.loadClipboard();
        
        while (true)
        {
            System.out.println();
            switch (Menus.mainMenu())
            {
                // 1.All notes
                case 1:
                {
                    int index = 0;
                    try
                    {
                        if (clipBoard.getAllNotes().size() == 0)
                            throw new IllegalStateException("Clipboard empty!");
                        
                        clipBoard.showAllNotes();
                        
                        System.out.print("\nSelect note: ");
                        index = Integer.parseInt(br.readLine());
                        
                        System.out.println();
                        if (index < 1 || index > clipBoard.getAllNotes().size())
                            throw new IllegalArgumentException("Index not in boundaries!");
                    } catch (IllegalStateException ex)
                    {
                        System.err.print("\n" + ex.getMessage());
                        Thread.sleep(400);
                        break;
                    } catch (IllegalArgumentException ex2)
                    {
                        System.err.print("\n" + ex2.getMessage());
                        Thread.sleep(400);
                        break;
                    }
                    
                    System.out.println();
                    List<Note> list = List.copyOf(clipBoard.getAllNotes().keySet());
                    switch (Menus.selectMainSubMenu())
                    {
                        // Show notes
                        case 1:
                            list.get(index - 1).showNote();
                            System.out.println(list.get(index - 1).getUid());
                            Thread.sleep(400);
                            break;
                        // 2.Edit notes
                        case 2:
                            Note selected = list.get(index - 1);
                            switch (Menus.editSubMenu())
                            {
                                // 1.Title field
                                case 1:
                                    System.out.println("Previous title: " +
                                            selected.getTitle());
                                    getTitle();
                                    clipBoard.editTitle(selected, title.toString());
                                    
                                    title.setLength(0);
                                    System.out.println("New title saved\n");
                                    break;
                                // 2.TextNote field
                                case 2:
                                    if (selected instanceof TextNote)
                                    {
                                        System.out.println("Previous text: " +
                                                ((TextNote) selected).getText());
                                        System.out.print("New text: ");
                                        clipBoard.editText(selected, br.readLine());
                                        
                                        System.out.println("Text edited and saved\n");
                                        Thread.sleep(400);
                                        break;
                                    } else if (selected instanceof PhotoNote)
                                    {
                                        System.out.println("Previous image description: " +
                                                ((PhotoNote) selected).getDescription());
                                        getImgDescription();
                                        clipBoard.editDescription(selected,
                                                description.toString());
                                        
                                        description.setLength(0);
                                        System.out.println("New description saved\n");
                                    }
                                    break;
                                // 3.The deadline field
                                case 3:
                                    if (selected instanceof Remindable)
                                    {
                                        System.out.println("Previous deadline is set for " +
                                                ((Remindable) selected).getHoursToDeadline(
                                                        ((Remindable) selected).getDeadline()) + " hours");
                                        getDeadline();
                                        clipBoard.editDeadline((Remindable) selected,
                                                new DateTime(deadline.getTimeInMillis()));
                                        
                                        System.out.println("New deadline set for " +
                                                ((Remindable) selected).getHoursToDeadline(
                                                        ((Remindable) selected).getDeadline()) + " hours");
                                    }
                                    break;
                                // 4.The priority field
                                case 4:
                                    System.out.println("Previous priority: " +
                                            selected.getPriority().toString().toLowerCase());
                                    getPriority();
                                    clipBoard.editPriority(selected, priority);
                                    break;
                            }
                            Thread.sleep(400);
                            break;
                        // 3.Delete a note
                        case 3:
                            Note toBeDeleted = list.get(index - 1);
                            clipBoard.deleteNote(toBeDeleted);
                            Thread.sleep(400);
                            break;
                        // 5.Pin/Unpin
                        case 4:
                            Note pinnedOrUnpinned = list.get(index - 1);
                            if (pinnedOrUnpinned.isPinned())
                            {
                                clipBoard.unpinNote(pinnedOrUnpinned);
                                System.out.println("\nNote unpinned!");
                            } else
                            {
                                clipBoard.pinNote(pinnedOrUnpinned);
                                System.out.println("\nNote pinned!");
                            }
                            Thread.sleep(400);
                            break;
                        //5.Archive/Restore
                        case 5:
                            Note archiveOrRestore = list.get(index - 1);
                            if (archiveOrRestore.isArchived())
                            {
                                clipBoard.restoreNoteFromArchive(archiveOrRestore);
                                System.out.println("Note restored!\n");
                            } else
                            {
                                clipBoard.archiveNote(archiveOrRestore);
                                System.out.println("Note archived!\n");
                            }
                            Thread.sleep(400);
                            break;
                        // 6. Change list items status
                        case 6:
                        {
                            Note changeList = list.get(index - 1);
                            try
                            {
                                clipBoard.promptToCheckListItems(((ListNote) changeList));
                            } catch (InvalidObjectException ex)
                            {
                                System.err.println("\n" + ex.getMessage());
                            }
                            
                            Thread.sleep(400);
                            break;
                        }
                        case 0:
                            break;
                    }
                    break;
                }
                //2.Reminders
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
                    br.readLine();
                    break;
                //4. Add menu
                case 3:
                    System.out.println();
                    switch (Menus.addSubMenu())
                    {
                        case 1:
                        {
                            switch (Menus.hasReminderMenu())
                            {
                                // 1.1.Text note reminder ON
                                case 1:
                                {
                                    deadline = Calendar.getInstance();
                                    
                                    getTitle();
                                    getDeadline();
                                    getPriority();
                                    
                                    dateTime = new DateTime(deadline.getTimeInMillis());
                                    
                                    clipBoard.addTextNote(title.toString(), dateTime, priority);
                                    
                                    title.setLength(0);
                                    break;
                                }
                                // 1.2.Text note reminder OFF
                                case 2:
                                {
                                    getTitle();
                                    getPriority();
                                    
                                    clipBoard.addTextNote(title.toString(), null, priority);
                                    
                                    title.setLength(0);
                                    break;
                                }
                            }
                            break;
                        }
                        // 2.CheckboxList Note
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
                                    
                                    clipBoard.addListNote(title.toString(), dateTime, priority);
                                    
                                    title.setLength(0);
                                    break;
                                }
                                case 2:
                                {
                                    getTitle();
                                    getPriority();
                                    
                                    clipBoard.addListNote(title.toString(), null, priority);
                                    
                                    title.setLength(0);
                                    break;
                                }
                                case 0:
                                    break;
                            }
                            break;
                        }
                        // 3.Voice Note
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
                                    
                                    clipBoard.addVoiceNote(title.toString(), dateTime,
                                            priority, fileName.toString());
                                    
                                    title.setLength(0);
                                    fileName.setLength(0);
                                    Thread.sleep(400);
                                    break;
                                }
                                case 2:
                                {
                                    getTitle();
                                    getPriority();
                                    getFileName();
                                    
                                    clipBoard.addVoiceNote(title.toString(), null,
                                            priority, fileName.toString());
                                    
                                    title.setLength(0);
                                    fileName.setLength(0);
                                    break;
                                }
                                case 0:
                                    break;
                            }
                            break;
                        // 4.Photo Note
                        case 4:
                        {
                            // PhotoNote doesn't implement Remindable
                            getTitle();
                            getPriority();
                            getFileName();
                            getImgDescription();
                            
                            try
                            {
                                clipBoard.addPhotoNote(title.toString(), priority,
                                        fileName.toString(), description.toString());
                            } catch (FileNotFoundException ex)
                            {
                                System.err.print("Image file not found!");
                                Thread.sleep(400);
                            }
                            
                            title.setLength(0);
                            fileName.setLength(0);
                            description.setLength(0);
                            break;
                        }
                    }
                    break;
                // 4.Search notes by title
                case 4:
                    List<Note> result = new ArrayList<>();
                    try
                    {
                        if (clipBoard.getAllNotes().size() == 0)
                            throw new IllegalStateException("Clipboard empty!");
                        else
                        {
                            System.out.print("\nTitle to search: ");
                            title.append(br.readLine());
                            
                            result = clipBoard.search(title.toString());
                        }
                    } catch (IllegalStateException stateex)
                    {
                        System.err.print("\n" + stateex.getMessage());
                        Thread.sleep(400);
                        break;
                    } catch (NoSuchElementException notfoundex)
                    {
                        System.err.println("Note not found!");
                        Thread.sleep(400);
                        break;
                    }
                    
                    System.out.println("[Titles found from search]");
                    result.forEach(note -> System.out.println("\t" +
                            note.getTitle() + "\n"));
                    
                    br.readLine();
                    Thread.sleep(400);
                    break;
                // 5.Showing archived notes
                case 5:
                    try
                    {
                        if (clipBoard.getArchivedNotes().size() == 0)
                            throw new IllegalStateException("Archive empty!");
                        else
                            clipBoard.showArchive();
                    } catch (IllegalStateException stateex)
                    {
                        System.err.print("\n" + stateex.getMessage());
                    }
                    Thread.sleep(400);
                    break;
                //6. Clear all saved notes
                case 6:
                    /* flag used to handle "Notes cleared" showing even
                       after entering the catch block and breaking ? */
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
                // 7.Clear the archive
                case 7:
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
                // Closing the stream and exiting the app
                case 0:
                    br.close();
                    
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }
}