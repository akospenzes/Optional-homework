import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		String order = new String();
		String waitings = new String();
		
		char tmp = 0;
		
		ArrayList<Task> Alltasks = new ArrayList<Task>();
		ArrayList<Task> Highprio = new ArrayList<Task>();
		ArrayList<Task> Lowprio = new ArrayList<Task>();
		Task Runningtask;
		
		ArrayList<Arriveorder> ao = new ArrayList<Arriveorder>();	
		
		Alltasks.clear();
		Highprio.clear();
		Lowprio.clear();
		ao.clear();
		
		int o = 0;
		
		Scanner scan = new Scanner(System.in);
		while(scan.hasNextLine()) {
			String[] t = scan.nextLine().split(",");
			if(t.length < 2) {
				break;
			}
			o++;
			char n = t[0].charAt(0);
			int p = Integer.parseInt(t[1]);
			int s = Integer.parseInt(t[2]);
			int r = Integer.parseInt(t[3]);
			Task task = new Task(n,p,s,r);
			ao.add(new Arriveorder(n,o));
			Alltasks.add(task);
			if(task.Priority == 1) {
				Highprio.add(task);
			}
			else{
				Lowprio.add(task);
			}
		}
		scan.close();
			
		Runningtask = null;
		int tick = 0;
		
		if(Lowprio.size() > 0 && Highprio.size() > 0) {
			while((Highprio.get(0).Runtime + Lowprio.get(0).Runtime) > 0) {
				if(Highprio.get(0).Starttime <= tick || Lowprio.get(0).Starttime <= tick) {
					if(Highprio.get(0).Starttime <= tick && Highprio.get(0).Runtime != 0) {
						Runningtask = Highprio.get(0);
						Highprio.remove(0);
						
						int rt = Runningtask.Runtime;
						for(int u = 0; u < rt; u++) {
							for(int i = 0; i < Highprio.size(); i++) {
								if(Highprio.get(i).Starttime <= tick && Highprio.get(i).Runtime != 0) {
									Highprio.get(i).Waiting += 1;
								}
							}
							for(int i = 0; i < Lowprio.size(); i++) {
								if(Lowprio.get(i).Starttime <= tick && Lowprio.get(i).Runtime != 0) {
									Lowprio.get(i).Waiting += 1;
								}
							}
							tick++;
						}
						Runningtask.Runtime = 0;
						Highprio.add(Runningtask);
						if(Runningtask.Name != tmp)
							order += Runningtask.Name;
						tmp = Runningtask.Name;
					}
					else if(Lowprio.get(0).Starttime <= tick && Lowprio.get(0).Runtime != 0){
						Runningtask = Lowprio.get(0);
						if(Lowprio.get(0).Runtime >= 2) {
							Lowprio.remove(0);
							for(int j = 0; j < 2; j++) {
								Runningtask.Runtime --;
								for(int i = 0; i < Highprio.size(); i++) {
									if(Highprio.get(i).Starttime <= tick && Highprio.get(i).Runtime != 0) {
										Highprio.get(i).Waiting += 1;
									}
								}
								for(int i = 0; i < Lowprio.size(); i++) {
									if(Lowprio.get(i).Starttime <= tick && Lowprio.get(i).Runtime != 0) {
										Lowprio.get(i).Waiting += 1;
									}
								}
								tick++;
								if(Highprio.size() != 0 && Highprio.get(0).Starttime <= tick && Highprio.get(0).Runtime != 0) {
									break;
								}
							}
							Lowprio.add(Runningtask);
							if(Runningtask.Name != tmp)
								order += Runningtask.Name;
							tmp = Runningtask.Name;
						}
						else {
							Runningtask = Lowprio.get(0);
							Lowprio.remove(0);
							for(int i = 0; i < Highprio.size(); i++) {
								if(Highprio.get(i).Starttime <= tick && Highprio.get(i).Runtime != 0) {
									Highprio.get(i).Waiting += 1;
								}
							}
							for(int i = 0; i < Lowprio.size(); i++) {
								if(Lowprio.get(i).Starttime <= tick && Lowprio.get(i).Runtime != 0) {
									Lowprio.get(i).Waiting += 1;
								}
							}
							tick++;
							Runningtask.Runtime = 0;
							Lowprio.add(Runningtask);
							if(Runningtask.Name != tmp)
								order += Runningtask.Name;
							tmp = Runningtask.Name;
						}
					}
				}
				else {
					tick ++;
				}
			}
		}
		else if(Highprio.size() > 0) {
			while(Highprio.get(0).Runtime > 0) {
				if(Highprio.get(0).Starttime <= tick && Highprio.get(0).Runtime != 0) {
					Runningtask = Highprio.get(0);
					Highprio.remove(0);
					int rt = Runningtask.Runtime;
					for(int u = 0; u < rt; u++) {
						for(int i = 0; i < Highprio.size(); i++) {
							if(Highprio.get(i).Starttime <= tick && Highprio.get(i).Runtime != 0) {
								Highprio.get(i).Waiting += 1;
							}
						}
						tick++;
					}
					Runningtask.Runtime = 0;
					Highprio.add(Runningtask);
					if(Runningtask.Name != tmp)
						order += Runningtask.Name;
					tmp = Runningtask.Name;
				}
				else {
					tick ++;
				}
			}
		}
		else {
			
		}

		
	
		
		Collections.sort(Alltasks, new Comparator<Task>() {
	        @Override
	        public int compare(Task t1, Task t2)
	        {
	        	int o1 = 0,o2 = 0;
	        	for(int j = 0; j < ao.size(); j++) {
	        		if(t1.Name == ao.get(j).Name) {
	        			o1 = ao.get(j).Arrival;
	        		}
	        		if(t2.Name == ao.get(j).Name) {
	        			o2 = ao.get(j).Arrival;
	        		}
	        	}
				return o1 - o2;
	        }
	    });
		
		for(int i = 0; i < Alltasks.size(); i++) {
			waitings += Alltasks.get(i).Name;
			waitings += ":";
			waitings += Alltasks.get(i).Waiting;
			if(i < Alltasks.size() -1) {
				waitings += ",";
			}
		}
		
		System.out.println(order);
		System.out.println(waitings);
	}

}
