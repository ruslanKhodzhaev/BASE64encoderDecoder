package test;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static main.Base64EncoderDecoder.DecodeBase64;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DecodeBase64Test {

    @Test
    public void DecodeLatinEqualsBytes() {
        String originBase64 = "SGVsbG8gV29ybGQhISE=";
        String expected = "Hello World!!!";

        assertArrayEquals(DecodeBase64(originBase64), expected.getBytes());
    }

    @Test
    public void DecodeLatinEqualsString() throws UnsupportedEncodingException {
        String originBase64 = "SGVsbG8gV29ybGQhISE=";
        String expected = "Hello World!!!";

        assertEquals(new String(DecodeBase64(originBase64), "UTF-8"), expected);
    }

    @Test
    public void DecodeСyrillicEqualsBytes() {
        String originBase64 = "0J/RgNC40LLQtdGCINCc0LjRgCEhIQ==";
        String expected = "Привет Мир!!!";

        assertArrayEquals(DecodeBase64(originBase64), expected.getBytes());
    }

    @Test
    public void DecodeСyrillicEqualsString() throws UnsupportedEncodingException {
        String originBase64 = "0J/RgNC40LLQtdGCINCc0LjRgCEhIQ==";
        String expected = "Привет Мир!!!";

        assertEquals(new String(DecodeBase64(originBase64), "UTF-8"), expected);
    }
}
