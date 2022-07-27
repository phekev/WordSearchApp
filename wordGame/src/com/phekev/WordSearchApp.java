package com.phekev;

import java.util.Arrays;
import java.util.List;


    public class WordSearchApp {

        public static void main(String[] args) {

            List<String> words = Arrays.asList("ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN");
            Grid grid = new Grid(10);
            grid.fillGrid(words);
            grid.displayGrid();



        }

    }


