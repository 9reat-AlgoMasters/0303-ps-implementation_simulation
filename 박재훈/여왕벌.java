import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int M, N;
    static int[][] map;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");
        M = Integer.parseInt(input[0]);
        N = Integer.parseInt(input[1]);
        map = new int[M][M];
        for (int i = 0; i < N; i++) {
            input = br.readLine().split(" ");
            //0, 1, 2의 개수에 따라 arr 배열 구성 ex) 2 1 2 입력 => arr: {0, 0, 1, 2, 2}
            int[] arr = new int[2*M-1];
            int idx = 0;
            for (int j = 0; j < 3; j++) {
                int size = Integer.parseInt(input[j]);
                while(size > 0){
                    arr[idx++] = j;
                    size--;
                }
            }

            //arr 배열 값을 맨 왼쪽 아래칸부터 첫 행까지, 첫 행 도착하면 맨 오른쪽 열까지 할당
            idx = 0;
            for (int j = M-1; j >= 0; j--) {
                map[j][0] += arr[idx++];
            }
            for (int j = 1; j < M; j++) {
                map[0][j] += arr[idx++];
            }
        }
        
        //왼쪽, 왼쪽 대각선, 위쪽 세칸 중 항상 위쪽 칸이 가장 큰 수 이므로 
        //행, 열이 0이 아닌 칸의 애벌레가 자란 크기는 그 열의 첫번째 행 칸(가장 위쪽 칸)의 애벌레가 자란 크기와 같음
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                if(i != 0 && j != 0) {
                    sb.append(map[0][j] + 1).append(" ");
                }else{
                    sb.append(map[i][j] + 1).append(" ");
                }
            }
            sb.append('\n');
        }
        System.out.println(sb);

    }
}
