package opre2;

public class Frame {
	public String frame_name;
	public int page_number;
	public int freeze;
	
	public Frame(String n, int p, int f) {
		frame_name = n;
		page_number = 0;
		freeze = f;
	}
	
	public Frame(Frame f) {
		frame_name = f.frame_name;
		page_number = f.page_number;
		freeze = f.freeze;
	}
}
