package com.company.lab3;

import java.util.ArrayList;

public class RSA_Client
{
    private final RSA_Server server;
    private RSA_Key publicServerKey;

    private final String username;
    private final String password;

    private final ArrayList<RSA_Chat> chats;

    RSA_Client(RSA_Server server, String username, String password)
    {
        this.server = server;
        this.username = username;
        this.password  = password;

        this.chats = new ArrayList<>();
    }


    public void sendMessage(String message, String opponentUsername) throws Exception
    {
        RSA_Chat currentChat = null;

        for(RSA_Chat chat : chats)
        {
            if(chat.opponentUsername.compareTo(opponentUsername) == 0)
            {
                currentChat = chat;
                break;
            }
        }

        if(currentChat != null)
        {
            String encryptedMessage = RSA.encrypt(message, currentChat.opponentPublicKey);
            System.out.println(this.username + " sent to " + opponentUsername + " message : '" + encryptedMessage +
                    "'('" + message + "')");
            this.server.redirectMessage(this.username, opponentUsername, encryptedMessage);
        }
        else
            throw new Exception("Chat doesn't exists");


    }
    public void receiveMessage(String message, String opponentUsername) throws Exception
    {
        RSA_Chat currentChat = null;

        for(RSA_Chat chat : chats)
        {
            if(chat.opponentUsername.compareTo(opponentUsername) == 0)
            {
                currentChat = chat;
                break;
            }
        }

        if(currentChat != null)
        {
            String decrypted = RSA.decrypt(message, currentChat.ownerPrivateKey);
            System.out.println(this.username + " received from " + opponentUsername + " message : '" + message + "'" +
                    "\n\t decryption result : '" + decrypted + "'\n");
        }
        else
            throw new Exception("Chat doesn't exists");




    }

    public RSA_Key processCreatingSecretChat(String opponentUsername)
    {
        RSA_Key[] keys = RSA_CreateKey.generateRSAKeys();

        this.chats.add(new RSA_Chat(null, keys[1], opponentUsername));

        return keys[0];
    }

    public void processSecretChatConfirmation(String opponentUsername, RSA_Key opponentPublicKey) throws Exception
    {
        RSA_Chat currentChat = null;

        for(RSA_Chat chat : chats)
        {
            if(chat.opponentUsername.compareTo(opponentUsername) == 0)
            {
                currentChat = chat;
                break;
            }
        }

        if(currentChat != null)
            currentChat.opponentPublicKey = opponentPublicKey;
        else
            throw new Exception("Error in processing chat, user doesn't exists");
    }

    public void createSecretChat(String opponentUsername) throws Exception { this.server.processCreatingSecretChat(this.username, opponentUsername); }

    public void sendLogin() throws Exception { this.server.receiveLogin(this.username, RSA.encrypt(this.password, this.publicServerKey)); }
    public void sendRegistrationFirstStep() throws Exception { this.server.processRegistrationFirstStep(this.username, this); }
    public void sendRegistrationSecondStep() throws Exception { this.server.processRegistrationSecondStep(this.username,
            RSA.encrypt(this.password, this.publicServerKey)); }

    public void receiveOpenedKey() { this.publicServerKey = server.sendPublicKey(); }




    static class RSA_Chat
    {
        private RSA_Key opponentPublicKey;
        private final RSA_Key ownerPrivateKey;
        private final String opponentUsername;


        public RSA_Chat(RSA_Key opponentPublicKey, RSA_Key ownerPrivateKey, String opponentUsername)
        {
            this.opponentPublicKey = opponentPublicKey;
            this.ownerPrivateKey = ownerPrivateKey;
            this.opponentUsername = opponentUsername;
        }
    }

}
