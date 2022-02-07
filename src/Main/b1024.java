package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class b1024 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        // a 는 차수가 1 인 등차수열의 합
        int a = Integer.parseInt(st.nextToken());

        // n 은 더해줄 수열의 개수
        int n = Integer.parseInt(st.nextToken());


        // 합 생성
        int sum = 0;

        // 등차수열의 합이 a 와 같은지 여부를 확인
        boolean success = false;

        // n의 최대 값
        // 초기 n개를 더했을때 만족하는 값이 없을 때 조건
        // n 의 최대 경우가 100임으로 조건을 설정
        int nM = 0;

        if (a > 100) {
            nM = 100;
        } else if (a <= 100) {
            nM = a;
        }

        // 등차수열의 합이 a 일 때 더해지 값을 구하기
        LinkedList<Integer> li = new LinkedList<>();


        // 더해주는 경우 수의 개수가 a 랑 같거나 작아야하기 때문에
        for (int n1 = n; n1 < (nM + 1) / 2; n1++) {
            for (int x = 0; x < a+1; x++) {
                // x 는 초기값이다  초기값은 0 부터 시작
                // (nM/2)+1 을 준 이유
                // 홀수 짝수로 나누는 경우 조건문을 달아야한다.
                for (int y = 0; y < n1; y++) {
                    // x 는 a1 즉 초기값
                    // y 는 a1 부터 등차가 1인 수를 차례대로 더하는 경우
                    sum = sum + x + y;
                    if (y == n1 - 1) {
                        if (sum == a) {
                            success = true;
                            for (int j = 0; j < n1; j++) {
                                li.add(x + j);
                            }
                            break;
                        } else {
                            sum = 0;
                        }
                    }
                }
                if (success == true) {
                    break;
                }


            }
            if (success == true) {
                break;
            }
        }
        if(success == true) {
            for (int i : li) {
                System.out.print(i+" ");
            }
        }else{
            System.out.println(-1);
        }
    }
}

//
//        for( int k = n; k <a; k++){
//
//            for(int x = 0 ; x<a ;x++){
//                for(int y=0; y<k ; y++){
//                    sum = sum + x +y;
//                    if(sum == a){
//                        success = true;
//                        for(int j=0; j<k ;j++){
//                            li.add(x+j);
//                        }
//                        break;
//                    }if(y==k-1&&sum!=a){
//                        sum = 0;
//                    }
//                }
//                if(success==true){
//                    break;
//                }
//            }
//            if(success==true){
//                break;
//            }
//        }
//        if(success==true) {
//            for (int i : li) {
//                System.out.print(i+" ");
//            }
//        }else{
//            System.out.println(-1);
//        }


