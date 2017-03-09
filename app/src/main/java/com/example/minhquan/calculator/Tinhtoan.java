package com.example.minhquan.calculator;

import java.util.Arrays;
import java.util.Stack;


import java.util.Arrays;
import java.util.Stack;

public class Tinhtoan {
    // attributes
    private String math;
    private Double result;
    private boolean isError = false;
    private String error = null;

    // constructor
    public Tinhtoan(String inputMath) {
        this.result = valueMath(inputMath);
    }

    // getters and setters
    public String getMath() {
        return math;
    }

    public void setMath(String math) {
        this.math = math;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    // methods
    private boolean isIntegerNumber(double num) {
        long a = (long) num;
        return a == num;
    }

    private long factorial(int num) {
        if (num >= 0) {
            long result = 1;
            for (int i = 1; i <= num; i++) {
                result *= i;
            }
            return result;
        }
        return -1;
    }

    // kiem tra chuoi s co la so khong (bien cung la so)
    private boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean isNumber(char c) {
        String numberChar = ".0123456789";
        int index = numberChar.indexOf(c);
        return index >= 0 && index <= 10;
    }

    // Chuoi sang so
    private double stringToNumber(String s) {
        if (s.charAt(s.length() - 1) == '.') {
            isError = true;
            error = "Lỗi cú pháp";
            return -1;
        }
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            isError = true;
            error = "Lỗi cú pháp";
        }
        return -1;
    }

    // kiem tra xem co phai toan tu
    private boolean isOperator(String s) {
        String operator[] = { "+", "-", "*", "/", "~", "!", ")", "(", "not"};
        Arrays.sort(operator);
        return Arrays.binarySearch(operator, s) > -1;
    }

    // thiet lap thu tu uu tien
    private int priority(String s) {
        int p = 1;
        if (s.equals("+") || s.equals("-")) {
            return p;
        }
        p++;
        if (s.equals("*") || s.equals("/")) {
            return p;
        }
        p++;
        if (s.equals("not") || s.equals("¬")) {
            return p;
        }
        p++;
        if (s.equals("~")) {
            return p;
        }
        return 0;
    }

    // kiem tra xem co phai la phep toan 1 ngoi
    private boolean isOneMath(String c) {
        String operator[] = { "(", "~", "not", "¬"};
        Arrays.sort(operator);
        return Arrays.binarySearch(operator, c) > -1;
    }

    // kiem tra xem co phai phep toan dang sau
    private boolean isPostOperator(String s) {
        String postOperator[] = { "!", "²" };
        for (String aPostOperator : postOperator) {
            if (s.equals(aPostOperator)) {
                return true;
            }
        }
        return false;
    }

