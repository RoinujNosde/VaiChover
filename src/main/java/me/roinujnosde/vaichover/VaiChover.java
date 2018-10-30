package me.roinujnosde.vaichover;

import me.roinujnosde.vaichover.control.Controlador;
import me.roinujnosde.vaichover.io.Recebedor;
import me.roinujnosde.vaichover.ui.MainFrame;

public class VaiChover {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame(new Controlador());
        mainFrame.show();
    }
}
