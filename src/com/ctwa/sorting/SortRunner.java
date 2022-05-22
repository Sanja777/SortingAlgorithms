package com.ctwa.sorting;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SortRunner {
	public static void main(String args[]) {

		// number of measurements to take from each algorithm for each array size
		final int numberOfTimesToMeassureAlgorithm = 10;

		// number of integers that each array will contain for test, comment out numbers
		// from 10000 for faster execution
		final int[] testArraySizes = { 100, 250, 500, 750, 1000, 1250, 2500, 3750, 5000, 6250, 7500, 8750/*, 10000, 40000,
				100000*/ };

		// number of sorting algorithms in this code, adjust this if algorithm is added
		// or removed
		final int numberOfSortAlgorithms = 5;

		// decimal format for time
		final DecimalFormat timeDecimalFormat = new DecimalFormat("0.00000");

		// individual sort result execution time for each algorithm
		double[][] testElapsedMs = new double[numberOfSortAlgorithms][numberOfTimesToMeassureAlgorithm];
		// average sort results time for all algorithms
		double[][] sortExecutionTimeMs = new double[numberOfSortAlgorithms][testArraySizes.length];

		// main sorter object
		Sort sort = new Sort();

		double startTime = 0;
		double endTime = 0;

		for (int iTestArray = 0; iTestArray < testArraySizes.length; iTestArray++) {

			for (int iSample = 0; iSample < numberOfTimesToMeassureAlgorithm; iSample++) {

				// create random array of integers that each algorithm will clone and use it's
				// data
				// and partially sort the array if it's on the partially sort index list
				int[] testArray = new int[testArraySizes[iTestArray]];
				fillArrayWithRandomNumbers(testArray);

				// 1. bubble sort
				int[] bubbleSort = testArray.clone();
				startTime = System.nanoTime();
				sort.bubbleSort(bubbleSort);
				endTime = System.nanoTime();
				testElapsedMs[0][iSample] = ((endTime - startTime) / 1000000);

				// 2. quick sort
				int[] quickSort = testArray.clone();
				startTime = System.nanoTime();
				//sort.quickSort(quickSort, bubbleSort[bubbleSort.length - 1], bubbleSort[0]);
				sort.quickSort(quickSort, 0, bubbleSort.length-1);
				endTime = System.nanoTime();
				testElapsedMs[1][iSample] = ((endTime - startTime) / 1000000);
				quickSort = null;

				// 3. bucket sort
				int[] bucketSort = testArray.clone();
				startTime = System.nanoTime();
				// this sort is creating problems, maybe the big numbers...
				sort.bucketSort(bucketSort, bubbleSort[bubbleSort.length - 1]);
				endTime = System.nanoTime();
				testElapsedMs[2][iSample] = ((endTime - startTime) / 1000000);
				bubbleSort = null;
				bucketSort = null;

				// 4. cocktail sort
				int[] cocktailSort = testArray.clone();
				startTime = System.nanoTime();
				sort.cocktailSort(cocktailSort);
				endTime = System.nanoTime();
				testElapsedMs[3][iSample] = ((endTime - startTime) / 1000000);
				cocktailSort = null;

				// 5. comb sort
				int[] combSort = testArray.clone();
				startTime = System.nanoTime();
				sort.combSort(combSort);
				endTime = System.nanoTime();
				testElapsedMs[4][iSample] = ((endTime - startTime) / 1000000);
				combSort = null;

				// store the measured average times
				for (int i = 0; i < numberOfSortAlgorithms; i++) {
					sortExecutionTimeMs[i][iTestArray] = findAverage(testElapsedMs[i]);
				}

			}
		}

		// print out results
		List<List<String>> rows = new ArrayList<>();
		List<String> headers = new ArrayList<String>();
		headers.add("Size");
		for (int i = 0; i < testArraySizes.length; i++) {
			headers.add(Integer.toString(testArraySizes[i]));
		}
		rows.add(headers);
		rows.add(Arrays.asList(concatenate(new String[] { "Bubble Sort" },
				doubleToString(sortExecutionTimeMs[0], timeDecimalFormat))));
		rows.add(Arrays.asList(
				concatenate(new String[] { "Quick Sort" }, doubleToString(sortExecutionTimeMs[1], timeDecimalFormat))));
		rows.add(Arrays.asList(concatenate(new String[] { "Bucket Sort" },
				doubleToString(sortExecutionTimeMs[2], timeDecimalFormat))));
		rows.add(Arrays.asList(concatenate(new String[] { "Cocktail Sort" },
				doubleToString(sortExecutionTimeMs[3], timeDecimalFormat))));
		rows.add(Arrays.asList(
				concatenate(new String[] { "Comb Sort" }, doubleToString(sortExecutionTimeMs[4], timeDecimalFormat))));

		System.out.println(formatAsTable(rows));

	}

	/*
	 * Utility functions
	 */

	static String[] doubleToString(double[] arr, DecimalFormat decimalFormat) {
		String rez[] = new String[arr.length];
		for (int i = 0; i < rez.length; i++) {
			rez[i] = decimalFormat.format(arr[i]);
		}
		return rez;
	}

	// fill array with random numbers
	static void fillArrayWithRandomNumbers(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = ThreadLocalRandom.current().nextInt(1, 999000);
		}
	}

	// find average number from an array
	static double findAverage(double[] array) {
		double sum = findSum(array);
		return (double) sum / array.length;
	}

	// Summarize all items from array
	static double findSum(double[] array) {
		double sum = 0;
		for (double value : array) {
			sum += value;
		}
		return sum;
	}

	static String formatAsTable(List<List<String>> rows) {
		int[] maxLengths = new int[rows.get(0).size()];
		for (List<String> row : rows) {
			for (int i = 0; i < row.size(); i++) {
				maxLengths[i] = Math.max(maxLengths[i], row.get(i).length());
			}
		}

		StringBuilder formatBuilder = new StringBuilder();
		for (int maxLength : maxLengths) {
			formatBuilder.append("%-").append(maxLength + 2).append("s");
		}
		String format = formatBuilder.toString();

		StringBuilder result = new StringBuilder();
		for (List<String> row : rows) {
			result.append(String.format(format, row.toArray(new String[0]))).append("\n");
		}
		return result.toString();
	}

	static <T> T[] concatenate(T[] array1, T[] array2) {
		List<T> resultList = new ArrayList<>(array1.length + array2.length);
		Collections.addAll(resultList, array1);
		Collections.addAll(resultList, array2);

		@SuppressWarnings("unchecked")
		// the type cast is safe as the array1 has the type T[]
		T[] resultArray = (T[]) Array.newInstance(array1.getClass().getComponentType(), 0);
		return resultList.toArray(resultArray);
	}
}
