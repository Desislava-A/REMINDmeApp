package me.remind;

import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.NoSuchElementException;

public class Main {
    private static StringBuilder title = new StringBuilder();
    private static StringBuilder fileName = new StringBuilder();
    private static StringBuilder description = new StringBuilder();
    private static Priority priority = null;
    private static Calendar deadline = null;
    private static DateTime dateTime = null;
    private static boolean flag = false;
    private static ClipboardFileManager cfm = new ClipboardFileManager();

    private static final BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));

    private static void getTitle() throws IOException {
        System.out.print("\nTitle: ");
        title.append(br.readLine());
    }

    private static void getDeadline() throws IOException {
        System.out.print("Deadline in days: ");
        deadline.add(Calendar.DATE, Integer.parseInt(br.readLine()));
        br.readLine();
    }

    private static void getFileName() throws IOException {
        System.out.print("File name: ");
        fileName.append(br.readLine());
    }

    private static void getImgDescription() throws IOException {
        System.out.print("Image description: ");
        description.append(br.readLine());
    }

    private static void getPriority() throws IOException {
        String testPriorityInput = null;
        boolean incorrectPriorityInput = true;

        do {
            System.out.println("\n[Priority choice]");
            System.out.println("Please choose one of the following: ");
            System.out.println("\t->[none, normal or critical]\n");
            System.out.print("\nPriority: ");
            String priorityStr = br.readLine();

            testPriorityInput = priorityStr.trim().toLowerCase();

            switch (testPriorityInput) {
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

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        ClipBoard clipBoard = cfm.loadClipboard();

        while (true) {
            System.out.println();
            switch (Menus.mainMenu()) {
                case 1:
                    try {
                        if (clipBoard.getAllNotes().size() == 0)
                            throw new IllegalStateException("Clipboard empty!");

                        clipBoard.showAllNotes();

                        System.out.print("\nSelect note number: ");
                        int index = Integer.parseInt(br.readLine());
                        br.readLine();

                        System.out.println();
                        if (index < 1 || index > clipBoard.getAllNotes().size())
                            throw new IllegalArgumentException("Index not in boundaries!");

                        clipBoard.getAllNotes().get(index - 1).showNote();
                        br.readLine();
                        System.out.println();
                    } catch (IllegalStateException stateex) {
                        System.err.println("\n" + stateex.getMessage());
                        break;
                    } catch (IllegalArgumentException argex) {
                        System.err.println("\n" + argex.getMessage());
                    }

                    Thread.sleep(400);
                    break;
                case 2:
                    try {
                        if (clipBoard.getRemindableNotes().size() == 0)
                            throw new IllegalStateException("No reminders!");
                    } catch (IllegalStateException stateex) {
                        System.err.println("\n" + stateex.getMessage());
                        Thread.sleep(400);
                        break;
                    }

                    clipBoard.showReminders();
                    br.readLine();
                    break;
                case 3:
                    try {
                        if (clipBoard.getArchivedNotes().size() == 0)
                            throw new IllegalStateException("No archived notes!");
                    } catch (IllegalStateException stateex) {
                        System.err.println("\n" + stateex.getMessage());
                        Thread.sleep(400);
                        break;
                    }

                    clipBoard.showArchive();
                    br.readLine();
                    break;
                case 4:
                    System.out.println();
                    switch (Menus.addSubMenu()) {
                        case 1: {
                            switch (Menus.hasReminderMenu()) {
                                case 1: {
                                    deadline = Calendar.getInstance();

                                    getTitle();
                                    getDeadline();
                                    getPriority();

                                    dateTime = new DateTime(deadline.getTimeInMillis());

                                    clipBoard.addTextNote(title.toString(), dateTime, priority);

                                    title.setLength(0);
                                    break;
                                }
                                case 2: {
                                    getTitle();
                                    getPriority();

                                    clipBoard.addTextNote(title.toString(), null, priority);

                                    title.setLength(0);
                                    break;
                                }
                            }
                            break;
                        }
                        case 2: {
                            switch (Menus.hasReminderMenu()) {
                                case 1: {
                                    deadline = Calendar.getInstance();

                                    getTitle();
                                    getDeadline();
                                    getPriority();

                                    dateTime = new DateTime(deadline.getTimeInMillis());

                                    clipBoard.addListNote(title.toString(), dateTime, priority);

                                    title.setLength(0);
                                    break;
                                }
                                case 2: {
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
                        case 3:
                            switch (Menus.hasReminderMenu()) {
                                case 1: {
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
                                    break;
                                }
                                case 2: {
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
                        case 4: {
                            // PhotoNote doesn't implement Remindable
                            getTitle();
                            getPriority();
                            getFileName();
                            getImgDescription();

                            clipBoard.addPhotoNote(title.toString(), null, priority,
                                    fileName.toString(), description.toString());

                            title.setLength(0);
                            fileName.setLength(0);
                            description.setLength(0);
                            break;
                        }
                        case 5:
                            try {
                                if (clipBoard.getAllNotes().size() == 0)
                                    throw new IllegalStateException("Clipboard empty!");

                                System.out.print("\nTitle to search: ");
                                title.append(br.readLine());

                                clipBoard.search(title.toString());
                            } catch (IllegalStateException stateex) {
                                System.err.print("\n" + stateex.getMessage());
                            } catch (NoSuchElementException notfoundex) {
                                System.err.println("Note not found!");
                            }

                            Thread.sleep(400);
                            break;
                        case 6:
                            System.out.println();
                            try {
                                if (clipBoard.getListNotes().size() == 0)
                                    throw new IllegalStateException("No list notes!");

                                clipBoard.showListTitles();

                                System.out.println("\nWhich title to edit: ");
                                title.append(br.readLine());

                                clipBoard.promptToCheckListItems(title.toString());
                            } catch (IllegalStateException stateex) {
                                System.err.println(stateex.getMessage());
                            } catch (NoSuchElementException notfoundex) {
                                System.err.println("Note not found!");
                            }

                            Thread.sleep(400);
                            break;
                        case 7:
                            System.out.println();
                            try {
                                if (clipBoard.getAllNotes().size() == 0)
                                    throw new IllegalStateException("Clipboard empty!");

                                clipBoard.showTitles();

                                System.out.print("\nTitle of note to be pinned: ");
                                String titleToBePinned = br.readLine();

                                clipBoard.pinNote(titleToBePinned);

                                if (!clipBoard.isNotePinned(titleToBePinned))
                                    throw new FailedPinException("Pin failed");
                            } catch (IllegalStateException stateex) {
                                System.err.println(stateex.getMessage());
                            } catch (NoSuchElementException notfoundex) {
                                System.err.println("Note not found!");
                            } catch (FailedPinException pinex) {
                                System.err.println(pinex.getMessage());
                            }

                            Thread.sleep(400);
                            break;
                        case 8:
                            try {
                                if (clipBoard.getPinnedNotes().size() == 0)
                                    throw new IllegalStateException("There aren't any pinned notes!");

                                clipBoard.showPinned();

                                System.out.println("\nTitle of note to be unpinned: ");
                                String titleToBeUnpinned = br.readLine();

                                clipBoard.unpinNote(titleToBeUnpinned);
                            } catch (IllegalStateException stateex) {
                                System.err.println("\n" + stateex.getMessage());
                            } catch (NoSuchElementException notfoundex) {
                                System.err.println("Note not found!");
                            }

                            Thread.sleep(400);
                            break;
                        case 9:
                            System.out.println();
                            try {
                                if (clipBoard.getAllNotes().size() == 0)
                                    throw new IllegalStateException("Clipboard empty!");

                                clipBoard.showTitles();

                                System.out.print("\nTitle of note to be archived: ");
                                String titleToBeArchived = br.readLine();

                                clipBoard.archiveNote(titleToBeArchived);

                                if (clipBoard.isNoteArchived(titleToBeArchived))
                                    System.out.print("\nNote archived");
                                else
                                    throw new FailedArchiveException("\nArchive failed");
                            } catch (IllegalStateException stateex) {
                                System.err.println(stateex.getMessage());
                            } catch (FailedArchiveException farchex) {
                                System.err.println(farchex.getMessage());
                            }

                            Thread.sleep(400);
                            break;
                        case 10:
                            try {
                                if (clipBoard.getAllNotes().size() == 0)
                                    throw new IllegalStateException("\nClipboard empty!\n");

                                clipBoard.showTitles();

                                System.out.print("\nTitle of note to be deleted: ");
                                String titleToBeDeleted = br.readLine();

                                clipBoard.deleteNote(titleToBeDeleted);
                            } catch (NoSuchElementException notfoundex) {
                                System.err.println("Note not found!");
                            } catch (IllegalStateException stateex) {
                                System.err.print(stateex.getMessage());
                            }

                            Thread.sleep(400);
                            break;
                        case 11:
                            /* flag used to handle "Notes cleared" showing even
                               after entering the catch block and breaking ? */
                            flag = false;

                            try {
                                clipBoard.clearAllNotes();
                            } catch (IllegalStateException stateex) {
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

                            try {
                                clipBoard.clearArchive();
                            } catch (IllegalStateException stateex) {
                                System.err.println("\n" + stateex.getMessage());
                                flag = true;
                                Thread.sleep(400);
                                break;
                            }

                            if (clipBoard.getArchivedNotes().size() == 0 && flag)
                                System.out.println("\nArchive cleared");

                            break;
                        case 0:
                            br.close();
                            Menus.closeStream();

                            System.exit(0);
                            break;
                        default:
                            break;
                    }
            }
        }
    }
}