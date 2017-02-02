package sample;

import javafx.scene.control.Button;

/**
 * Created by eddylloyd on 11/21/16.
 */
public class Shared {

    static  void styleBtn(Button btn)
    {
        btn.setOnMouseEntered(e ->{
            btn.setScaleX(1.1); btn.setScaleY(1.1);
        });
        btn.setOnMouseExited(e ->{
            btn.setScaleX(1.0); btn.setScaleY(1.0);
        });
        btn.setId("button");
    }

}


