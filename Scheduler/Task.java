
public class Task {
	public char Name;
	public int Priority;
	public int Starttime;
	public int Runtime;
	public int Waiting;

	public Task(char n,int p,int s,int r) {
		Name = n;
		Priority = p;
		Starttime = s;
		Runtime = r;
		Waiting = 0;
	}
	
	public static void printtask(Task t) {
		System.out.println(t.Name + " " + t.Priority + " " + t.Starttime + " " + t.Runtime + " " + t.Waiting);
	}
}
