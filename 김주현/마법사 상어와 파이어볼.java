import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Q20056 {
    static final int[][] DIR = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
    static final int[][] splitDirs = {
            {0, 2, 4, 6},
            {1, 3, 5, 7}
    };
    static final int SIM = 0;
    static final int DIFF = 1;
    static int N, M, K;
    static BallContainer[][] containers;
    
    static class BallContainer {
        static boolean EVEN = true; // 짝수
        List<Ball> balls;
        
        public BallContainer() {
            balls = new ArrayList<>();
        }
        
        public void addBall(Ball ball) {
            balls.add(ball);
        }
        
        public void mergeAndSplit() {
            // 들어있는 파이어 볼이 한개 이하면 넘어간다.
            if (balls.size() <= 1) {
                return;
            }
            
            int dirSet = hasSimilarDirections() ? SIM : DIFF;
            int newM = findSumM() / 5;
            int newS = findSumS() / balls.size();
            balls = new ArrayList<>();
            // 새로 만들어지는 볼의 질량이 0이면 추가하지 않는다.
            if (newM == 0) {
                return;
            }
            for (int d : splitDirs[dirSet]) {
                balls.add(new Ball(newM, newS, d));
            }
        }
        
        private boolean hasSimilarDirections() {
            if (balls.isEmpty()) return false;
            
            int ballDirKind = balls.get(0).d % 2;
            for (Ball ball : balls) {
                if (ball.d % 2 != ballDirKind) {
                    return false;
                }
            }
            return true;
        }
        
        private int findSumM() {
            int sum = 0;
            for (Ball ball : balls) {
                sum += ball.m;
            }
            return sum;
        }
        
        private int findSumS() {
            int sum = 0;
            for (Ball ball : balls) {
                sum += ball.s;
            }
            return sum;
        }
    }
    
    static class Ball {
        int m, s, d;
        
        public Ball(int m, int s, int d) {
            this.m = m;
            this.s = s;
            this.d = d;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        
        containers = new BallContainer[N][N];
        initContainers(containers);
        
        while(M-- >0) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            containers[r - 1][c - 1].addBall(new Ball(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }
        
        while (K-- > 0) {
            moveBalls();
        }
        
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum += containers[i][j].findSumM();
            }
        }
        
        sb.append(sum);
    
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    public static void moveBalls() {
        BallContainer[][] newContainers = new BallContainer[N][N];
        initContainers(newContainers);
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (Ball ball : containers[i][j].balls) {
                    int[] nextPos = findNextPos(ball, i, j);
                    newContainers[nextPos[0]][nextPos[1]].addBall(ball);
                    
                }
            }
        }
        
        containers = newContainers;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                containers[i][j].mergeAndSplit();
            }
        }
    }
    
    private static int[] findNextPos(Ball ball, int r, int c) {
        int moves = ball.s % N;
        int newR = (r + (DIR[ball.d][0] * moves) + N) % N;
        int newC = (c + (DIR[ball.d][1] * moves) + N) % N;
        return new int[]{newR, newC};
    }
    
    private static void initContainers(BallContainer[][] containers) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                containers[i][j] = new BallContainer();
            }
        }
    }
}
