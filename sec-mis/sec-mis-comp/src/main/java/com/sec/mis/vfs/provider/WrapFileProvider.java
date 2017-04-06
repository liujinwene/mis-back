package com.sec.mis.vfs.provider;

import java.util.Collection;

import org.apache.commons.vfs2.Capability;
import org.apache.commons.vfs2.FileName;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemConfigBuilder;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.provider.AbstractVfsContainer;
import org.apache.commons.vfs2.provider.FileProvider;

import com.sec.mis.vfs.WrapFileSystemManager;

public class WrapFileProvider extends AbstractVfsContainer implements FileProvider {

	private FileObject target;
	private WrapFileSystemManager mgr;
	private WrapFileNameParser parser;

	public WrapFileProvider(WrapFileSystemManager mgr, String targetUri) throws FileSystemException {
		this.mgr = mgr;
		this.target = mgr.resolveFile(targetUri);
		this.parser = new WrapFileNameParser();
	}

	@Override
	public FileObject findFile(FileObject baseFile, String uri, FileSystemOptions fileSystemOptions) throws FileSystemException {

		FileName baseName = baseFile == null ? null : baseFile.getName();
		WrapFileName fileName = (WrapFileName) parseUri(baseName, uri);

		String realURI = fileName.getRealURI();
		
		FileObject realFile = mgr.resolveFile(realURI);

		return new WrapFileObject(realFile, fileName);

	}

	@Override
	public Collection<Capability> getCapabilities() {
		try {
			return mgr.getProviderCapabilities(target.getName().getScheme());
		} catch (FileSystemException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public FileName parseUri(FileName base, String uri) throws FileSystemException {
		WrapFileName fileName = (WrapFileName) parser.parseUri(getContext(), base, uri);
		String suburi = fileName.getURI().substring(fileName.getRootURI().length());
		FileName targetName = mgr.resolveName(target.getName(), suburi);
		fileName.setTarget(targetName);
		return fileName;
	}

	@Override
	public FileObject createFileSystem(String scheme, FileObject file, FileSystemOptions fileSystemOptions) throws FileSystemException {
		return null;
	}

	@Override
	public FileSystemConfigBuilder getConfigBuilder() {
		return null;
	}

}
