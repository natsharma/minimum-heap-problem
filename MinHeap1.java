package minHeap;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MinHeap1 {
	
    public static void main(String[] args) throws IOException, FileNotFoundException {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out, "ASCII"), 4096);
        Heap myHeap = new Heap();
        File file = new File("src/minHeap/hash-heap-sample-io/input01.txt");
        Scanner scanner = new Scanner(file);
        /*
        try {
        	scanner = new Scanner(System.in);
        } catch (Exception e) {
			System.out.print("File not found!");
		}*/
        HashMap<String, Soldier> database = new HashMap<>();
        int dataSize = scanner.nextInt(); 
        Soldier[] soldiers = new Soldier[dataSize+1];
        int total = 0;
        Soldier soldier0 = new Soldier();
        soldier0.score = -1;
        soldiers[0] = soldier0;
        for (int i=1; i<=dataSize; i++) {
            Soldier soldier = new Soldier(); soldier.name = scanner.next(); soldier.score = Long.valueOf(scanner.next());
            soldier.position = ++total; soldiers[total] = soldier;
            myHeap.rise(soldiers, total);
            if (soldier.score <= soldier0.score){
                soldier0.score = 2*soldier.score;
            }
            database.put(soldier.name, soldier);
        } 
        int query = 0;
        int queries = scanner.nextInt();
        long standard;
        for (int i=0; i<queries; i++) {
            query = scanner.nextInt();
            if (query == 1) {
                Soldier temp = database.get(scanner.next());
                temp.score = temp.score + scanner.nextLong();
                int tempPosition = temp.position;
                myHeap.swap(soldiers, soldiers[temp.position], soldiers[total]);
                total--;
                if(tempPosition==1 || soldiers[tempPosition/2].score < soldiers[tempPosition].score)
                    myHeap.sink(soldiers, tempPosition, total);
                else myHeap.rise(soldiers, tempPosition);
                myHeap.rise(soldiers, ++total);
            } else {
                standard = scanner.nextLong();
                while(soldiers[1].score<standard && total> 0){
                    myHeap.swap(soldiers, soldiers[1], soldiers[total]);
                    total--;
                    myHeap.sink(soldiers, 1, total);
                }
                output.write(total + "\n");
            }
        }
        
        output.flush();
    }
    /* heap will be of type Soldier, can hold more than one value*/
    static class Soldier{
        //var for soldier's position in the heap
        String name; int position; long score;
        long compareTo(Soldier s2){
            return  this.score - s2.score;
        }

    }

    static class Heap{

        ArrayList<Soldier> myHeap;
        
        Heap(){
            myHeap = new ArrayList<Soldier>(1000);
        }

        static void sink(Soldier[] soldiers, int position, int total){
            if (2*position < total && (soldiers[position].score > soldiers[2*position].score || soldiers[position].score > soldiers[2*position + 1].score)) {
                if (soldiers[2*position].score < soldiers[2*position + 1].score) {
                    swap(soldiers, soldiers[position], soldiers[2*position]);
                    sink(soldiers, 2*position, total);
                } else {
                    swap(soldiers, soldiers[position], soldiers[2*position+1]);
                    sink(soldiers, 2*position+1, total);
                }
            }
        }
        
        static void rise(Soldier[] soldiers, int position){
            //while its parent is greater than it, switch with parent
            while(position/2 > 0 && soldiers[position].score<soldiers[position/2].score){
                swap(soldiers, soldiers[position], soldiers[position/2]);
                position = position/2;
            }
        }
        
        static void swap(Soldier[] soldiers, Soldier soldier1, Soldier soldier2){
            soldiers[soldier1.position] = soldier2;
            soldiers[soldier2.position] = soldier1;
            int temp = soldier1.position;
            soldier1.position = soldier2.position;
            soldier2.position = temp;
        }  
    }

}

