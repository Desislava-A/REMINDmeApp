package me.remind;

import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        Scanner input = new Scanner(System.in);

        ClipboardFileManager cfm = new ClipboardFileManager();
        ClipBoard clipBoard = cfm.loadClipboard();

        while (true) {
            /*
                persist the changes to cliboard after the user has completed actions than
                load the clipboard from file
             */

            System.out.println("\n");
            switch (Menus.mainMenu()) {
                case 1:
                    System.out.println();
                    switch (Menus.showSubMenu()) {
                        case 1:
                            clipBoard.showAllNotes();
                            input.nextLine();
                            break;
                        case 2: {
                            System.out.println("\n[Titles]");
                            clipBoard.getImages().forEach(x -> System.out.println(
                                    "\t" + x.toString()));

                            System.out.print("\nWhich title to show: ");
                            String title = input.nextLine();

                            for (Viewable img : clipBoard.getImages())
                                if (img.toString().equals(title))
                                    img.view();

                            break;
                        }
                        case 3: {
                            System.out.println("\n[Titles]");
                            clipBoard.getVoiceRecords().forEach(x -> System.out.println(
                                    "\t" + x.toString()));

                            System.out.print("\nWhich title to play: ");
                            String title = input.nextLine();

                            for (Playable record : clipBoard.getVoiceRecords())
                                if (record.toString().equals(title))
                                    record.play();

                            break;
                        }
                        case 4:
                            clipBoard.showListTitles();

                            System.out.print("\nWhich title to edit: ");
                            String title = input.nextLine();

                            clipBoard.promptToCheckListItems(title);

                            break;
                    }

                    break;
                case 2:
                    clipBoard.showReminders();
                    input.nextLine();
                    break;
                case 3:
                    clipBoard.showArchive();
                    input.nextLine();
                    break;
                case 4:
                    System.out.println();
                    switch (Menus.addSubMenu()) {
                        case 1: {
                            switch (Menus.hasReminderMenu()) {
                                case 1: {
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
                                case 2: {
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
                        case 2: {
                            switch (Menus.hasReminderMenu()) {
                                case 1: {
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
                                    String title;

                                    System.out.print("\nTitle: ");
                                    title = input.nextLine();

                                    clipBoard.addListNote(title, null,
                                            Priority.NONE);
                                    break;
                                default:
                                    System.out.println("Going back a menu...");
                                    Thread.sleep(150);

                                    break;
                            }
                            break;
                        }
                        case 3:
                            switch (Menus.hasReminderMenu()) {
                                case 1: {
                                    String title, filePath;

                                    Calendar deadline = Calendar.getInstance();

                                    System.out.print("\nTitle: ");
                                    title = input.nextLine();
                                    System.out.print("Deadline in days: ");
                                    deadline.add(Calendar.DATE, input.nextInt());
                                    input.nextLine();
                                    System.out.print("File name: ");
                                    filePath = input.nextLine();

                                    clipBoard.addVoiceNote(title, deadline.getTime(),
                                            Priority.NONE, filePath);

                                    break;
                                }
                                case 2:
                                    String title, filePath;

                                    System.out.print("\nTitle: ");
                                    title = input.nextLine();
                                    System.out.print("File name: ");
                                    filePath = input.nextLine();

                                    clipBoard.addVoiceNote(title, null,
                                            Priority.NONE, filePath);

                                    break;
                                default:
                                    System.out.println("Going back a menu...");
                                    Thread.sleep(150);

                                    break;
                            }
                            break;
                        case 4:
                            switch (Menus.hasReminderMenu()) {
                                case 1: {
                                    String title, filePath, description;

                                    Calendar deadline = Calendar.getInstance();

                                    System.out.print("\nTitle: ");
                                    title = input.nextLine();
                                    System.out.print("Deadline in days: ");
                                    deadline.add(Calendar.DATE, input.nextInt());
                                    input.nextLine();
                                    System.out.print("File name: ");
                                    filePath = input.nextLine();
                                    System.out.print("Image description: ");
                                    description = input.nextLine();

                                    clipBoard.addPhotoNote(title, null, Priority.NONE,
                                            filePath, description);

                                    break;
                                }
                                case 2:
                                    String title, filePath, shortText;

                                    Calendar deadline = Calendar.getInstance();

                                    System.out.print("\nTitle: ");
                                    title = input.nextLine();
                                    System.out.print("File name: ");
                                    filePath = input.nextLine();
                                    System.out.print("Image description: ");
                                    shortText = input.nextLine();

                                    clipBoard.addPhotoNote(title, null, Priority.NONE,
                                            filePath, shortText);

                                    break;
                                default:
                                    System.out.println("Going back a menu...");
                                    Thread.sleep(150);

                                    break;
                            }
                    }
                    break;
                case 5:
                    clipBoard.showTitles();

                    System.out.print("\nTitle of note to be pinned: ");
                    String titleToBePinned = input.nextLine();
                    clipBoard.pinNote(titleToBePinned);

                    try {
                        if (!clipBoard.isNotePinned(titleToBePinned))
                            throw new FailedPinException("Pin failed");
                    } catch (FailedPinException ex) {
                        System.err.println(ex.getMessage());
                        ex.printStackTrace();
                    }

                    break;
                case 6:
                    clipBoard.showPinnedTitles();

                    try {
                        if (clipBoard.getPinnedNotes().size() == 0)
                            throw new IllegalArgumentException();
                    } catch (IllegalArgumentException iargex) {
                        Thread.sleep(250);
                        System.err.println("\nPinned notes list empty");
                        break;
                    }

                    System.out.print("\nTitle of note to be unpinned: ");
                    String titleToBeUnpinned = input.nextLine();
                    clipBoard.unpinNote(titleToBeUnpinned);
                    break;
                case 7:
                    clipBoard.showTitles();

                    System.out.print("\nTitle of note to be archived: ");
                    String titleToBeArchived = input.nextLine();

                    clipBoard.archiveNote(titleToBeArchived);

                    Thread.sleep(300);
                    try {
                        if (clipBoard.isNoteArchived(titleToBeArchived))
                            System.out.println("Note archived");
                        else
                            throw new FailedArchiveException("\nArchive failed");
                    } catch (FailedArchiveException farchex) {
                        System.err.println(farchex.getMessage());
                    }

                    break;
                case 8:
                    System.out.print("\nTitle of note to be deleted: ");
                    String titleToBeDeleted = input.nextLine();

                    clipBoard.deleteNote(titleToBeDeleted);
                    break;
                case 9:
                    clipBoard.clearAllNotes();

                    Thread.sleep(300);
                    if (clipBoard.getAllNotes().size() == 0)
                        System.out.println("\nNotes cleared");
                    break;
                case 10:
                    clipBoard.clearArchive();

                    Thread.sleep(300);
                    if (clipBoard.getArchivedNotes().size() == 0)
                        System.out.println("\nArchive cleared");
                    break;
                case 0: {
//                    cfm.serialize(clipBoard);
                    System.exit(0);
                    break;
                }
            }
        }

    }
}