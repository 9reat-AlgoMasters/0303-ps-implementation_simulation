import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;

import java.util.Arrays;

import java.util.LinkedList;

import java.util.Queue;

import java.util.StringTokenizer;

public class Main {

	static int N; //맵의 크기	static int M; //파이어볼 개수

	static int K; //이동 명령 횟수

	static FireBall map[][];

	static Queue<FireBall> q ;

	static int dir [][] = {{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};

	static class FireBall{

		int r;

		int c;

		int m; //파이어볼 질량

		int s; //파이어볼 속력

		int d; //파이어볼 방향

		

		

		int count;

		boolean isDEven; //true 이면 다음 방향이 0,2,4,6 / false 이면 1, 3, 5, 7

		

		

		public FireBall(int r, int c, int m, int s, int d) {

			super();

			this.r = r;

			this.c = c;

			this.m = m;

			this.s = s;

			this.d = d;

			

			count = 1;

			isDEven = true;

		}

		@Override

		public String toString() {

			return "FireBall [r=" + r + ", c=" + c + ", m=" + m + ", s=" + s + ", d=" + d + ", count=" + count

					+ ", isDEven=" + isDEven + "]";

		}

	}

	

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());

		

		N = Integer.parseInt(st.nextToken()); 

		M = Integer.parseInt(st.nextToken());

		K = Integer.parseInt(st.nextToken());

		map = new FireBall[N][N];

		q = new LinkedList<>();

		for (int i = 0; i < M; i++) {

			st = new StringTokenizer(br.readLine());

			int r = Integer.parseInt(st.nextToken())-1;

			int c = Integer.parseInt(st.nextToken())-1;

			int m = Integer.parseInt(st.nextToken());

			int s = Integer.parseInt(st.nextToken());

			int d = Integer.parseInt(st.nextToken());

			

			map[r][c] = new FireBall(r, c, m, s, d);

			q.add(map[r][c]);

		}

		

		

		int count = 0;

		while ( K >count ) {

//			System.out.println("\n============="+(count+1)+"번 째 명령================");

//			

//			for (int i = 0; i < N; i++) {

//				System.out.println(Arrays.toString(map[i]));

//			}

//			System.out.println("..................");

		

			move(); // 파이어볼 d 방향으로 s칸 만큼 이동

			split();

			count++;

		}

		

		

//		for (int i = 0; i < N; i++) {

//			System.out.println(Arrays.toString(map[i]));

//		}

//		System.out.println("..................");

		int sumM = 0;

		while (!q.isEmpty()) {

			FireBall f = q.poll();

			sumM += f.m;

		}

		

		System.out.println(sumM);

		

	}

	

	static void move() {

//		System.out.println("==== 이동하자 ! ====");

		int size = q.size();

		for (int i = 0; i < size; i++) {

			FireBall f = q.poll();

//			System.out.println("이동 전 " + f.toString());

			f.r = findNext(f.r, f.d, f.s, 0);

			f.c = findNext(f.c, f.d, f.s, 1);

//			System.out.println("이동 후 "+f.toString());

			q.add(f);

		}

		map = new FireBall[N][N];

		size = q.size();

//		System.out.println("===== 지도에 집어 넣기 ====");

		for (int i = 0; i < size; i++) {

			FireBall f = q.poll();

//			System.out.println(f.toString());

			if(map[f.r][f.c] == null) {

				map[f.r][f.c] = f;

			}else {

				FireBall fireballs = map[f.r][f.c];

				fireballs.count ++;

				

				fireballs.m += f.m;

				fireballs.s += f.s;

				

				if(fireballs.d % 2 != f.d % 2) {

					fireballs.isDEven = false;

				}

			}

		}

		

//		for (int i = 0; i < N; i++) {

//			System.out.println(Arrays.toString(map[i]));

//		}

//		System.out.println("..................");

		

	}

	

	

	

	static int findNext(int n, int d, int s, int i) {

		int result = n + ( dir[d][i] * ( s % N ));

		if(result < 0) {

			result  = (result+N);

		}

		result = result%N;

		return result;

	}

	

	

	

	

	

	static void split() {

		for(int i = 0; i < N; i++) {

			for (int j = 0; j < N; j++) {

				if(map[i][j] != null){

					FireBall f = map[i][j];

//					System.out.println("지도에 넣어둔 파이어볼 : "+f.toString());

					if(f.count > 1) {

						int m = (int)f.m / 5;

						if(m == 0) {

							continue;

						}

						int s = (int)f.s / f.count;

						int start = 0;

						if(!f.isDEven) {

							start = 1;

						}

						for (int d = start; d <=7; d+=2) {

							FireBall temp = new FireBall(f.r, f.c, m, s, d);

//							System.out.println("큐에 들어가는 분열한 파이어볼 : "+temp.toString());

							q.add(new FireBall(f.r, f.c, m, s, d));

						}

						continue;

					}

//					System.out.println("큐에 들어가는 분열 하지 않은 파이어볼 : "+f.toString());

					q.add(f);

				}

			}

		}

		

		

	}

}
