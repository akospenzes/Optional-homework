package opre2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

	@SuppressWarnings("null")
	public static void main(String[] args) {
		String line = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] stringinput = line.split(",");
		ArrayList<Integer> input = new ArrayList<Integer>();
		for(int i = 0; i < stringinput.length ; i++) {
			input.add(Math.abs(Integer.parseInt(stringinput[i])));
		}
		
		String output = new String();
		int x = 0;
		ArrayList<Frame> frames = new ArrayList<Frame>();
		Frame A = new Frame("A",0,0);
		Frame B = new Frame("B",0,0);
		Frame C = new Frame("C",0,0);
		frames.add(A);
		frames.add(B);
		frames.add(C);
		
		for(int i = 0; i < input.size(); i++) {
			//System.out.println(frames.get(0).frame_name + frames.get(0).page_number + "\n" +frames.get(1).frame_name + frames.get(1).page_number + "\n" + frames.get(2).frame_name + frames.get(2).page_number + "\n" );
			if(PageInFrames(frames, input.get(i)) == true) {
				output += "-";
				for(int z = 0; z < frames.size(); z++) {
					if(frames.get(z).page_number == input.get(i)) {
						Frame nf = new Frame(frames.get(z));
						frames.remove(z);
						frames.add(nf);
						break;
					}
				}
				for(int k = 0; k < frames.size(); k++) {
					if(frames.get(k).freeze > 0) {
						frames.get(k).freeze -= 1;
					}
				}
			}
			else if(AllFrozen(frames)) {
				output += "*";
				x++;
				for(int k = 0; k < frames.size(); k++) {
					if(frames.get(k).freeze > 0) {
						frames.get(k).freeze -= 1;
					}
				}
			}
			else {
				for(int j = 0; j < frames.size(); j++) {
					if(frames.get(j).freeze <= 0) {
						Frame nf = new Frame(frames.get(j));
						nf.freeze = 3;
						nf.page_number = input.get(i);
						frames.remove(j);
						for(int k = 0; k < frames.size(); k++) {
							if(frames.get(k).freeze > 0) {
								frames.get(k).freeze -= 1;
							}
						}
						frames.add(nf);
						output += nf.frame_name;
						x++;
						break;
					}
				}
			}
		}
		
		System.out.println(output);
		System.out.println(x);
	}
	
	public static boolean PageInFrames(ArrayList<Frame> f, int p) {
		for(int i = 0; i < f.size(); i++) {
			if(f.get(i).page_number == p) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean AllFrozen(ArrayList<Frame> f) {
		for(int i = 0; i < f.size(); i++) {
			if(f.get(i).freeze <= 0) {
				return false;
			}
		}
		return true;
	}
}
