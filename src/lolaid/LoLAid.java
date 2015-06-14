/*Copyright (C) <2015>  <George Erfesoglou>
 * 
 *This program is free software: you can redistribute it and/or modify
 *it under the terms of the GNU General Public License as published by
 *the Free Software Foundation, either version 3 of the License, or
 *(at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program.  If not, see <http://www.gnu.org/licenses/>.*/
package lolaid;

import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import static javafx.scene.input.DataFormat.URL;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 *
 * @author George
 */
public class LoLAid extends Application {

	int scale = -1;
	double pixleCount = 0;
	String versionNum = "1.2.7";
	boolean playUp = true;
	boolean playDown = false;
	String soundC1, soundC2, soundC3, soundC4;@Override
	public void start(Stage primaryStage) {

		//Creating a GridPane container
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);

		//Defining the Name text field
		final TextField scaleInput = new TextField();
		scaleInput.setPromptText("Enter Lol Hud Scale Factor.");
		GridPane.setConstraints(scaleInput, 0, 0);
		grid.getChildren().add(scaleInput);

		//Defining the Submit button
		Button submit = new Button("Start");
		GridPane.setConstraints(submit, 1, 0);
		grid.getChildren().add(submit);

		//Defining the Clear button
		Button clear = new Button("Stop");
		GridPane.setConstraints(clear, 1, 1);
		grid.getChildren().add(clear);

		//Adding a Label
		final Label label = new Label();
		GridPane.setConstraints(label, 0, 3);
		GridPane.setColumnSpan(label, 2);
		grid.getChildren().add(label);

		//Adding a Label
		final Label madebyLable = new Label();
		GridPane.setConstraints(madebyLable, 0, 12);
		GridPane.setColumnSpan(madebyLable, 2);
		grid.getChildren().add(madebyLable);
		madebyLable.setText("Developed by George Erfesoglou using JavaFX.\n" + "Voice is Salli by IVONA Software an Amazon company.");

		//Adding a Label
		final Label description = new Label();
		GridPane.setConstraints(description, 0, 2);
		GridPane.setColumnSpan(description, 2);
		grid.getChildren().add(description);
		description.setText("Enter your Lol Hud Scale Factor in the above text field.");

		//Adding a Label
		final Label timeLabel1 = new Label();
		GridPane.setConstraints(timeLabel1, 1, 5);
		GridPane.setColumnSpan(timeLabel1, 2);
		grid.getChildren().add(timeLabel1);

		//Add a checkbox
		final CheckBox enableDown = new CheckBox("Enable Down Sound");
		GridPane.setConstraints(enableDown, 0, 9);
		GridPane.setColumnSpan(enableDown, 2);
		grid.getChildren().add(enableDown);
		enableDown.setText("Enable Down Sound");
		enableDown.setSelected(false);
		enableDown.setOnAction((ActionEvent e) -> {
			playDown = !playDown;
			System.out.println(playDown);
		});

		submit.setOnAction((ActionEvent e) -> {
			if (
			(Integer.parseInt(scaleInput.getText()) >= 0 && Integer.parseInt(scaleInput.getText()) <= 100 && !scaleInput.getText().isEmpty())) {
				// label.setText("Scale Factor Set At " + scaleInput.getText() );
				//Eseentialy stop timer to restart it;
				Timer timer = new Timer();
				runCheck(timeLabel1, Integer.parseInt(scaleInput.getText()), label, timer, clear, scaleInput, submit);

			} else {
				label.setText("You have not enetred a scale factor.");
			}
		});


		ChampVoiceSlectionBoxes(grid);

