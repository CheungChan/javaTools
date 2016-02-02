package com.cheungchan;


public class ProgressJava {
	private int count;
	private int total;
	private int width;
	ProgressJava(int count,int total,int width){
		this.count = count;
		this.total = total;
		this.width = width;
	}
	public void move(){
		count += 1;
	}
	public void log(String s){
		for (int i = 0;i < this.width+9;i++){
			System.out.print(" ");
		}
		System.out.print("\r");
		System.out.flush();
		System.out.println(s);
		int progress = count * width / total;
		System.out.print(String.format("%d / %d", count,total));
		for (int i = 0; i < progress; i++) {
			System.out.print("#");
		}
		for (int i = 0; i < (width - progress); i++) {
			System.out.print("-");
		}
		System.out.print("\r");
		if(progress == width){
			System.out.print("\n");
		}
		System.out.flush();
	}
	public static void main(String[] args) {
		ProgressJava pj = new ProgressJava(0,10,50);
		for(int i = 0;i < 10;i++){
			pj.move();
			pj.log("We have arrived at " + (i+1));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
