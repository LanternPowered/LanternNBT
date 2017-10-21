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

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.layout.HBox;
import org.lanternpowered.nbt.ArrayTag;
import org.lanternpowered.nbt.BooleanArrayTag;
import org.lanternpowered.nbt.BooleanTag;
import org.lanternpowered.nbt.ByteArrayTag;
import org.lanternpowered.nbt.ByteTag;
import org.lanternpowered.nbt.CharArrayTag;
import org.lanternpowered.nbt.CharTag;
import org.lanternpowered.nbt.CompoundArrayTag;
import org.lanternpowered.nbt.CompoundTag;
import org.lanternpowered.nbt.DoubleArrayTag;
import org.lanternpowered.nbt.DoubleTag;
import org.lanternpowered.nbt.FloatArrayTag;
import org.lanternpowered.nbt.FloatTag;
import org.lanternpowered.nbt.IntArrayTag;
import org.lanternpowered.nbt.IntTag;
import org.lanternpowered.nbt.ListTag;
import org.lanternpowered.nbt.LongArrayTag;
import org.lanternpowered.nbt.LongTag;
import org.lanternpowered.nbt.ShortArrayTag;
import org.lanternpowered.nbt.ShortTag;
import org.lanternpowered.nbt.StringArrayTag;
import org.lanternpowered.nbt.StringTag;
import org.lanternpowered.nbt.Tag;
import org.lanternpowered.nbt.editor.converter.CharConverter;
import org.lanternpowered.nbt.editor.converter.NumberConverter;
import org.lanternpowered.nbt.editor.converter.StringConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public final class NbtEditorCell extends TreeCell<TreeElement> {

    private static final List<Class<? extends Tag<?>>> listTagTypes = Arrays.asList(
            IntTag.class,
            IntArrayTag.class,
            ShortTag.class,
            ShortArrayTag.class,
            ByteTag.class,
            BooleanArrayTag.class,
            DoubleTag.class,
            DoubleArrayTag.class,
            FloatTag.class,
            FloatArrayTag.class,
            LongTag.class,
            LongArrayTag.class,
            StringTag.class,
            StringArrayTag.class,
            BooleanTag.class,
            BooleanArrayTag.class,
            CharTag.class,
            CharArrayTag.class,
            CompoundTag.class,
            CompoundArrayTag.class
    );
    private static final List<String> listTagNames = new ArrayList<>();

    static {
        for (Class<? extends Tag<?>> tagType : listTagTypes) {
            String name = tagType.getSimpleName();
            name = name.replace("Tag", ""); // Get rid of the tag suffix
            // Add a space before every uppercase:
            // LongArray -> Long Array
            final StringBuilder builder = new StringBuilder();
            for (int i = 0; i < name.length(); i++) {
                final char c = name.charAt(i);
                if (i != 0 && Character.isUpperCase(c)) {
                    builder.append(' ');
                }
                builder.append(c);
            }
            listTagNames.add(builder.toString());
        }
    }

    private static Object defaultElementValue(Object object) {
        if (object instanceof Integer) {
            return 0;
        } else if (object instanceof Short) {
            return (short) 0;
        } else if (object instanceof Byte) {
            return (byte) 0;
        } else if (object instanceof Double) {
            return 0.0;
        } else if (object instanceof Float) {
            return 0f;
        } else if (object instanceof Long) {
            return 0L;
        } else if (object instanceof Boolean) {
            return false;
        } else if (object instanceof Character) {
            return (char) 0;
        } else if (object instanceof String) {
            return "";
        } else if (object instanceof CompoundTag) {
            return new CompoundTag();
        } else if (object instanceof ListTag) {
            return new ListTag<>();
        } else if (object instanceof int[]) {
            return new int[0];
        } else if (object instanceof short[]) {
            return new short[0];
        } else if (object instanceof byte[]) {
            return new byte[0];
        } else if (object instanceof double[]) {
            return new double[0];
        } else if (object instanceof float[]) {
            return new float[0];
        } else if (object instanceof long[]) {
            return new long[0];
        } else if (object instanceof boolean[]) {
            return new boolean[0];
        } else if (object instanceof char[]) {
            return new char[0];
        } else if (object instanceof String[]) {
            return new String[0];
        } else if (object instanceof CompoundTag[]) {
            return new CompoundTag[0];
        }
        throw new IllegalStateException();
    }

    private static Object defaultCollectionElementValue(Tag<?> tag) {
        if (tag instanceof IntArrayTag) {
            return 0;
        } else if (tag instanceof ShortArrayTag) {
            return (short) 0;
        } else if (tag instanceof ByteArrayTag) {
            return (byte) 0;
        } else if (tag instanceof DoubleArrayTag) {
            return 0.0;
        } else if (tag instanceof FloatArrayTag) {
            return 0f;
        } else if (tag instanceof LongArrayTag) {
            return 0L;
        } else if (tag instanceof StringArrayTag) {
            return "";
        } else if (tag instanceof BooleanArrayTag) {
            return false;
        } else if (tag instanceof CharArrayTag) {
            return (char) 0;
        } else if (tag instanceof CompoundArrayTag) {
            return new CompoundTag();
        }
        throw new IllegalStateException();
    }

    private ContextMenu contextMenu;

    NbtEditorCell() {
        // This will be called when right clicking on a
        // element to open the context menu.
        setOnContextMenuRequested(event -> {
            final TreeElement treeElement = getItem();
            // The context menu may not be opened if we are
            // editing the value
            if (isEditing() || treeElement == null ||
                    // Prevent multiple context menu's at the same time
                    (this.contextMenu != null && this.contextMenu.isShowing())) {
                return;
            }
            final ContextMenu contextMenu = new ContextMenu();
            // You can insert elements before/after collection values
            if (treeElement instanceof TreeCollectionValueElement) {
                final TreeCollectionValueElement valueElement = (TreeCollectionValueElement) treeElement;
                final BiConsumer<ActionEvent, Integer> function = (actionEvent, indexOffset) ->
                        valueElement.getParent().insertAt(valueElement.getIndex() + indexOffset,
                                defaultElementValue(valueElement.get()));
                // Create a item to insert elements before the current one
                final MenuItem insertBefore = new MenuItem("Insert before");
                insertBefore.setOnAction(actionEvent -> function.accept(actionEvent, 0));
                // Create a item to insert elements before the current one
                final MenuItem insertAfter = new MenuItem("Insert after");
                insertAfter.setOnAction(actionEvent -> function.accept(actionEvent, 1));
                // Add the insert buttons
                contextMenu.getItems().addAll(insertBefore, insertAfter);
                // Add a nice line in the menu
                contextMenu.getItems().add(new SeparatorMenuItem());
            } else if (treeElement instanceof TreeCollectionElement) {
                final TreeCollectionElement collectionElement = (TreeCollectionElement) treeElement;
                // A list tag does not have a type when it's empty, so we
                // we let the user specify the first tag type.
                if (collectionElement.length() == 0) {
                    if (collectionElement instanceof TreeListElement) {
                        // Create a item to insert elements before the current one
                        final MenuItem insert = new MenuItem("Insert");
                        insert.setOnAction(actionEvent -> {
                            final ContextMenu subContextMenu = new ContextMenu();
                            for (int i = 0; i < listTagTypes.size(); i++) {
                                final int index = i;
                                final MenuItem insertBefore = new MenuItem(listTagNames.get(index));
                                insertBefore.setOnAction(actionEvent1 -> collectionElement.insertAt(
                                        0, Tag.defaultTagValue((Class) listTagTypes.get(index))));
                                // Add the insert buttons
                                subContextMenu.getItems().add(insertBefore);
                            }
                            // Show the sub context menu
                            subContextMenu.show(this, event.getScreenX(), event.getScreenY());
                            this.contextMenu = subContextMenu;
                        });
                        // Add the insert button
                        contextMenu.getItems().addAll(insert);
                    } else {
                        final MenuItem insert = new MenuItem("Insert");
                        insert.setOnAction(actionEvent -> collectionElement.insertAt(
                                0, defaultCollectionElementValue(collectionElement.tag)));
                        // Add the insert button
                        contextMenu.getItems().addAll(insert);
                    }
                } else {
                    final BiConsumer<ActionEvent, Integer> function = (actionEvent, index) ->
                            collectionElement.insertAt(index, defaultElementValue(collectionElement.getAt(0)));
                    // Create a item to insert elements before the current one
                    final MenuItem insertFirst = new MenuItem("Insert first");
                    insertFirst.setOnAction(actionEvent -> function.accept(actionEvent, 0));
                    // Create a item to insert elements before the current one
                    final MenuItem insertLast = new MenuItem("Insert last");
                    insertLast.setOnAction(actionEvent -> function.accept(actionEvent, collectionElement.length()));
                    // Add the insert buttons
                    contextMenu.getItems().addAll(insertFirst, insertLast);
                }
                // Add a nice line in the menu
                contextMenu.getItems().add(new SeparatorMenuItem());
            }
            if (treeElement.parent != null) {
                final HBox modifyHBox = modifyValueHBox();
                if (modifyHBox != null) {
                    // Create the modify menu item
                    final MenuItem modify = new MenuItem("Modify");
                    modify.setOnAction(actionEvent -> modifyValue(modifyHBox));
                    contextMenu.getItems().add(modify);
                }
                // Allow the name of a tag to be modified
                if (treeElement instanceof TreeTagElement) {
                    final TreeTagElement tagElement = (TreeTagElement) treeElement;
                    if (tagElement.name != null) {
                        // Create the rename menu item
                        final MenuItem rename = new MenuItem("Rename");
                        rename.setOnAction(actionEvent -> {
                            final HBox hBox = new HBox();
                            hBox.setAlignment(Pos.CENTER_LEFT);
                            final TextFormatter<String> formatter = StringConverter.INSTANCE.constructFormatter();
                            formatter.valueProperty().addListener((observable, oldValue, newValue) -> {
                                final CompoundTag parent = (CompoundTag) tagElement.parent.tag;
                                if (newValue != null && !newValue.isEmpty() && !parent.containsKey(newValue)) {
                                    parent.remove(tagElement.name);
                                    parent.put(newValue, tagElement.tag);
                                    tagElement.name = newValue;
                                    tagElement.treeItem.setValue(tagElement); // Refresh the tree element
                                }
                            });
                            final TextField textField = new TextField();
                            textField.setTextFormatter(formatter);
                            textField.setText(tagElement.name);
                            hBox.getChildren().addAll(textField, new Label(" : " + getElementText(tagElement.tag)));
                            super.startEdit();
                            setText(null);
                            setGraphic(hBox);
                        });
                        // Add the rename button
                        contextMenu.getItems().add(rename);
                    }
                }
                // Create the delete menu item
                final MenuItem delete = new MenuItem("Delete");
                delete.setOnAction(actionEvent -> treeElement.remove());
                // Add the delete button
                contextMenu.getItems().add(delete);
            } else if (treeElement instanceof TreeTagElement) {
                final TreeTagElement tagElement = (TreeTagElement) treeElement;
                final MenuItem modifyCompression = new MenuItem("Modify compression");
                modifyCompression.setOnAction(actionEvent -> {
                    final Compression[] compressions = Compression.values();
                    final ChoiceBox<String> choiceBox = new ChoiceBox<>(
                            FXCollections.observableArrayList(Arrays.stream(compressions)
                                    .map(Compression::name)
                                    .map(String::toLowerCase)
                                    .collect(Collectors.toList())));
                    choiceBox.getSelectionModel().select(tagElement.compression.ordinal());
                    choiceBox.getSelectionModel().selectedIndexProperty().addListener(
                            (observable, oldValue, newValue) -> tagElement.compression = compressions[newValue.intValue()]);
                    final HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    hBox.getChildren().add(new Label(getElementText(tagElement) + " "));
                    hBox.getChildren().add(choiceBox);
                    super.startEdit();
                    setText(null);
                    setGraphic(hBox);
                });
                contextMenu.getItems().add(modifyCompression);
            }
            if (!contextMenu.getItems().isEmpty()) {
                // Show the context menu
                contextMenu.show(this, event.getScreenX(), event.getScreenY());
                this.contextMenu = contextMenu;
            }
        });
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    private HBox modifyValueHBox() {
        final HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);

        final TreeElement treeElement = getItem();
        if (treeElement instanceof IValue) {
            final IValue iValue = (IValue) treeElement;
            final Object object = iValue.get();
            if (object instanceof Boolean) {
                final ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList("true", "false"));
                choiceBox.getSelectionModel().select((Boolean) object ? 0 : 1);
                choiceBox.getSelectionModel().selectedIndexProperty().addListener(
                        (observable, oldValue, newValue) -> iValue.set(newValue.intValue() == 0));
                hBox.getChildren().add(choiceBox);
            } else if (object instanceof Number) {
                final TextField textField = new TextField();
                final NumberConverter converter;
                if (object instanceof Integer) {
                    converter = NumberConverter.INT;
                    textField.setTooltip(new Tooltip("int [" + Integer.MIN_VALUE + ".." + Integer.MAX_VALUE + "]"));
                } else if (object instanceof Long) {
                    converter = NumberConverter.LONG;
                    textField.setTooltip(new Tooltip("long [" + Long.MIN_VALUE + ".." + Long.MAX_VALUE + "]"));
                } else if (object instanceof Byte) {
                    converter = NumberConverter.BYTE;
                    textField.setTooltip(new Tooltip("byte [" + Byte.MIN_VALUE + ".." + Byte.MAX_VALUE + "]"));
                } else if (object instanceof Short) {
                    converter = NumberConverter.SHORT;
                    textField.setTooltip(new Tooltip("short [" + Short.MIN_VALUE + ".." + Short.MAX_VALUE + "]"));
                } else if (object instanceof Float) {
                    converter = NumberConverter.FLOAT;
                    textField.setTooltip(new Tooltip("float [" + Float.MIN_VALUE + ".." + Float.MAX_VALUE + "]"));
                } else if (object instanceof Double) {
                    converter = NumberConverter.DOUBLE;
                    textField.setTooltip(new Tooltip("double [" + Float.MIN_VALUE + ".." + Float.MAX_VALUE + "]"));
                } else {
                    throw new IllegalStateException();
                }
                final IValue<Number> iNumberValue = iValue;
                final TextFormatter<Number> formatter = converter.constructFormatter();
                formatter.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        iNumberValue.set(newValue);
                    }
                });
                textField.setTextFormatter(formatter);
                textField.setText(converter.toString(iNumberValue.get()));
                hBox.getChildren().add(textField);
            } else if (object instanceof Character) {
                final IValue<Character> iCharValue = iValue;
                final TextFormatter<Character> formatter = CharConverter.INSTANCE.constructFormatter();
                formatter.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        iCharValue.set(newValue);
                    }
                });
                final TextField textField = new TextField();
                textField.setTextFormatter(formatter);
                textField.setText(CharConverter.INSTANCE.toString(iCharValue.get()));
                hBox.getChildren().add(textField);
            } else if (object instanceof String) {
                final IValue<String> iStringValue = iValue;
                final TextFormatter<String> formatter = StringConverter.INSTANCE.constructFormatter();
                formatter.valueProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        iStringValue.set(newValue);
                    }
                });
                final TextField textField = new TextField();
                textField.setTextFormatter(formatter);
                textField.setText(iStringValue.get());
                hBox.getChildren().add(textField);
            }
        }
        if (hBox.getChildren().isEmpty()) {
            return null;
        }
        if (this.contextMenu != null &&
                this.contextMenu.isShowing()) {
            this.contextMenu.hide();
            this.contextMenu = null;
        }
        super.startEdit();
        if (treeElement instanceof TreeTagElement) {
            final TreeTagElement treeTagElement = (TreeTagElement) treeElement;
            if (treeTagElement.name != null) {
                hBox.getChildren().add(0, new Label(treeTagElement.name + ": "));
            }
        }
        return hBox;
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    private void modifyValue(HBox hBox) {
        if (hBox == null) {
            return;
        }

        if (this.contextMenu != null &&
                this.contextMenu.isShowing()) {
            this.contextMenu.hide();
            this.contextMenu = null;
        }
        super.startEdit();

        setText(null);
        setGraphic(hBox);
    }

    @Override
    public void startEdit() {
        final TreeElement treeElement = getItem();
        if (treeElement instanceof TreeTagElement) {
            final TreeTagElement treeTagElement = (TreeTagElement) treeElement;
            final Tag<?> tag = treeTagElement.tag;
            if (tag instanceof CompoundTag ||
                    tag instanceof ListTag ||
                    tag instanceof ArrayTag) {
                return;
            }
        }

        modifyValue(modifyValueHBox());
    }

    private static String getElementText(Tag<?> tag) {
        if (tag instanceof CompoundTag) {
            final int entries = ((CompoundTag) tag).size();
            return entries + " " + (entries == 1 ? "entry" : "entries");
        } else if (tag instanceof ListTag) {
            final int entries = ((ListTag) tag).size();
            return entries + " " + (entries == 1 ? "entry" : "entries");
        } else if (tag instanceof ArrayTag) {
            final int entries = ((ArrayTag) tag).length();
            return entries + " " + (entries == 1 ? "entry" : "entries");
        } else {
            return tag.get().toString();
        }
    }

    private static String getElementText(TreeElement item) {
        if (item instanceof TreeTagElement) {
            final TreeTagElement item1 = (TreeTagElement) item;
            String base = item1.name != null ? item1.name + ": " : "";
            base += getElementText(item1.tag);
            return base;
        } else if (item instanceof TreeCollectionValueElement) {
            final Object obj = ((TreeCollectionValueElement) item).get();
            if (obj instanceof Tag) {
                return getElementText((Tag<?>) obj);
            }
            return obj.toString();
        }
        throw new IllegalStateException();
    }

    private String getElementText() {
        return getElementText(getItem());
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getElementText());
        setGraphic(getTreeItem().getGraphic());
    }

    @Override
    public void updateItem(TreeElement item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                setText(null);
                setGraphic(null);
            } else {
                setText(getElementText());
                setGraphic(getTreeItem().getGraphic());
            }
        }
    }
}
