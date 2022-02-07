package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b14563 {
    public static void main(String []args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int testcase = Integer.parseInt(br.readLine());

        st = new StringTokenizer(br.readLine(), " ");

        for(int i = 0 ; i <testcase ; i++){
            int sum = 0;
            int caseN = Integer.parseInt(st.nextToken());
            for(int j = 1; j<caseN ; j ++){
                if(caseN%j==0){
                    sum = sum +j;
                }
            }
            if(caseN == sum){
                System.out.println("Perfect");
            }else if(caseN>sum){
                System.out.println("Deficient");
            }else if(caseN<sum){
                System.out.println("Abundant");
            }
        }
    }
}
