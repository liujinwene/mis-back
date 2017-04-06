package com.sec.mis.vfs;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;

public final class WFS {

	public static WrapFileSystemManager manager;

	public void setManager(WrapFileSystemManager manager) {
		WFS.manager = manager;
	}

	public static FileObject resolveFile(String uri) throws FileSystemException {
		System.out.println("\n WFS manager:" + manager);
		return manager.resolveFile(uri);

	}

	public static FileObject resolveFile(FileObject baseFile, String uri) throws FileSystemException {
		return manager.resolveFile(baseFile, uri);
	}

	public static void closeQuietly(FileObject file) {
		try {
			if (file != null) file.close();
		} catch (FileSystemException e) {
			throw new RuntimeException(e);
		}
	}

}
