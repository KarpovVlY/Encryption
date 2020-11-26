package com.company.lab2;

public class Person
{

    private final int[] publicKeys;
    private int secretKey;

    private int myIntermediateKey;
    private int opponentIntermediateKey;

    private int finalEncryptionKey;

    private final String name;

    Person(String name, int publicKeyFirst, int publicKeySecond)
    {
        this.name = name;

        this.publicKeys = new int[2];
        this.publicKeys[0] = publicKeyFirst;
        this.publicKeys[1] = publicKeySecond;

        createSecretKey();
        System.out.println("'" + this.name + "' generated secret key = " + this.secretKey);

        createSentKey();
        System.out.println("'" + this.name + "' generated intermediate key = " + this.myIntermediateKey + '\n');
    }

    private void createSecretKey() { this.secretKey = (int) (1 + Math.random() * 10); }

    private void createSentKey() { this.myIntermediateKey = (int) (Math.pow(publicKeys[0], secretKey) % publicKeys[1]); }


    public void createFinalEncryptionKey() { this.finalEncryptionKey = (int) (Math.pow(opponentIntermediateKey, secretKey) % publicKeys[1]); }

    public void printFinalEncryptionKey() {  System.out.println("\n'" + this.name + "'calculated final key = " + this.finalEncryptionKey); }

    public int sendSentKey () { return this.myIntermediateKey; }

    public void receiveSendKey(int opponentIntermediateKey, String name)
    {
        this.opponentIntermediateKey = opponentIntermediateKey;
        System.out.println("'" + this.name + "' received opponent intermediate key = " + opponentIntermediateKey + " from '" + name + "'");
    }

    public String sendName() { return this.name; }
}
