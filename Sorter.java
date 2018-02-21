import java.util.Arrays;

public class Sorter {

	// array to sort
	int arr[];
	
	public Sorter(int[] array) {
		this.arr = array;
	}
	
    public void QuickSort(int left, int right) {

    	// pick pivot - middle of the array
        int pivotIndex = left + (right - left) / 2;
        int pivotValue = arr[pivotIndex];

        int i = left;
        int j = right;

        while(i <= j) {

        	// find larger val on the left of pivot
            while(arr[i] < pivotValue) {
                i++;
            }

            // find smaler val on the right of pivot
            while(arr[j] > pivotValue) {
                j--;
            }

            // swap these values
            if(i <= j) {
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                i++;
                j--;
            }

            // recursively call self until j reaches left
            if(left < j) {
            	QuickSort(left, j);
            }
            // call self until i reaches the right
            if(right > i) {
            	QuickSort(i, right);
            }
        }
    }
	

	public static void main(String[] args) {
        
		int[] arr = { 9, 2, 4, 7, 3, 32, 12, 4, 8, 0, -2 };
		
		System.out.println("Unsorted list: " + Arrays.toString(arr));
 
		int start = 0;
		int end = arr.length - 1;
 
		new Sorter(arr).QuickSort(start, end);
		
		System.out.println("Sorted list: " + Arrays.toString(arr));
    }
}
