import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int M;
    static int N;

    static int[][] map;
    static int[] counts;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken()); 
        N = Integer.parseInt(st.nextToken()); //일 수

        map = new int[M][M];
        
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            counts = new int[3];
            counts[0] = Integer.parseInt(st.nextToken());
            counts[1] = Integer.parseInt(st.nextToken());
            counts[2] = Integer.parseInt(st.nextToken());

            int r = M - 1;

            int t;
            for (int j = r; j >= 0; j--) {
                t = getNum();
                map[j][0] += t;
            }


            for (int j = 1; j < M; j++) {
                t = getNum();
                map[0][j] += t;
            }
        }
        

        for (int i = 1; i < M; i++) {
            for (int j = 1; j < M; j++) {
                map[i][j] = Math.max(map[i - 1][j], Math.max(map[i - 1][j - 1], map[i][j - 1]));
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                sb.append(map[i][j] + 1).append(" ");
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }

    static int getNum() {
        if (counts[0]!=0) {
            counts[0]--;
            return 0;

        } else if (counts[1] != 0) {
            counts[1]--;
            return 1;
        } else {
            counts[2]--;
            return 2;
        }
    }
}
