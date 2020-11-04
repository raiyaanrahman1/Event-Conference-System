package Controller;

import Gateway.FileGateway;
import Gateway.IGateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateSpeakerController {

    IGateway g = new FileGateway("");

    public void CreateSpeaker() {

        Scanner myObj = new Scanner(System.in);
        String username1;
        boolean incorrectCode = true;
        do {
            System.out.println("Enter your organizer code." );
            String code = myObj.nextLine();
            if (!code.equals("f9h2q6" )) {
                System.out.println("Invalid code." );
            } else {
                incorrectCode = false;
            }
        }
        while (incorrectCode);

        boolean userExists = true;
        do {
            System.out.println("Enter a username" );
            username1 = myObj.nextLine();
            if (exists(g, username1)) {
                System.out.println("Username already exists" );
            } else {
                userExists = false;
            }
        }
        while (userExists);
        System.out.println("Enter a password." );
        String password = myObj.nextLine();
        List<String> userInfo = new ArrayList<>();
        userInfo.add(username1);
        userInfo.add(password);
        userInfo.add("S");
        g.append(userInfo);

    }


    public boolean exists(IGateway gt, String username){
        g.read();
        while (gt.hasNext()) {
            List<String> actual = gt.next();
            if(actual.contains(username)){
                return true;
            }
        }
        return false;
    }
}
