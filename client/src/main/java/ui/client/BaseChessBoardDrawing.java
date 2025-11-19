package ui.client;

import ui.EscapeSequences;

import java.io.PrintStream;
import java.util.ArrayList;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.SET_BG_COLOR_BLACK;
import static ui.EscapeSequences.SET_TEXT_COLOR_BLACK;
import static ui.EscapeSequences.SET_TEXT_COLOR_BLUE;
import static ui.EscapeSequences.SET_TEXT_COLOR_MAGENTA;

public class BaseChessBoardDrawing {



    public void processBoard(
            ArrayList<String> row1, ArrayList<String> row2,
            ArrayList<String> row7, ArrayList<String> row8,
            PrintStream out, int boardRow,
            int boardCol, String colorSquare) {



        if (boardRow % 2 == 0) {
            if (boardCol % 2 == 1) {
                colorSquare = SET_BG_COLOR_WHITE;
            } else {
                colorSquare = SET_BG_COLOR_BLACK;
            }
        } else {
            if (boardCol % 2 == 1) {
                colorSquare = SET_BG_COLOR_BLACK;
            } else {
                colorSquare = SET_BG_COLOR_WHITE;
            }
        }



        switch (boardRow % 2) {
            case 0 -> {
                if (boardCol % 2 == 1) {
                    colorSquare = SET_BG_COLOR_WHITE;
                } else {
                    colorSquare = SET_BG_COLOR_BLACK;
                }
            }
            default -> {
                if (boardCol % 2 == 1) {
                    colorSquare = SET_BG_COLOR_BLACK;
                } else {
                    colorSquare = SET_BG_COLOR_WHITE;
                }
            }
        }


        switch (boardRow) {
            case 1:
                printWhitePlayer(out, row1.get(boardCol - 1), colorSquare);
                break;
            case 2:
                printWhitePlayer(out, row2.get(boardCol - 1), colorSquare);
                break;
            case 7:
                printBlackPlayer(out, row7.get(boardCol - 1), colorSquare);
                break;
            case 8:
                printBlackPlayer(out, row8.get(boardCol - 1), colorSquare);
                break;
            default:
                printEmpty(out, colorSquare);
                break;
        }
    out.print(SET_BG_COLOR_DARK_GREY +SET_TEXT_COLOR_WHITE +" "+String.valueOf(boardRow)+" ");
    setBlack(out);
    out.println();
}

private static void setWhite(PrintStream out) {
    out.print(SET_BG_COLOR_WHITE);
    out.print(SET_TEXT_COLOR_WHITE);
}

private static void setBlack(PrintStream out) {
    out.print(SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_BLACK);
}

private static void printEmpty(PrintStream out, String color) {
    //This is only to print blank players
    out.print(color);
    out.print(EscapeSequences.EMPTY);
    setWhite(out);
}

private static void printBlackPlayer(PrintStream out, String player, String color) {
    out.print(color);
    out.print(SET_TEXT_COLOR_MAGENTA);
    out.print(player);
    setWhite(out);
}


private static void printWhitePlayer(PrintStream out, String player, String color) {
    out.print(color);
    out.print(SET_TEXT_COLOR_BLUE);
    out.print(player);
    setWhite(out);
}
}

