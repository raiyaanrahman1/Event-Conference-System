package Controller;

import Gateway.FileGateway;
import Gateway.IGateway;

import java.util.Scanner;

public class CreateSpeakerController {

    IGateway g = new FileGateway("");

    public void CreateSpeaker() {

        Scanner myObj = new Scanner(System.in); //Create scanner
        System.out.println("Create a Speaker");
        String code = enterOrganizerCode();
        while (!code.equals("f9h2q6")) {
            enterOrganizerCode();
        } // exit while loop
        System.out.println("Invalid code. Please enter your organizer code");
        CreateSpeaker();
        String username = enterUsername();
        while (exists(g, username)) {
            System.out.println("Username is already taken. Please enter a different one.");
            enterUsername();
        } // exit while loop
        System.out.println("Enter a password.");
        String password = myObj.nextLine();
        g.append(username + " " + password + " S");
        }



    private String enterOrganizerCode() {
        Scanner myObj = new Scanner(System.in); //Create scanner
        System.out.println("Enter your organizer code.");
        String code = myObj.nextLine();
        return code;
    }
    private String enterUsername() {
        Scanner myObj = new Scanner(System.in); //Create scanner
        System.out.println("Enter a username");
        String username = myObj.nextLine();
        return username;
    }


    public boolean exists(IGateway gt, String username){
        g.read();
        while (gt.hasNext()) {
            String actual = gt.next();
            if(actual.contains(username)){
                return true;
            }
        }
        return false;
    }
}
