package com.company.lab3;

import java.math.BigInteger;

public class RSA_Key
{
    private final BigInteger firstKeyPart;
    private final BigInteger secondKeyPart;

    public RSA_Key(BigInteger firstKeyPart, BigInteger secondKeyPart)
    {
        this.firstKeyPart = firstKeyPart;
        this.secondKeyPart = secondKeyPart;
    }

    public BigInteger getFirstKeyPart() { return firstKeyPart; }
    public BigInteger getSecondKeyPart() { return secondKeyPart; }
}
