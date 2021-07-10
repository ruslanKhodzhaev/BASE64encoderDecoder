package test;

import org.junit.Test;

import static main.Base64EncoderDecoder.base64Encode;
import static org.junit.Assert.assertEquals;

public class EncodeBase64Test {
    @Test
    public void EncodeLatin() {
        String origin = "Hello World!!!";
        String expectedBase64 = "SGVsbG8gV29ybGQhISE=";

        assertEquals(base64Encode(origin.getBytes()), expectedBase64);
    }

    @Test
    public void EncodeСyrillic() {
        String origin = "Привет Мир!!!";
        String expectedBase64 = "0J/RgNC40LLQtdGCINCc0LjRgCEhIQ==";

        assertEquals(base64Encode(origin.getBytes()), expectedBase64);
    }

    @Test
    public void EncodeOtherSymbols() {
        String origin = "%1\"2:3%4^5@6?7)8]9*0";
        String expectedBase64 = "JTEiMjozJTReNUA2PzcpOF05KjA=";

        assertEquals(base64Encode(origin.getBytes()), expectedBase64);
    }
}
