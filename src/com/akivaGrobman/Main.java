package com.akivaGrobman;

import com.akivaGrobman.backend.*;
import com.akivaGrobman.frontend.Window;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Main {

    private static Window window;
    private static int listSize;

    public static void main(String[] args) throws InterruptedException {
        window = new Window();
    }

    private static void runAllAlgorithms(List<Integer> list) throws InterruptedException {
        // add to array every new sorting algorithm
        SortingAlgorithm[] sortingAlgorithms = new SortingAlgorithm[]{new BubbleSort(new ArrayList<>(list)), new InsertionSort(new ArrayList<>(list)), new MergeSort(new ArrayList<>(list)), new QuickSort(new ArrayList<>(list)), new SelectionSort(new ArrayList<>(list))};
        for (SortingAlgorithm sortingAlgorithm: sortingAlgorithms) {
            runSingeAlgorithm(sortingAlgorithm);
            window.resetDisplay();

        }
    }

    private static void runSingeAlgorithm(SortingAlgorithm sortingAlgorithm) throws InterruptedException {
        sortingAlgorithm.sort();
        window.displayFinish(sortingAlgorithm.getClass().getSimpleName(), sortingAlgorithm.getSwapCount());
        window.refresh();
        sleep(2500);
    }

    // will return an array to sort
    private static List<Integer> getNonOrderedList() {
        List<Integer> list = new ArrayList<>();
        // this represents the numbers already used
        boolean[] usedNumbers = new boolean[listSize];
        Random randomNumberGenerator = new Random();
        // this is because we start off with no numbers used
        Arrays.fill(usedNumbers, false);
        for (int i = 0; i < listSize; i++) {
            int randomNumber;
            do {
                // will generate a new random number until we find one not used
                randomNumber  = randomNumberGenerator.nextInt(listSize);
            }while (usedNumbers[randomNumber]);
            // the plus one is so we don't have a 0 element(not really an issue i'm just insane)
            list.add(randomNumber + 1);
            usedNumbers[randomNumber] = true;
        }
        return list;
    }

    // tells the window to update and updates the list and bar being moved position
    public static void updateDisplay(List<Integer> list, int barBeingMovedPosition, List<Integer> evaluatedBarPositions) {
        window.validate();
        window.updateBarBeingMoved(barBeingMovedPosition);
        window.updateBarBeingEvaluated(evaluatedBarPositions);
        window.updateList(list);
        window.refresh();
    }

    public static void startSorting(int listS) throws InterruptedException {
        listSize=listS;
        Thread thread = new Thread(){
            public void run(){

        List<Integer> list = getNonOrderedList();
                try {
                    runAllAlgorithms(list);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.exit(0);

            }
        };

        thread.start();

        window.replacePanels();

    }

}
