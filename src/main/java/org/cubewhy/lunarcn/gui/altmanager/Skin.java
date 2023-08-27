package org.cubewhy.lunarcn.gui.altmanager;

import java.io.File;
import java.util.Objects;

public class Skin {
    private String name;
    private File localFile;

    public Skin() {
    }

    public Skin(String name, File localFile) {
        this.name = name;
        this.localFile = localFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getLocalFile() {
        return localFile;
    }

    public void setLocalFile(File localFile) {
        this.localFile = localFile;
    }

    @Override
    public String toString() {
        return "Skin{" +
                "name='" + name + '\'' +
                ", localFile=" + localFile +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skin skin = (Skin) o;
        return Objects.equals(name, skin.name) && Objects.equals(localFile, skin.localFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, localFile);
    }
}
