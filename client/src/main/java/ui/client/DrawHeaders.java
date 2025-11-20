package ui.client;

import java.io.PrintStream;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.SET_TEXT_COLOR_BLACK;

public class DrawHeaders {

    public static void draw(PrintStream out, String[] headers, int BOARD_SIZE) {

        for (int boardCol = 0; boardCol < (BOARD_SIZE); ++boardCol) {
            drawHeader(out, headers[boardCol]);
            out.print("\u2003 ");
        }
        setBlack(out);
        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {

        out.print(SET_BG_COLOR_DARK_GREY);
        printHeaderText(out, headerText);
        out.print(SET_BG_COLOR_DARK_GREY);
    }

    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_WHITE);

        out.print(player);

        setBlack(out);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }



}
