package minHeap;
import java.util.*;
import java.io.*;
public class MinHeap2 {
	
	static HashMap<String, Long> map = new HashMap<String, Long>();
	static PriorityQueue<Long> queue = new PriorityQueue<Long>();
	static long standard = 0;
	
	public static void main (String[] args) throws IOException{
		
		
		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out, "ASCII"), 4096);
		File file = new File("src/minHeap/hash-heap-sample-io/input01.txt");
        Scanner sc = new Scanner(file);
        long dataSize = Long.parseLong(sc.nextLine());
        for (int i=0; i<dataSize; i++){
        	String line = sc.nextLine();
        	String[] info = line.split("\\s+");
        	if (!map.containsKey(info[0])){
        		map.put(info[0], Long.parseLong(info[1]));
        		queue.offer(Long.parseLong(info[1]));
        	}
        }
        long queries = Long.parseLong(sc.nextLine());
        for (int i=0; i<queries; i++){
        	String line = sc.nextLine();
        	String[] info = line.split("\\s+");
        	if (line.startsWith("1")){
        		long oldValue = map.get(info[1]);
        		long increment = Long.parseLong(info[2]);
        		long newValue = oldValue + increment;
        		map.replace(info[1], oldValue, newValue);
        		queue.remove(oldValue);
        		queue.offer(newValue);
            }
            else if (line.startsWith("2")){
            	standard = Long.parseLong(info[1]);
            	while (queue.peek() < standard){
            		queue.poll();
            	}
            	output.write(queue.size() + "\n");
            }
        }

        output.flush();
		
	}

}
