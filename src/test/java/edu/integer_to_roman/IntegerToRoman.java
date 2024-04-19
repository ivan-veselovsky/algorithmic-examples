package edu.integer_to_roman;

public class IntegerToRoman {

    public String intToRoman(int x) {
        final StringBuilder sb = new StringBuilder(10);

        if (x >= 1000) {
            int m = x / 1000;
            sb.repeat('M', m);
            x -= m * 1000;
        }

        if (x >= 900) {
            sb.append('C').append('M');
            x -= 900;
        } else if (x >= 500) {
            sb.append('D');
            x -= 500;
        } else if (x >= 400) {
            sb.append('C').append('D');
            x -= 400;
        }
        int c = x / 100;
        if (c > 0) {
            sb.repeat('C', c);
            x -= c * 100;
        }
        //assert x < 100;

        if (x >= 90) {
            sb.append('X').append('C');
            x -= 90;
        } else if (x >= 50) {
            sb.append('L');
            x -= 50;
        } else if (x >= 40) {
            sb.append('X').append('L');
            x -= 40;
        }
        int xx = x / 10;
        if (xx > 0) {
            sb.repeat('X', xx);
            x -= xx * 10;
        }
        //assert x < 10;

        if (x == 9) {
            sb.append('I').append('X');
            x -= 9;
        } else if (x >= 5) {
            sb.append('V');
            x -= 5;
        }
        if (x == 4) {
            sb.append('I').append('V');
            //x -= 4;
        }  else {
            //int x1 = x;
            sb.repeat('I', x);
            //x -= x1;
        }
        //assert x == 0;

        return sb.toString();
    }

}
