package model;

import java.util.List;

public class FeedItem {
    private String rowId;
    private String shareRowId;
    private String title;
    private String avatar;
    private String message;
    private String createdBy;
    private String createdDate;
    private boolean status;
    private boolean showComment;
    private List postComments;
    private List<GalleryItem> postGallaries;
    private List<StatusType> statusTypes;
    private String selectedEmoji;

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getShareRowId() {
        return shareRowId;
    }

    public void setShareRowId(String shareRowId) {
        this.shareRowId = shareRowId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isShowComment() {
        return showComment;
    }

    public void setShowComment(boolean showComment) {
        this.showComment = showComment;
    }

    public List getPostComments() {
        return postComments;
    }

    public void setPostComments(List postComments) {
        this.postComments = postComments;
    }

    public List<GalleryItem> getPostGallaries() {
        return postGallaries;
    }

    public void setPostGallaries(List<GalleryItem> postGallaries) {
        this.postGallaries = postGallaries;
    }

    public List<StatusType> getStatusTypes() {
        return statusTypes;
    }

    public void setStatusTypes(List<StatusType> statusTypes) {
        this.statusTypes = statusTypes;
    }

    public String getSelectedEmoji() {
        return selectedEmoji;
    }

    public void setSelectedEmoji(String selectedEmoji) {
        this.selectedEmoji = selectedEmoji;
    }
}