		Scene scene = new Scene(grid, 320, 340);
		primaryStage.setTitle("LolAid - Ult Notifier " + versionNum);
		primaryStage.setScene(scene);
		primaryStage.show();


	}@Override
	public void stop() {
		System.exit(0);
	}
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		launch(args);

	}

	public void runCheck(Label timeLabel, int scale, Label label, Timer timer, Button clear, TextField scaleInput, Button submit) {

		submit.setDisable(true);

		double height = Toolkit.getDefaultToolkit().getScreenSize().height;
		double width = Toolkit.getDefaultToolkit().getScreenSize().width;
		//Based on 2560x1440 resolution
		double baseMaxX = 67.0;
		double baseMaxY = 260.0;
		double baseMaxNextY = (110.0);

		double baseMinX = 32.0;
		double baseMinY = (131.0);
		double baseMinNextY = (56.0);

		double heightRatio = (height / 1440.0);
		double widthRatio = (width / 2560.0);
		//Scale max and min to match new max and min of current resolution. 
		double maxValueX = baseMaxX * widthRatio;
		double maxValueY = baseMaxY * heightRatio;
		double maxValueNextY = baseMaxNextY * heightRatio;

		double minValueX = baseMinX * widthRatio;
		double minValueY = baseMinY * heightRatio;
		double minValueNextY = baseMinNextY * heightRatio;

		//Scale is from 0 to 100 and ratio is from minV/maxV to 1. 
		//Obtained from minValue/maxValue at scale 0 and scale 100
		double baseRatioX = (minValueX / maxValueX);
		double baseRatioY = (minValueY / maxValueY);
		double baseRatioNextY = (minValueNextY / maxValueNextY);
		//Since raio range is minV/maxV to 1.0, to calculate the how much a scale step is to a ratio step see how many times it takes 100 to get to minV/maxV by diving by 100
		//ex 0.55 / 100 = 0.0055
		double ratioStepPerScaleStepX = (1.0 - baseRatioX) / 100.0;
		double ratioStepPerScaleStepY = (1.0 - baseRatioY) / 100.0;
		double ratioStepPerScaleStepNextY = (1.0 - baseRatioNextY) / 100.0;
		//Pixle Mod is the amount based on the scale and ratio to modify the pixle placement of x and y
		//If Scale is 0 return the baseRatio, unchanged. 
		//Else return the scale * the ratio steps we need to take to accomidate a scale step plus the baseRatio to get actual Mod amount;
		double pixleModX = (scale == 0 ? baseRatioX : (scale * ratioStepPerScaleStepX) + baseRatioX);
		double pixleModY = (scale == 0 ? baseRatioY : (scale * ratioStepPerScaleStepY) + baseRatioY);
		double pixleModNextY = (scale == 0 ? baseRatioY : (scale * ratioStepPerScaleStepNextY) + baseRatioNextY);

		System.out.println("Before Res change X  Y: " + (maxValueX * (pixleModX) + " " + (maxValueY * (pixleModY))));
		int x = (int)((maxValueX * (pixleModX)));
		int y = (int)((maxValueY * (pixleModY)));
		int nextY = (int)((maxValueNextY * (pixleModNextY)));
		System.out.println("Width Ratio " + widthRatio + " " + "Height Ratio: " + heightRatio);
		label.setText("Scale Factor Set At: " + scale + "\n" + "Checking Ults\n" + "Detected Screen Res: " + width + "x" + height + "\n" + "X Value is :" + x + " " + "Y Value is :" + y);
		clear.setOnAction((ActionEvent e) -> {
			scaleInput.clear();
			label.setText(null);
			timer.cancel();
			timer.purge();
			submit.setDisable(false);
		});
		//CheckForUlts test = new CheckForUlts();
		System.out.println("Checking Ults");
		System.out.println("Screen Size " + width + "x" + height);
		System.out.println("X Value is :" + x + " " + "Y Value is :" + y + "Next Y Every " + nextY);
		Timer clock = new Timer();
		int count = 0;

		//clock.scheduleAtFixedRate(new UltCoolDown(timeLabel), 0, 1000);
		timer.scheduleAtFixedRate(new CheckForUlts(x, y, nextY, playUp, playDown, soundC1, soundC2, soundC3, soundC4), 0, 500);
	}

	public void ChampVoiceSlectionBoxes(GridPane grid) {

		final String[] champVoices = new String[] {
			"champ1green.wav", "akali.wav"
		};
		//Champ1
		ChoiceBox champ1 = new ChoiceBox(FXCollections.observableArrayList(
			"Champ1", "Aatrox", "Ahri", "Akali", "Alistar", "Amumu", "Anivia", "Annie", "Ashe", "Azir", "Blitzcrank", "Brand", "Braum", "Caitlyn", "Cassiopeia", "Chogath", "Corki", "Darius", "Diana", "Draven", "DrMundo", "Elise", "Evelynn", "Ezreal", "FiddleSticks", "Fiora", "Fizz", "Galio", "Gangplank", "Garen", "Gnar", "Gragas", "Graves", "Hecarim", "Heimerdinger", "Irelia", "Janna", "JarvanIV", "Jax", "Jayce", "Jinx", "Kalista", "Karma", "Karthus", "Kassadin", "Katarina", "Kayle", "Kennen", "Khazix", "KogMaw", "Leblanc", "LeeSin", "Leona", "Lissandra", "Lucian", "Lulu", "Lux", "Malphite", "Malzahar", "Maokai", "MasterYi", "MissFortune", "MonkeyKing", "Mordekaiser", "Morgana", "Nami", "Nasus", "Nautilus", "Nidalee", "Nocturne", "Nunu", "Olaf", "Orianna", "Pantheon", "Poppy", "Quinn", "Rammus", "RekSai", "Renekton", "Rengar", "Riven", "Rumble", "Ryze", "Sejuani", "Shaco", "Shen", "Shyvana", "Singed", "Sion", "Sivir", "Skarner", "Sona", "Soraka", "Swain", "Syndra", "Talon", "Taric", "Teemo", "Thresh", "Tristana", "Trundle", "Tryndamere", "TwistedFate", "Twitch", "Udyr", "Urgot", "Varus", "Vayne", "Veigar", "Velkoz", "Vi", "Viktor", "Vladimir", "Volibear", "Warwick", "Xerath", "XinZhao", "Yasuo", "Yorick", "Zac", "Zed", "Ziggs", "Zilean", "Zyra"));
		GridPane.setConstraints(champ1, 0, 5);
		GridPane.setColumnSpan(champ1, 2);
		champ1.setTooltip(new Tooltip("Select champ 1's champ voice."));
		grid.getChildren().add(champ1);
		champ1.getSelectionModel().selectFirst();
		soundC1 = champ1.getValue().toString();
		champ1.valueProperty().addListener(new ChangeListener < String > () {@Override
			public void changed(ObservableValue ov, String t, String t1) {
				soundC1 = champ1.getValue().toString();
			}
		});

		//Champ2
		ChoiceBox champ2 = new ChoiceBox(FXCollections.observableArrayList(
			"Champ2", "Aatrox", "Ahri", "Akali", "Alistar", "Amumu", "Anivia", "Annie", "Ashe", "Azir", "Blitzcrank", "Brand", "Braum", "Caitlyn", "Cassiopeia", "Chogath", "Corki", "Darius", "Diana", "Draven", "DrMundo", "Elise", "Evelynn", "Ezreal", "FiddleSticks", "Fiora", "Fizz", "Galio", "Gangplank", "Garen", "Gnar", "Gragas", "Graves", "Hecarim", "Heimerdinger", "Irelia", "Janna", "JarvanIV", "Jax", "Jayce", "Jinx", "Kalista", "Karma", "Karthus", "Kassadin", "Katarina", "Kayle", "Kennen", "Khazix", "KogMaw", "Leblanc", "LeeSin", "Leona", "Lissandra", "Lucian", "Lulu", "Lux", "Malphite", "Malzahar", "Maokai", "MasterYi", "MissFortune", "MonkeyKing", "Mordekaiser", "Morgana", "Nami", "Nasus", "Nautilus", "Nidalee", "Nocturne", "Nunu", "Olaf", "Orianna", "Pantheon", "Poppy", "Quinn", "Rammus", "RekSai", "Renekton", "Rengar", "Riven", "Rumble", "Ryze", "Sejuani", "Shaco", "Shen", "Shyvana", "Singed", "Sion", "Sivir", "Skarner", "Sona", "Soraka", "Swain", "Syndra", "Talon", "Taric", "Teemo", "Thresh", "Tristana", "Trundle", "Tryndamere", "TwistedFate", "Twitch", "Udyr", "Urgot", "Varus", "Vayne", "Veigar", "Velkoz", "Vi", "Viktor", "Vladimir", "Volibear", "Warwick", "Xerath", "XinZhao", "Yasuo", "Yorick", "Zac", "Zed", "Ziggs", "Zilean", "Zyra"));
		GridPane.setConstraints(champ2, 0, 6);
		GridPane.setColumnSpan(champ2, 2);
		champ2.setTooltip(new Tooltip("Select champ 2's champ voice."));
		grid.getChildren().add(champ2);
		champ2.getSelectionModel().selectFirst();
		soundC2 = champ2.getValue().toString();
		champ2.valueProperty().addListener(new ChangeListener < String > () {@Override
			public void changed(ObservableValue ov, String t, String t1) {
				soundC2 = champ2.getValue().toString();
			}
		});
		//Champ3
		ChoiceBox champ3 = new ChoiceBox(FXCollections.observableArrayList(
			"Champ3", "Aatrox", "Ahri", "Akali", "Alistar", "Amumu", "Anivia", "Annie", "Ashe", "Azir", "Blitzcrank", "Brand", "Braum", "Caitlyn", "Cassiopeia", "Chogath", "Corki", "Darius", "Diana", "Draven", "DrMundo", "Elise", "Evelynn", "Ezreal", "FiddleSticks", "Fiora", "Fizz", "Galio", "Gangplank", "Garen", "Gnar", "Gragas", "Graves", "Hecarim", "Heimerdinger", "Irelia", "Janna", "JarvanIV", "Jax", "Jayce", "Jinx", "Kalista", "Karma", "Karthus", "Kassadin", "Katarina", "Kayle", "Kennen", "Khazix", "KogMaw", "Leblanc", "LeeSin", "Leona", "Lissandra", "Lucian", "Lulu", "Lux", "Malphite", "Malzahar", "Maokai", "MasterYi", "MissFortune", "MonkeyKing", "Mordekaiser", "Morgana", "Nami", "Nasus", "Nautilus", "Nidalee", "Nocturne", "Nunu", "Olaf", "Orianna", "Pantheon", "Poppy", "Quinn", "Rammus", "RekSai", "Renekton", "Rengar", "Riven", "Rumble", "Ryze", "Sejuani", "Shaco", "Shen", "Shyvana", "Singed", "Sion", "Sivir", "Skarner", "Sona", "Soraka", "Swain", "Syndra", "Talon", "Taric", "Teemo", "Thresh", "Tristana", "Trundle", "Tryndamere", "TwistedFate", "Twitch", "Udyr", "Urgot", "Varus", "Vayne", "Veigar", "Velkoz", "Vi", "Viktor", "Vladimir", "Volibear", "Warwick", "Xerath", "XinZhao", "Yasuo", "Yorick", "Zac", "Zed", "Ziggs", "Zilean", "Zyra"));
		GridPane.setConstraints(champ3, 0, 7);
		GridPane.setColumnSpan(champ3, 2);
		champ3.setTooltip(new Tooltip("Select champ 3's champ voice."));
		grid.getChildren().add(champ3);
		champ3.getSelectionModel().selectFirst();
		soundC3 = champ3.getValue().toString();
		champ3.valueProperty().addListener(new ChangeListener < String > () {@Override
			public void changed(ObservableValue ov, String t, String t1) {
				soundC3 = champ3.getValue().toString();
			}
		});
		//Champ4
		ChoiceBox champ4 = new ChoiceBox(FXCollections.observableArrayList(
			"Champ4", "Aatrox", "Ahri", "Akali", "Alistar", "Amumu", "Anivia", "Annie", "Ashe", "Azir", "Blitzcrank", "Brand", "Braum", "Caitlyn", "Cassiopeia", "Chogath", "Corki", "Darius", "Diana", "Draven", "DrMundo", "Elise", "Evelynn", "Ezreal", "FiddleSticks", "Fiora", "Fizz", "Galio", "Gangplank", "Garen", "Gnar", "Gragas", "Graves", "Hecarim", "Heimerdinger", "Irelia", "Janna", "JarvanIV", "Jax", "Jayce", "Jinx", "Kalista", "Karma", "Karthus", "Kassadin", "Katarina", "Kayle", "Kennen", "Khazix", "KogMaw", "Leblanc", "LeeSin", "Leona", "Lissandra", "Lucian", "Lulu", "Lux", "Malphite", "Malzahar", "Maokai", "MasterYi", "MissFortune", "MonkeyKing", "Mordekaiser", "Morgana", "Nami", "Nasus", "Nautilus", "Nidalee", "Nocturne", "Nunu", "Olaf", "Orianna", "Pantheon", "Poppy", "Quinn", "Rammus", "RekSai", "Renekton", "Rengar", "Riven", "Rumble", "Ryze", "Sejuani", "Shaco", "Shen", "Shyvana", "Singed", "Sion", "Sivir", "Skarner", "Sona", "Soraka", "Swain", "Syndra", "Talon", "Taric", "Teemo", "Thresh", "Tristana", "Trundle", "Tryndamere", "TwistedFate", "Twitch", "Udyr", "Urgot", "Varus", "Vayne", "Veigar", "Velkoz", "Vi", "Viktor", "Vladimir", "Volibear", "Warwick", "Xerath", "XinZhao", "Yasuo", "Yorick", "Zac", "Zed", "Ziggs", "Zilean", "Zyra"));
		GridPane.setConstraints(champ4, 0, 8);
		GridPane.setColumnSpan(champ4, 2);
		champ4.setTooltip(new Tooltip("Select champ 4's champ voice."));
		grid.getChildren().add(champ4);
		champ4.getSelectionModel().selectFirst();
		soundC4 = champ4.getValue().toString();
		champ4.valueProperty().addListener(new ChangeListener < String > () {@Override
			public void changed(ObservableValue ov, String t, String t1) {
				soundC4 = champ4.getValue().toString();
				System.out.println("Sound:" + soundC4);
			}
		});

	}
	public class UltCoolDown extends TimerTask {
		int count = 0;
		Label label;
		public UltCoolDown(Label lab1) {
			label = lab1;
		}
		public void run() {
			count++;
			final Label clock = new Label();
			final DateFormat format = DateFormat.getInstance();
			final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler < ActionEvent > () {@Override
				public void handle(ActionEvent event) {
					final Calendar cal = Calendar.getInstance();

					label.setText(Integer.toString(count));
				}
			}));
			timeline.setCycleCount(Animation.INDEFINITE);
			timeline.play();
		}
	}

}