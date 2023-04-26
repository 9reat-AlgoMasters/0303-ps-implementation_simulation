import java.io.*;
import java.util.*;

public class Q2933 {
    static final int[][] DIR = {{1,0}, {0,1}, {-1,0}, {0,-1}};
    static final boolean LEFT = true;
    static final boolean RIGHT = false;
    
    static final int EMPTY = '.';
    static final int MINERAL = 'x';
    private static final int NOTHING = -1;
    static final List<Point> EMPTY_LIST = new ArrayList<>();
    static int N, M, K;
    static int[][] map;
    
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
        
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        
        for (int i = 0; i < N; i++) {
            char[] input = br.readLine().toCharArray();
            for (int j = 0; j < M; j++) {
                map[i][j] = input[j];
            }
        }
        
        K = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        boolean side = LEFT;
        while (K-- > 0) {
            int height = N - Integer.parseInt(st.nextToken());
            int removedMineralCol = findTarget(side, height);
            side = !side;
            if (removedMineralCol == NOTHING) {
                continue;
            }
            
            map[height][removedMineralCol] = EMPTY;
            
            List<Point> mineralOnAir = findMineralOnAir(height, removedMineralCol);
            // 공중에 뜬 미네랄이 없다면 다음 창 던지기
            if (mineralOnAir.isEmpty()) continue;
            
            dropMinerals(mineralOnAir);
        }
        
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                sb.append(map[i][j]==EMPTY ? '.' : 'x');
            }
            sb.append("\n");
        }
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static int findTarget(boolean side, int height) {
        if (side == LEFT) {
            for (int i = 0; i < M; i++) {
                if (map[height][i] == MINERAL) {
                    return i;
                }
            }
        } else {
            for (int i = M-1; i >= 0; i--) {
                if (map[height][i] == MINERAL) {
                    return i;
                }
            }
        }
        return NOTHING;
    }
    
    private static List<Point> findMineralOnAir(int height, int removedMineralCol) {
        boolean[][] visited = new boolean[N][M];
        
        // 없어진 미네랄과 인접한 미네랄에 대해서 공중에 떠 있는지 탐색
        for (int[] d : DIR) {
            int nextX = height + d[0];
            int nextY = removedMineralCol + d[1];
//            System.out.printf("(%d, %d)\n", nextX, nextY);
            
            if (isInRange(nextX, nextY) && !visited[nextX][nextY] && map[nextX][nextY] == MINERAL) {
                List<Point> minerals = bfs(visited, nextX, nextY);
                if (!minerals.isEmpty()) {
                    removeFromMap(minerals);
                    return minerals;
                }
            }
        }
        // 비어있는 리스트를 리턴할 경우 떠 있는 미네랄이 없다는 뜻
        return new ArrayList<>();
    }
    
    private static List<Point> bfs(boolean[][] visited, int x, int y) {
        List<Point> minerals = new ArrayList<>();
        
        Deque<Point> q = new ArrayDeque<>();
        q.add(new Point(x, y));
        minerals.add(new Point(x, y));
        visited[x][y] = true;
        boolean isOnGround = false;
        
        while (!q.isEmpty()) {
            Point now = q.poll();
            
            for (int[] d : DIR) {
                int nextX = now.x + d[0];
                int nextY = now.y + d[1];
                // 미네랄이 지면과 맞 붙어 있다면 공중에 떠 있는게 아님
                if (nextX == N) {
                    isOnGround = true;
                }
                if (isInRange(nextX, nextY) && !visited[nextX][nextY] && map[nextX][nextY] == MINERAL) {
                    visited[nextX][nextY] = true;
                    q.add(new Point(nextX, nextY));
                    minerals.add(new Point(nextX, nextY));
                }
            }
        }
        
        if (isOnGround) {
            return EMPTY_LIST;
        } else {
            return minerals;
        }
    }
    
    private static void removeFromMap(List<Point> minerals) {
        for (Point mineral : minerals) {
            map[mineral.x][mineral.y] = EMPTY;
        }
    }
    
    private static void dropMinerals(List<Point> mineralOnAir) {
        int dropCnt = 0;
        boolean canDrop = true;
        
        while (canDrop) {
            dropCnt++;
            for (Point mineral : mineralOnAir) {
                int nextX = mineral.x + dropCnt;
                int nextY = mineral.y;
                if (nextX >= N || map[nextX][nextY] == MINERAL) {
                    canDrop = false;
                    break;
                }
            }
        }
        dropCnt--;
        
        for (Point mineral : mineralOnAir) {
            map[mineral.x + dropCnt][mineral.y] = MINERAL;
        }
    }
    private static boolean isInRange(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < M;
    }
}
