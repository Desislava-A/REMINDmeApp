package me.remind;

import java.util.Scanner;

/**
 * A class containing the different menus for the console UI
 */
public class Menus
{
    private static Scanner input = new Scanner(System.in);
    
    // just making sure this class can't be instantiated
    private Menus()
    {
    }
    
    protected static int mainMenu()
    {
        int choice = -1;
        
        System.out.println();
        System.out.println(".---------------------------------.");
        System.out.println("| 1.All notes                     |");
        System.out.println("| 2.Reminders                     |");
        System.out.println("| 3.Add note                      |");
        System.out.println("| 4.Search note                   |");
        System.out.println("| 5.Archive                       |");
        System.out.println("| 6.Clear all                     |");
        System.out.println("| 7.Clear archive                 |");
        System.out.println("| 0.Exit                          |");
        System.out.println(".---------------------------------.");
        
        do
        {
            System.out.print("\nChoice: ");
            
            // Using input.hasNextInt() handles input mismatch errors
            if (input.hasNextInt())
                choice = input.nextInt();
            else
            {
                System.err.println("Please choose a number from the menu options");
                input.nextLine();
            }
            
        } while (choice < 0 || choice > 12);
        
        return choice;
    }
    
    protected static int selectMainSubMenu()
    {
        int choice = -1;
        
        System.out.println("1.Show");
        System.out.println("2.Edit");
        System.out.println("3.Delete");
        System.out.println("4.Pin/Unpin");
        System.out.println("5.Archive/Restore");
        System.out.println("0.Back");
        
        do
        {
            System.out.print("\nChoice: ");
            
            //  Using input.hasNextInt() handles input mismatch errors
            if (input.hasNextInt())
                choice = input.nextInt();
            else
            {
                System.err.println("Please choose a number from the menu options!");
                input.nextLine();
            }
            
        } while (choice < 0 || choice > 4);
        
        return choice;
    }
    
    protected static int editSubMenu()
    {
        int choice = -1;
        
        System.out.println("\n[What to edit]");
        System.out.println("1.Title");
        System.out.println("2.Text/description");
        System.out.println("3.Deadline");
        System.out.println("4.Priority");
        System.out.println("5.*List items status)");
        System.out.println("0.Back");
        
        do
        {
            System.out.print("\nChoice: ");
            
            //  Using input.hasNextInt() handles input mismatch errors
            if (input.hasNextInt())
                choice = input.nextInt();
            else
            {
                System.err.println("Please choose a number from the menu options!");
                input.nextLine();
            }
            
        } while (choice < 0 || choice > 5);
        
        return choice;
    }
    
    protected static int addSubMenu()
    {
        int choice = -1;
        
        System.out.println("1.Add text note");
        System.out.println("2.Add checkbox list");
        System.out.println("3.Add voice note");
        System.out.println("4.Add photo note");
        System.out.println("0.Back");
        
        do
        {
            System.out.print("\nChoice: ");
            
            //  Using input.hasNextInt() handles input mismatch errors
            if (input.hasNextInt())
                choice = input.nextInt();
            else
            {
                System.err.println("Please choose a number from the menu options!");
                input.nextLine();
            }
            
        } while (choice < 0 || choice > 4);
        
        return choice;
    }
    
    protected static int hasReminderMenu()
    {
        int choice = -1;
        
        System.out.println("\n1.Reminder on");
        System.out.println("2.Reminder off");
        System.out.println("0.Back\n");
        
        do
        {
            System.out.print("Choice: ");
            
            if (input.hasNextInt())
                choice = input.nextInt();
            else
            {
                System.err.println("Please choose a number from the menu options!");
                input.nextLine();
            }
            
        } while (choice < 0 || choice > 3);
        
        return choice;
    }
    
}
