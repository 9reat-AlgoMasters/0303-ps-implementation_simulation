import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

public class Main {
    //뱀이 회전하는 시간과 방향을 담는 클래스
    static class Turn{
        int s;
        char d;
        public Turn(int s, char d) {
            super();
            this.s = s;
            this.d = d;
        }
    }
    //뱀이 이동했던 칸을 담는 클래스
    static class Move{
        int r, c;

        public Move(int r, int c) {
            super();
            this.r = r;
            this.c = c;
        }

    }

    static int N, K, L;
    static int[][] map;
    static int[] dr = {0, 1, 0, -1};
    static int[] dc = {1, 0, -1, 0};
    static Turn[] tArr;
    static Deque<Move> deque = new ArrayDeque<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        K = Integer.parseInt(br.readLine());
        map = new int[N][N];
        //사과 입력, 사과는 보드(map)에서 1
        for (int i = 0; i < K; i++) {
            String[] input = br.readLine().split(" ");
            int r = Integer.parseInt(input[0])-1;
            int c = Integer.parseInt(input[1])-1;
            map[r][c] = 1;
        };
        L = Integer.parseInt(br.readLine());
        //회전 정보 입력
        tArr = new Turn[L];
        for (int i = 0; i < L; i++) {
            String[] input = br.readLine().split(" ");
            int s = Integer.parseInt(input[0]);
            char d = input[1].charAt(0);
            tArr[i] = new Turn(s, d);
        }

        /*
        cnt : 게임 시간
        d : 방향 => 0-우  1-하  2-좌  3-상
        t : 회전 정보를 담은 배열 tArr 인덱스를 위한 변수
        r, c : 현재 좌표(현재 머리)
         */
        int cnt = 0, d = 0, t = 0, r = 0, c = 0;
        map[0][0] = -1;
        //덱에 맨 0, 0에서 출발함을 저장. 뱀이 있는 칸은 보드에 -1 기록
        deque.add(new Move(0, 0));
        while(true) {
            cnt++;
            
            //이동할 칸 계산
            r = r + dr[d];
            c = c + dc[d];
            deque.addFirst(new Move(r, c));
            //벽에 부딪히거나(map이탈) 자기 몸에 닿으면(-1) 끝
            if(!check(r, c)) {
                break;
            }

            //해당 칸이 사과 아니면 꼬리가 줄어듦
            if(map[r][c] != 1) {
                //덱의 뒤에서 가져와서(먼저 들어온 순으로 뺌) map 정보 바꾼 후 삭제
                Move m = deque.getLast();
                map[m.r][m.c] = 0;
                deque.removeLast();
            }
            map[r][c] = -1;
    
            //이동 후 회전할 시간인지 확인, 방향 계산
            if(t < L && cnt == tArr[t].s) {
                if(tArr[t].d == 'L') {
                    d--;
                    if(d < 0) {
                        d += 4;
                    }
                }else {
                    d++;
                    if(d > 3) {
                        d -= 4;
                    }
                }
                t++;
            }
        }

        System.out.println(cnt);
    }

    static boolean check(int r, int c) {
        if(r < 0 || r >= N || c < 0 || c >= N) {
            return false;
        }
        if(map[r][c] == -1) {
            return false;
        }
        return true;
    }

}
