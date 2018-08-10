import java.util.Arrays;
import java.util.LinkedList;

public class BucketSort {
	public static void main(String[] args) {
		double[] array = {0.1,0.15,0.23,0.36,0.23,0.45,0.96,0.88,0.55,0.66,0.73};
		System.out.println(Arrays.toString(array));
		bucketSort(array);
		System.out.println(Arrays.toString(array));
	}
	public static void bucketSort(double[] array) {
		int n = array.length;
		@SuppressWarnings("unchecked")
		LinkedList<Double> [] buckets = new LinkedList[n];
		for(int i = 0; i < n; i++) {
			buckets[i] = new LinkedList<>();
		}
		for(int i = 0; i < n; i++) {
			int b = (int) (array[i]*n);
			buckets[b].add(array[i]);
		}
		int index = 0;
		for(int i = 0; i < n; i++) {
			if(buckets[i].size() > 0) {
				double[] arrayAux = new double[buckets[i].size()];
				for(int j = 0; j < arrayAux.length; j++) {
					arrayAux[j] = buckets[i].get(j);
				}
				insertionSort(arrayAux);
				for(int j = 0; j < arrayAux.length; j++) {
					array[index] = arrayAux[j];
					index++;
				}
			}
		}
	}
	
	public static void insertionSort(double arr[]) {
		int i,j;
		double key;
		for (i = 1; i < arr.length; i++) {
			key = arr[i];
			j = i - 1;
			while (j >= 0 && greaterThan(arr[j], key)) {
				arr[j + 1] = arr[j];
				j--;
			}
			arr[j + 1] = key;
		}
	}

	public static boolean greaterThan(double a, double b) {
		return a > b;
	}
	public static double [] randomDoubleArray(int n) {
		double[] array = new double[n];
		for(int i = 0; i < array.length; i++) {
			array[i] = Math.random();
		}
		return array;
	}
}
