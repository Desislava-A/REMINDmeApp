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
    
    public ListNote(String title, Calendar deadline, Priority priority, List<String> checkBoxesList)
    {
        this(title, deadline, priority);
        initializeList();
    }
    
    public ListNote(String title, Calendar deadline, Priority priority)
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
                        
                        Item listNote2 = new Item();
                        listNote2.setItemText(line);
                        listNote2.setCheck(Check.UNCHECKED);
                        return listNote2;
                        
                        
                    }).collect(Collectors.toList());
            
            checkBoxesList = checkBoxesAndText;
        } catch (IOException ioex)
        {
            ioex.printStackTrace();
        }
    }
    
    /**
     * Method that prompts for checking different items from a list note by index
     */
    public void promptToCheck()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("Select which index to check: ");
        try
        {
            // when done with marking items checked, type "-1" to terminate the process
            while (true)
            {
                int index = Integer.parseInt(br.readLine());
                
                if ((index < MIN_LIST_SIZE_LENGTH || index > checkBoxesList.size()) &&
                        index != EXIT_PROMPT)
                    return;
                else if (index == EXIT_PROMPT)
                    return;
                else
                    checkBoxesList.get(index).setCheck(Check.CHECKED);
            }
        } catch (IOException ioex)
        {
            ioex.printStackTrace();
        }
    }
    
    @Override
    public void showNote()
    {
        getCheckBoxesList().forEach(System.out::println);
    }
    
    @Override
    public void remind()
    {
        showNote();
        System.out.print("->[Reminder set in " + getDaysToDeadline() + "]");
    }
}