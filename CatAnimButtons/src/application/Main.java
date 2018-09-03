package application;
	
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.image.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.css.*;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.*;
import javafx.beans.binding.*;

public class Main extends Application {
	
	private Cat[] mycats;
	
	public static void main(String[] args) {
	Application.launch(args);
	}
	
	@Override
	public void init() throws Exception {
	super.init();
	mycats = new Cat[3];
	Cat black = buildCat("/blackcat.jpg", "/blackcat.jpg",
	"Show this cat ");
	Cat white = buildCat("/whitecat.jpg", "/whitecat.jpg",
	"Show this cat ");
	Cat grey = buildCat("/greycat.jpg", "/greycat.jpg",
	"Showt this cat to go ");
	mycats[0] = black;
	mycats[1] = white;
	mycats[2] = grey;
	}
	private Cat buildCat(String catForwardFile, String catBackwardFile, String description) {
	Image carGoingForward = new Image(catForwardFile);
	Image carBackward = new Image(catBackwardFile);
	return new Cat(carGoingForward, carBackward, description);
	}
	
	@Override
	public void start(Stage stage) {
	stage.setTitle("Button Fun");
	BorderPane root = new BorderPane();
	root.setId("background");
	Scene scene = new Scene(root, 900, 250);
	// load JavaFX CSS style
	scene.getStylesheets().add(getClass().getResource("/buttoncat.css").toExternalForm());
	VBox leftControlPane = new VBox(10);
	leftControlPane.setPadding(new Insets(0, 10, 20, 15));
	// Create radio buttons for linear,
	// ease in and ease out
	ToggleGroup group = new ToggleGroup();
	RadioButton easeLinearBtn = new RadioButton("Black");
	easeLinearBtn.setUserData(mycats[0]);
	easeLinearBtn.getStyleClass().add("option-button");
	easeLinearBtn.setSelected(true);
	easeLinearBtn.setToggleGroup(group);
	RadioButton easeInBtn = new RadioButton("White");
	easeInBtn.setUserData(mycats[1]);
	easeInBtn.getStyleClass().add("option-button");
	easeInBtn.setToggleGroup(group);
	RadioButton easeOutBtn = new RadioButton("Grey");
	easeOutBtn.setUserData(mycats[2]);
	easeOutBtn.getStyleClass().add("option-button");
	easeOutBtn.setToggleGroup(group);
	// hyperlink
	Hyperlink carInfoLink = createHyperLink(group);
	leftControlPane.getChildren().add(carInfoLink);
	// Create check boxes to turn lights on or off.
	CheckBox headLightsCheckBox = new CheckBox("Headlights on");
	leftControlPane.getChildren().add(headLightsCheckBox);
	leftControlPane.setAlignment(Pos.BOTTOM_LEFT);
	leftControlPane.getChildren().addAll(easeLinearBtn, easeInBtn, easeOutBtn);
	
	// Create button controls to move car forward or backward.
	HBox hbox = new HBox(10);
	Button leftBtn = new Button("<");
	leftBtn.getStyleClass().add("nav-button");
	Button rightBtn = new Button(">");
	rightBtn.getStyleClass().add("nav-button");
	FlowPane controlPane = new FlowPane();
	FlowPane.setMargin(hbox, new Insets(0, 5, 10, 10));
	hbox.getChildren().addAll(leftBtn, rightBtn);
	controlPane.getChildren().add(hbox);
	root.setBottom(controlPane);
	// Draw the ground surface
	AnchorPane surface = new AnchorPane();
	root.setCenter(surface);
	root.setLeft(leftControlPane);
	int x1 = 20, x2 = 500;
	int y1 = 100, y2 = 100;
	ImageView carView = new ImageView(mycats[0].carForwards);
	carView.setPreserveRatio(true);
	carView.setFitWidth(150);
	carView.setX(x1);
	Arc carHeadlights = new Arc();
	carHeadlights.setId("car-headlights-1");
	carHeadlights.setCenterX(50.0f);
	carHeadlights.setCenterY(90.0f);
	carHeadlights.setRadiusX(300.0f);
	carHeadlights.setRadiusY(300.0f);
	carHeadlights.setStartAngle(170.0f);
	carHeadlights.setLength(15f);
	carHeadlights.setType(ArcType.ROUND);
	carHeadlights.visibleProperty().bind(headLightsCheckBox.selectedProperty());
	// Easing car (sports car)
	AnchorPane.setBottomAnchor(carView, 20.0);
	AnchorPane.setBottomAnchor(carHeadlights, 20.0);
	AnchorPane carPane = new AnchorPane(carHeadlights, carView);
	AnchorPane.setBottomAnchor(carPane, 20.0);
	surface.getChildren().add(carPane);
	// The animation based on the currently selected radio buttons.
	TranslateTransition animateCar = new TranslateTransition(Duration.millis(400),
	carPane);
	animateCar.setInterpolator(Interpolator.LINEAR);
	animateCar.toXProperty().set(x2);
	//animateCar.setInterpolator((Interpolator) group.getSelectedToggle().
	//getUserData());
	animateCar.setDelay(Duration.millis(100));
	
	// Go forward (Left)
	leftBtn.setTooltip(new Tooltip("Drive forward"));
	leftBtn.setOnAction( ae -> {
	animateCar.stop();
	Cat selectedCar = (Cat) group.getSelectedToggle().getUserData();
	carView.setImage(selectedCar.carForwards);
	animateCar.toXProperty().set(x1);
	animateCar.playFromStart();
	});
	// Go backward (Right)
	rightBtn.setTooltip(new Tooltip("Drive backward"));
	rightBtn.setOnAction( ae -> {
	animateCar.stop();
	Cat selectedCar = (Cat) group.getSelectedToggle().getUserData();
	carView.setImage(selectedCar.carBackwards);
	animateCar.toXProperty().set(x2);
	animateCar.playFromStart();
	});
	group.selectedToggleProperty().addListener((ob, oldVal, newVal) -> {
	Cat selectedCar = (Cat) newVal.getUserData();
	System.out.println("selected cat: " + selectedCar.carDescription);
	carView.setImage(selectedCar.carForwards);
	});
	stage.setScene(scene);
	stage.show();
	}
	private Hyperlink createHyperLink(ToggleGroup chosenCarToggle) {
	Hyperlink carInfoLink = new Hyperlink("Car Information");
	Popup carInfoPopup = new Popup();
	carInfoPopup.getScene().getStylesheets()
	.add(getClass().getResource("/buttoncat.css")
	.toExternalForm());
	carInfoPopup.setAutoHide(true);
	carInfoPopup.setHideOnEscape(true);
	Arc pointer = new Arc(0, 0, 20, 20, -20, 40);
	pointer.setType(ArcType.ROUND);
	Rectangle msgRect = new Rectangle( 18, -20, 200.5, 150);
	Shape msgBubble = Shape.union(pointer, msgRect);
	msgBubble.getStyleClass().add("message-bubble");
	TextFlow textMsg = new TextFlow();
	textMsg.setPrefWidth(msgRect.getWidth() -5);
	textMsg.setPrefHeight(msgRect.getHeight() -5);
	textMsg.setLayoutX(pointer.getBoundsInLocal().getWidth()+5);
	textMsg.setLayoutY(msgRect.getLayoutY() + 5);
	
	Text descr = new Text();
	descr.setFill(Color.ORANGE);
	textMsg.getChildren().add(descr);
	// whenever a selected car set the text.
	chosenCarToggle.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
	Cat selectedCar = (Cat) newVal.getUserData();
	descr.setText(selectedCar.carDescription);
	});
	carInfoPopup.getContent().addAll(msgBubble, textMsg);
	carInfoLink.setOnAction(actionEvent -> {
	Bounds linkBounds = carInfoLink.localToScreen(carInfoLink.getBoundsInLocal());
	carInfoPopup.show(carInfoLink, linkBounds.getMaxX(), linkBounds.getMinY() -10);
	});
	return carInfoLink;
	}
	
	}

	class Cat {
	String carDescription;
	Image carBackwards;
	Image carForwards;
	public Cat(){};
	public Cat(Image carForwards, Image carBackwards, String desc) {
	this.carForwards = carForwards;
	this.carBackwards = carBackwards;
	this.carDescription = desc;
	}
	}
	
