/*
 * This file is part of LanternNBT, licensed under the MIT License (MIT).
 *
 * Copyright (c) LanternPowered https://github.com/LanternPowered/LanternNBT
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the Software), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, andor sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED AS IS, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.lanternpowered.nbt.editor;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.lanternpowered.nbt.ArrayTag;
import org.lanternpowered.nbt.CompoundTag;
import org.lanternpowered.nbt.ListTag;
import org.lanternpowered.nbt.Tag;
import org.lanternpowered.nbt.io.NbtTagInputStream;
import org.lanternpowered.nbt.io.NbtTagOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class NbtEditor extends Application {

    static {
        // Install the svg image loader
        SvgImageLoaderFactory.install();
    }

    public static void main(String[] args) {
        // Launch the app
        launch(args);
    }

    // The main bar -> file
    @FXML private MenuItem file_open;
    @FXML private MenuItem file_save;
    @FXML private MenuItem file_close;

    // The quick bar, just icons
    @FXML private Button quick_open;
    @FXML private Button quick_save;
    @FXML private Button quick_close;
    @FXML private Pane quick_menu_spacer;

    @FXML private TreeView tags_view;

    // The stage
    private Stage stage;

    // The tag that is currently opened
    private TreeTagElement rootTagElement;
    private File openFile;

    @Override
    public void start(Stage primaryStage) throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/lanternpowered/nbt/editor/NbtEditor.fxml"));
        fxmlLoader.setController(this);

        final VBox root = fxmlLoader.load();

        loadIcon(this.file_open, "assets/icons/unarchive.svg");
        loadIcon(this.file_save, "assets/icons/save.svg");
        loadIcon(this.file_close, "assets/icons/clear.svg");
        loadIcon(this.quick_open, "assets/icons/unarchive.svg");
        loadIcon(this.quick_save, "assets/icons/save.svg");
        loadIcon(this.quick_close, "assets/icons/clear.svg");

        // Make sure that the tags view fills the anchor completely
        AnchorPane.setBottomAnchor(this.tags_view, 0.0);
        AnchorPane.setLeftAnchor(this.tags_view, 0.0);
        AnchorPane.setRightAnchor(this.tags_view, 0.0);
        AnchorPane.setTopAnchor(this.tags_view, 0.0);

        // Make the spacer grow at it's maximum so that the close button
        // ends up at the right side
        HBox.setHgrow(this.quick_menu_spacer, Priority.ALWAYS);

        // A custom cell factory for the tags
        this.tags_view.setCellFactory(treeView -> new NbtEditorCell());

        // Link the quick and menu properties
        this.quick_open.onActionProperty().bindBidirectional(this.file_open.onActionProperty());

        this.quick_save.disableProperty().bindBidirectional(this.file_save.disableProperty());
        this.quick_save.onActionProperty().bindBidirectional(this.file_save.onActionProperty());

        this.quick_close.disableProperty().bindBidirectional(this.file_close.disableProperty());
        this.quick_close.onActionProperty().bindBidirectional(this.file_close.onActionProperty());

        // Link the disabled state to the file close button
        this.file_save.disableProperty().bindBidirectional(this.file_close.disableProperty());

        // The file can only be saved if there is one loaded
        this.file_save.setDisable(true);

        final Scene scene = new Scene(root);
        primaryStage.setTitle("NBT Editor");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();

        this.stage = primaryStage;
    }

    private TreeTagElement toElement(Tag<?> tag, TreeTagElement parent, String name) {
        final TreeTagElement tagElement;
        if (tag instanceof ListTag) {
            tagElement = new TreeListElement((ListTag) tag, parent, name);
        } else if (tag instanceof ArrayTag) {
            tagElement = new TreeArrayElement((ArrayTag) tag, parent, name);
        } else if (tag instanceof CompoundTag) {
            tagElement = new TreeTagElement(tag, parent, name);
        } else {
            tagElement = new TreeValueTagElement(tag, parent, name);
        }
        if (tag instanceof CompoundTag) {
            final CompoundTag compound = (CompoundTag) tag;
            for (Map.Entry<String, Tag<?>> entry : compound.entrySet()) {
                tagElement.treeItem.getChildren().add(toElement(entry.getValue(), tagElement, entry.getKey()).treeItem);
            }
        }
        return tagElement;
    }

    @FXML
    private void onClickPageClose(ActionEvent event) {
        if (this.rootTagElement == null) {
            return;
        }
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Saving?");
        alert.setContentText("Do you want to save your latest changes?");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);

        final ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (result == ButtonType.YES) {
            save();
        }
        if (result != ButtonType.CANCEL) {
            this.tags_view.setRoot(null);
            this.file_save.setDisable(true);
            this.rootTagElement = null;
            this.openFile = null;
        }
    }

    private void save() {
        try {
            OutputStream outputStream = new FileOutputStream(this.openFile);
            // Transform the output stream for a specific compression
            outputStream = this.rootTagElement.compression.transform(outputStream);
            try (NbtTagOutputStream tos = new NbtTagOutputStream(outputStream)) {
                tos.write(this.rootTagElement.tag);
            }
        } catch (IOException e) {
            e.printStackTrace();
            final Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Failed to save the NBT file: " + e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void onClickPageSave(ActionEvent event) {
        if (this.rootTagElement == null) {
            return;
        }
        save();
    }

    @FXML
    private void onClickPageOpen(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        final File file = fileChooser.showOpenDialog(this.stage);
        // A file was selected, let's open it
        if (file != null) {
            try {
                InputStream is = null;
                Compression compression1 = null;
                for (Compression compression : Compression.values()) {
                    is = new FileInputStream(file);
                    if (compression.test(is)) {
                        is.close();
                        is = compression.transform(new FileInputStream(file));
                        compression1 = compression;
                        break;
                    }
                }
                final Tag<?> tag;
                try (NbtTagInputStream tis = new NbtTagInputStream(is)) {
                    tag = tis.read();
                }

                // Create the root tag element
                this.rootTagElement = toElement(tag, null, file.getName());
                this.rootTagElement.compression = compression1;

                // Make the view visible
                this.tags_view.setRoot(this.rootTagElement.treeItem);
                this.file_save.setDisable(false);
                this.openFile = file;
            } catch (IOException e) {
                e.printStackTrace();
                final Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Failed to load the NBT file: " + e.getMessage());
                alert.show();
            }
        }
    }

    private void loadIcon(Labeled labeled, String path) {
        loadIcon(labeled::setGraphic, path);
    }

    private void loadIcon(MenuItem menuItem, String path) {
        loadIcon(menuItem::setGraphic, path);
    }

    private void loadIcon(Consumer<ImageView> iconConsumer, String path) {
        final Image icon = new Image(getClass().getResourceAsStream("/" + path));
        final ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(22);
        iconView.setFitHeight(22);
        iconConsumer.accept(iconView);
    }
}
