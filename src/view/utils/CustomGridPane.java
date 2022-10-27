package view.utils;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CustomGridPane extends GridPane {

    private int initialColumnsNumber;
    private int breakPoint;
    private int finalColumnsNumber;
    /**
     * true when the CustomGridPane is already resized
     */
    private boolean inResponsiveMode = false;

    public CustomGridPane(int columnsNumber){
        super();
        this.initialColumnsNumber = columnsNumber;
        this.addColumnsConstraints(columnsNumber);
    }

    public void setBreakPoint(int breakPoint, Stage stage){
        this.breakPoint = breakPoint;
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            if((double)newVal <= this.breakPoint && !this.inResponsiveMode){
                this.resize();
            }
            if(this.inResponsiveMode && (double)newVal > this.breakPoint){
                this.resize();
            }
        });
    }

    private void resize() {
        int cn = this.initialColumnsNumber;
        if(!this.inResponsiveMode){
            cn = this.finalColumnsNumber;
        }
        this.addColumnsConstraints(cn);
        ObservableList<Node> children = this.getChildren();
        for (int i = 0; i<children.size(); i++){
            Node child = children.get(i);
            int columnIndex = i%cn;
            int rowIndex = (int) Math.floor(i/cn);
            GridPane.setColumnIndex(child, columnIndex);
            GridPane.setRowIndex(child, rowIndex);
        }
        this.inResponsiveMode = !this.inResponsiveMode;
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

    public void setFinalColumnsNumber(int columnsNumber){
        this.finalColumnsNumber = columnsNumber;
    }

    public void add(Node node){
        int index = this.getChildren().size();
        int columnIndex = index%this.initialColumnsNumber;
        int rowIndex = (int) Math.floor(index/this.initialColumnsNumber);
        this.add(node, columnIndex, rowIndex);
    }
}
