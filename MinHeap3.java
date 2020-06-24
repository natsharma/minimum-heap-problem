package minHeap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Scanner;

public class MinHeap3 {
	
	public static void main(String [] args) throws IOException, FileNotFoundException {
		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out, "ASCII"), 4096);
        MinHeap myHeap = new MinHeap();
        File file = new File("src/minHeap3/hash-heap-sample-io/input01.txt");
        Scanner scanner = new Scanner(file);
        
        HashMap<String, Soldja> database = new HashMap<>();
        int dataSize = scanner.nextInt(); 
        //push all the soldiers to the min heap 
        for (int i=0; i<=dataSize; i++) {
            Soldja soldja = new Soldja();
            soldja.name = scanner.next();
            soldja.score = Long.valueOf(scanner.next());
            myHeap.push(soldja);
            database.put(soldja.name, soldja);
        }
        int query = 0;
        int queries = scanner.nextInt();
        long standard;
        for (int i=0; i<queries; i++) {
            query = scanner.nextInt();
            if (query == 1) {
                Soldja temp = database.get(scanner.next());
                temp.score = temp.score + scanner.nextLong();
                myHeap.heapify();
            } else {
                standard = scanner.nextLong();
                while(myHeap.peek() < standard){
                    myHeap.pop();
                }
                output.write(myHeap.size);
            }
        }
        output.flush();
	}
}

class Soldja{ 
    String name;
    long score;
    int position;
	public long compareTo(long l) {
		// TODO Auto-generated method stub
		return this.score - l;
	}
}

class MinHeap {
	Soldja [] heap;
	int size;
	public MinHeap() {
		heap = new Soldja[1000]; size = 0;
	}
	public void push(Soldja s) {
		if(size == heap.length) {throw new IllegalStateException("Queue is full! Can't push anything.");}
		int pos = size;
		heap[pos] = s;
		s.position = pos;
		size++;
		while(pos > 0) {
			int parent = (pos + 1) / 2 - 1;
			if(heap[parent].score <= heap[pos].score) break;
			swapIndices(parent, pos);
			pos = parent;
		}
	}
	public Soldja pop() {
		if(size == 0) {throw new IllegalStateException("Empty queue! Can't pop anything.");}
		Soldja toReturn = heap[0];
		//put the last one in first place
		heap[0] = heap[size-1]; 
		size--;
		
		int pos = 0;
		while(pos < size/2) {
			int leftChild = pos * 2 + 1;
			int rightChild = pos * 2 + 2;
			
			if(rightChild < size && heap[leftChild].score > heap[rightChild].score) {
				if(heap[pos].score <= heap[rightChild].score) break;
				swapIndices(pos, rightChild);
				pos = rightChild;
			}
			else {
				if(heap[pos].score <= heap[leftChild].score) break;
				swapIndices(pos, leftChild);
				pos = leftChild;
			}
		}
		return toReturn;
	}
	public long peek() {
		return heap[0].score;
	}
	public void swapIndices(int i, int j) {
		int tempIndex = heap[i].position;
		heap[i].position = heap[j].position;
		heap[j].position = tempIndex;
		
		Soldja temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
		
	}
	
	public void heapify() {
		int pos = 0;
		while(pos < size/2) {
			int leftChild = pos * 2 + 1;
			int rightChild = pos * 2 + 2;
			
			if(rightChild < size && heap[leftChild].score > heap[rightChild].score) {
				if(heap[pos].score <= heap[rightChild].score) break;
				swapIndices(pos, rightChild);
				pos = rightChild;
			}
			else {
				if(heap[pos].score <= heap[leftChild].score) break;
				swapIndices(pos, leftChild);
				pos = leftChild;
			}
		}
	}
	
	public void changeScore(Soldja s, long change) {
		heap[s.position].score += change;
		heapify();
	}
	public int size() {
		return size;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Soldja s : heap) {
			sb.append(s.name + " " + s.score + "\n");
		}
		return sb.toString();		
	}
	
}


