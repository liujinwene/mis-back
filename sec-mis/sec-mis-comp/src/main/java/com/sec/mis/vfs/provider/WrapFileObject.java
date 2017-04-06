package com.sec.mis.vfs.provider;

import java.net.URL;
import java.util.List;

import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileName;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSelector;
import org.apache.commons.vfs2.FileSystem;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.NameScope;
import org.apache.commons.vfs2.operations.FileOperations;

public class WrapFileObject implements FileObject {

	private FileObject target;
	private WrapFileName name;

	public WrapFileObject(FileObject target, WrapFileName name) {
		this.target = target;
		this.name = name;
	}

	public FileName getName() {
		return this.name;
	}

	public URL getURL() throws FileSystemException {
		return target.getURL();
	}

	public boolean exists() throws FileSystemException {
		return target.exists();
	}

	public boolean isHidden() throws FileSystemException {
		return target.isHidden();
	}

	public boolean isReadable() throws FileSystemException {
		return target.isReadable();
	}

	public boolean isWriteable() throws FileSystemException {
		return target.isWriteable();
	}

	public FileType getType() throws FileSystemException {
		return target.getType();
	}

	public FileObject getParent() throws FileSystemException {
		return target.getParent();
	}

	public FileSystem getFileSystem() {
		return target.getFileSystem();
	}

	public FileObject[] getChildren() throws FileSystemException {
		return target.getChildren();
	}

	public FileObject getChild(String name) throws FileSystemException {
		return target.getChild(name);
	}

	public FileObject resolveFile(String name, NameScope scope) throws FileSystemException {
		return target.resolveFile(name, scope);
	}

	public FileObject resolveFile(String path) throws FileSystemException {
		return target.resolveFile(path);
	}

	public FileObject[] findFiles(FileSelector selector) throws FileSystemException {
		return target.findFiles(selector);
	}

	public void findFiles(FileSelector selector, boolean depthwise, List<FileObject> selected) throws FileSystemException {
		target.findFiles(selector, depthwise, selected);
	}

	public boolean delete() throws FileSystemException {
		return target.delete();
	}

	public int delete(FileSelector selector) throws FileSystemException {
		return target.delete(selector);
	}

	public void createFolder() throws FileSystemException {
		target.createFolder();
	}

	public void createFile() throws FileSystemException {
		target.createFile();
	}

	public void copyFrom(FileObject srcFile, FileSelector selector) throws FileSystemException {
		target.copyFrom(srcFile, selector);
	}

	public void moveTo(FileObject destFile) throws FileSystemException {
		target.moveTo(destFile);
	}

	public boolean canRenameTo(FileObject newfile) {
		return target.canRenameTo(newfile);
	}

	public FileContent getContent() throws FileSystemException {
		return target.getContent();
	}

	public void close() throws FileSystemException {
		target.close();
	}

	public void refresh() throws FileSystemException {
		target.refresh();
	}

	public boolean isAttached() {
		return target.isAttached();
	}

	public boolean isContentOpen() {
		return target.isContentOpen();
	}

	public FileOperations getFileOperations() throws FileSystemException {
		return target.getFileOperations();
	}

}
