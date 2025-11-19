package ui;


import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import chess.ChessGame;

import static ui.EscapeSequences.*;


public class BlackChessBoardDrawing {

    // Board dimensions.
    private static final int BOARD_SIZE_IN_SQUARES = 8;


    private static final String X = WHITE_KING;
    private static final String O = BLACK_KING;
    private static ChessGame.TeamColor PlayerColor;
    static String colorSquare;


    //White Printing
    private static List<String> row_1 = new ArrayList<>(Arrays.asList(
            WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_KING, WHITE_QUEEN, WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK));
    private static ArrayList<String> row_2 = new ArrayList<>(Arrays.asList(
            WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN));


    //Black Printing
    private static ArrayList<String> row_7 = new ArrayList<>(Arrays.asList(
            BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN));
    private static ArrayList<String> row_8= new ArrayList<>(Arrays.asList(
            BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_KING, BLACK_QUEEN, BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK));




    private static Random rand = new Random();


    public static void main() {

        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out);

        drawChessBoard(out);

        drawHeaders(out);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawHeaders(PrintStream out) {

        setBlack(out);

        String[] headers = {EMPTY + " a", "b", "c", "d", "e", "f", "g", "h", EMPTY};
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawHeader(out, headers[boardCol]);

            if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
                out.print("\u2003 ");
            }
        }

        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {

        out.print(SET_BG_COLOR_LIGHT_GREY + EMPTY);
        printHeaderText(out, headerText);
        out.print(SET_BG_COLOR_LIGHT_GREY + EMPTY);
    }

    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_WHITE);

        out.print(player);

        setBlack(out);
    }

    private static void drawChessBoard(PrintStream out) {

        for (int boardRow = 1 ; boardRow < BOARD_SIZE_IN_SQUARES + 1; ++boardRow) {
            out.print(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_WHITE + " " + String.valueOf(boardRow) + " ");

            drawRowOfSquares(out);
            out.print(SET_BG_COLOR_LIGHT_GREY + " " + String.valueOf(boardRow) + " ");;
            setBlack(out);
            out.println();




//            if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
//                // Draw horizontal row separator.
//                drawHorizontalLine(out);
//                setBlack(out);
//            }
        }
    }

    private static void drawRowOfSquares(PrintStream out) {



        for (int squareRow = 0; squareRow < BOARD_SIZE_IN_SQUARES + 1; ++squareRow) {
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {


                if (boardCol % 2 == 0) {
                    if (squareRow % 2 == 1) {
                        colorSquare = SET_BG_COLOR_WHITE;
                    } else {
                        colorSquare = SET_BG_COLOR_BLACK;
                    }
                } else {
                    if (squareRow % 2 == 1) {
                        colorSquare = SET_BG_COLOR_BLACK;
                    } else {
                        colorSquare = SET_BG_COLOR_WHITE;
                    }
                }


                switch (squareRow) {
                    case 1:
                        printPlayer(out, row_1.get(squareRow));
                        break;
                    case 2:
                        printPlayer(out, row_2.get(squareRow));
                        break;
                    case 7:
                        printPlayer(out, row_7.get(squareRow));
                        break;
                    case 8:
                        printPlayer(out, row_8.get(squareRow));
                        break;
                    default:
                        break;
                }
            }
        }
    }
    private void printSquare(PrintStream out) {
                    out.print(colorSquare);
                    out.print(EMPTY);

                    out.print(EMPTY);
                }


    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setRed(PrintStream out) {
        out.print(SET_BG_COLOR_RED);
        out.print(SET_TEXT_COLOR_RED);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void printPlayer(PrintStream out, String player) {
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(player);
        setWhite(out);
    }



}
