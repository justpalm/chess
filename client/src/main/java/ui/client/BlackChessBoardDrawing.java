package ui.client;


import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import static ui.EscapeSequences.*;


public class BlackChessBoardDrawing {

    //Create Printing Class
    static BaseChessBoardDrawing baseChessBoardDrawing = new BaseChessBoardDrawing();


    // Board dimensions.
    private static final int BOARD_SIZE = 8;


    static String colorSquare;


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


    public static void main() {

        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);
        out.print(SET_TEXT_FAINT);

        drawHeaders(out);

        drawChessBoard(out);

        drawHeaders(out);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawHeaders(PrintStream out) {


        String[] headers = {EMPTY + "h", "g", "f", "e", "d", "c", "b", "a" + " \u2002"};
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

    private static void drawChessBoard(PrintStream out) {

        for (int boardRow = 1; boardRow < BOARD_SIZE + 1; ++boardRow) {
            out.print(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " " + String.valueOf(boardRow) + " ");
            for (int boardCol = 1; boardCol < BOARD_SIZE + 1; ++boardCol) {
                boardPrinting(out, boardRow, boardCol);
            }
            out.print(SET_BG_COLOR_DARK_GREY +SET_TEXT_COLOR_WHITE +" "+String.valueOf(boardRow)+" ");
            setBlack(out);
            out.println();
        }
    }



    public static void boardPrinting(PrintStream out, int boardRow, int boardCol) {
        baseChessBoardDrawing.processBoard(row1, row2, row7, row8, out, boardRow, boardCol, colorSquare);
    }





    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }
}



