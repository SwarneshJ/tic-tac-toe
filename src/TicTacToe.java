import java.util.Scanner;

public class TicTacToe {

    public static void printBoard(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static int[] readCoordinates(Scanner sc) {
        String s = sc.nextLine();
        String kwargs[] = s.split(",");
        int[] ans = new int[2];
        ans[0] = Integer.parseInt(kwargs[0]);
        ans[1] = Integer.parseInt(kwargs[1]);
        return ans;
    }

    public static void initiateMove(Scanner sc, char[][] board, Player p) {
        boolean success = false;
        while (!success) {
            try {
                int[] kwargs = readCoordinates(sc);
                int i = kwargs[0];
                int j = kwargs[1];
                p.addMarker(board, i, j);
                success = true;
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        printBoard(board);
        int winParam = p.checkIfPlayerWon(board);
        if (winParam == 1) {
            System.out.println("Player "+p.getMarker()+" wins");
            System.exit(0);
        }
        else if (winParam == 2) {
            System.out.println("It is a draw");
            System.exit(0);
        }
    }

    public static void main (String args[]) {
        char[][] board = new char[3][3];
        Player p1 = new Player(Marker.cross);
        Player p2 = new Player(Marker.circle);
        int i = 0, j = 0;
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            initiateMove(sc, board, p1);
            initiateMove(sc, board, p2);
        }
    }
}

enum Marker {
    cross, circle
}

class Player {

    private char c;

    Player(Marker marker) {
        if (marker == Marker.cross) {
            c = 'X';
        }
        else if (marker == Marker.circle) {
            c = 'O';
        }
    }

    char getMarker() {
        return c;
    }

    void addMarker(char[][] board, int i, int j) throws IllegalArgumentException{
        int m = board.length, n = board[0].length;
        if (i >= m || j >= n || i < 0 || j < 0) {
            throw new IllegalArgumentException("Move outside the boundary of board");
        }
        if (board[i][j] == 0) {
            board[i][j] = c;
        }
        else {
            throw new IllegalArgumentException("Position already occupied");
        }
    }

    /*
    checks if a player has won after the last move
    0 -> player hasn't won
    1 -> player has won
    2 -> it is a draw
    @Param board
     */
    int checkIfPlayerWon(char[][] board) {
        for (int i = 0; i < 3; i++) {
            boolean check = true;
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != c) check = false;
            }
            if (check) return 1;
        }

        for (int i = 0; i < 3; i++) {
            boolean check = true;
            for (int j = 0; j < 3; j++) {
                if (board[j][i] != c) check = false;
            }
            if (check) return 1;
        }

        boolean check = true;
        for (int i = 0; i < 3; i++) {
            if (board[i][i] != c) check = false;
        }
        if (check) return 1;

        if (board[0][2] == c && board[1][1] == c && board[2][0] == c) return 1;

        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) count++;
            }
        }
        if (count == 0) return 2;

        return 0;
    }

}
