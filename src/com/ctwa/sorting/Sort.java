package com.ctwa.sorting;

public class Sort {

	/*********************************************
	 * 1. Bubble Sort algorithm
	 * 
	 *********************************************/
	public void bubbleSort(int arrToSort[]) {
		int n = arrToSort.length;
		// loop to access each array element
		for (int i = 0; i < n - 1; i++)
			// loop to compare array elements
			for (int j = 0; j < n - i - 1; j++)
				// compare two elements
				// change > to < to sort in descending order
				if (arrToSort[j] > arrToSort[j + 1]) {
					int temp = arrToSort[j];
					arrToSort[j] = arrToSort[j + 1];
					arrToSort[j + 1] = temp;
				}
	}

	/*********************************************
	 * 2. Quick Sort algorithm
	 * 
	 *********************************************/
	public void quickSort(int[] arrToSort, int low, int high) {
		if (low < high) {
			// p is partitioning index, arrToSort[p]
			// is now at right place
			int p = partition(arrToSort, low, high);
			// Separately sort elements before
			// partition and after partition
			quickSort(arrToSort, low, p - 1);
			quickSort(arrToSort, p + 1, high);
		}
	}

	// quick sort utility function to swap two elements
	private void substitute(int[] arrToSort, int low, int pivot) {
		int tmp = arrToSort[low];
		arrToSort[low] = arrToSort[pivot];
		arrToSort[pivot] = tmp;
	}

	/*
	 * Takes last element as pivot, places the pivot element at its correct position
	 * in sorted array, and places all smaller (smaller than pivot) to left of pivot
	 * and all greater elements to right of pivot
	 */
	private int partition(int[] arrToSort, int low, int high) {
		// pivot

		int p = low, j;
		for (j = low + 1; j <= high; j++)
			// If current element is smaller
			// than the pivot
			if (arrToSort[j] < arrToSort[low])
				// Increment index of
				// smaller element
				substitute(arrToSort, ++p, j);

		substitute(arrToSort, low, p);
		return p;
	}

	/*********************************************
	 * 3. Bucket Sort algorithm
	 * 
	 *********************************************/
	public void bucketSort(int[] arrToSortay, int maxValue) {
		// Create n empty buckets (Or lists).
		int[] Bucket = new int[maxValue + 1];
		int[] sorted_arr = new int[arrToSortay.length];
		// Do following for every array element arr[i].
		// Insert arrToSortay[i] into bucket[n*array[i]]
		for (int i = 0; i < arrToSortay.length; i++)
			Bucket[arrToSortay[i]]++;
		int outPos = 0;
		// Sort individual buckets using insertion sort
		// and concatenate all sorted buckets.
		for (int i = 0; i < Bucket.length; i++)
			for (int j = 0; j < Bucket[i]; j++)
				sorted_arr[outPos++] = i;
		arrToSortay = sorted_arr;
	}

	/*********************************************
	 * 4. Cocktail Sort algorithm
	 * 
	 *********************************************/
	public void cocktailSort(int arrToSort[]) {
		boolean swapped = true;
		int start = 0;
		int end = arrToSort.length;

		while (swapped == true) {
			// reset the swapped value at the begining
			// because it might be positive from
			// previous loop.
			swapped = false;

			// loop from bottom to top same as
			// the bubble sort
			for (int i = start; i < end - 1; ++i) {
				if (arrToSort[i] > arrToSort[i + 1]) {
					int temp = arrToSort[i];
					arrToSort[i] = arrToSort[i + 1];
					arrToSort[i + 1] = temp;
					swapped = true;
				}
			}

			// if nothing has moved, then array is sorted.
			if (swapped == false)
				break;

			// otherwise, reset the swapped value so that it
			// can be used in the next stage
			swapped = false;

			// move the end point back by one, because
			// item at the end is in its rightful spot
			end = end - 1;

			// from top to bottom, doing the
			// same comparison as in the previous stage
			for (int i = end - 1; i >= start; i--) {
				if (arrToSort[i] > arrToSort[i + 1]) {
					int temp = arrToSort[i];
					arrToSort[i] = arrToSort[i + 1];
					arrToSort[i + 1] = temp;
					swapped = true;
				}
			}

			// increase the starting point, because
			// the last stage would have moved the next
			// smallest number to its rightful spot.
			start = start + 1;
		}
	}

	/*********************************************
	 * 5. Comb Sort algorithm
	 * 
	 *********************************************/
	public void combSort(int arrToSort[]) {
		int arrToSortSize = arrToSort.length;

		// Initialize gapSize size equal to the size of array
		int gapSize = arrToSortSize;

		// Initialize swapped as true to make sure that
		// loop runs
		boolean swapped = true;

		// Keep running while gapSize is more than 1 and last
		// iteration caused a swap
		while (gapSize != 1 || swapped == true) {
			// find updated gapSize
			// Initialize swapped as false so that we can
			// check if swap happened or not
			gapSize = updateGap(gapSize);

			// Initialize swapped as false so that we can
			// check if swap happened or not
			swapped = false;

			// Compare all elements with current gapSize
			for (int i = 0; i < arrToSortSize - gapSize; i++) {
				if (arrToSort[i] > arrToSort[i + gapSize]) {
					int temp = arrToSort[i];
					arrToSort[i] = arrToSort[i + gapSize];
					arrToSort[i + gapSize] = temp;
					// indicate that item is swapped
					swapped = true;
				}
			}
		}
	}

	// To find gap between elements
	private int updateGap(int gapSize) {
		// Shrink gap by Shrink factor
		gapSize = (gapSize * 10) / 13;
		if (gapSize < 1)
			return 1;
		return gapSize;
	}

}
