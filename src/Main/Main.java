package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 4344
public class Main {
    public static void main(String[] args)throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st ;

        int totalCase = Integer.parseInt(br.readLine());


        int [] blackBoard;


        // 순환해서 지정하기
        for (int i = 0 ; i< totalCase ; i++) {

            st = new StringTokenizer(br.readLine()," ");

            int sno = Integer.parseInt(st.nextToken());

            blackBoard = new int[sno];


            // 평균을 내기위한 성적들의 합

            double sums  = 0;

            for (int k = 0 ; k <sno ; k++) {
                int score = Integer.parseInt(st.nextToken());
                blackBoard[k] = score;
                sums = sums +score ;

            }
            // 평균
            double avr = (sums/sno);

            // 평균을 넘는 학생 수
            double oversno = 0;

            for(int x = 0 ; x<sno ; x++) {
                if(blackBoard[x]>avr) {
                    oversno++;
                }
            }

            System.out.printf("%.3f", 100.0 * oversno / sno);
            System.out.println("%");

        }

    }
}
