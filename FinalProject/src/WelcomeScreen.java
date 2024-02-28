import lejos.hardware.Button;

public class WelcomeScreen {
    public static void main(String[] args) {
        System.out.println("Welcome to Our Robot Program!");
        System.out.println("Authors: Samuel Haddock, Yash Kumar and Miski Hussein");
        System.out.println("Version: 1.0");

        System.out.println("Press any button to continue...");
        Button.waitForAnyPress();
    }
}
