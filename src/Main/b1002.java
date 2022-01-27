package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b1002 {
    public static void main(String [] args) throws IOException {
        // Buffered 사용
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 띄어쓰기 해야하기 때문에 StringTokenizer 가 나옴
        StringTokenizer st ;




        // main case : 몇번 이것을 실행 할 것인가 ?
        int mainCase = Integer.parseInt(br.readLine());

        for (int i = 1; i <= mainCase; i++) {
            // st 를 instance 생성, 정의해서 사용
            st = new StringTokenizer(br.readLine(), " ");

            // 배열에 저장했다가 나중에 출력하는 형식으로 사용
            // x1 y1 r1 x2 y2 r2 총 6개의 변수 필요
            // 제곱근으로 구해야하기 때문에 Double 로 설정
            double[] caseArray = new double[6];
            for (int j = 0; j < 6; j++) {
                caseArray[j] = Integer.parseInt(st.nextToken());
            }

            double x1 = caseArray[0];
            double y1 = caseArray[1];
            double r1 = caseArray[2];
            double x2 = caseArray[3];
            double y2 = caseArray[4];
            double r2 = caseArray[5];

            // x 와 y의 길이를 구함 -1 : 차의 제곱의 합
            double between_center = Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2);

            // x 와 y의 길이를 구함 -2 : 위에서 구한 값을 1/2 해줌 = 제곱근 사용
            double result_bc = Math.sqrt(between_center);

            // 원의 반지름의 합
            double radian_out = Math.abs(r1 + r2);

//            System.out.println(radian_out);

            double radian_In = Math.abs(r1 - r2);
//            System.out.println(radian_In);
            int result = 0;

            // 동심원
            if(result_bc==0){
                if(r1==r2) {
                    // 반지름의 길이 까지 같으면 두원은 같음 경우의 수 무한대
                    result = -1;
                    // 반지름의 길이가 다르면 두 원이 마나는 지점이 없음
                }else{
                    result = 0;
                }
                // 두점에서 만나는 경우 (내접 외접 포함)
            }else if((radian_In < result_bc && result_bc<radian_out)){
                result =2;
                // 한점에서 만나는 경우 두 점의 길이와 반지름 의 합 또는 차의 값이 같다
            }else if(radian_In==result_bc||radian_out==result_bc){
                result = 1;
                // 동심원이 아니면서  만나지 않는 경우는
                // 외접의 경우 두 점 사이의 길이가 반지름의 합보다 크고
                // 내벚의 경우 두 점사이의 길이가 반지름의 차보다 작을 경우
            }else if(radian_out<result_bc||result_bc<radian_In){
                result = 0;
            }
            System.out.println(result);

        }
    }
}


