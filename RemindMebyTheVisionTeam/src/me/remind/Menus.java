package me.remind;

import java.util.Scanner;

/**
 * A class containing the different menus for the console UI
 */
public class Menus
{
    public static int mainMenu()
    {
        int choice = -1;
        
        System.out.println("1.All notes");
        System.out.println("2.Reminders");
        System.out.println("3.Archive");
        System.out.println("4.Add note");
        System.out.println("5.Search note");
        System.out.println("6.Edit list note items");
        System.out.println("7.Pin note");
        System.out.println("8.Unpin note");
        System.out.println("9.Archive note");
        System.out.println("10.Delete note");
        System.out.println("11.Clear all");
        System.out.println("12.Clear archive");
        System.out.println("0.Exit");
        
        Scanner input = new Scanner(System.in);
        do
        {
            System.out.print("\nChoice: ");
            
            // Using input.hasNextInt() handles input mismatch errors
            if (input.hasNextInt())
                choice = input.nextInt();
            else
            {
                System.err.println("Please choose a number from the menu options");
                input.next();
            }
            
        } while (choice < 0 || choice > 12);
        
        return choice;
    }
    
    public static int addSubMenu()
    {
        int choice = -1;
        
        System.out.println("1.Add text note");
        System.out.println("2.Add checkbox list");
        System.out.println("3.Add voice note");
        System.out.println("4.Add photo note");
        System.out.println("0.Exit");
        
        Scanner input = new Scanner(System.in);
        do
        {
            System.out.print("\nChoice: ");
            
            //  Using input.hasNextInt() handles input mismatch errors
            if (input.hasNextInt())
                choice = input.nextInt();
            else
            {
                System.err.println("Please choose a number from the menu options!");
                input.next();
            }
            
        } while (choice < 0 || choice > 5);
        
        return choice;
    }
    
    public static int hasReminderMenu()
    {
        int choice = -1;
        
        System.out.println("\n1.Reminder on");
        System.out.println("2.Reminder off\n");
        
        Scanner input = new Scanner(System.in);
        do
        {
            System.out.print("Choice: ");
            
            
            // Using input.hasNextInt() handles input mismatch errors
            
            if (input.hasNextInt())
                choice = input.nextInt();
            else
            {
                System.err.println("Please choose a number from the menu options!");
                input.next();
            }
            
        } while (choice < 0 || choice > 3);
        
        return choice;
    }
}
