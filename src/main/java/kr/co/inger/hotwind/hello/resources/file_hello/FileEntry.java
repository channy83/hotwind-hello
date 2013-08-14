package kr.co.inger.hotwind.hello.resources.file_hello;

import java.io.Serializable;

public class FileEntry implements Serializable {

	private static final long serialVersionUID = -2217665917936774559L;

	private String filename;

	private long filesize;

	private byte[] filebytes;

	public FileEntry() {
		super();
	}

	public FileEntry(String filename, long filesize, byte[] filebytes) {
		super();
		this.filename = filename;
		this.filesize = filesize;
		this.filebytes = filebytes;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public long getFilesize() {
		return filesize;
	}

	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}

	public byte[] getFilebytes() {
		return filebytes;
	}

	public void setFilebytes(byte[] filebytes) {
		this.filebytes = filebytes;
	}

	@Override
	public String toString() {
		return "FileEntry [filename=" + filename + ", filesize=" + filesize
				+ "]";
	}

}
