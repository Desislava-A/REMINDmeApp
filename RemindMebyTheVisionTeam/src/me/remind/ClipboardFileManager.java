package me.remind;

import java.io.*;

/**
 * Created by Teodor on 7/4/2018.
 */
public class ClipboardFileManager implements Serializable {
    /**
     * A method that saves the clipboard object to a files
     *
     * @throws IOException
     */
    public void serialize(ClipBoard clipBoard) throws IOException {
        FileOutputStream fout = new FileOutputStream(new File("clipboard"));
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(clipBoard);
    }

    /**
     * A method that reads the clipboard data from a file
     *
     * @return Cliboard object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ClipBoard loadClipboard() throws IOException, ClassNotFoundException {

        FileInputStream inputStream = null;
        File file = new File("clipboard");
        if (file.length() == 0) {
            return new ClipBoard();
        }
        inputStream = new FileInputStream(file);
        ObjectInputStream oos = new ObjectInputStream(inputStream);

        return (ClipBoard) oos.readObject();
    }
}
