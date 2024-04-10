package Utils.InputControl;

import java.util.InputMismatchException;
import java.util.Scanner;


public class InputControl {
    private static Scanner scanner = new Scanner(System.in);

    private static void inputMismatchException(Exception e) {
        System.out.println("Format Error.");
        scanner.nextLine();
    }
    private static void exception(Exception e) {
        System.out.println("Format Error.");
        scanner.nextLine();
    }

    public static byte readByte(String mensaje) {

        while (true) {
            System.out.println(mensaje);
            try {
                return scanner.nextByte();
            } catch (InputMismatchException e) {
                inputMismatchException(e);
            }
        }
    }

    public static int readInt(String mensaje) {
        while(true){
            System.out.println(mensaje);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                inputMismatchException(e);
            } finally {
                scanner.nextLine();
            }
        }
    }
    public static int readIntinRange(String mensaje, int max) {
        while (true) {
            System.out.println(mensaje);
            try {
                int input = scanner.nextInt();
                scanner.nextLine();
                if (input >= 1 && input <= max) {
                    return input;
                } else {
                    System.out.println("Type a numer in range 1 to " + max + ".");
                }
            } catch (InputMismatchException e) {
                inputMismatchException(e);
            }
        }
    }
    public static float readFloat(String mensaje) {

        while (true) {
            System.out.println(mensaje);
            try {
                return scanner.nextFloat();
            } catch (InputMismatchException e) {
                inputMismatchException(e);
            } finally {
                scanner.nextLine();
            }
        }
    }
    public static double readDouble(String mensaje) {

        while (true) {
            System.out.println(mensaje);
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                inputMismatchException(e);
            } finally {
                scanner.nextLine();
            }
        }
    }
    public static char readChar(String mensaje) {

        while (true) {
            System.out.println(mensaje);
            try {
                return scanner.next().charAt(0);
            } catch (Exception e) {
                exception(e);
            }
        }
    }
    public static String readString(String mensaje) {

        while (true) {
            System.out.println(mensaje);
            try {
                return scanner.nextLine().toLowerCase();
            } catch (Exception e) {
                exception(e);
            }
        }
    }
    public static boolean readBoolean(String mensaje) {
        boolean result = false;
        boolean validData = false;
        String response = "";

        do {
            try {
                System.out.println(mensaje);
                response = scanner.nextLine();
                if (response.equalsIgnoreCase("y")) {
                    validData = true;
                    result = true;
                } else if (response.equalsIgnoreCase("n")) {
                    validData = true;
                }
            } catch (Exception e) {
                System.out.println("Format error. Type only a 'y' or 'n'");
            }
        } while (!validData);
        return result;
    }
}


