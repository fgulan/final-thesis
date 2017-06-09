package hr.fer.zemris.image;

/**
 * @author Filip Gulan
 */
public class EnglishCharacterMapper implements ICharacterMapper {
    @Override
    public int mapCharacter(char character) {
        switch (character) {
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

    @Override
    public char mapInteger(int integer) {
        switch (integer) {
            case 0:
                return 'A';
            case 1:
                return 'B';
            case 2:
                return 'C';
            case 3:
                return 'D';
            case 4:
                return 'E';
            case 5:
                return 'F';
            case 6:
                return 'G';
            case 7:
                return 'H';
            case 8:
                return 'I';
            case 9:
                return 'J';
            case 10:
                return 'K';
            case 11:
                return 'L';
            case 12:
                return 'M';
            case 13:
                return 'N';
            case 14:
                return 'O';
            case 15:
                return 'P';
            case 16:
                return 'R';
            case 17:
                return 'S';
            case 18:
                return 'T';
            case 19:
                return 'U';
            case 20:
                return 'V';
            case 21:
                return 'Z';
            case 22:
                return 'X';
            case 23:
                return 'Y';
            case 24:
                return 'W';
            case 25:
                return 'Q';
            case 26:
                return '-';
            default:
                return ' ';
        }
    }

    @Override
    public int getSize() {
        return 27;
    }
}
