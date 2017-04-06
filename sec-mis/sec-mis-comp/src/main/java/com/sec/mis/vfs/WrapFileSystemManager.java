package com.sec.mis.vfs;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;

import com.sec.mis.vfs.provider.WrapFileProvider;

public class WrapFileSystemManager extends StandardFileSystemManager {

	private Map<String, String> schemes;
	
	public WrapFileSystemManager() {
		this.setConfiguration(StandardFileSystemManager.class.getResource("providers.xml"));
	}

	@Override
	protected void configurePlugins() throws FileSystemException {
		
	}

	public void init() throws FileSystemException {
		super.init();

		if (schemes == null) return;
		for (Entry<String, String> entry : schemes.entrySet()) {
			String schemas = entry.getKey();
			String targetUri = entry.getValue();
			this.addProvider(schemas, new WrapFileProvider(this, targetUri));
		}
	}

	public void setSchemes(Map<String, String> schemes) {
		this.schemes = schemes;
	}

}
