import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Q3190 {
    static final int[][] DIR = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // 오른쪽, 아래, 왼쪽, 위 -> +1 은 오른쪽 회전
    static final int EMPTY = 0;
    static final int APPLE = 1;
    static final int SNAKE = 2;
    static int N, M, K;
    static int[][] map;
    static Deque<Point> snake;
    static boolean isEnd;
    static int time;
    
    static class Point {
        int x, y;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        map[0][0] = SNAKE;
        snake = new ArrayDeque<>();
        snake.add(new Point(0, 0));
        
        M = Integer.parseInt(br.readLine());
        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            map[Integer.parseInt(st.nextToken())-1][Integer.parseInt(st.nextToken())-1] = APPLE;
        }
        
        K = Integer.parseInt(br.readLine());
        time = 1; // 현재 시간
        int nowD = 0; // 현재 방향
        isEnd = false;
        
        loop : while (K-- > 0) {
            st = new StringTokenizer(br.readLine());
            int rotateTime = Integer.parseInt(st.nextToken());
            char d = st.nextToken().charAt(0);
            
            // 현재 시간부터 rotateTime 까지 진행
            while (time <= rotateTime) {
                if (!moveSnake(nowD)) {
                    break loop;
                }
                
                time++;
            }
            
            if (d == 'D') {
                nowD = (nowD + 1) % 4;
            } else {
                nowD = (nowD - 1 + 4) % 4;
            }
//            System.out.printf("뱀이 회전합니다. 회전 후 방향은 %d 입니다.\n", nowD);
        }
        
        while (!isEnd) {
            if (!moveSnake(nowD)) {
                break;
            }
            time++;
        }
        
        sb.append(time);
        
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static boolean moveSnake(int nowD) {
//        System.out.printf("%d초에서 뱀이 움직입니다.\n", time);
        
        int nextX = snake.peekLast().x + DIR[nowD][0];
        int nextY = snake.peekLast().y + DIR[nowD][1];
        // 종료 조건
        if (isNotInRange(nextX, nextY) || map[nextX][nextY] == SNAKE) {
//            System.out.println("GAME OVER");
            isEnd = true;
            return false;
        }
        
        // 1. 머리를 늘린다.
//        System.out.printf("뱀이 머리를 (%d, %d)로 늘립니다.\n", nextX, nextY);
        snake.add(new Point(nextX, nextY));
        
        
        // 2. 사과가 아니라면 꼬리를 줄인다.
        if (map[nextX][nextY] != APPLE) {
            Point tail = snake.poll();
            map[tail.x][tail.y] = EMPTY;
        }
        
        map[nextX][nextY] = SNAKE;
        
        return true;
    }
    
    private static boolean isNotInRange(int x, int y) {
        return x < 0 || y < 0 || x >= N || y >= N;
    }
}
