package edu.add_binary_67;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class AddBinary {
    public String addBinary(String a, String b) {
        char[] aa = a.toCharArray();
        char[] bb = b.toCharArray();
        int max = max(aa.length, bb.length);
        StringBuilder sb = new StringBuilder(max + 1);
        int flag = 0;
        int min = min(aa.length, bb.length);
        for (int i=0; i < min; i++) {
            int indexA = aa.length - i - 1;
            int indexB = bb.length - i - 1;
            flag = addOneChar(toInt(aa[indexA]), toInt(bb[indexB]), flag, sb);
        }
        if (max > min) {
            char[] remainder = (aa.length > bb.length) ? aa : bb;
            for (int i=0; i<(max - min); i++) {
                int index = remainder.length - min - i - 1;
                flag = addOneChar(toInt(remainder[index]), 0, flag, sb);
            }
        }
        if (flag > 0) {
            sb.append(flag);
        }
        return sb.reverse().toString();
    }

    private int addOneChar(int bX, int bY, int flag, StringBuilder sb) {
        int result = bX + bY + flag;
        flag = result / 2;
        result %= 2;
        sb.append(result);
        return flag;
    }

    static int toInt(char c) {
        return (c == '1') ? 1 : 0;
    }
}
