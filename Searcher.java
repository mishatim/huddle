import java.util.Arrays;

public class Searcher {

	private int[] arr;
	
	public Searcher(int[] searchArray) {
		arr = searchArray;
	}
	
	/**
	 * Perform binary search on sorted array.
	 * @param begin
	 * @param end
	 * @param findMe Search element
	 * @return Returns index of search element or -1 if it's absent.
	 */
    public int binarySearch(int begin, int end, int findMe)
    {
        if (end >= begin)
        {
        	// divide
            int mid = begin + (end - begin)/2;
 
            // if we hit it right away, bingo
            if (arr[mid] == findMe) {
               return mid;
            }
 
            // if element is smaller than mid, lookup in left half
            if (arr[mid] > findMe) {
               return binarySearch(begin, mid-1, findMe);
            }
            else {
            	// otherwise, lookup in the right half
            	return binarySearch(mid+1, end, findMe);
            }
        }
 
        // was not found
        return -1;
    }
    
	public static void main(String[] args) {
		// unsorted arr
		int[] arr = { 9, 2, 4, 7, 3, 32, 12, 4, 8, 0, -2 };
		new Sorter(arr).QuickSort(0, arr.length-1);
		
		// now the array is sorted
		int findMe = 32;
		System.out.println("Searching for " + findMe + " in this array: " + Arrays.toString(arr));
		//int[] arr = {-2, 0, 2, 3, 4, 4, 7, 8, 9, 12, 32 };

		int foundPos = new Searcher(arr).binarySearch(0, arr.length-1, findMe);
		System.out.println("Found search value of " + findMe + " at position " + foundPos);
	}

}
