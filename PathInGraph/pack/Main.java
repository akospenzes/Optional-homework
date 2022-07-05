package pack;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		int npaths;
		int nnodes;
		int nedges;
		Scanner sc = new Scanner(System.in);
		npaths = sc.nextInt();
		nnodes = sc.nextInt();
		nedges = sc.nextInt();
		sc.nextLine();
		int[][] paths = new int[npaths][2];
		for(int i = 0; i < npaths; i++) {
			paths[i][0] = sc.nextInt();
			paths[i][1] = sc.nextInt();
		}
		sc.nextLine();
		int[][] nodes = new int[nnodes][2];
		for(int i = 0; i < nnodes; i++) {
			nodes[i][0] = sc.nextInt();
			nodes[i][1] = sc.nextInt();
		}
		sc.nextLine();
		double[][] graph = new double[nnodes][nnodes];
		for(int i = 0; i < nedges; i++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			double dist = distance(nodes[a][0], nodes[a][1], nodes[b][0], nodes[b][1]);
			graph[a][b] = dist;
			graph[b][a] = dist;
		}
		sc.close();
		String resultstring = new String();

		for(int n = 0; n < npaths; n++) {
			int start = paths[n][0];
			int end = paths[n][1];
			Node min = null;
			ArrayList<Node> fromstart = new ArrayList<Node>();
			for(int i = 0; i < nnodes; i++) {
				if(graph[start][i] > 0) {
					fromstart.add(new Node(i, graph[start][i]));
				}
			}
			boolean done = false;
			while(done!= true) {
				min = fromstart.get(0);
				for(int i = 0; i < fromstart.size(); i++) {
					if (fromstart.get(i).fromstart < min.fromstart)
						min = fromstart.get(i);
				}
				if(min.index == end) {
					double rounded = Math.round(min.fromstart * 100) / 100.0;
					String str = Double.toString(rounded);
					if(str.charAt(str.length() - 2) == '.') {
						str += '0';
					}
					resultstring += str;
					if(n == npaths - 1) {
						resultstring += "\n";
					}
					else {
						resultstring += "\t";
					}
					done = true;
					break;
				}
				else {
					for(int i = 0; i < nnodes; i++) {
						if(graph[min.index][i] > 0) {
							Node a = new Node(i, graph[min.index][i] + min.fromstart);
							Node tmp = getfromnodeindex(fromstart, i);
							if(tmp == null) {
								fromstart.add(a);
							}
							else {
								if(a.fromstart < tmp.fromstart) {
									fromstart.remove(tmp);
									fromstart.add(a);
								}
							}
						}
						fromstart.remove(min);
					}
				}
			}
		}
		System.out.print(resultstring);
	}
	public static double distance(int sx, int sy, int ex, int ey) {
		double dx = sx - ex;
		double dy = sy - ey;
		return Math.sqrt(dx * dx + dy * dy);	
	}
	
	public static Node getfromnodeindex(ArrayList<Node>a, int idx) {
		for(int i = 0; i < a.size(); i++) {
			if(a.get(i).index == idx)
				return a.get(i);
		}
		return null;
	}
}
