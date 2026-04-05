package file_system_demo_factory;

import java.time.LocalDate;

class MultimediaFile extends FileSystemComponent {

	private int size;

	MultimediaFile(String name, int size, String ownerId) {
		this.name = name;
		this.type = "Multimedia";
		this.size = size;
		this.ownerId = ownerId;
		this.createDate = LocalDate.now();
	}

	// Convenience constructor
	public MultimediaFile(String name, int size) {
		this(name, size, "root");
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public void changePermission(boolean oR, boolean oW, boolean otherR, boolean otherW) {
		this.ownerRead = oR;
		this.ownerWrite = oW;
		this.othersRead = otherR;
		this.othersWrite = otherW;
	}

	@Override
	public String display(String indent) {
		return indent + "[MEDIA] " + name + " | Owner: " + ownerId + " | Created: " + createDate + " | Size: " + size
				+ " KB" + " | " + permissionString() + "\n";
	}
}
