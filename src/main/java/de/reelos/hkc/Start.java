package de.reelos.hkc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.NumberStringConverter;

public class Start extends Application {

	private IntegerProperty counter = new SimpleIntegerProperty(0);
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.initStyle(StageStyle.TRANSPARENT);

		primaryStage.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
			primaryStage.setX(event.getScreenX() - (primaryStage.getWidth() / 2));
			primaryStage.setY(event.getScreenY() - (primaryStage.getHeight() / 2));
		});


		ImageView icon = new ImageView();
		
		Text text = new Text("TestText");
		text.setFont(new Font(40));
		text.setFill(Color.RED);
		text.textProperty().bindBidirectional(counter, new NumberStringConverter());
		
		VBox box = new VBox();
		box.setAlignment(Pos.TOP_CENTER);
		box.setBackground(new Background(new BackgroundFill(new Color(1, 1, 1, 0.1),null,null)));
		box.getChildren().add(icon);
		box.getChildren().add(text);

		Scene scene = new Scene(box, 100, 150);
		scene.setFill(null);

		scene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
			if (event.getCode() == KeyCode.SPACE) {
				counter.set(counter.get() + 1);
			}

			if (event.getCode() == KeyCode.BACK_SPACE) {
				if (counter.get() > 0)
					counter.set(counter.get() - 1);
			}

			if (event.getCode() == KeyCode.R && event.isControlDown()) {
				counter.set(0);
			}

			if (event.getCode() == KeyCode.X && event.isControlDown()) {
				primaryStage.close();
			}

			if (event.getCode() == KeyCode.P && event.isControlDown()) {
				FileChooser dia = new FileChooser();

				dia.getExtensionFilters().addAll(new ExtensionFilter("JPG", "*.jpg"),
						new ExtensionFilter("PNG", "*.png"), new ExtensionFilter("GIF", "*.gif"));
				File file = dia.showOpenDialog(primaryStage);
				if (file != null) {
					Image image = null;
					try {
						image = new Image(new FileInputStream(file), 100, 100, true, false);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					icon.setImage(image);
				}
			}
			
			if (event.getCode() == KeyCode.S && event.isControlDown()) {
				TextInputDialog tid = new TextInputDialog();
				tid.setTitle("Set Startnumber");
				tid.setHeaderText(null);
				tid.setContentText("Please Insert Startnumber:");
				Optional<String> result = tid.showAndWait();
				
				if(result.isPresent()) {
					if(result.get().matches("[0-9]*")) {
						counter.set(Integer.valueOf(result.get()));
					}
				}
			}
		});

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
