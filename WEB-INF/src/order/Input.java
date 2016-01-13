package order;

import java.util.Scanner;

public class Input {
  static int convertToInteger() {
    Scanner stdIn = new Scanner(System.in);
    String line = stdIn.next();
    int number = -1;
    if (isNumber(line)) {
      number = Integer.parseInt(line);
    }
    return number;
  }

  static String characterString() {
    Scanner stdIn = new Scanner(System.in);
    String line = stdIn.next();
    return line;
  }

  private static boolean isNumber(String value) {
    try {
      Integer.parseInt(value);
      return true;
    } catch (NumberFormatException nfex) {
      return false;
    }
  }
}