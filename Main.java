package assignment3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	
	public static int[][] map;
	public static int[][] colored;
	public static int rows, columns;

	public static void main(String[] args) {

		Scanner keyboard = new Scanner(System.in);
		String[] size = keyboard.nextLine().split(" ");
		rows = Integer.parseInt(size[0]);
		columns = Integer.parseInt(size[1]);
		map = new int[rows][columns];
		colored = new int[rows][columns];
		for (int r = 0; r < rows; r++) {
			char[] nextRow = keyboard.nextLine().toCharArray();
			int nextChar = 0;
			for (int c = 0; c < columns; c++) {
				map[r][c] = Character.getNumericValue(nextRow[nextChar++]);
			}
		}
		
		int numQueries = keyboard.nextInt();
		keyboard.nextLine();
		int rFrom, cFrom, rTo, cTo;
		for (int q = 0; q < numQueries; q++) {
			int answer;
			String[] query = keyboard.nextLine().split(" ");
			rFrom = Integer.parseInt(query[0]) -1;
			cFrom = Integer.parseInt(query[1]) -1;
			rTo = Integer.parseInt(query[2]) -1;
			cTo = Integer.parseInt(query[3]) -1;
			if (colored[rFrom][cFrom] != 0 || colored[rTo][cTo] != 0) {
				if (colored[rTo][cTo] == colored[rFrom][cFrom]) {
					answer = map[rFrom][cFrom];
				}
				else {
					answer = -1;
				}
			}
			else {
				answer = search(rFrom, cFrom, rTo, cTo, q+1);
			}
			System.out.println(answer == -1 ? "neither" : (answer == 0 ? "binary" : "decimal"));
		}
		keyboard.close();
	}
	
	public static int search(int r, int c, int rT, int cT, int id) {
		
		Queue<int[]> queue = new LinkedList<>();
		
		boolean found = false;
		int[] start = {r, c};
		int type = map[r][c];
		queue.offer(start);
		
		int[] current;
		int r1, c1, up, down, right, left;
		while (!queue.isEmpty()) {
			current = queue.poll();
			r1 = current[0];
			c1 = current[1];
			if (r1 == rT && c1 == cT) {
				found = true;
			}
			up = r1-1;
			down = r1+1;
			right = c1+1;
			left = c1-1;

			if (up >= 0 && colored[up][c1] == 0 && map[up][c1] == type) {
				queue.offer(new int[] {up, c1});
				colored[up][c1] = id;
			}
			if (down < rows && colored[down][c1] == 0 && map[down][c1] == type) {
				queue.offer(new int[] {down, c1});
				colored[down][c1] = id;
			}
			if (left >= 0 && colored[r1][left] == 0 && map[r1][left] == type) {
				queue.offer(new int[] {r1, left});
				colored[r1][left] = id;
			}
			if (right < columns && colored[r1][right] == 0 && map[r1][right] == type) {
				queue.offer(new int[] {r1, right});
				colored[r1][right] = id;
			}
		}
		if (found)
			return type;
		return -1;
	}
}
