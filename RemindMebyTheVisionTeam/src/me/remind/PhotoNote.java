package me.remind;

import java.awt.*;

public class PhotoNote extends Note {
    private Image photo;

    PhotoNote(String title, String deadline, Image photo) {
        super(title, deadline);
        this.photo=photo;
    }

    @Override
    public void makeNote() {

    }
}
