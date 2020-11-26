package com.company.lab3;

import java.math.BigInteger;

public class RSA
{

    RSA()
    {
        RSA_Server server = new RSA_Server();

        try
        {
            RSA_Client firstClient = new RSA_Client(server, "user1", "pass00");
            RSA_Client secondClient = new RSA_Client(server, "userSecond", "MyPa_00ss");
            RSA_Client thirdClient = new RSA_Client(server, "us3e4r5", "par_098");

            System.out.println();

            firstClient.receiveOpenedKey();
            secondClient.receiveOpenedKey();
            thirdClient.receiveOpenedKey();

            System.out.println();

            firstClient.sendRegistrationFirstStep();
            firstClient.sendRegistrationSecondStep();

            secondClient.sendRegistrationFirstStep();
            secondClient.sendRegistrationSecondStep();

            thirdClient.sendRegistrationFirstStep();
            thirdClient.sendRegistrationSecondStep();


            firstClient.sendLogin();
            thirdClient.sendLogin();
            secondClient.sendLogin();


            firstClient.createSecretChat("userSecond");
            thirdClient.createSecretChat("user1");


            firstClient.sendMessage("Hello", "userSecond");
            firstClient.sendMessage("How are you", "userSecond");
            firstClient.sendMessage("?", "userSecond");

            secondClient.sendMessage("Good", "user1");
            secondClient.sendMessage("and u&", "user1");

            firstClient.sendMessage("Hi", "us3e4r5");

            thirdClient.sendMessage("Hiii", "user1");
            thirdClient.sendMessage("Data is 25/12/2020", "user1");

        }
        catch (Exception e) { System.err.println(e.getMessage()); }
    }


    public static String encrypt(String message, RSA_Key encryptionKey)
    {
        StringBuilder newMessage = new StringBuilder();

        BigInteger buf;
        char c;

        for(int i = 0 ; i < message.length() ; i ++)
        {
            c = message.charAt(i);

            buf = BigInteger.valueOf(c);
            newMessage.append(buf.modPow(encryptionKey.getFirstKeyPart(), encryptionKey.getSecondKeyPart()).toString()).append(':');
        }

        return newMessage.toString();
    }

    public static String decrypt(String message, RSA_Key decryptionKey)
    {
        String[] messageReceived = message.split(":");
        StringBuilder newMessage = new StringBuilder();

        for(String s : messageReceived)
            newMessage.append((char) new BigInteger(s).modPow(decryptionKey.getFirstKeyPart(), decryptionKey.getSecondKeyPart()).intValue());

        return newMessage.toString();
    }

}
