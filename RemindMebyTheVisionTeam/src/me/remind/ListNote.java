package me.remind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class ListNote extends Note implements Remindable
{
    public static final int EXIT_PROMPT = -1;
    public static final int MIN_LIST_SIZE_LENGTH = 0;
    
    private List<Item> checkBoxesList;
    
    public ListNote()
    {
    }
    
    public ListNote(String title)
    {
        super(title);
    }
    
    public ListNote(String title, Date deadline, Priority priority, List<String> checkBoxesList)
    {
        this(title, deadline, priority);
        initializeList();
    }
    
    public ListNote(String title, Date deadline, Priority priority)
    {
        super(title, deadline, priority);
        initializeList();
    }
    
    public List<Item> getCheckBoxesList()
    {
        return new ArrayList<>(checkBoxesList);
    }
    
    /**
     * Method to initialize the ListNote fields
     * All items are initialized with Check status "UNCHECKED"
     */
    private void initializeList()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // When done adding new lines, type "ok" to terminate the adding process
        try
        {
            Queue<String> lines = new LinkedList<>();
            
            String newLine = "";
            while (true)
            {
                newLine = br.readLine();
                
                if (newLine.equals("ok"))
                    break;
                
                lines.add(newLine);
            }
            
            List<Item> checkBoxesAndText = lines.stream()
                    .map(line ->
                    {
                        
                        Item listNote = new Item();
                        listNote.setItemText(line);
                        listNote.setCheck(Check.UNCHECKED);
                        return listNote;
                        
                        
                    }).collect(Collectors.toList());
            
            checkBoxesList = checkBoxesAndText;
        } catch (IOException ioex)
        {
            ioex.printStackTrace();
        }
    }
    
    private void listAllItemsFromList()
    {
        System.out.println("\n[List]");
        checkBoxesList.forEach(x -> System.out.println("\t" + x.toString()));
    }
    
    /**
     * Method that prompts for checking different items from a list note by index
     */
    public void promptToChangeStatus()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        try
        {
            // when done with marking items checked, type "-1" to terminate the process
            while (true)
            {
                listAllItemsFromList();
                System.out.print("\nSelect which index status to change: ");
                
                int index = Integer.parseInt(br.readLine());
                
                if ((index < MIN_LIST_SIZE_LENGTH || index > checkBoxesList.size()) &&
                        index != EXIT_PROMPT)
                    return;
                else if (index == EXIT_PROMPT)
                    return;
                else
                {
                    if (checkBoxesList.get(index - 1).getCheck() == Check.UNCHECKED)
                        checkBoxesList.get(index - 1).setCheck(Check.CHECKED);
                    else
                        checkBoxesList.get(index - 1).setCheck(Check.UNCHECKED);
                }
            }
        } catch (IOException ioex)
        {
            ioex.printStackTrace();
        }
    }
    
    @Override
    public String getTitleWithType()
    {
        return "[ListNote]" + "\n\t" + getTitle();
    }
    
    @Override
    public String toString()
    {
        return getTitle();
    }
    
    @Override
    public void showNote()
    {
        System.out.println("\n[List]");
        getCheckBoxesList().forEach(x -> System.out.println("\t" + x));
    }
    
    @Override
    public void remind()
    {
        showNote();
        System.out.print("->[Reminder set in " + getDaysToDeadline() + "]");
    }
}