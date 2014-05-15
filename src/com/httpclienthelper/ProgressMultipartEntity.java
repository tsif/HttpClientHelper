package com.httpclienthelper;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;

public class ProgressMultipartEntity extends MultipartEntity {

	private final ProgressListener _listener;
	 
	public ProgressMultipartEntity(final ProgressListener listener) {
		super();
		_listener = listener;
	}
 
	public ProgressMultipartEntity(final HttpMultipartMode mode, final ProgressListener listener) {
		super(mode);
		_listener = listener;
	}
 
	public ProgressMultipartEntity(HttpMultipartMode mode, final String boundary, final Charset charset, final ProgressListener listener) {
		super(mode, boundary, charset);
		_listener = listener;
	}
 
	@Override public void writeTo(final OutputStream outstream) throws IOException {
		super.writeTo(new CountingOutputStream(outstream, _listener));
	}
 
	public static interface ProgressListener {
		void transferred(long num);
	}
 
	public static class CountingOutputStream extends FilterOutputStream {
 
		private final ProgressListener listener;
		private long transferred;
 
		public CountingOutputStream(final OutputStream out, final ProgressListener listener) {
			super(out);
			this.listener = listener;
			this.transferred = 0;
		}
 
		public void write(byte[] b, int off, int len) throws IOException {
			out.write(b, off, len);
			this.transferred += len;
			this.listener.transferred(this.transferred);
		}
 
		public void write(int b) throws IOException {
			out.write(b);
			this.transferred++;
			this.listener.transferred(this.transferred);
		}
	}
}
