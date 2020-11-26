package com.company.lab3;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA_CreateKey
{

    public static RSA_Key[] generateRSAKeys()
    {
        BigInteger[] primes = generateSafePrimes();
        BigInteger n = generateN(primes);

        BigInteger[] service = generateE(primes);
        BigInteger d = generateD(service);


        RSA_Key[] keys = new RSA_Key[2];

        keys[0] = new RSA_Key(service[1], n);
        keys[1] = new RSA_Key(d, n);

        return keys;
    }


    private static BigInteger[] generateE(BigInteger[] primes)
    {
        SecureRandom random = new SecureRandom();

        BigInteger[] service = new BigInteger[2];
        service[0] = (primes[0].subtract(BigInteger.ONE)).multiply((primes[1].subtract(BigInteger.ONE)));

        do { service[1] = BigInteger.probablePrime(8, random); }
        while (isAcceptable(service[0], service[1]));

        return service;
    }

    private static BigInteger generateD(BigInteger[] service)
    {
        BigInteger i = BigInteger.TWO;
        for(;;)
        {
            if( service[1].multiply(i).mod(service[0]).equals(BigInteger.ONE))
                return i;

            i = i.add(BigInteger.ONE);
        }
    }


    private static BigInteger generateN(BigInteger[] primes) { return primes[0].multiply(primes[1]); }


    private static BigInteger[] generateSafePrimes()
    {
        BigInteger[] primes = {BigInteger.ZERO, BigInteger.ZERO};
        BigInteger buffer;

        SecureRandom random = new SecureRandom();

        while (primes[0].equals(BigInteger.ZERO) || primes[1].equals(BigInteger.ZERO))
        {
            buffer = BigInteger.probablePrime(8, random);

            if(primes[0].equals(BigInteger.ZERO))
                primes[0] = buffer;
            else if(primes[1].equals(BigInteger.ZERO))
                if(!primes[0].equals(buffer))
                    primes[1] = buffer;
                else if(primes[0].equals(BigInteger.ZERO) && primes[1].equals(BigInteger.ZERO))
                    break;

        }

        return primes;
    }

    private static boolean isAcceptable(BigInteger nonAcceptable, BigInteger probableAcceptable) { return nonAcceptable.mod(probableAcceptable).equals(BigInteger.ZERO); }

}
