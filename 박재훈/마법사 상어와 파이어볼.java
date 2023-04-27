import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class Main {
    static class Fireball{
        //질량, 속도, 방향, 몇번째 명령에 도착했는지
        int m, s, d, t;

        public Fireball(int m, int s, int d) {
            super();
            this.m = m;
            this.s = s;
            this.d = d;
            this.t = -1;
        }

    }
    static int N, M, K;
    static int[] dr= {-1,-1,0,1,1,1,0,-1};
    static int[] dc= {0,1,1,1,0,-1,-1,-1};
    static Queue<Fireball>[][] map;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");
        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);
        K = Integer.parseInt(input[2]);
        
        //이차원 큐 배열 초기화 (칸마다 큐를 가짐)
        map = new ArrayDeque[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = new ArrayDeque<>();
            }
        }

        //입력으로 주어진 파이어볼 위치 시키기(큐에 넣기)
        for (int i = 0; i < M; i++) {
            input = br.readLine().split(" ");
            int r = Integer.parseInt(input[0])-1;
            int c = Integer.parseInt(input[1])-1;
            int m = Integer.parseInt(input[2]);
            int s = Integer.parseInt(input[3]);
            int d = Integer.parseInt(input[4]);
            Fireball fb = new Fireball(m,s,d);
            map[r][c].add(fb);
        }

        
        for (int t = 0; t < K; t++) {//K번 명령동안
            //모든 칸을 탐색하며 각 칸의 큐에 있는 파이어볼 이동시키기
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    while(!map[i][j].isEmpty()) {
                        Fireball fb = map[i][j].poll();
                        //뽑았는데 현재 명령때 이동해온 파이어볼은 다시 이동시키면 안됨
                        if(fb.t == t) {
                            map[i][j].add(fb);
                            break;
                        }
                        
                        //이동할 좌표계산, 해당 칸 큐에 저장
                        int nr = (i + dr[fb.d] * fb.s) % N;
                        if(nr < 0) {
                            nr += N;
                        }
                        int nc = (j + dc[fb.d] * fb.s) % N;
                        if(nc < 0) {
                            nc += N;
                        }
                        fb.t = t;
                        map[nr][nc].add(fb);
                    }
                }
            }

            //모든 칸을 탐색하며 같은 공간의 파이어볼을 합치고 나누는 작업
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    int size = map[i][j].size();
                    //두개 이상이 같은 칸
                    if(size > 1) {
                        int sumM = 0, sumS = 0;
                        boolean isEven = true;
                        boolean first = true;
                        int dirFlag = 0;
                        while(!map[i][j].isEmpty()) {
                            Fireball fb = map[i][j].poll();
                            sumM += fb.m;
                            sumS += fb.s;
                            if(fb.d % 2 == 0) {
                                //처음 들어오는게 짝수인지 체크 + 이전에 홀수였는데 짝수가 들어오면 방향 플래그 1
                                if(first) {
                                    isEven = true;
                                    first = false;
                                }else if(!isEven){
                                    dirFlag = 1;
                                }
                            //처음 들어오는게 홀수인지 체크 + 이전에 짝수였는데 홀수가 들어오면 방향 플래그 1
                            }else {
                                if(first) {
                                    isEven = false;
                                    first = false;
                                }else if(isEven){
                                    dirFlag = 1;
                                }
                            }
                        }
                        int newM = sumM / 5;
                        int newS = sumS / size;
                        if(newM == 0) continue;
                        for (int k = 0; k < 4; k++) {
                            //방향 플래그에 따라 0246 혹은 1357 방향 부여
                            Fireball fb = new Fireball(newM, newS, k*2 + dirFlag);
                            map[i][j].add(fb);
                        }
                    }
                }
            }
        }

        int ans = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                while(!map[i][j].isEmpty()) {
                    Fireball fb = map[i][j].poll();
                    ans += fb.m;
                }
            }
        }

        System.out.println(ans);
    }

}
