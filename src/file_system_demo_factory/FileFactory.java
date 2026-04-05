package file_system_demo_factory;

/**
 * Factory Pattern: The client knows the TYPE of file it wants but delegates the
 * actual instantiation logic to FileFactory. This decouples object creation
 * from the client (FileSystemDemoGUI).
 */
class FileFactory {

	static FileSystemComponent createFile(String type, String name, int size, String ownerId) {
		switch (type) {
		case "Text":
			return new TextFile(name, size, ownerId);
		case "Multimedia":
			return new MultimediaFile(name, size, ownerId);
		case "Directory":
			return new Directory(name, ownerId);
		default:
			throw new IllegalArgumentException("Unknown file type: " + type);
		}
	}

	// Overload for backward compatibility (defaults ownerId to "root")
	public static FileSystemComponent createFile(String type, String name, int size) {
		return createFile(type, name, size, "root");
	}
}
