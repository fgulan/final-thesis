package hr.fer.zemris.image;

/**
 * @author Filip Gulan
 */
public class UniqueEnglishLetterMapper {

    public static int mapLetter(Character letter) {
        switch (letter) {
            case 'a':
            case 'A':
                return 0;
            case 'b':
            case 'B':
                return 1;
            case 'c':
            case 'C':
                return 2;
            case 'D':
            case 'd':
                return 3;
            case 'E':
            case 'e':
                return 4;
            case 'F':
            case 'f':
                return 5;
            case 'G':
            case 'g':
                return 6;
            case 'H':
            case 'h':
                return 7;
            case 'I':
            case 'i':
                return 8;
            case 'J':
            case 'j':
                return 9;
            case 'K':
            case 'k':
                return 10;
            case 'L':
            case 'l':
                return 11;
            case 'M':
            case 'm':
                return 12;
            case 'N':
            case 'n':
                return 13;
            case 'O':
            case 'o':
                return 14;
            case 'P':
            case 'p':
                return 15;
            case 'R':
            case 'r':
                return 16;
            case 'S':
            case 's':
                return 17;
            case 'T':
            case 't':
                return 18;
            case 'U':
            case 'u':
                return 19;
            case 'V':
            case 'v':
                return 20;
            case 'Z':
            case 'z':
                return 21;
            case 'X':
            case 'x':
                return 22;
            case 'Y':
            case 'y':
                return 23;
            case 'W':
            case 'w':
                return 24;
            case 'Q':
            case 'q':
                return 25;
            case '-':
                return 26;
            default:
                return -1;
        }
    }
}
