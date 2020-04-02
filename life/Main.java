package life;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {
    public static void main(String[] args) {
        GameOfLife  GameOfLife = new GameOfLife();

    }


}

class game {
    private static int N = 40;  //size of universe
    private static char[][] current_generation= new char[40][40];
    private static int Generation = 0;

    public static char[][] getCurrent_generation() {
        return current_generation;
    }

    public static int getGeneration() {
        return Generation;
    }

    public static void setGeneration() {
        Generation = 0;
    }

    public static int count_alive() {
        int count = 0;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++) {
                if(current_generation[i][j] == 'O') {
                    count++;
                }

            }
        }
        return count;
    }

    public static void generate() {
        Random random = new Random();

        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++) {
                boolean state = random.nextBoolean();
                if(state == true){
                    current_generation[i][j] = 'O';
                }
                else{
                    current_generation[i][j] = ' ';
                }
            }
        }
    }

    public static void check_neighbours() {
        char[][] next_generation = new char[N][N];
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++) {
                int neighbours = 0;
                for(int k = i-1; k < i+2; k++) {
                    for(int l = j-1; l < j+2; l++) {
                        int x = k;
                        int y = l;

                        if(k < 0) {
                            x = k+N;
                        }
                        if(k > N-1) {
                            x = k-N;
                        }
                        if(l <0) {
                            y = l+N;
                        }
                        if(l > N-1) {
                            y = l-N;
                        }
                        if(current_generation[x][y] == 'O') {
                            neighbours++;
                        }
                    }
                }
                if(current_generation[i][j] == 'O') {
                    neighbours--;
                }
                if(current_generation[i][j] == ' ' && neighbours == 3) {
                    next_generation[i][j] = 'O';
                }
                else if(current_generation[i][j] == 'O' && (neighbours ==2 || neighbours ==3)) {
                    next_generation[i][j] = 'O';
                }
                else {
                    next_generation[i][j] = ' ';
                }
            }
        }
        current_generation = next_generation;
        Generation++;
    }

}

