import Model.Board;
import Model.Player;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

public class Game extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Release the Kracken");

        Board p1Board = new Board(8, 30);
        Board p2Board = new Board(8, 30);
        Player player1 = new Player(null, p1Board);
        Player player2 = new Player(null, p2Board);


        EventHandler<MouseEvent> p2fireEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                double posX = me.getX();
                double posY = me.getY();
                int colX = (int) (posX / p2Board.getRectWidth());
                int colY = (int) (posY / p2Board.getRectWidth());
                p2Board.tileList.get(colX).get(colY).fire();
                String missImagePath = "resources/miss.png";
                String hitImagePath = "resources/fire.png";
                Image missImage = new Image(missImagePath);
                Image hitImage = new Image(hitImagePath);
                if(p2Board.tileList.get(colX).get(colY).isOccupied()){
                    p2Board.rec[colX][colY].setFill(new ImagePattern(hitImage));
                }
                else {
                    p2Board.rec[colX][colY].setFill(new ImagePattern(missImage));
                }
            }
        };

        EventHandler<MouseEvent> p1fireEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                double posX = me.getX();
                double posY = me.getY();
                int colX = (int) (posX / p1Board.getRectWidth());
                int colY = (int) (posY / p1Board.getRectWidth());
                p1Board.tileList.get(colX).get(colY).fire();
                String missImagePath = "resources/miss.png";
                String hitImagePath = "resources/fire.png";
                Image missImage = new Image(missImagePath);
                Image hitImage = new Image(hitImagePath);
                if(p1Board.tileList.get(colX).get(colY).isOccupied()){
                    p1Board.rec[colX][colY].setFill(new ImagePattern(hitImage));
                }
                else {
                    p1Board.rec[colX][colY].setFill(new ImagePattern(missImage));
                }
            }
        };

        EventHandler<MouseEvent> p1PlaceShips = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (player1.getFleetNumber() != 0) {
                    double posX = me.getX();
                    double posY = me.getY();
                    int colX = (int) (posX / p1Board.getRectWidth());
                    int colY = (int) (posY / p1Board.getRectWidth());
                    p1Board.tileList.get(colX).get(colY).setOccupied();
                    String shipPath = "resources/boat.png";
                    Image shipImage = new Image(shipPath);
                    p1Board.rec[colX][colY].setFill(new ImagePattern(shipImage));
                    player1.setFleetNumber((player1.getFleetNumber()-1));
                    System.out.println(player1.getFleetNumber());
                }
            }
        };

        EventHandler<MouseEvent> p2PlaceShips = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                double posX = me.getX();
                double posY = me.getY();
                int colX = (int) (posX / p2Board.getRectWidth());
                int colY = (int) (posY / p2Board.getRectWidth());
                p2Board.tileList.get(colX).get(colY).setOccupied();
                String shipPath = "resources/boat.png";
                Image shipImage = new Image(shipPath);
                p2Board.rec[colX][colY].setFill(new ImagePattern(shipImage));
            }
        };
        // Scene 0 setup
        VBox welcome = new VBox();
        Label label = new Label("Welcome to Battleships - Player 1, select your ship locations");
        Button button1 = new Button("Click when finished");
        button1.setOnAction(actionEvent -> {
            Label label1 = new Label("Player 2, select your ships");
            Button button2 = new Button("Select to Start Game");
            button2.setOnAction(actionEvent1 -> {
                VBox root = new VBox();
                p2Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p2PlaceShips);
                p2Board.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p2fireEvent);

                root.getChildren().add(p2Board.getGameBoard());
                root.getChildren().add(p1Board.getGameBoard());
                p1Board.getGameBoard().removeEventFilter(MouseEvent.MOUSE_CLICKED, p1PlaceShips);
                root.setAlignment(Pos.CENTER);
                root.setPadding(new Insets(10, 10, 10, 10));
                root.setSpacing(10);
                Scene scene2 = new Scene(root, 320, 640);
                primaryStage.setScene(scene2);
            });
            Label nameLabel = new Label("Player 2 enter your name!");
            TextField nameInput = new TextField();
            VBox root1 = new VBox();
            root1.getChildren().add(label1);
            root1.getChildren().add(nameLabel);
            root1.getChildren().add(nameInput);
            root1.getChildren().add(button2);
            root1.getChildren().add(p2Board.getGameBoard());
            root1.setAlignment(Pos.CENTER);
            root1.setPadding(new Insets(10, 10, 10, 10));
            root1.setSpacing(10);
            Scene p2SelectShipScreen = new Scene(root1, 320, 640);
            p2Board.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p2PlaceShips);
            primaryStage.setScene(p2SelectShipScreen);
        });

        welcome.getChildren().add(label);
        welcome.getChildren().add(button1);
        // This line carries over into play screen and allows p1 to place ships even when game has already started.
        p1Board.getGameBoard().addEventFilter(MouseEvent.MOUSE_CLICKED, p1PlaceShips);
        welcome.getChildren().add(p1Board.getGameBoard());
        Scene welcomeScene = new Scene(welcome, 320, 640);
        primaryStage.setScene(welcomeScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}