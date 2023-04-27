import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int D, N;
    static int[] oven, pizza;
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");
        D = Integer.parseInt(input[0]);
        N = Integer.parseInt(input[1]);
        oven = new int[D];
        pizza = new int[N];

        input = br.readLine().split(" ");
        //오븐 입력
        for (int i = 0; i < D; i++) {
            oven[i] = Integer.parseInt(input[i]);
            //위쪽 오븐보다 큰 오븐 : 위쪽 오븐 크기보다 큰 피자가 떨어지질 않으므로 이전 오븐크기에 맞춤
            // => 오븐 크기는 위에서부터 내림차순이 됨 
            if(i != 0 && oven[i] > oven[i-1]) {
                oven[i] = oven[i-1];
            }
        }
        
        //피자 입력
        input = br.readLine().split(" ");
        for (int i = 0; i < N; i++) {
            pizza[i] = Integer.parseInt(input[i]);
        }
        
        
        if(D >= N) {
            //floor : 현재 갈 수 있는 최대 깊이 오븐
            int floor = D;
            //모든 피자들 탐색
            for (int i = 0; i < N; i++) {
                floor--;
                //남은 피자 수가 남은 오븐 수 보다 크면 전체 피자가 다 못 들어감
                if(N - i > floor + 1) {
                    floor = -1;
                    break;
                }
                
                //아래쪽 오븐부터 탐색하며 처음 들어갈 수 있는 크기의 오븐 찾기
                //위쪽부터 내림차순이므로 찾은 오븐 위쪽은 모두 통과 가능
                while(floor >= 0 && pizza[i] > oven[floor]) {
                    floor--;
                }
                //피자를 다 못 넣었는데 오븐 모두 탐색 
                if(floor < 0){
                    break;
                }
            }
            System.out.println(floor + 1);
        //피자 수 > 오븐 수이면 어차피 다 못 들어감
        }else {
            System.out.println(0);
        }
    }

}

