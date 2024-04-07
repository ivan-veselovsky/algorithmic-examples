package edu;

import lombok.Getter;

import java.util.function.Function;
import java.util.function.IntSupplier;

public class TwoObjects {

    static class CircularRef<S> {
        @Getter
        private final S ref;
        CircularRef(Function<CircularRef<S>, S> creator) {
            ref = creator.apply(this);
        }
    }

    static class CircularRefWithPayload extends CircularRef<CircularRefWithPayload> {
        private final int v;
        CircularRefWithPayload(Function<CircularRef<CircularRefWithPayload>, CircularRefWithPayload> creator, IntSupplier v1) {
            super(creator);
            v = v1.getAsInt();
        }
    }

    public static void main(String[] args) {
        CircularRef<CircularRef> circularRef1 = new CircularRef<>(x -> new CircularRef<>(y -> x));
        System.out.println("r1 = " + circularRef1);
        System.out.println("r1 -> ref = " + circularRef1.ref());
        System.out.println("r1 -> ref -> ref = " + circularRef1.ref().ref());


        CircularRef<CircularRef<CircularRef>> circularRef3 = new CircularRef<>(
                x -> new CircularRef<>(
                        y -> new CircularRef<>(
                                z -> x)));
        System.out.println("r3 = " + circularRef3);
        System.out.println("r3 -> ref = " + circularRef3.ref());
        System.out.println("r3 -> ref -> ref = " + circularRef3.ref().ref());
        System.out.println("r3 -> ref -> ref -> ref = " + circularRef3.ref().ref().ref());


        CircularRefWithPayload p1 = new CircularRefWithPayload(
                p -> new CircularRefWithPayload(q -> (CircularRefWithPayload)p, () -> 2),
                () -> 1);
        System.out.println("p1 = " + p1.v);
        System.out.println("p1 -> ref = " + p1.ref().v);
        System.out.println("p1 -> ref -> ref = " + p1.ref().ref().v);
    }
}
