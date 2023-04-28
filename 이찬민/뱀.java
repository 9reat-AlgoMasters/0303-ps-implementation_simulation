import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N;
    static int K;
    static int L;
    static int[][] map;
    static Deque<int[]> snake;
    static Map<Integer, String> turns;
    
    static int[] dx = { 0, 1, 0, -1 };
    static int[] dy = { 1, 0, -1, 0 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        K = Integer.parseInt(br.readLine());

        map = new int[N][N];
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken()) - 1;
            int B = Integer.parseInt(st.nextToken()) - 1;
            map[A][B] = 1;
        }

        L = Integer.parseInt(br.readLine());

        turns = new HashMap<>();
        for (int i = 0; i < L; i++) {
            st = new StringTokenizer(br.readLine());
            int time = Integer.parseInt(st.nextToken());
            String turn = st.nextToken();
            turns.put(time, turn);
        }
        snake = new ArrayDeque<>();

        solve();

    }

    public static void solve() {
        int sx = 0;
        int sy = 0;
        int time = 0;
        int dir = 0;
        snake.add(new int[]{0, 0});
        map[0][0] = 2;

        while (true) {
            time++;

            int nx = sx + dx[dir];
            int ny = sy + dy[dir];

            if (!check(nx, ny)) {
                break;
            }

            if (map[nx][ny] == 1) {
                snake.addFirst(new int[]{nx, ny});
                map[nx][ny] = 2;
            } else {
                map[nx][ny] = 2;
                snake.addFirst(new int[]{nx, ny});
                int[] temp = snake.pollLast();
                map[temp[0]][temp[1]] = 0;
            }


            if (turns.containsKey(time)) {
                if (turns.get(time).equals("D")) {
                    dir = (dir + 1) % 4;
                } else {
                    dir--;
                    if (dir == -1) {
                        dir = 3;
                    }
                }
                
            }
            
            sx = nx;
            sy = ny;
        }

        System.out.println(time);
    }

    public static boolean check(int nx, int ny) {
        if (nx < 0 || nx >= N || ny < 0 || ny >= N) {
            return false;
        }

        if (map[nx][ny] == 2) {
            return false;
        }
        return true;
    }
}
