package me.remind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class ListNote extends Note implements Remindable
{
    private List<Item> checkBoxesList;
    
    public ListNote(String title, String deadline, Priority priority,
                    List<String> checkBoxesList) throws IOException
    {
        this(title, deadline, priority);
        setCheckBoxesList(initializeList());
    }
    
    public ListNote(String title, String deadline, Priority priority)
    {
        super(title, deadline, priority);
    }
    
    public List<Item> getCheckBoxesList()
    {
        return new ArrayList<>(checkBoxesList);
    }
    
    public void setCheckBoxesList(List<Item> checkBoxesList)
    {
        this.checkBoxesList = checkBoxesList;
    }
    
    public List<Item> initializeList() throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        Queue<String> lines = new LinkedList<>();
        
        String newLine = "";
        while (!newLine.equals("ok"))
        {
            lines.add(br.readLine());
        }
        
        List<Item> checkboxes = new ArrayList<>();
        
        checkboxes = lines.stream()
                .map(line ->
                {
                    Item newItem = new Item(lines.poll(), Check.UNCHECKED);
                    return newItem;
                    
                }).collect(Collectors.toList());
        
        return checkboxes;
    }
    
    @Override
    public void showNote(Note note)
    {
    
    }
    
    @Override
    public void previewNote()
    {
    
    }
    
    @Override
    public void remind()
    {
    
    }
}