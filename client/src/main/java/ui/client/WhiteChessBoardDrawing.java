package ui.client;


import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import static ui.EscapeSequences.*;


public class WhiteChessBoardDrawing {

    //Create Printing Class
    static BaseChessBoardDrawing baseChessBoardDrawing = new BaseChessBoardDrawing();

    // Board dimensions.
    private static final int BOARD_SIZE = 8;


    static String colorSquare;


    //WHITE BOARD

    //White Printing
    private static ArrayList<String> row1 = new ArrayList<>(Arrays.asList(
            WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_KING, WHITE_QUEEN, WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK));
    private static ArrayList<String> row2 = new ArrayList<>(Arrays.asList(
            WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN));


    //Black Printing
    private static ArrayList<String> row7 = new ArrayList<>(Arrays.asList(
            BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN));
    private static ArrayList<String> row8 = new ArrayList<>(Arrays.asList(
            BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_KING, BLACK_QUEEN, BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK));


    private final String[] headers = {EMPTY + "a", "b", "c", "d", "e", "f", "g", "h" + " \u2003"};




    public void main() {



        DrawHeaders drawHeaders = new DrawHeaders();
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);
        out.print(SET_TEXT_FAINT);

        DrawHeaders.draw(out, this.headers, BOARD_SIZE);
        drawChessBoard(out);
        DrawHeaders.draw(out, this.headers, BOARD_SIZE);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawChessBoard(PrintStream out) {

        for (int boardRow = 8; boardRow > 0; boardRow--) {
            out.print(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " " + String.valueOf(boardRow) + " ");
            for (int boardCol = 8; boardCol > 0; boardCol--) {

                //This determines what the color of the square should be
                boardprinting(out, boardRow, boardCol);
            }
            out.print(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " " + String.valueOf(boardRow) + " ");
            setBlack(out);
            out.println();
        }
    }

    public static void boardprinting(PrintStream out, int boardRow, int boardCol) {
        baseChessBoardDrawing.processBoard(row1, row2, row7, row8, out, boardRow, boardCol, colorSquare);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }
}

