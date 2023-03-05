package org.example;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.*;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(in);
        int selector;
        do {
            out.println("1: Start");
            out.println("2: High Score");
            out.println("3: Exit");
            selector = scanner.nextInt();
            if (selector < 0 || selector > 3) {
                out.println("Please select from above!");
            }
            if (selector == 1) {
                readingFromFile();
            } else if (selector == 2) {
                readingHighScore();
            } else if (selector == 3) {
                return;
            }
        } while (selector != 1 || selector != 2 || selector != 3);
    }

    public static void readingFromFile() throws IOException, ParseException {
        Scanner scanner = new Scanner(in);
        File json = new File("questions.json");
        JSONParser jsonParser = new JSONParser();
        FileReader fileReader = new FileReader(json);
        Integer highScore = 0;

        JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
        JSONArray questions = (JSONArray) jsonObject.get("questions");

        for (Object object : questions) {
            JSONObject question = (JSONObject) object;
            String answer = (String) question.get("answer");
            String quesText = (String) question.get("question");

            out.println(quesText);

            JSONArray options = (JSONArray) question.get("options");
            char k = 'A';
            for (int i = 0; i < options.size(); i++) {
                String optionText = (String) options.get(i);
                out.println(k + ": " + optionText);
                k++;
            }
            out.println("Enter your answer: ");
            String answerInput = scanner.next();
            String result = null;
            switch (answerInput) {
                case "A":
                    result = (String) options.get(0);
                    break;
                case "B":
                    result = (String) options.get(1);
                    break;
                case "C":
                    result = (String) options.get(2);
                    break;
                case "D":
                    result = (String) options.get(3);
                    break;
                default:
                    out.println("Pick from above options: ");
            }

            if (answer.equals(result)) highScore++;
            else out.println("Oops! that's incorrect.");
            saveHighScore(highScore);
        }
        out.println("Your score is: " + highScore);
    }

    public static void saveHighScore(Integer highScore) throws IOException {
        File txt = new File("highScore.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(txt));
        bufferedWriter.write(highScore.toString());
        bufferedWriter.close();

    }

    public static void readingHighScore() throws Exception {
        File file = new File("highScore.txt");
        if (file.exists() == true) {
            return;
        } else {
            file.createNewFile();
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        int highScore = Integer.parseInt(bufferedReader.readLine());
        out.println(highScore);
        bufferedReader.close();
    }
}