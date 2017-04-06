package com.sec.mis.vfs.provider;

import org.apache.commons.vfs2.FileName;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.provider.local.GenericFileNameParser;

public class WrapFileNameParser extends GenericFileNameParser {

	protected FileName createFileName(String scheme, final String rootFile, final String path, final FileType type) {
		return new WrapFileName(scheme, path, type);
	}
}
