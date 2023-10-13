package com.browserstack.test.consoleannotations;

import org.bouncycastle.util.test.TestRandomBigInteger;

public class TestInheritance {

    public static void main(String... args) {
        TestInheritance test =new TestInheritance();
        test.print();
    }

    public void print()
    {
        A obj=new A();
        obj = (B)obj;
        obj.print();

    }
}


class A
{
    public void print()
    {
        System.out.println("A");
    }

}

class B extends A
{
    @Override
    public void print()
    {
        System.out.println("B");
    }

    public void test()
    {
        System.out.println("Test oinheritance");
    }
}