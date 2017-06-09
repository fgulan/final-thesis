package hr.fer.zemris.learn;

import hr.fer.zemris.image.BinaryImage;
import hr.fer.zemris.image.UniqueLetterMapper;

/**
 * @author Filip Gulan
 */
public class InputImage {

    private static final int BINARY_SYMBOL_SIZE = 6;
    private BinaryImage image;
    private char symbol;

    public InputImage(BinaryImage image, char symbol) {
        this.image = image;
        this.symbol = symbol;
    }

    public BinaryImage getImage() {
        return image;
    }

    public char getSymbol() {
        return symbol;
    }

    public double[] getBinarySymbol() {
        String binaryString = Integer.toBinaryString(UniqueLetterMapper.mapLetter(symbol));
        int stringSize = binaryString.length();
        int offset = BINARY_SYMBOL_SIZE - stringSize;

        double[] vector = new double[BINARY_SYMBOL_SIZE];
        for (int i = 0; i < stringSize; i++) {
            char symbol = binaryString.charAt(i);
            if (symbol == '0') {
                vector[offset + i] = 0;
            } else if (symbol == '1') {
                vector[offset + i] = 1;
            } else {
                throw new IllegalArgumentException("Output vector string can contain only '1' and '0'.");
            }
        }
        return vector;
    }
}
