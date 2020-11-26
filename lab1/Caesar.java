package com.company.lab1;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Caesar
{

    private int step;
    private final Caesar_Table table;


    Caesar()
    {
        generateStep();
        step = 91;

        String output = encrypt(readFile());
        writeFile(output);

        table = new Caesar_Table(output.length());
        createStatisticTable(output);
        this.table.printTable();
    }

    private void createStatisticTable(String output)
    {

        char c;
        for(int i = 0 ; i < output.length() ; i ++)
        {

            c = output.charAt(i);

            int index = this.table.checkRowExists(c);

            if(index != -1)
                this.table.increaseCharCount(index);
            else
                this.table.createRow(c);

        }
    }


    private String encrypt(String input)
    {
        StringBuilder output = new StringBuilder();

        for(int i = 0 ; i < input.length() ; i ++)
        {
            if(input.charAt(i) != '\n')
                output.append((char) ((int) input.charAt(i) - this.step));
            else
                output.append('\n');
        }

        return output.toString();
    }


    private void generateStep() { this.step =(int) (20 + Math.random() * 80); }


    private String readFile()
    {
        StringBuilder input = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("./src/com/company/lab1/read.txt"), StandardCharsets.UTF_8)))
        {
            String line;

            while ((line = reader.readLine()) != null)
                input.append(line).append('\n');
        }
        catch (IOException e) { System.err.println(e.getMessage()); }

        return input.toString();
    }


    private void writeFile(String output)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/com/company/lab1/write.txt"))) { writer.write(output); }
        catch (IOException e) { System.err.println(e.getMessage());}
    }
}
