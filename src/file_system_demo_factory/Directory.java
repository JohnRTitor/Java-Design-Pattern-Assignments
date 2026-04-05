package file_system_demo_factory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class Directory extends FileSystemComponent {

	private List<FileSystemComponent> children = new ArrayList<>();

	Directory(String name, String ownerId) {
		this.name = name;
		this.type = "Directory";
		this.ownerId = ownerId;
		this.createDate = LocalDate.now();
	}

	public Directory(String name) {
		this(name, "root");
	}

	/** Add a child file/directory */
	public void add(FileSystemComponent file) {
		children.add(file);
	}

	/** Remove a child by name; returns true if found and removed */
	public boolean remove(String fileName) {
		return children.removeIf(f -> f.getName().equals(fileName));
	}

	/** Returns unmodifiable view of children (for GUI tree navigation) */
	public List<FileSystemComponent> getChildren() {
		return java.util.Collections.unmodifiableList(children);
	}

	@Override
	public int getSize() {
		int total = 0;
		for (FileSystemComponent f : children) {
			total += f.getSize();
		}
		return total;
	}

	@Override
	public void changePermission(boolean oR, boolean oW, boolean otherR, boolean otherW) {
		this.ownerRead = oR;
		this.ownerWrite = oW;
		this.othersRead = otherR;
		this.othersWrite = otherW;
		// Recursively apply to all children
		for (FileSystemComponent f : children) {
			f.changePermission(oR, oW, otherR, otherW);
		}
	}

	@Override
	public String display(String indent) {
		String result = indent + "[DIR] " + name + " | Owner: " + ownerId + " | Created: " + createDate + " | Size: "
				+ getSize() + " KB" + " | " + permissionString() + "\n";
		for (FileSystemComponent f : children) {
			result += f.display(indent + "   ");
		}
		return result;
	}
}
