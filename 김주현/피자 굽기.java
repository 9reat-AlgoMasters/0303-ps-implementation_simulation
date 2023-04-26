import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Q1756_2 {
    static int N, M;
    static PriorityQueue<Oven> ovens;
    
    static class Oven implements Comparable<Oven> {
        int depth, size;
        
        public Oven(int depth, int size) {
            this.depth = depth;
            this.size = size;
        }
        
        // size 우선 오름차순, 만약 size가 같다면 얕은 깊이 순서대로 나오도록
        @Override
        public int compareTo(Oven o) {
            return this.size == o.size ? this.depth - o.depth : this.size - o.size;
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
        ovens = new PriorityQueue<>();
        st = new StringTokenizer(br.readLine());
        int depth = 1;
        while (N-- > 0) {
            ovens.add(new Oven(depth++, Integer.parseInt(st.nextToken())));
        }
        
        st = new StringTokenizer(br.readLine());
        int lastPizzaDepth = 0;
        int maxDepth = depth-1;
        boolean isEnough = true;
        while (M-- > 0) {
            int pizza = Integer.parseInt(st.nextToken());
            
            // maxDepth 보다 큰 높이들은 다 빼냄
            while (!ovens.isEmpty() && (ovens.peek().depth > maxDepth || ovens.peek().size < pizza)) {
                if (ovens.peek().size < pizza) {
                    maxDepth = Math.min(maxDepth, ovens.peek().depth - 1);
                }
                ovens.poll();
            }
            
            // 오븐이 비었으면
            if (ovens.isEmpty()) {
                isEnough = false;
                break;
            }
            
            // 들어갈 수 있다면 maxDepth에 들어간다.
//            System.out.printf("크기가 %d인 피자는 깊이 %d 오븐에 들어갑니다.\n", pizza, maxDepth);
            lastPizzaDepth = maxDepth--;
        }
        
        if (isEnough) {
            sb.append(lastPizzaDepth);
        } else {
            sb.append(0);
        }
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}
