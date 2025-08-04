package green.web.chopsticks;

import java.util.Scanner;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.json.*;
import java.io.File;
import java.io.FileNotFoundException;

@RestController
public class POSTController {
    @PostMapping("/updateStates")
    public int updateStates(@RequestBody JSONObject states, @RequestBody String playerID) throws FileNotFoundException {
        JSONObject newStates = new JSONObject(states);
        Scanner reader = new Scanner(new File("./states.json"));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.nextLine()) != null) {
            builder.append(line);
        }
        reader.close();
        JSONObject allStates = new JSONObject(builder.toString());
        JSONObject playerStates = allStates.getJSONObject(playerID);
        playerStates.put("left", newStates.getInt("left"));
        playerStates.put("right", newStates.getInt("right"));
        allStates.put(playerID, playerStates);
        return 1;
    }

    @PostMapping("/register")
    public int register(@RequestBody String username, @RequestBody String password) throws FileNotFoundException {
        Scanner reader = new Scanner(new File("./accounts.json"));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.nextLine()) != null) {
            builder.append(line);
        }
        reader.close();
        JSONObject accounts = new JSONObject(builder.toString());
        try {
            accounts.getJSONObject(username);
            throw new Exception("Username already chosen. Try another");
        }
        catch (Exception e) {
            JSONObject newAccount = new JSONObject();
            newAccount.put("password", BCrypt.hashpw(password, BCrypt.gensalt()));
            newAccount.put("stats", new JSONObject().put("wins", 0).put("draws", 0).put("losses", 0));
            newAccount.put("skins", new JSONArray().put(0));
            accounts.put(username, newAccount);
        }

        return 1;
    }
    
}
