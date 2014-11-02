package missouri.edu.pi_cluster;

import java.util.LinkedList;

public class MessageList {

	private LinkedList<String> queue = new LinkedList<String>();
	
	public synchronized void add(String s){
		queue.add(s);
	}
	
	public synchronized String remove(){
		return queue.remove();
	}
	
	public synchronized boolean isEmpty(){
		return queue.isEmpty();
	}
}
