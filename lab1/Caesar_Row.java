package com.company.lab1;

public class Caesar_Row
{
    private char c;
    private int count;


    public Caesar_Row(char c, int count)
    {
        this.c = c;
        this.count = count;
    }

    public char getChar() { return c; }
    public int getCount() { return count; }

    public void increaseCount() { ++this.count; }

}