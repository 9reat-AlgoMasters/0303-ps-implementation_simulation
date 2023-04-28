import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;

import java.util.ArrayDeque;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.Deque;

import java.util.Queue;

import java.util.StringTokenizer;

public class Main {

		static final int APPLE = 1;

	static final char LEFT = 'L';

	static final char RIGHT = 'D';

	static final int SNAKE = 8;

	

	

	static int N; // 맵의 크기

	static int map[][];

	static int K; // 사과의 개수

	static int L; //뱀의 방향전환 횟수

	static SnakeDir[] snakeDirs; //뱀의 방향 전환 정보 배열

	static int time; 

	static int dir[][] = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } }; // 상 우 하 좌

	static Deque<Point> snakebody;

//	static Point head; //뱀의 머리가 위치하는 좌표

//	static Point tail; //뱀의 꼬리가 위치하는 좌표

	static class Point {

		int r;

		int c;

		public Point(int r, int c) {

			super();

			this.r = r;

			this.c = c;

		}

		@Override

		public String toString() {

			return "Point [r=" + r + ", c=" + c + "]";

		}

		

		

	}

	static class SnakeDir {

		int x;

		char c;

		public SnakeDir(int x, char c) {

			super();

			this.x = x;

			this.c = c;

		}

	}

	public static void main(String[] args) throws NumberFormatException, IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());

		K = Integer.parseInt(br.readLine());

		map = new int[N][N];

		for (int i = 0; i < K; i++) {

			StringTokenizer st = new StringTokenizer(br.readLine());

			int r = Integer.parseInt(st.nextToken());

			int c = Integer.parseInt(st.nextToken());

			map[r - 1][c - 1] = APPLE; //주어진 좌표가 1부터 시작이여서 1빼고 넣어준다. 

			//맵에 사과 위치 1로 표기하기 APPLE = 1

		}

		

		L = Integer.parseInt(br.readLine());

		snakeDirs = new SnakeDir[L];

		for (int i = 0; i < L; i++) {

			StringTokenizer st = new StringTokenizer(br.readLine());

			int x = Integer.parseInt(st.nextToken());

			char c = st.nextToken().charAt(0);

			snakeDirs[i] = new SnakeDir(x, c);

		}

		//-------------- 입력

		

		

		

		map[0][0] = SNAKE; //뱀의 첫 시작 위치 표기

		snakebody = new ArrayDeque<>();

		snakebody.offerFirst(new Point(0, 0));

		

		//뱀의 머리랑 꼬리 위치 지정

//		head = new Point(0, 0); 

//		tail = new Point(0, 0);

		

		dfs(0, 1);

	}

	

	

	static void dfs(int idx, int d) {

//		System.out.println("=============="+ time +"================");

//		for (int i = 0; i < N; i++) {

//			System.out.println(Arrays.toString(map[i]));

//		}

		Point head = snakebody.peekFirst();

	

		int nr = head.r+dir[d][0];

		int nc = head.c+dir[d][1];

		if (!isinRange(nr, nc) || map[nr][nc] == SNAKE ) {

			System.out.println(time+1);

			System.exit(0);

		}

		if (map[nr][nc] == APPLE) {

			snakebody.offerFirst(new Point(nr, nc));

			map[nr][nc] = SNAKE;

			

		} else {

			snakebody.offerFirst(new Point(nr, nc));

			map[nr][nc] = SNAKE;

			Point tail = snakebody.pollLast();

//			System.out.println("tail : " + tail.toString());

			map[tail.r][tail.c] = 0;

		}

		

		time++;

		if (idx < snakeDirs.length) {

			if (snakeDirs[idx].x == time) { //

				

				if (snakeDirs[idx].c == LEFT) {

					if (d != 0)

						d--;

					else

						d = 3;

				} else {

					if (d != 3)

						d++;

					else

						d = 0; 

				}

				idx++;

			}

		}

		

		dfs(idx, d);

	}

	

	

	//idx 는 snakeDirs 배열을 가르키는 포인터변수로 

	//snakeDirs 배열에 가르키고 있는 뱀의 방향변환 정보

//	static void dfs(int idx, int d, int num ) {

//

//		head.r += dir[d][0];

//		head.c += dir[d][1];

//		if (!isinRange(head.r, head.c) || (map[head.r][head.c] != 0 && map[head.r][head.c] != 1) ) {

//			System.out.println(time+1);

//			System.exit(0);

//		}

//

//		num++;

//		if (map[head.r][head.c] == APPLE) {

//			

//			map[head.r][head.c] = num;

//			

//		} else {

//			

//			map[head.r][head.c] = num;

//			int tnum = map[tail.r][tail.c];

//			map[tail.r][tail.c] = 0;

//			for (int i = 0; i < dir.length; i++) {

//				int r = tail.r + dir[i][0];

//				int c = tail.c + dir[i][1];

//				if (isinRange(r, c) && map[r][c] == tnum-1) {

//					tail.r = r;

//					tail.c = c;

//				}

//			}

//		}

//		

//		time++;

//		if (idx < snakeDirs.length) {

//			if (snakeDirs[idx].x == time) { //

//				

//				if (snakeDirs[idx].c == LEFT) {

//					if (d != 0)

//						d--;

//					else

//						d = 3;

//				} else {

//					if (d != 3)

//						d++;

//					else

//						d = 0; 

//				}

//				idx++;

//			}

//		}

//		

//		dfs(idx, d, num);

//	}

	

	static boolean isinRange(int nr, int nc) {

		return nr >= 0 && nc >= 0 && nr < N && nc < N;

	}

}
