package ba.unsa.etf.rpr.tutorijal05;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

    public Label display = new Label();
    private SimpleStringProperty number = new SimpleStringProperty("0");
    private double result = 0;
    private boolean hasDot = false, operation = false;
    private Button previous = null;


    public String getNumber() {
        return number.get();
    }

    public SimpleStringProperty numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    public void numBtnPress(ActionEvent actionEvent) {
        display.textProperty().bind(number);
        Button button = (Button) actionEvent.getSource();
        if (number.get().equals("0") || operation) {
            number.set("");
            hasDot = false;
        }
        switch (button.getId()) {
            case "btn1" :
                number.set(number.get() + "1");
                System.out.println(number.get());
                break;
            case "btn2" :
                number.set(number.get() + "2");
                System.out.println(number.get());
                break;
            case "btn3" :
                number.set(number.get() + "3");
                System.out.println(number.get());
                break;
            case "btn4" :
                number.set(number.get() + "4");
                System.out.println(number.get());
                break;
            case "btn5" :
                number.set(number.get() + "5");
                System.out.println(number.get());
                break;
            case "btn6" :
                number.set(number.get() + "6");
                System.out.println(number.get());
                break;
            case "btn7" :
                number.set(number.get() + "7");
                System.out.println(number.get());
                break;
            case "btn8" :
                number.set(number.get() + "8");
                System.out.println(number.get());
                break;
            case "btn9" :
                number.set(number.get() + "9");
                System.out.println(number.get());
                break;
            case "btn0" :
                number.set(number.get() + "0");
                System.out.println(number.get());
                break;
            case "dotBtn" :
                if (number.get().isEmpty())
                    number.set(number.get() + "0");
                if (!hasDot && number.get().charAt(number.get().length()-1) != '.') {
                    number.set(number.get() + ".");
                    hasDot = true;
                }
        }
        operation = false;

    }

    public void operation(ActionEvent actionEvent) {
        if (previous == null || operation) {
            result = Double.parseDouble(number.get());
        }
        else {
            double operand = Double.parseDouble(number.get());
            switch (previous.getId()) {
                case "plusBtn" :
                    result += operand;
                    break;
                case "subBtn" :
                    result -= operand;
                    break;
                case "equalsBtn" :
                    previous = null;
                    break;
            }
        }
        number.set(String.valueOf(result));
        previous = (Button) actionEvent.getSource();
        operation = true;
    }

}
