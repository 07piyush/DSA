package piyush;

public class QuickSort {

	public static void sort(int[] arr, int low, int high) {
		if(low < high) {
			int pIndex = partition(arr, low, high);
			sort(arr, 0, pIndex-1);
			sort(arr, pIndex+1, high);
		}
	}
	
	private static int partition(int[] arr, int low, int high) {
		
		int pivot = arr[low];
		int i = 0;
		int j = high;
		while(i < j) {
			while(i<=j && arr[i]<=pivot)
				i++;
			while(j>=i && arr[j]>pivot)
				j--;
			
			if(i<j)
				swap(arr, i, j);
		}
		swap(arr, low, j);
		return j;
	}
	
	private static void swap(int[] arr, int index1, int index2) {
		int temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;
	}
	
	public static void printArray(int[] arr) {
		for(int item = 0; item < arr.length; item++)
			System.out.print(arr[item] + ", ");
	}
}
