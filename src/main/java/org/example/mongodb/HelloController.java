package org.example.mongodb;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class HelloController {
    private MongoDatabase database;
    public HelloController(){
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().register(User.class).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
        try  {
           database = mongoClient.getDatabase("JAVA").withCodecRegistry(pojoCodecRegistry);
            ;
            System.out.println("Connected to the database:"+database.getName());
        } catch (MongoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    @FXML

private TextField idinput;
    @FXML
    private TextField cityinput;
    @FXML
    private TextField ageinput;
    @FXML
    private TextField nameinput;
private MongoClient mongoClient = db.getMongoClient();
    public void openPopup(String labelMessage) {
        // Create a new stage for the popup
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Popup");

        // Create the layout for the popup
        VBox popupLayout = new VBox();
        popupLayout.setSpacing(10);
        popupLayout.setAlignment(javafx.geometry.Pos.CENTER);
        Label messageLabel = new Label(labelMessage);
        Button okButton = new Button("OK");
        okButton.setOnAction(event -> popupStage.close());

        // Add components to the layout
        popupLayout.getChildren().addAll(messageLabel, okButton);

        // Set up the scene and show the popup
        Scene popupScene = new Scene(popupLayout, 200, 100);
        popupStage.setScene(popupScene);
        popupStage.show();
    }

    @FXML
protected  void onRead(){
        try{
            MongoCollection<User> collection = database.getCollection("users", User.class);
            User user = collection.find(eq("_id", idinput.getText())).first();
            openPopup(user.toString());

        }
            catch (Exception e){
            System.out.println("Error: " + e.getMessage());

        }
    }
    @FXML
protected  void onCreate() {
        try{
            MongoCollection<User> collection = database.getCollection("users", User.class);
            User user = new User();
            user.setName(nameinput.getText());
            user.setCity(cityinput.getText());
            user.setAge(Integer.parseInt(ageinput.getText()));
            user.setId(idinput.getText());
            collection.insertOne(user);
            openPopup("added user");
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
}
    @FXML
    protected  void onUpdate() {
        try {
            MongoCollection<User> collection = database.getCollection("users", User.class);
            User user = new User();
            user.setName(nameinput.getText());
            user.setCity(cityinput.getText());
            user.setAge(Integer.parseInt(ageinput.getText()));
            collection.replaceOne(eq("_id", idinput.getText()), user);
           openPopup("updated user");
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    @FXML
    protected  void onDelete() {
        try{
            MongoCollection<User> collection = database.getCollection("users", User.class);
            collection.deleteOne(eq("_id", idinput.getText()));
            openPopup("deleted user");
            }catch (Exception e){
            System.out.println("Error: " + e.getMessage());

        }
    }



}
