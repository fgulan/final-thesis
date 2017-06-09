package hr.fer.zemris.test;

import hr.fer.zemris.image.LetterMapper;

/**
 * @author Filip Gulan
 */
public class TestUnicode {

    public static void main(String[] args) {
        int test = LetterMapper.mapLetter('-');
        System.out.println(Integer.toBinaryString(test));
    }
}
