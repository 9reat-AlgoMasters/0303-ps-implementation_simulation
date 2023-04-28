import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int M;
    static int K;

    static int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};

    static List<Fireball> fireballs;
    static int[][][] fireballmap;
    static int ballCount;

    static class Fireball {
        int r;
        int c;
        int m;
        int s;
        int d;

        public Fireball(int r, int c, int m, int s, int d) {
            this.r = r;
            this.c = c;
            this.m = m;
            this.s = s;
            this.d = d;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int answer = 0;

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        fireballs = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int m = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            fireballs.add(new Fireball(r, c, m, s, d));
        }
        
        // 횟수 시작
        while (K-- > 0) {
            ballCount = fireballs.size();

            fireballmap = new int[N][N][ballCount]; // n*n개의 좌표에 최대 파이어볼의 개수 만큼 존재할 수 있음

            // 1. 모든 파이어볼이 자신의 방향 di로 속력 si칸 만큼 이동한다.
            moveTheFireball();

            // 2. 중복된 위치의 파이어볼을 합치고 나눈다.
            fireballs = splitTheFireball();
        }

        // 3. 남아있는 파이어볼의 질량을 더한다.
        for (Fireball fireball : fireballs) {
            answer += fireball.m;
        }

        System.out.println(answer);

    }

    //모든 파이어볼이 자신의 방향 di로 속력 si칸 만큼 이동한다.
    //이동하는 중에는 같은 칸에 여러 개의 파이어볼이 있을 수도 있다.
    static void moveTheFireball() {
        for (int i = 0; i < fireballs.size(); i++) {
            Fireball fireball = fireballs.get(i);

            // 방향 di로 속력 si칸 만큼 이동후 좌표
            int nx = makeNewPoint(fireball.r, dx[fireball.d] * fireball.s);
            int ny = makeNewPoint(fireball.c, dy[fireball.d] * fireball.s);
            
            // 파이버 볼 위치
            for (int j = 0; j < ballCount; j++) {
                if (fireballmap[nx][ny][j] == 0) { 
                    fireballmap[nx][ny][j] = i + 1;
                    break;
                }
            }
            // 좌표 변경
            fireball.r = nx;
            fireball.c = ny;
        }
    }

    static int makeNewPoint(int point, int move) {
        if (move < 0) {
            move = -(Math.abs(move) % N);
        } else {
            move = move % N;
        }

        point += move;

        if (point >= 0 && point < N) {
            return point;
        } else if (point >= N) { // n보다 클 때
            return point - N;
        } else { // 0보다 작을 때
            return N + point;
        }
    }

//    파이어볼은 4개의 파이어볼로 나누어진다.
//    나누어진 파이어볼의 질량, 속력, 방향은 다음과 같다.
//            질량은 ⌊(합쳐진 파이어볼 질량의 합)/5⌋이다.
//            속력은 ⌊(합쳐진 파이어볼 속력의 합)/(합쳐진 파이어볼의 개수)⌋이다.
//    합쳐지는 파이어볼의 방향이 모두 홀수이거나 모두 짝수이면, 방향은 0, 2, 4, 6이 되고, 그렇지 않으면 1, 3, 5, 7이 된다.
    static List<Fireball> splitTheFireball() {
        List<Fireball> newFireballs = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 두 개 이상의 파이어볼이 있는 경우
                if (ballCount > 1 && fireballmap[i][j][1] > 0) {
                    int sumM = 0;
                    int sumS = 0;
                    int firstDirection = fireballs.get(fireballmap[i][j][0] - 1).d;
                    boolean isAllEvenOrOddDirection = true;
                    int cnt = 0;

                    for (int k = 0; k < ballCount; k++) {
                        if (fireballmap[i][j][k] == 0) {
                            break;
                        }

                        Fireball fireball = fireballs.get(fireballmap[i][j][k] - 1);
                        sumM += fireball.m; // 합쳐진 파이어볼 질량의 합
                        sumS += fireball.s; // 합쳐진 파이어볼 속력의 합

                        // 전부 홀수, 전부 짝수인지 확인
                        if (isAllEvenOrOddDirection) {
                            if (firstDirection % 2 != fireball.d % 2) {
                                isAllEvenOrOddDirection = false;
                            }
                        }
                        cnt++; // 합쳐진 파이어볼의 개수
                    }

                    for (int k = 0; k < 4; k++) {
                        int splitM = sumM / 5;
                        int splitS = sumS / cnt;
                        // 소멸
                        if (splitM == 0) {
                            break;
                        }

                        if (isAllEvenOrOddDirection) {
                            newFireballs.add(new Fireball(i, j, splitM, splitS, k * 2));
                        } else {
                            newFireballs.add(new Fireball(i, j, splitM, splitS, (k * 2) + 1));
                        }
                    }
                } else if (ballCount > 0 && fireballmap[i][j][0] > 0) { // 한 개의 파이어볼만 있는 경우
                    newFireballs.add(fireballs.get(fireballmap[i][j][0] - 1));
                }
            }
        }

        return newFireballs;
    }


}