    // kiem tra xem cac ky tu lien nhau co la 1 tu khong
    private boolean isWord(char c1, char c2) {
        char word[][] = {{ 'n', 'o', 't' }};
        for (char[] aWord : word) {
            for (int j = 0; j < aWord.length; j++) {
                for (int k = j + 1; k < aWord.length; k++) {
                    if (c1 == aWord[j] && c2 == aWord[k]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // kiem tra chuoi s co la 1 tu khong
    private boolean isWord(String s) {
        String word[] = { "not"};
        for (String aWord : word) {
            if (s.equals(aWord)) {
                return true;
            }
        }
        return false;
    }

    // chuan hoa chuoi
    private String standardize(String s) {
        s = s.trim();
        s = s.replaceAll("\\s+", " ");
        return s;
    }

    // cat chuoi thanh cac phan tu
    private String[] trimString(String s) {
        return s.split(" ");
    }

    private String standardizeMath(String[] s) { // chuan hoa bieu thuc
        String s1 = "";
        int open = 0, close = 0;
        for (String value : s) {
            if (value.equals("(")) {
                open++;
            } else if (value.equals(")")) {
                close++;
            }
        }
        for (int i = 0; i < s.length; i++) {
            // chuyen ...)(... thanh ...)*(...
            if (i > 0 && isOneMath(s[i]) && (s[i - 1].equals(")") || isNumber(s[i - 1]))) {
                s1 = s1 + "* ";
            }
            // 3!2!
            if (i > 0 && isPostOperator(s[i - 1]) && isNumber(s[i])) {
                s1 = s1 + "* ";
            }
            // so duong
            if ((i == 0 || (i > 0 && !isNumber(s[i - 1]) && !s[i - 1].equals(")") && !isPostOperator(s[i - 1])))
                    && (s[i].equals("+")) && (isNumber(s[i + 1]) || s[i + 1].equals("+"))) {
                continue;
            }
            // check so am
            if ((i == 0 || (i > 0 && !isNumber(s[i - 1]) && !s[i - 1].equals(")") && !isPostOperator(s[i - 1])))
                    && (s[i].equals("-")) && (isNumber(s[i + 1]) || s[i + 1].equals("-"))) {
                s1 = s1 + "~ ";
            }
            else {
                s1 = s1 + s[i] + " ";
            }
        }
        // them cac dau ")" vao cuoi neu thieu
        for (int i = 0; i < (open - close); i++) {
            s1 += ") ";
        }
        return s1;
    }

    // xu ly bieu thuc nhap vao thanh cac phan tu
    private String processInput(String sMath) {
        sMath = sMath.toLowerCase();
        sMath = standardize(sMath); // chuan hoa bieu thuc
        String s = "", temp = "";
        for (int i = 0; i < sMath.length(); i++) {
            // is'nt number
            if (!isNumber(sMath.charAt(i))
                    || (i < sMath.length() - 1 && isWord(sMath.charAt(i), sMath.charAt(i + 1)))) {
                s += " " + temp;
                temp = "" + sMath.charAt(i);
                // is operator and isn't word
                if (isOperator(sMath.charAt(i) + "") && i < sMath.length() - 1
                        && !isWord(sMath.charAt(i), sMath.charAt(i + 1))) {
                    s += " " + temp;
                    temp = "";
                } else { // isn't operator but is word
                    i++;
                    while (i < sMath.length() && !isNumber(sMath.charAt(i)) && (!isOperator(sMath.charAt(i) + ""))
                            || (i < sMath.length() - 1 && isWord(sMath.charAt(i - 1), sMath.charAt(i)))) {
                        temp += sMath.charAt(i);
                        i++;
                        if (isWord(temp)) {
                            s += " " + temp;
                            temp = "";
                            break;
                        }
                    }
                    i--;
                    s += " " + temp;
                    temp = "";
                }
            } else { // is number
                temp = temp + sMath.charAt(i);
            }
        }
        s += " " + temp;
        s = standardize(s);
        s = standardizeMath(trimString(s));
        return s;
    }

    private String postFix(String math) {
        String[] elementMath = trimString(math);
        String s1 = "";
        Stack<String> S = new Stack<>();
        for (String anElementMath : elementMath) { // duyet cac phan tu
            if (!isOperator(anElementMath)) // neu khong la toan tu
            {
                s1 = s1 + anElementMath + " "; // xuat elem vao s1
            } else { // c la toan tu
                if (anElementMath.equals("(")) {
                    S.push(anElementMath); // c la "(" -> day phan tu vao Stack
                } else {
                    if (anElementMath.equals(")")) { // c la ")"
                        // duyet lai cac phan tu trong Stack
                        String temp;
                        do {
                            temp = S.peek();
                            if (!temp.equals("(")) {
                                s1 = s1 + S.peek() + " "; // trong khi c1 != "("
                            }
                            S.pop();
                        } while (!temp.equals("("));
                    } else {
                        // Stack khong rong va trong khi phan tu trong Stack co
                        // do uu tien >= phan tu hien tai
                        while (!S.isEmpty() && priority(S.peek()) >= priority(anElementMath)
                                && !isOneMath(anElementMath)) {
                            s1 = s1 + S.pop() + " ";
                        }
                        S.push(anElementMath); // dua phan tu hien tai vao
                        // Stack
                    }
                }
            }
        }
        while (!S.isEmpty()) {
            s1 = s1 + S.pop() + " "; // Neu Stack con phan tu thi day het vao s1
        }
        return s1;
    }

    private Double valueMath(String math) {
        math = processInput(math);
        math = postFix(math);
        String[] elementMath = trimString(math);
        Stack<Double> S = new Stack<>();
        double num = 0.0;
        double ans;
        for (String anElementMath : elementMath) {
            if (!isOperator(anElementMath)) {
                S.push(stringToNumber(anElementMath));
            } else { // toan tu
                if (S.isEmpty()) {
                    isError = true;
                    error = "Phép tính rỗng!";
                    return 0.0;
                }
                double num1 = S.pop();
                if (anElementMath.equals("~")) {
                    num = -num1;
                } else if (anElementMath.equals("not") || anElementMath.equals("¬") || anElementMath.equals("!")) {
                    if (isIntegerNumber(num1) && num1 >= 0) {
                        if (anElementMath.equals("not") || anElementMath.equals("¬")) {
                            num = ~(long) num1;
                        } else if (anElementMath.equals("!")) {
                            num = factorial((int) num1);
                        }
                    }
                } else if (!S.empty()) {
                    double num2 = S.peek();
                    switch (anElementMath) {
                        case "+":
                            num = num2 + num1;
                            S.pop();
                            break;
                        case "-":
                            num = num2 - num1;
                            S.pop();
                            break;
                        case "*":
                            num = num2 * num1;
                            S.pop();
                            break;
                        case "/":
                            if (num1 != 0) {
                                num = num2 / num1;
                            } else {
                                isError = true;
                                error = "Lỗi chia 0";
                                return 0.0;
                            }
                            S.pop();
                            break;
                    }
                } else {
                    isError = true;
                    error = "Lỗi phép tính!";
                    return 0.0;
                }
                S.push(num);
            }
        }
        ans = S.pop();
        return ans;
    }
}
