package org.xusheng.ioliw.haxl2;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.xusheng.ioliw.haxl2.Trampoline.done;
import static org.xusheng.ioliw.haxl2.Trampoline.more;

public class IOUtils {

    public static IO<Void> printf(PrintStream s, String format, Object... args) {
        return IO.of(() -> {
            s.printf(format, args);
            return null;
        });
    }

    public static IO<Void> printf(String format, Object... args) {
        return printf(System.out, format, args);
    }

    public static IO<String> readLine(InputStream s) {
        return IO.of(() -> new Scanner(s).nextLine());
    }

    public static IO<String> readLine() {
        return readLine(System.in);
    }

    public static IO<Integer> readInt(InputStream s) {
        return readLine(s).map(Integer::parseInt);
    }

    public static IO<Integer> readInt() {
        return readInt(System.in);
    }

    public static IO<Long> readLong(InputStream s) {
        return readLine(s).map(Long::parseLong);
    }

    public static IO<Long> readLong() {
        return readLong(System.in);
    }

    public static IO<Float> readFloat(InputStream s) {
        return readLine(s).map(Float::parseFloat);
    }

    public static IO<Float> readFloat() {
        return readFloat(System.in);
    }

    public static IO<Double> readDouble(InputStream s) {
        return readLine(s).map(Double::parseDouble);
    }

    public static IO<Double> readDouble() {
        return readDouble(System.in);
    }

    public static void main(String[] args) {
        printf("What's your name? ")
            .bind(readLine()).bind(name -> printf("Hello, %s\n", name))
            .bind(printf("How old are you? "))
            .bind(readInt())
            .bind(age -> printf("You are %d years old.\n", age))
            .runIO();

        IO<Void> m = printf("trampoline\n")
            .bind(printf("begin\n"));
        for (int i = 0; i < 10000; i++) {
            m = m.bind(IO.ret(0)).bind(IO.ret(null));
        }
        m = m.bind(printf("end\n"));
        m.runIO();
    }
}
