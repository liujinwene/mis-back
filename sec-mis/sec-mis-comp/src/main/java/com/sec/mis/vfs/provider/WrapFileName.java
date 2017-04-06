package com.sec.mis.vfs.provider;

import org.apache.commons.vfs2.FileName;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.provider.local.LocalFileName;

public class WrapFileName extends LocalFileName implements FileName {

	private FileName target;

	protected WrapFileName(String scheme, String path, FileType type) {
		super(scheme, "", path, type);
	}

	public void setTarget(FileName target) {
		this.target = target;
	}

	@Override
	public FileName createName(String path, FileType type) {
		return new WrapFileName(getScheme(), path, type);
	}

	public String getRealURI() {
		if (target instanceof WrapFileName) return ((WrapFileName) target).getRealURI();
		return target.getURI();
	}

	public String toString() {
		return getRealURI();
	}

}
