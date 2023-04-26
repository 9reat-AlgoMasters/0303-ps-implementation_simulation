import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Q10836 {
    static int N, M;
    static int[] diff;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        diff = new int[2*N-1];
        Arrays.fill(diff, 1);
        
        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());
            int zero = Integer.parseInt(st.nextToken());
            int one = Integer.parseInt(st.nextToken());
            int two = Integer.parseInt(st.nextToken());
            for (int i = 0; i < 2*N-1; i++) {
                if (zero > 0) {
                    zero--;
                } else if (one > 0) {
                    diff[i]++;
                    one--;
                } else {
                    diff[i] += 2;
                    two--;
                }
            }
        }
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (j == 0) {
                    sb.append(diff[N-1-i]);
                } else {
                    sb.append(diff[N-1+j]);
                }
                sb.append(" ");
            }
            sb.append("\n");
        }
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
}
