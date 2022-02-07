package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class b1037 {
    public static void main(String[] ars) throws IOException {
        // Buffered 사용
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st ;
        int realCode = 0;

        // 처음 넣어주는 값이 첫줄에 들어가서 Frist_Line
        // 1과 자기자신을 제외한 약수의 개수
        int fl = Integer.parseInt(br.readLine());

        // 배열에다가 정수 값을 넣어줌
        int [] arr = new int[fl];

        //
        st = new StringTokenizer(br.readLine()," ");

        for(int i = 0 ; i <fl ; i++){

            arr[i] = Integer.parseInt(st.nextToken());
        }
        // 배열을 정렬
        Arrays.sort(arr);

        if(fl%2==0){// 만약 1과 자기 자신을 제외한 약수의 개수가 짝수라면
            realCode = arr[0] * arr[fl-1];
        }else{// 만약 1과 자기 자신을 제외한 약수의 개수가 홀수 라면
            realCode = arr[(fl-1)/2] * arr[(fl-1)/2];
        }
        System.out.println(realCode);
    }
}
