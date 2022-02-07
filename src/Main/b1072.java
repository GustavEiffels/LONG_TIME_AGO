package Main;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.StringTokenizer;

public class b1072 {
    public static void main(String [] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        int gameTry  = Integer.parseInt(st.nextToken());
        int gameWin  = Integer.parseInt(st.nextToken());
        int z = makePercent(gameTry, gameWin);

        int result = -1;
        int left = 0;
        int right = (int) 1e9;
        while (left <= right) {
            int mid = (left + right) / 2;

            if (makePercent(gameTry + mid, gameWin + mid) != z) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        System.out.println(result);
    }

    static int makePercent(int gameTry, int gameWin) {
        return (int) ((long) gameWin * 100 / gameTry);
    }
}

