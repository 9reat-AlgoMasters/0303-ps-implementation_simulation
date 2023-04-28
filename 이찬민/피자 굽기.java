import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    static int D;
    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        D = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        List<Integer> list = new ArrayList<>();

        int min = Integer.MAX_VALUE;

        st = new StringTokenizer(br.readLine());
        int i = 0;
        // 현재 위치까지 올 수 있는 최대 지름 크기로 다 바꿔줌
        // 예시 기준 5 5 4 3 3 2 2
        while (st.hasMoreTokens()) {
            list.add(Math.min(min, Integer.parseInt(st.nextToken())));
            min = list.get(i++);
        }

        int endPos = D - 1;
        String[] str = br.readLine().split(" ");
        int cnt = 0;
        int answer = 0;
        //뒤에서 부터 자신의 위치를 찾는다
        for (int k = 0; k < N; k++) {
            int t = Integer.parseInt(str[k]);

            while (endPos >= 0) {
                if (list.get(endPos) >= t) {
                    answer = endPos + 1;
                    cnt++;
                    endPos--;
                    break;
                }
                endPos--;
            }
        }

        if (cnt != N) {
            System.out.println(0);
        } else {
            System.out.println(answer);
        }
    }
}
