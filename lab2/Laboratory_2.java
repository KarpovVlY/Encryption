package com.company.lab2;

public class Laboratory_2
{
    public Laboratory_2()
    {
        int firstKey = (int) (20 + Math.random() * 150);
        int secondKey = (int) (20 + Math.random() * 150);

        System.out.println("Generated public numbers : " + firstKey + " and " + secondKey + '\n');

        Person first = new Person("User1", firstKey, secondKey);
        Person second = new Person("SecondPerson",firstKey, secondKey);

        first.receiveSendKey(second.sendSentKey(), second.sendName());
        second.receiveSendKey(first.sendSentKey(), first.sendName());


        first.createFinalEncryptionKey();
        second.createFinalEncryptionKey();

        first.printFinalEncryptionKey();
        second.printFinalEncryptionKey();

    }
}
