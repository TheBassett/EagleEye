package javaTestApp;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;


/**
 * @author Team Scooby Doo
 * This class contains the basis of the GUI of the project for sprint 1. This class contains the main method, 
 * which launches the GUI, as well as a popup method which generates a popup box with the string name of the 
 * building match generated with OpenCV. The GUI provides the user with a button asking them to select the picture.
 * Clicking the button launches a file system explorer, where the user chooses which image from the file system
 * to load in. This image is then set as a new tab and a pop up is generated that states the building name match
 * returned from OpenCV. 
 */

public class DeviceTestGUI extends Application {
	
	//Dimension for Stage
	private int h = 750;
	private int w = 1000;
	
	
	//Border Panes
	BorderPane border1 = new BorderPane();
	BorderPane border2 = new BorderPane();
	
	//Instantiate tabs for scene
	TabPane eagleEye = new TabPane(); 
	Tab fileIn = new Tab("Select/Take Image");
	Tab dispPic = new Tab("Location");
	
	//Instantiate file Chooser and button to get pi 
	FileChooser choosePic = new FileChooser();
	Button takePic = new Button("Take Picture");
	
	//Instantiate image tools
	ImageView campusPic;
	
	//Instantiate PicProcessing class

	PicProcessing picnic = new PicProcessing();
	
	//Method to display a popup with the matching building name passed by software
	
	/**Method to generate a popup window with containing the name of the matched building
	 * @param s : String passed into the function to be displayed as the name of the building 
	 * Credit to ItachiUchiha (Stack Overflow forums) for popup window ideas
	 */
	public void popup(String s) {
		Window stage = null;
		final Stage dialog = new Stage();
		dialog.initOwner(stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("The name of the building is " +s));
        Scene dialogScene = new Scene(dialogVbox, 200, 50);
        dialog.setScene(dialogScene);
        dialog.show();
    }
	

	

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 * 
	 * Code to generate a GUI that allows user to select a picture for comparison, sets the picture and 
	 * creates a pop up box with the name of the matched building.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		
		//Set Up file I/O
		choosePic.getExtensionFilters().add(new ExtensionFilter("Picture", "*.jpg"));
		takePic.setPrefWidth(300);
		border1.setCenter(takePic);
		fileIn.setContent(border1);
		
		//Get image
		campusPic = new ImageView();
		campusPic.setFitWidth(w);
		campusPic.setPreserveRatio(true);
		border2.setCenter(campusPic);
		
		//Display Image Tab
		dispPic.setContent(border2);
		
		//Set up Scene with tabs and tab pane
		eagleEye.getTabs().addAll(fileIn, dispPic);
		Scene scene = new Scene(eagleEye, w, h );
		
		//Set Scene in Stage for display
		stage.setScene(scene);
		stage.setTitle("Eagle Eye Test GUI");
		stage.show();
		
		
		
		//Get Image Event Listener
		takePic.setOnMouseClicked(e ->{
			//Switch to image display tab
			eagleEye.getSelectionModel().select(dispPic);
			
			//Let User choose picture
			File file = choosePic.showOpenDialog(stage);
			
			//Take in image to display
			try {
				picnic.DetectObject(file.getAbsolutePath());
				File done = new File("/Users/joelvandepolder/EagleEye/EagleEyeBase/result.jpeg");
				BufferedImage bufImg = ImageIO.read(done);
				Image hope = SwingFXUtils.toFXImage(bufImg, null);
				campusPic.setImage(hope);
				popup(picnic.result); //Call method to display building name and pass matching building name
				//System.out.println();
				
				//Method for Image Processing and Recognition
				
			}catch (Exception e1){
				System.out.println("Error in Picture get");
				System.out.println("Exiting Program");
				e1.printStackTrace();
				System.exit(404);
			}
			
			
		});
	}
	
	
	/**Main method to launch the GUI so that the program can run.
	 * @param args
	 */
	public static void main(String[] args){
		launch(args);
	}

}
