package S2;

public class SortingAlgorithms {

    // ไม่อนุญาตให้สร้าง Object เพราะใช้งานผ่าน static method
    private SortingAlgorithms() {
    }

    public static void bubbleSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
    }

    public static void selectionSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int minimumIndex = i;

            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minimumIndex]) {
                    minimumIndex = j;
                }
            }

            if (minimumIndex != i) {
                swap(array, i, minimumIndex);
            }
        }
    }

    public static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = key;
        }
    }

    public static void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);

            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] array, int low, int high) {
        // เลือกตำแหน่งตรงกลางเป็น Pivot
        int middle = low + (high - low) / 2;
        swap(array, middle, high);

        int pivot = array[high];
        int smallerIndex = low - 1;

        for (int current = low; current < high; current++) {
            if (array[current] <= pivot) {
                smallerIndex++;
                swap(array, smallerIndex, current);
            }
        }

        swap(array, smallerIndex + 1, high);

        return smallerIndex + 1;
    }

    private static void swap(int[] array, int first, int second) {
        int temporary = array[first];
        array[first] = array[second];
        array[second] = temporary;
    }
}