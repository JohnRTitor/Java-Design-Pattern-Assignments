package file_system_demo_factory;

import java.time.LocalDate;

abstract class FileSystemComponent {
    protected String name;
    protected String type;
    protected LocalDate createDate;
    protected String ownerId;
    protected boolean ownerRead  = true;
    protected boolean ownerWrite = true;
    protected boolean othersRead  = false;
    protected boolean othersWrite = false;

    public String    getName()     { return name; }
    public String    getType()     { return type; }
    public String    getOwnerId()  { return ownerId; }
    public LocalDate getCreateDate(){ return createDate; }

    public abstract int getSize();

    public abstract void changePermission(boolean oR, boolean oW, boolean otherR, boolean otherW);

    public abstract String display(String indent);

    /** Returns a human-readable permission string e.g. "Owner[rw] Others[r-]" */
    protected String permissionString() {
        return "Owner[" + (ownerRead  ? "r" : "-") + (ownerWrite  ? "w" : "-") + "] "
             + "Others["+ (othersRead ? "r" : "-") + (othersWrite ? "w" : "-") + "]";
    }
}
