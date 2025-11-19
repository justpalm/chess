package ui.client;


import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import chess.ChessGame;
import ui.EscapeSequences;

import static ui.EscapeSequences.*;


public class BlackChessBoardDrawing {

    // Board dimensions.
    private static final int BOARD_SIZE = 8;


    private static ChessGame.TeamColor PlayerColor = ChessGame.TeamColor.BLACK;
    static String colorSquare;


    //White Printing
    private static ArrayList<String> row_1 = new ArrayList<>(Arrays.asList(
            WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_KING, WHITE_QUEEN, WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK));
    private static ArrayList<String> row_2 = new ArrayList<>(Arrays.asList(
            WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN));


    //Black Printing
    private static ArrayList<String> row_7 = new ArrayList<>(Arrays.asList(
            BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN));
    private static ArrayList<String> row_8 = new ArrayList<>(Arrays.asList(
            BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_KING, BLACK_QUEEN, BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK));


    private static Random rand = new Random();


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


        String[] headers = {EMPTY + "h", "g", "f", "e", "d", "c", "b", "a" + " \u2002" };
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

                //This determines what the color of the square should be
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


                switch (boardRow) {
                    case 1:
                        printWhitePlayer(out, row_1.get(boardCol - 1), colorSquare);
                        break;
                    case 2:
                        printWhitePlayer(out, row_2.get(boardCol - 1), colorSquare);
                        break;
                    case 7:
                        printBlackPlayer(out, row_7.get(boardCol - 1), colorSquare);
                        break;
                    case 8:
                        printBlackPlayer(out, row_8.get(boardCol - 1), colorSquare);
                        break;
                    default:
                        printEmpty(out, colorSquare);
                        break;
                }
            }
            out.print(SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " " + String.valueOf(boardRow) + " ");
            setBlack(out);
            out.println();
    }
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


