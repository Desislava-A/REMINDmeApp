package me.remind;

import java.util.Scanner;

public class Menus
{
    public static int mainMenu()
    {
        int choice;
        
        System.out.println("1.Show notes");
        System.out.println("2.Show reminders");
        System.out.println("3.Show archive");
        System.out.println("3.Add note");
        System.out.println("4.Pin note");
        System.out.println("5.Unpin note");
        System.out.println("6.Archive note");
        System.out.println("7.Delete note");
        System.out.println("8.Clear all notes");
        System.out.println("9.Clear archive");
        System.out.println("0.Exit");
        
        Scanner input = new Scanner(System.in);
        do
        {
            System.out.print("\nChoice: ");
            choice = input.nextInt();
        } while (choice < 0 || choice > 10);
        
        return choice;
    }
    
    public static int addMenu()
    {
        int choice;
        
        System.out.println("1.Add text note");
        System.out.println("2.Add checkbox list");
        System.out.println("3.Add voice note");
        System.out.println("4.Add photo note");
        System.out.println("0.Exit");
        
        Scanner input = new Scanner(System.in);
        do
        {
            System.out.print("\nChoice: ");
            choice = input.nextInt();
        } while (choice < 0 || choice > 5);
        
        return choice;
    }
    
    public static int hasReminderMenu()
    {
        int choice;
    
        System.out.println("\n1.Reminder on");
        System.out.println("2.Reminder off\n");
    
        Scanner input = new Scanner(System.in);
        do
        {
            System.out.print("Choice: ");
            choice = input.nextInt();
        } while (choice < 0 || choice > 3);
    
        return choice;
    }
}
