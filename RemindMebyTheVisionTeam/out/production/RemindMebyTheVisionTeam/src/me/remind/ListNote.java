package me.remind;

import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class ListNote extends Note implements Remindable
{
    public static final int EXIT_INDEX = -1;
    public static final int MIN_LIST_SIZE = 0;
    
    private List<Item> checkBoxesList;
    private DateTime deadline;
    
    public ListNote()
    {
    }
    
    public ListNote(String title, DateTime deadline, Priority priority)
            throws IOException
    {
        super(title, priority);
        setDeadline(deadline);
        setCheckBoxesList(new ArrayList<>());
        initializeList();
    }
    
    protected List<Item> getCheckBoxesList()
    {
        return new ArrayList<>(checkBoxesList);
    }
    
    private void setCheckBoxesList(List<Item> checkBoxesList)
    {
        this.checkBoxesList = checkBoxesList;
    }
    
    /**
     * Method to initialize the ListNote fields
     */
    private void initializeList() throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // When done adding new lines, type "ok" to terminate the adding process
        Queue<String> lines = new LinkedList<>();
        
        String newLine = "";
        while (!newLine.equals("ok"))
        {
            newLine = br.readLine();
            
            lines.add(newLine);
        }
        
        // initializing all items as "unchecked"
        List<Item> checkBoxesAndText = lines.stream()
                .map(line ->
                {
                    Item listNote = new Item();
                    listNote.setItemText(line);
                    listNote.setCheck(Check.NOT_CHECKED);
                    return listNote;
                }).collect(Collectors.toList());
        
        checkBoxesList = checkBoxesAndText;
    }
    
    private void listAllCheckboxItems()
    {
        System.out.println("\n[List]");
        checkBoxesList.forEach(x -> System.out.println("\t" + x.toString()));
    }
    
    /**
     * Method that prompts for checking different items from a list note by index
     */
    protected void promptToChangeStatus() throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // when done with marking items checked, type "-1" to terminate the process
        while (true)
        {
            listAllCheckboxItems();
            System.out.print("\nSelect which index status to change: ");
            
            int index = Integer.parseInt(br.readLine());
            
            if ((index < MIN_LIST_SIZE || index > checkBoxesList.size()) &&
                    index != EXIT_INDEX)
                return;
            else if (index == EXIT_INDEX)
                return;
            else
            {
                // on select, always changing the check field to the opposite value
                    /* substracting 1 because the output ordering starts with 1;
                            => the actual index of the element is minus one */
                if (checkBoxesList.get(index - 1).getCheck() == Check.NOT_CHECKED)
                    checkBoxesList.get(index - 1).setCheck(Check.CHECKED);
                else
                    checkBoxesList.get(index - 1).setCheck(Check.NOT_CHECKED);
            }
        }
    }
    
    @Override
    protected String getTitleWithTypeAndPriority()
    {
        return "[ListNote] {Priority: " + getPriority().toString().toLowerCase() + "}" +
                "\n\t\t" + getTitle();
    }
    
    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof ListNote &&
                ((ListNote) obj).getUid().equals(this.getUid());
    }
    
    @Override
    public int hashCode()
    {
        return getUid().hashCode();
    }
    
    @Override
    public String toString()
    {
        return getTitle();
    }
    
    @Override
    protected void showNote()
    {
        System.out.println("\n[List]");
        getCheckBoxesList().forEach(x -> System.out.println("\t" + x));
    }
    
    @Override
    public void remind()
    {
        System.out.print(" ->[Reminder set in " + getHoursToDeadline(deadline) + " hours]\n");
    }
    
    @Override
    public void setDeadline(DateTime deadline)
    {
        this.deadline = deadline;
    }
    
    @Override
    public DateTime getDeadline()
    {
        return deadline;
    }
}