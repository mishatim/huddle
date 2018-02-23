using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SearcherForHuddle
{
    class Searcher
    {
        private int[] arr;

        public Searcher(int[] searchArray)
        {
            arr = searchArray;
        }

        /**
         * Perform binary search on sorted array.
         */
        public int binarySearch(int begin, int end, int findMe)
        {
            if (end >= begin)
            {
                // divide
                int mid = begin + (end - begin) / 2;

                // if we hit it right away, bingo
                if (arr[mid] == findMe)
                {
                    return mid;
                }

                // if element is smaller than mid, lookup in left half
                if (arr[mid] > findMe)
                {
                    return binarySearch(begin, mid - 1, findMe);
                }
                else
                {
                    // otherwise, lookup in the right half
                    return binarySearch(mid + 1, end, findMe);
                }
            }

            // was not found
            return -999;
        }

        static void Main(string[] args)
        {
            Console.Write("Type a number...");
            string input = Console.ReadLine();
            // array to search
            int[] arr = { -11, -5 -2, 0, 2, 3, 4, 4, 7, 8, 9, 12, 32 };
            int valueToFind;
            if (int.TryParse(input, out valueToFind))
            {
                int position = new Searcher(arr).binarySearch(0, arr.Length - 1, valueToFind);
                Console.WriteLine("The the postion for the provided value is: " + position);
            }
            else
            {
                Console.WriteLine("Please provide a numeric value.");
            }

        }
    }
}
