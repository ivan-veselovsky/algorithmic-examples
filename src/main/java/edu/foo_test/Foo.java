package edu.foo_test;

public class Foo {

    static class A {
        int qq;
        A() {
            qq = 5;
            System.out.println(qq);
        }
        void moo() {

        }
    }

    static class B extends A {
        int qq;
        B (int i) {
            qq = i;
            System.out.println(qq);
        }

        @Override
        void moo() {

        }
    }

    public static void main(String[] args) {
        new B(8);
    }
}
