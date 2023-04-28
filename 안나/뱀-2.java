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

	static Point head; //뱀의 머리가 위치하는 좌표

	static Point tail; //뱀의 꼬리가 위치하는 좌표

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

		head = new Point(0, 0); 

		tail = new Point(0, 0);

		

//		dfs(0, 1);

		

		map[0][0] = 3; //뱀의 첫 시작 위치 표기

		dfs(0, 1, 3);

	}

	

	

//	//idx 는 snakeDirs 배열을 가르키는 포인터변수로 snakeDirs 배열에 가르키고 있는 뱀의 방향정보의 초(x)와 현재 time 과 같으면 방향을 c로 전환한다.

//	static void dfs(int idx, int d) {

//

//		

//		Point head = snakebody.peekFirst();

//		//현재 머리를 뽑아서 가던 방향으로 한칸 이동

//		int nr = head.r+dir[d][0];

//		int nc = head.c+dir[d][1];

//

//		

// 		//벽과 부딪히거나 || 자기 몸과 닿으면 시간을 출력하고 종료

//		if (!isinRange(nr, nc) || map[nr][nc] == SNAKE ) {

//			System.out.println(time+1);

//			System.exit(0);

//		}

//

//		// 이동한 곳에 사과가 있어도 없어도 우선 머리는 움직이므로 머리 추가

//		snakebody.offerFirst(new Point(nr, nc));

//		map[nr][nc] = SNAKE;

//		

//		//머리가 이동한 위치에 사과가 없으면 꼬리 없애기 

//		if (map[nr][nc] != APPLE) {

//			Point tail = snakebody.pollLast();

//			map[tail.r][tail.c] = 0;

//		}

//		

//		

//		time++;

//		//마지막 방향정보 확인하면 배열의 인덱스보다 idx가 커지니까

//		//그 경우에 배열 인덱스에 접근하는 것을 막기 위해서 조건 추가

//		if (idx < snakeDirs.length) {

//			if (snakeDirs[idx].x == time) { //지금 시간이 방향을 바꿔야할 시간이라면 

//				//뱡향을 확인하고 바꾸어준다.

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

//				//다음 방향정보를 가르킬 수 있도록 idx 증가

//				idx++;

//			}

//		}

//		dfs(idx, d);

//	}

	

	

//	idx 는 snakeDirs 배열을 가르키는 포인터변수로 

//	snakeDirs 배열에 가르키고 있는 뱀의 방향변환 정보

	static void dfs(int idx, int d, int num ) {

//		System.out.println("============"+time +" / "+ num +"================");

//		for (int i = 0; i < N; i++) {

//			System.out.println(Arrays.toString(map[i]));

//			

//		}

		head.r += dir[d][0];

		head.c += dir[d][1];

		if (!isinRange(head.r, head.c) || (map[head.r][head.c] != 0 && map[head.r][head.c] != 1) ) {

			System.out.println(time+1);

			System.exit(0);

		}

		num++;

		if (map[head.r][head.c] == APPLE) {

			

			map[head.r][head.c] = num;

			

		} else {

			

			map[head.r][head.c] = num;

			int tnum = map[tail.r][tail.c];

			map[tail.r][tail.c] = 0;

			for (int i = 0; i < dir.length; i++) {

				int r = tail.r + dir[i][0];

				int c = tail.c + dir[i][1];

				if (isinRange(r, c) && map[r][c] == tnum+1) {

					tail.r = r;

					tail.c = c;

				}

			}

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

		

		dfs(idx, d, num);

	}

	

	static boolean isinRange(int nr, int nc) {

		return nr >= 0 && nc >= 0 && nr < N && nc < N;

	}

}
