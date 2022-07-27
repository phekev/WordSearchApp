package com.phekev.wordsearchgameapi.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class WordGridService {





        private enum Direction {
            HORIZONTAL,
            VERTICAL,
            DIAGONAL,
            HORIZONTAL_REVERSE,
            VERTICAL_REVERSE,
            DIAGONAL_REVERSE
        }

        private class Coordinate {
            int x;
            int y;
            Coordinate(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }

        public char[][] generateGrid(int gridSize, List<String> words) {
            List<Coordinate> coordinates = new ArrayList<>();
            char[][] contents = new char[gridSize][gridSize];

            for (int i=0; i < gridSize; i++) {
                for (int j=0; j < gridSize; j++) {
                    coordinates.add(new Coordinate(i, j));
                    contents[i][j] = '_';
                }
            }

            Collections.shuffle(coordinates);
            for (String word : words) {
                for (Coordinate coordinate : coordinates) {
                    int x = coordinate.x;
                    int y = coordinate.y;
                    Direction selectedDirection = getDirectionThatFits(contents, word, coordinate);
                    if (selectedDirection != null) {
                        switch(selectedDirection) {
                            case HORIZONTAL:
                                for (char c : word.toCharArray()) {
                                    contents[x][y++] = c;
                                }
                                break;
                            case VERTICAL:
                                for (char c : word.toCharArray()) {
                                    contents[x++][y] = c;
                                }
                                break;
                            case DIAGONAL:
                                for (char c : word.toCharArray()) {
                                    contents[x++][y++] = c;
                                }
                                break;
                            case HORIZONTAL_REVERSE:
                                for (char c : word.toCharArray()) {
                                    contents[x][y--] = c;
                                }
                                break;
                            case VERTICAL_REVERSE:
                                for (char c : word.toCharArray()) {
                                    contents[x--][y] = c;
                                }
                                break;
                            case DIAGONAL_REVERSE:
                                for (char c : word.toCharArray()) {
                                    contents[x--][y--] = c;
                                }
                                break;
                        }

                        break;
                    }
                }
            }
            randomFillGrid(contents);
            return contents;
        }

        private Direction getDirectionThatFits (char[][] contents, String word, Coordinate coordinate) {
            List<Direction> directions = Arrays.asList(Direction.values());
            Collections.shuffle(directions);
            for (Direction direction : directions) {
                if (doesFit(contents, word, coordinate, direction)) {
                    return direction;
                }
            }
            return null;
        }

        private boolean doesFit(char[][] contents, String word, Coordinate coordinate, Direction direction) {
            int gridSize = contents[0].length;
            int wordLength = word.length();
            switch (direction) {
                case HORIZONTAL :
                    if (coordinate.y + wordLength > gridSize) return false;
                    for (int i = 0; i < wordLength; i++) {
                        if(contents[coordinate.x][coordinate.y + i] != '_' || contents[coordinate.x][coordinate.y + i] == word.charAt(i)) return false;
                    }
                    break;
                case VERTICAL :
                    if (coordinate.x + wordLength > gridSize) return false;
                    for (int i = 0; i < wordLength; i++) {
                        if(contents[coordinate.x + i][coordinate.y] != '_'|| contents[coordinate.x + i][coordinate.y] == word.charAt(i)) return false;
                    }
                    break;
                case DIAGONAL :
                    if (coordinate.x + wordLength > gridSize || coordinate.y + wordLength > gridSize ) return false;
                    for (int i = 0; i < wordLength; i++) {
                        if(contents[coordinate.x + i][coordinate.y + i] != '_' || contents[coordinate.x + i][coordinate.y + i] == word.charAt(i)) return false;
                    }
                    break;
                case HORIZONTAL_REVERSE:
                    if (coordinate.y - wordLength < 0) return false;
                    for (int i = 0; i < wordLength; i++) {
                        if(contents[coordinate.x][coordinate.y - i] != '_' || contents[coordinate.x][coordinate.y - i] == word.charAt(i)) return false;
                    }
                    break;
                case VERTICAL_REVERSE:
                    if (coordinate.x - wordLength < 0) return false;
                    for (int i = 0; i < wordLength; i++) {
                        if(contents[coordinate.x - i][coordinate.y] != '_'|| contents[coordinate.x - i][coordinate.y] == word.charAt(i)) return false;
                    }
                    break;
                case DIAGONAL_REVERSE:
                    if (coordinate.x - wordLength < 0 || coordinate.y - wordLength < 0 ) return false;
                    for (int i = 0; i < wordLength; i++) {
                        if(contents[coordinate.x - i][coordinate.y - i] != '_' || contents[coordinate.x - i][coordinate.y - i] == word.charAt(i)) return false;
                    }
                    break;
            }
            return true;
        }

        public void displayGrid(char[][] contents) {
            int gridSize = contents[0].length;
            for (int i=0; i < gridSize; i++) {
                for (int j=0; j < gridSize; j++) {
                    System.out.print(contents[i][j] + " ");
                }
                System.out.println("");
            }
        }


        private void randomFillGrid(char[][] contents) {
            int gridSize = contents[0].length;
            String allCharLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    if (contents[i][j] == '_') {
                        int randIndex = ThreadLocalRandom.current().nextInt(0, allCharLetters.length());
                        contents[i][j] = allCharLetters.charAt(randIndex);
                    }
                }
            }
        }

}
