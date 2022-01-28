package Main;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

public class b1010 {
    public static void main(String [] args) throws IOException {
        // Test case 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 띄어서 입력을 받음으로 StrignTokenizer  가 필요
        // 일단 선언만
        StringTokenizer st;

        //test case 지정
        int testcase = Integer.parseInt(br.readLine());

        //반복문 실행

        for(int i = 0 ; i< testcase; i++){
            //StringTokenizer Instance 생성
            st = new StringTokenizer(br.readLine()," ");

            // East site 와 west site 수를 받음
            int west = Integer.parseInt(st.nextToken());
            int east = Integer.parseInt(st.nextToken());


            // 값이 잘 받아지는지 확인 test
//            System.out.println(west);
//            System.out.println(east);
            // 확인 결과 잘됨

            // combination method 구현 eCw

            // ePw 구현
            // factorial  은 숫자가 너무 크기 때문에
            // BigInteger 를 사용
            BigInteger per = new BigInteger("1");
            for(int e = 0 ; e<east-west;e++ ){
                BigInteger current = new BigInteger(String.valueOf(east-e));
//                System.out.println(current);
                per = per.multiply(current);
//                System.out.println(per.multiply(current));

            }
//            System.out.println(per);
            // west 와 east 차만큼의 factorial
            BigInteger fac = new BigInteger("1");
            for (int e = 0 ; e <east-west ; e++){
                BigInteger current = new BigInteger(String.valueOf(east-west-e));
                fac = fac.multiply(current);
            }
            System.out.println(per.divide(fac));


            // west factorial
            // west factorial 구하기
//            long wfac =1;
//            for(long w = 0 ; w<west ; w++){
//                long cur = west-w;
//                wfac = wfac * cur;
//            }
//            System.out.println(wfac);
//
//
//            // East 의 factorial
//            // east factorial
//            long efac =1;
//            for(long e = 0 ; e<east ; e++){
//                long cur = east-e;
//                efac = efac *cur;
//            }
//            // east factorial test
//             System.out.println(efac);
//            // 실행 잘됨
                // 팩토리얼을 사용해서 입력하면
            // 데이터 범위를 넘어섬
            // 30 팩토리얼이 265252859812191058636308480000000
            // 30 팩토리얼 출력 -8764578968847253504

//            long sfac =1;
//            for(long s = 0 ; s<east-west; s++){
//                long cur = (east-west)-s;
//                sfac = sfac * cur;
//            }
//            System.out.println(efac*(1/wfac)*(1/sfac));
        }
    }
}
