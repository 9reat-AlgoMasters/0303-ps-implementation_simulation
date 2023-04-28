import java.io.*;

import java.util.*;

public class Main {

	static int D; // 오븐의 깊이	static int N; // 피자 반죽의 개수

	static int top = 0; // 맨 위 피자를 넣었을 때의 깊이

	static int oven[]; // 최상단에서 차례대로 넣었을때 들어갈 수 있는 피자 반죽의 지름

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());

		D = Integer.parseInt(st.nextToken());

		N = Integer.parseInt(st.nextToken());

		oven = new int[D];

		st = new StringTokenizer(br.readLine());

		int ovenDia = Integer.parseInt(st.nextToken());

		oven[0] = ovenDia;

		for (int i = 1; i < D; i++) {

			ovenDia = Integer.parseInt(st.nextToken());

			// oven : 현 oven에 들어올 수 있는 pizza의 지름

			// 현 oven의 길이가 위 오븐의 길이보다 크다면 위 오븐의 길이로 설정한다.

			if (oven[i - 1] < ovenDia)

				oven[i] = oven[i - 1];

			else

				oven[i] = ovenDia;

		}

		top = Integer.MAX_VALUE;

		st = new StringTokenizer(br.readLine());

		int right = D; // 오븐에 피자가 들어가면 최상단부터 피자가 마지막으로 들어간 곳까지 탐색하기 위해 두는 변수

		for (int i = 0; i < N; i++) {

			int pizzaDia = Integer.parseInt(st.nextToken());

			//idx : 피자를 넣을 오븐의 위치 

			int idx = findPizzaIdx(pizzaDia, right);

			

			//피자가 오븐의 크기보다 크거나 다 넣지 못하면 -1을 출력한다.

			if (idx < 0) {

				System.out.println(0);

				return;

			}

			if (top > idx)

				top = idx;

			right = idx; //탐색 위치 변경

		}

		System.out.println(top + 1);

	}

	

	//이분탐색을 활용한 피자의 오븐 위치를 구하는 함수

	private static int findPizzaIdx(int key, int right) {

		int left = 0;

		int mid = 0;

		while (left < right) {

			mid = (left + right) / 2;

			if (key > oven[mid]) {

				right = mid;

			} else {

				left = mid + 1;

			}

		}

		return left - 1;

	}

}
