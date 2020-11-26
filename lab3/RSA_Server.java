package com.company.lab3;

import java.util.ArrayList;

public class RSA_Server
{


    private final ArrayList<RSA_Person> clients;
    private final RSA_Key[] serverKeys;


    RSA_Server()
    {
        this.clients = new ArrayList<>();
        this.serverKeys = RSA_CreateKey.generateRSAKeys();

        System.out.println("Server started");
        System.out.println("Server generated : \n\t publicKey = " + serverKeys[0].getFirstKeyPart() + "  ;  " + serverKeys[0].getSecondKeyPart() +
                "\n\t privateKey = " + serverKeys[1].getFirstKeyPart() + "  ;  " + serverKeys[1].getSecondKeyPart() + '\n');

    }

    public void redirectMessage(String firstUsername, String secondUsername, String message) throws Exception
    {
        RSA_Person currentPerson = null;

        for(RSA_Person person : this.clients)
            if(person.username.compareTo(secondUsername) == 0)
            {
                currentPerson = person;
                break;
            }

        if(currentPerson != null)
        {
           currentPerson.client.receiveMessage(message, firstUsername);
        }
        else
            throw new Exception("Error, user doesn't exists");
    }


    public void processCreatingSecretChat(String firstUser, String secondUser) throws Exception
    {

        RSA_Client[] clients = {null,  null};


        for(RSA_Person person : this.clients)
        {
            if(person.username.compareTo(firstUser) == 0)
                clients[0] = person.client;
            else if(person.username.compareTo(secondUser) == 0)
                clients[1] = person.client;

            if(clients[0] != null && clients[1] != null)
                break;
        }


        if(clients[0] == null || clients[1] == null)
            throw new Exception("Error, some of user doesn't exists");
        else
        {
            System.out.println("Server received creating chat process : \n\tfirstUsername = " + firstUser +
                    "\n\tsecondUsername = " + secondUser + "\nSecret chat was successfully created");

            createSecretChat(clients, firstUser, secondUser);

        }
    }


    private void createSecretChat(RSA_Client[] clients, String firstUser, String secondUser) throws Exception
    {

        RSA_Key firstPublicKey = clients[0].processCreatingSecretChat(secondUser);
        RSA_Key secondPublicKey = clients[1].processCreatingSecretChat(firstUser);

        System.out.println("Server redirected secret chat public key from '" + firstUser + "' to '" + secondUser + "'");
        System.out.println("Server redirected secret chat public key from '" + secondUser + "' to '" + firstUser + "'");

        clients[0].processSecretChatConfirmation(secondUser, secondPublicKey);
        clients[1].processSecretChatConfirmation(firstUser, firstPublicKey);


    }

    public RSA_Key sendPublicKey() { return this.serverKeys[0]; }


    public void receiveLogin(String username, String data) throws Exception
    {

        RSA_Person currentPerson = null;

        for(RSA_Person person : this.clients)
            if(person.username.compareTo(username) == 0)
            {
                currentPerson = person;
                break;
            }

        if(currentPerson != null)
        {
            if(currentPerson.password.compareTo(RSA.decrypt(data, this.serverKeys[1])) == 0)
            {
                System.out.println("Server received login process : \n\tusername = " + username +
                        "\n\tpassword = " + data + "\n\tdecrypted password = " + currentPerson.password +
                        "\n\treceived password is identical to password in database" +
                        "\n\tclient logined successfully\n");
            }
            else
                throw new Exception("Error, password doesn't match");
        }
        else
            throw new Exception("Error ,user doesn't exists");
    }


    public void processRegistrationFirstStep(String data, RSA_Client client) throws Exception
    {
        RSA_Person currentPerson = null;

        for(RSA_Person person : this.clients)
        {
            if(person.username.compareTo(data) == 0)
            {
                currentPerson = person;
                break;
            }
        }

        if(currentPerson == null)
        {
            this.clients.add(new RSA_Person(client, data));
        }
        else
            throw new Exception("Error, client with this data already exists");
    }

    public void processRegistrationSecondStep(String username, String data) throws Exception
    {
        RSA_Person currentPerson = null;

        for(RSA_Person person : this.clients)
            if(person.username.compareTo(username) == 0)
            {
                currentPerson = person;
                break;
            }

        if(currentPerson != null)
        {

            currentPerson.setPassword(RSA.decrypt(data, this.serverKeys[1]));
            System.out.println("Server received registration process : \n\tusername = " + username +
                    "\n\tpassword = " + data + "\n\tdecrypted password = " + currentPerson.password +
                    "\n\tclient registrated successfully\n");
        }
        else
            throw new Exception("Error ,user doesn't exists");
    }







    static class RSA_Person
    {
        private final String username;
        private String password;

        private final RSA_Client client;

        RSA_Person(RSA_Client client, String username)
        {
            this.client = client;
            this.username = username;
        }

        public void setPassword(String password) { this.password = password; }
    }
}
