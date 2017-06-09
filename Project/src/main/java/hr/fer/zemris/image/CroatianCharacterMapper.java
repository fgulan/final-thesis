package hr.fer.zemris.image;

/**
 * @author Filip Gulan
 */
public class CroatianCharacterMapper implements ICharacterMapper {

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
            case 'č':
            case 'Č':
                return 3;
            case 'Ć':
            case 'ć':
                return 4;
            case 'D':
            case 'd':
                return 5;
            case 'Đ':
            case 'đ':
                return 6;
            case 'E':
            case 'e':
                return 7;
            case 'F':
            case 'f':
                return 8;
            case 'G':
            case 'g':
                return 9;
            case 'H':
            case 'h':
                return 10;
            case 'I':
            case 'i':
                return 11;
            case 'J':
            case 'j':
                return 12;
            case 'K':
            case 'k':
                return 13;
            case 'L':
            case 'l':
                return 14;
            case 'M':
            case 'm':
                return 15;
            case 'N':
            case 'n':
                return 16;
            case 'O':
            case 'o':
                return 17;
            case 'P':
            case 'p':
                return 18;
            case 'R':
            case 'r':
                return 19;
            case 'S':
            case 's':
                return 20;
            case 'Š':
            case 'š':
                return 21;
            case 'T':
            case 't':
                return 22;
            case 'U':
            case 'u':
                return 23;
            case 'V':
            case 'v':
                return 24;
            case 'Z':
            case 'z':
                return 25;
            case 'Ž':
            case 'ž':
                return 26;
            case 'X':
            case 'x':
                return 27;
            case 'Y':
            case 'y':
                return 28;
            case 'W':
            case 'w':
                return 29;
            case 'Q':
            case 'q':
                return 30;
            case '-':
                return 31;
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
                return 'Č';
            case 4:
                return 'Ć';
            case 5:
                return 'D';
            case 6:
                return 'Đ';
            case 7:
                return 'E';
            case 8:
                return 'F';
            case 9:
                return 'G';
            case 10:
                return 'H';
            case 11:
                return 'I';
            case 12:
                return 'J';
            case 13:
                return 'K';
            case 14:
                return 'L';
            case 15:
                return 'M';
            case 16:
                return 'N';
            case 17:
                return 'O';
            case 18:
                return 'P';
            case 19:
                return 'R';
            case 20:
                return 'S';
            case 21:
                return 'Š';
            case 22:
                return 'T';
            case 23:
                return 'U';
            case 24:
                return 'V';
            case 25:
                return 'Z';
            case 26:
                return 'Ž';
            case 27:
                return 'X';
            case 28:
                return 'Y';
            case 29:
                return 'W';
            case 30:
                return 'Q';
            case 31:
                return '-';
            default:
                return ' ';
        }
    }

    @Override
    public int getSize() {
        return 32;
    }
}
