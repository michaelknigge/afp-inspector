package de.textmode.afpinspector;

import java.io.File;

import org.controlsfx.control.StatusBar;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public final class Main extends Application {

    private final MenuBar menuBar = this.createMenuBar();
    private final SplitPane splitPane = this.createSplitPane();
    private final StatusBar statusBar = this.createStatusBar();

    private Stage stage;
    private TreeView<String> treeView;
    private ListView<String> listView;

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {

        this.stage = primaryStage;

        final VBox rootPane = new VBox();
        final ObservableList<Node> rootPaneChilren = rootPane.getChildren();
        rootPaneChilren.add(this.menuBar);
        rootPaneChilren.add(this.splitPane);
        rootPaneChilren.add(this.statusBar);

        VBox.setVgrow(this.menuBar, Priority.NEVER);
        VBox.setVgrow(this.splitPane, Priority.ALWAYS);
        VBox.setVgrow(this.statusBar, Priority.NEVER);

        // TODO: Abmasse aus INI-Datei laden
        final Scene scene = new Scene(rootPane, 800, 600);

        // TODO: Titel aus Manifest holen!
        primaryStage.setTitle("AFP-Inspector");
        primaryStage.getIcons().add(new Image("/icons/axialis/Web2-Blue/16x16/Search.png"));
        primaryStage.getIcons().add(new Image("/icons/axialis/Web2-Blue/24x24/Search.png"));
        primaryStage.getIcons().add(new Image("/icons/axialis/Web2-Blue/32x32/Search.png"));
        primaryStage.getIcons().add(new Image("/icons/axialis/Web2-Blue/48x48/Search.png"));
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> this.handleQuitApplication());
        primaryStage.show();
    }

    private MenuBar createMenuBar() {
        final MenuItem closeFileMenuItem = new MenuItem("Close");
        closeFileMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN));
        closeFileMenuItem.setDisable(true);
        closeFileMenuItem.setOnAction(e -> this.handleCloseFile(closeFileMenuItem));

        final MenuItem openFileMenuItem = new MenuItem("Open...");
        openFileMenuItem.setGraphic(new ImageView(new Image("/icons/fatcow/16x16/open_folder.png")));
        openFileMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN));
        openFileMenuItem.setOnAction(e -> this.handleOpenFile(closeFileMenuItem));

        final MenuItem quitMenuItem = new MenuItem("Quit");
        quitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN));
        quitMenuItem.setOnAction(e -> this.handleQuitApplication());

        final Menu fileMenu = new Menu("_File");
        fileMenu.getItems().add(openFileMenuItem);
        fileMenu.getItems().add(closeFileMenuItem);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(quitMenuItem);

        final MenuItem aboutMenuItem = new MenuItem("About...");
        aboutMenuItem.setGraphic(new ImageView(new Image("/icons/fatcow/16x16/information.png")));
        aboutMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F1));
        aboutMenuItem.setOnAction(e -> this.handleAboutDialog());

        final Menu helpMenu = new Menu("_Help");
        helpMenu.getItems().add(aboutMenuItem);

        final MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);
        menuBar.getMenus().add(helpMenu);

        return menuBar;
    }

    private SplitPane createSplitPane() {
        // TODO: Position vom Splitter aus Config laden....
        final SplitPane pane = new SplitPane();
        this.treeView = new TreeView<>();
        this.listView = new ListView<>();

        pane.getItems().add(this.treeView);
        pane.getItems().add(this.listView);

        return pane;
    }

    private StatusBar createStatusBar() {
        final StatusBar statusBar = new StatusBar();
        statusBar.setText("Ready.");
        statusBar.setProgress(0);

        return statusBar;
    }

    private void handleQuitApplication() {
        // TODO: Config speichern...
        Platform.exit();
        System.exit(0);
    }

    private void handleOpenFile(final MenuItem closeFileMenuItem) {

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open AFP file");
        fileChooser.getExtensionFilters()
                   .addAll(
                           new FileChooser.ExtensionFilter("AFP files", "*.afp"),
                           new FileChooser.ExtensionFilter("All files", "*.*"));

        // TODO: Letztes WorkingDir aus INI-Datei laden....
        final File file = fileChooser.showOpenDialog(this.stage);
        if (file == null) {
            return;
        }
        // TODO: WorkingDir in INI-Datei speichern....

        this.handleCloseFile(closeFileMenuItem);
        this.listView.getItems().add("foo");
        this.listView.getItems().add("ba");

        closeFileMenuItem.setDisable(false);
    }

    private void handleCloseFile(final MenuItem closeFileMenuItem) {
        this.treeView.setRoot(null);
        this.listView.getItems().clear();
        closeFileMenuItem.setDisable(true);
    }

    private void handleAboutDialog() {
        final Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(this.stage);
        // TODO: Titel aus Manifest holen!
        alert.setTitle("About AFP-Inspector");

        // TODO: Version aus Manifest holen!
        // TODO: URL aus Manifest holen!
        alert.setHeaderText("AFP-Inspector 1.2.3\nhttps://github.com/michaelknigge/afp-inspector");

        final StringBuilder sb = new StringBuilder();
        sb.append("Java version\t\t: ");
        sb.append(System.getProperty("java.version"));
        sb.append("\n");

        sb.append("Java vendor\t\t: ");
        sb.append(System.getProperty("java.vendor"));
        sb.append("\n");

        sb.append("\n");

        sb.append("Max heap size\t\t: ");
        sb.append(String.format("%.2f", Runtime.getRuntime().maxMemory() / 1024.0 / 1024.0));
        sb.append(" MB");
        sb.append("\n");

        sb.append("Current heap size\t: ");
        sb.append(String.format("%.2f", Runtime.getRuntime().totalMemory() / 1024.0 / 1024.0));
        sb.append(" MB");
        sb.append("\n");

        sb.append("Free heap size\t\t: ");
        sb.append(String.format("%.2f", Runtime.getRuntime().freeMemory() / 1024.0 / 1024.0));
        sb.append(" MB");
        sb.append("\n");

        sb.append("\n");

        sb.append("Operating system\t: ");
        sb.append(System.getProperty("os.name"));
        sb.append("\n");

        sb.append("OS version\t\t: ");
        sb.append(System.getProperty("os.version"));
        sb.append("\n");

        sb.append("OS architecture\t: ");
        sb.append(System.getProperty("os.arch"));
        sb.append("\n");

        // TODO: Geiler machen ;-)
        // TODO: Hinweis auf Copyright der Icons (ggf. mit Hyperlink)
        // TODO: Link auf GitHub Seite....
        // https://code.makery.ch/blog/javafx-dialogs-official/
        // ---> Dialog mit GripPane....

        //        sb.append("Operating system\t\t\t\t: ");
        //        sb.append(System.getProperty("os.name"));
        //        sb.append("\n");
        //
        //        sb.append("Operating system version\t\t: ");
        //        sb.append(System.getProperty("os.version"));
        //        sb.append("\n");
        //
        //        sb.append("Operating system architecture\t: ");
        //        sb.append(System.getProperty("os.arch"));
        //        sb.append("\n");
        alert.setContentText(sb.toString());
        alert.showAndWait();
    }
}
