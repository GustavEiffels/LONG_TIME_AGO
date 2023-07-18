package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class b1978 {
    public static void main(String [] args)throws IOException {
        // TEST  CASE 삽입
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 띄어쓰기필요하니까 StringTokenizer 호출만 해두
        StringTokenizer st;



        int howMuch = Integer.parseInt(br.readLine());

        // 배열에 저장할 예정이라 배열 생성
        int [] answer = new int[howMuch];

        // String Tokenizer instance 생성
        // for 문 밖에서 생성해줘야함
        st = new StringTokenizer(br.readLine()," ");

        // how much 만큼 숫자 넣기
        for(int i = 0 ; i < howMuch ; i++){

            int val = Integer.parseInt(st.nextToken());
            answer[i] = val;
        }
        // 값이 잘 들어가는지 test
//        for(int a:answer){
//            System.out.println(a);
//        }

        // 소수의 개수를 구하기 위한 int 생성
        int prime_number = 0;
        for(int k = 0 ; k < howMuch; k++){
            // 저장한 숫자들을 다시 지정
            int n = answer[k];

            if(n==2){
                prime_number++;
            }
            //for 문을 사용해서 소수인지 아닌지 확인
            for(int z1=2 ; z1 <= ((n/2)+1); z1++){
                // 나누어 떨어지면 소수가 아님으로 반복문 break
                if(n%z1 ==0){
                    break;
                    //
                }else if(z1==(n/2)+1){
                    prime_number++;
                }


            }
        }
        System.out.println(prime_number);
    }
}
