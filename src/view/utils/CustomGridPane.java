package view.utils;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CustomGridPane extends GridPane {

    /**
     * numero di colonne fino al primo breakpoint
     */
    private int initialColumnsNumber;
    private int currentColumnNumber;
    /**
     * contiene il numero di colonne associato a un certo range di larghezza dello schermo
     * [start, end)
     */
    private ArrayList<MediaQuery> mediaQueries = new ArrayList<>();
    /**
     * true when the CustomGridPane is already resized
     */
    private boolean inResponsiveMode = false;

    public CustomGridPane(int columnsNumber){
        super();
        this.initialColumnsNumber = columnsNumber;
        this.addColumnsConstraints(columnsNumber);
    }

    public void setBreakPoint(double start, double end, int columnNumber){
        MediaQuery mediaQuery = new MediaQuery(start, end, columnNumber);
        this.mediaQueries.add(mediaQuery);
    }

    public void startToListenForAdjustments(Stage stage){
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            for(MediaQuery mediaQuery:mediaQueries){
                if(mediaQuery.contains(newVal.doubleValue())){
                    int colNum = (int) mediaQuery.getBehaviour();
                    if(colNum != this.currentColumnNumber){
                        this.resize(colNum);
                        break;
                    }
                }
            }
        });
    }

    private void resize(int columnNumber){
        this.addColumnsConstraints(columnNumber);
        ObservableList<Node> children = this.getChildren();
        for (int i = 0; i<children.size(); i++){
            Node child = children.get(i);
            int columnIndex = i%columnNumber;
            int rowIndex = (int) Math.floor(i/columnNumber);
            GridPane.setColumnIndex(child, columnIndex);
            GridPane.setRowIndex(child, rowIndex);
        }
        this.currentColumnNumber = columnNumber;
    }

    private void addColumnsConstraints(int columnsNumber){
        this.getColumnConstraints().clear();
        double width = Math.floor(100/columnsNumber);
        for(int i = 0; i< columnsNumber; i++){
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(width);
            this.getColumnConstraints().add(column);
        }
    }

    public void add(Node node){
        int index = this.getChildren().size();
        int columnIndex = index%this.initialColumnsNumber;
        int rowIndex = (int) Math.floor(index/this.initialColumnsNumber);
        this.add(node, columnIndex, rowIndex);
    }
}
