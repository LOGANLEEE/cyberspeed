import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.IOException;

public class ScratchGame {
    public static void main(String[] args) {
        try {
            // Create a new Gson instance
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // Read the config.json file
            FileReader reader = new FileReader("config.json");

            // Deserialize JSON into GameConfig class
            GameConfig config = gson.fromJson(reader, GameConfig.class);

            System.out.println(config); // You can verify the parsed data here
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}