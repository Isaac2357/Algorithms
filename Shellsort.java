
public class Shellsort {

	public static void main(String[] args) {
		int[]array=randomArray(100_000_000);

		shellSort(array);
		System.out.println("Luis me la pela");
	}

	public static void shellSort(int[] array) {
		int n = array.length;
		int[] deltas = obtainDeltas(n);
		int deltaAux;
		for (int i = deltas.length - 1; i >= 0; i--) {
			deltaAux = deltas[i];
			for (int j = 0; j < deltaAux; j++) { 
				if (j + deltaAux > array.length) break;
				insertionSort(deltaAux, j, array);
			}
		}

	}

	public static void insertionSort(int delta, int inicio, int[] array) {
		int i, key, j;
		for (i = inicio + delta; i < array.length; i += delta) {
			key = array[i];
			j = i - delta;

			while (j >= inicio && greaterThan(array[j], key)) {
				array[j + delta] = array[j];
				j -= delta;
			}
			array[j + delta] = key;
		}
	}

	public static int[] obtainDeltas(int n) {
		int delta = 1;
		int[] deltas = null;
		int count = 0;
		if (n == 1) {
			deltas = new int[1];
			deltas[0] = 1;
			return deltas;
		}
		while (delta < n) {
			delta = (3 * delta) + 1;
			count++;
		}
		deltas = new int[count];
		delta = 1;
		for (int i = 0; i < deltas.length; i++) {
			deltas[i] = delta;
			delta = (3 * delta) + 1;
		}
		return deltas;
	}
	public static int[] obtainDeltas2(int n) {
		int delta = 1;
		int[] deltas = null;
		int count = 0;
		if (n == 1) {
			deltas = new int[1];
			deltas[0] = 1;
			return deltas;
		}
		while (delta < n) {
			delta = (3 * delta) + 1;
			count++;
		}
		deltas = new int[count];
		delta = 1;
		for (int i = 0; i < deltas.length; i++) {
			deltas[i] = delta;
			delta = (3 * delta) + 1;
		}
		return deltas;
	}

	public static boolean greaterThan(int a, int b) {
		return a > b;
	}

	public static int[] randomArray(int n) {
		int[] array = new int[n];
		for (int i = 0; i < n; i++) {
			array[i] = ((int) (Math.random() * n)) % n;
		}
		return array;
	}
}
