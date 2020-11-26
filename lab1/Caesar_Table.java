package com.company.lab1;

import java.util.ArrayList;

class Caesar_Table
{
    private ArrayList<Caesar_Row> rows;
    private int charCount;

    Caesar_Table(int charCount)
    {
        this.charCount = charCount;
        this.rows = new ArrayList<>();
    }


    public int checkRowExists(char c)
    {
        for(int i = 0 ; i < rows.size() ; i ++)
            if(rows.get(i).getChar() == c)
                return i;

        return -1;
    }

    public void increaseCharCount(int index) { rows.get(index).increaseCount(); }
    public void createRow(char c) { this.rows.add(new Caesar_Row(c, 1)); }



    public void printTable()
    {

        System.out.println("+-------+-------+-----------+");

        for(int i = 0 ; i < rows.size() ; i ++)
        {
            Caesar_Row row = rows.get(i);

            System.out.println("|   " + row.getChar() + "   |  " + row.getCount() + "   | " + String.format("%.6f", ((double) row.getCount() / (double) this.charCount))  + "%  |");
            System.out.println("+-------+-------+-----------+");
        }
    }

}