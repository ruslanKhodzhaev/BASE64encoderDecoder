package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Base64EncoderDecoder {

    static final String BASE64_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    static final int SIZE_MAP_64BIN_CODES = 64;
    static final int SIZE_64BIN_CODE = 6;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Encode;
        System.out.print("Input String for Encode: \n");
        System.out.println(base64Encode(reader.readLine().getBytes()));

        // Decode
        System.out.print("Input String for Decode (UTF-8): \n");
        System.out.println(new String(DecodeBase64(reader.readLine()), "UTF-8"));
    }

    public static String base64Encode(byte[] byteData) {
        Map<String, Character> map64BinCodeToChar = new HashMap<String, Character>(SIZE_MAP_64BIN_CODES);
        for (int i = 0; i < SIZE_MAP_64BIN_CODES; i++)
            map64BinCodeToChar.put(intToBin64value(i), BASE64_CHARS.charAt(i));

        StringBuilder allBytesString = new StringBuilder();
        for (Byte b : byteData)
            allBytesString.append(byteToBinaryString(b));

        // processing the full occurrences of a 6 digit binary code
        StringBuilder base64String = new StringBuilder();
        while (allBytesString.length() > SIZE_64BIN_CODE-1) {
            base64String.append(map64BinCodeToChar.get(allBytesString.substring(0, SIZE_64BIN_CODE)));
            allBytesString.delete(0, SIZE_64BIN_CODE);
        }

        // the resulting remainder is converted into characters
        if (allBytesString.length() != 0) {
            int enrichCount = SIZE_64BIN_CODE - allBytesString.length();

            for (int i = 0; i < enrichCount; i++)
                allBytesString.append("0");
            base64String.append(map64BinCodeToChar.get(allBytesString.toString()));

            for (int i = 0; i < enrichCount/2; i++)
                base64String.append("=");
        }

        return base64String.toString();
    }

    public static byte[] DecodeBase64(String s) {
        Map<Character, String> map = new HashMap<Character, String>(SIZE_MAP_64BIN_CODES);
        for (int i = 0; i < SIZE_MAP_64BIN_CODES; i++)
            map.put(BASE64_CHARS.charAt(i), intToBin64value(i));

        while (s.charAt(s.length() - 1) == '=')
            s = s.substring(0, s.length() - 1);

        StringBuilder binaryString = new StringBuilder();
        for (int i = 0; i < s.length(); i++)
            binaryString.append(map.get(s.charAt(i)));

        int sizeByteArr = binaryString.length() / Byte.SIZE;
        byte[] bytes = new byte[sizeByteArr];

        // DECODE
        for (int i = 0, step = 0; i < sizeByteArr; step += Byte.SIZE, i++)
            bytes[i] = parseByte(binaryString.substring(step, step + Byte.SIZE));

        return bytes;
    }

    private static String intToBin64value(int x) { // Функция преообразования в 6 значный бинарный код;
        char[] buffer = new char[7];
        for (int i = 0, mask = 0x40; i < buffer.length; i++, mask >>>= 1)
            buffer[i] = (x & mask) == 0 ? '0' : '1';

        return new String(buffer).substring(1);
    }

    private static String byteToBinaryString(byte x) { // Преобразовние байта в строку для наглядности;
        char[] buffer = new char[Byte.SIZE];
        for (int i = 0, mask = 0x80; i < buffer.length; i++, mask >>>= 1)
            buffer[i] = (x & mask) == 0 ? '0' : '1';

        return new String(buffer);
    }

    private static byte parseByte(String str) {
        int byteValueRange = Math.abs(Byte.MIN_VALUE) + Byte.MAX_VALUE;
        int bt = Integer.parseInt(str, 2);

        if (bt < Byte.MIN_VALUE || bt > Byte.MAX_VALUE)
            bt -= byteValueRange+1;

        return (byte)bt;
    }
}