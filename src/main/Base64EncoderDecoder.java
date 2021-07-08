package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class Base64EncoderDecoder {

    private static final String BASE64_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private static final int BASE64_SIZE = 64;

    public static void main(String[] args) throws IOException {
        System.out.println("Input String for Encode:\n");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String string = reader.readLine();

//      Encode
        string = base64Encode(string.getBytes());
        System.out.println(string);

//      Decode
        System.out.println("Input String for Decode:\n");
        string = reader.readLine();
        byte[] bytes = Decode64base64(string);
        for (Byte bt : bytes)
            System.out.println(byteToBinaryString(bt));
    }

    public static String base64Encode(byte[] data) {
        int i = 0;
        String forConcat = "", resultString = "";
        Map<String, Character> map = new HashMap<String, Character>(BASE64_SIZE);

        for (i=0; i<BASE64_SIZE; i++)
            map.put(intToBin64value(i), BASE64_CHARS.charAt(i)); // инициализация мапы BASE64: код -> буква;

        for (Byte someByte: data)
        {
            forConcat += byteToBinaryString(someByte); // собрали байты в строку для последовательной обработки;
        }

        int countSubstring = forConcat.length() / 6; // кол-во полных вхождений 6-разрядного кода;
        int enrichCount = 6 - (forConcat.length() % 6 == 0 ? 6 : forConcat.length() % 6);    // фиксируем кол-во символом 0 и '=' для обогащения

        for (i=0; i < countSubstring; i++) // собираем строку BASE64
        {
            resultString += map.get(forConcat.substring(0,6));
            forConcat = forConcat.substring(6);
        }

        if (enrichCount != 0) // обогащение 0 и '='
        {
            for (i = 0; i < enrichCount; i++) {
                forConcat += "0";
            }

            resultString += map.get(forConcat);

            for (i = 0; i < enrichCount/2; i++) {
                resultString += "=";
            }
        }
        return resultString;
    }

    public static byte[] Decode64base64 (String s) {
        int i;
        String tmp = "";
        Map<Character, String> map = new HashMap<Character, String>(BASE64_SIZE);

        for (i=0; i<BASE64_SIZE; i++)
            map.put(BASE64_CHARS.charAt(i), intToBin64value(i)); // инициализация мапы BASE64: буква -> код;

        while(s.charAt(s.length()-1) == '=') // убираем допсимволы
            s = s.substring(0, s.length() - 1);


        for (i=0; i<s.length(); i++)
            tmp += map.get(s.charAt(i)); // собираем бинарные коды символов в строку;

        int sizeByteArr = tmp.length()/8; // размер массива для его инициализации;
        int step = 0;
        byte[] bytes = new byte[sizeByteArr];

        for (i=0, step=0; i<sizeByteArr; step+=8, i++) // DECODE
            bytes[i] = Byte.parseByte(tmp.substring(step, step+8), 2);

        return bytes;
    }

    private static String byteToBinaryString(byte x) { // Преобразовние байта в строку для наглядности;
        char[] buffer = new char[Byte.SIZE];
        for (int i = 0, mask = 0x80; i < buffer.length; i++, mask >>>= 1) {
            buffer[i] = (x & mask) == 0 ? '0' : '1';
        }
        return new String(buffer);
    }
    private static String intToBin64value(int x) { // Функция преообразования в 6 значный бинарный код;
        char[] buffer = new char[7];
        for (int i = 0, mask = 0x40; i < buffer.length; i++, mask >>>= 1) {
            buffer[i] = (x & mask) == 0 ? '0' : '1';
        }
        return new String(buffer).substring(1);
    }
}
