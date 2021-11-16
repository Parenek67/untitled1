package sample.Elements;

import javafx.beans.property.SimpleStringProperty;

public class SWElement {
    private SimpleStringProperty name;
    private SimpleStringProperty type;
    private SimpleStringProperty isNull;
    public SWElement(String name, String type, String isNull){
        this.isNull = new SimpleStringProperty(isNull);;
        this.type = new SimpleStringProperty(type);;
        this.name = new SimpleStringProperty(name);;
    }

    public String getIsNull() {
        return isNull.get();
    }

    public String getName() {
        return name.get();
    }

    public String getType() {
        return type.get();
    }


}
