package model;

/**
 * Created by beschi.agent@gmail.com on 3/8/2016.
 */

public class GalleryItem {
    private String rowId;
    private String id;
    private String picName;

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public GalleryItem(String rowId, String id, String picName) {
        this.rowId = rowId;
        this.id = id;
        this.picName = picName;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
