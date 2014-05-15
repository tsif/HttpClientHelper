package com.httpclienthelper;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.entity.InputStreamEntity;

public class ProgressEntity extends InputStreamEntity {

	private final ProgressListener _listener;
	
	public ProgressEntity(InputStream instream, long length, ProgressListener listener) {
		super(instream, length);
		_listener = listener;
	}
	
	@Override public void writeTo(final OutputStream outstream) throws IOException {
		super.writeTo(new CountingOutputStream(outstream, _listener));
	}
	
	public static interface ProgressListener {
		void transferred(long num);
	}
	
	public static class CountingOutputStream extends FilterOutputStream {
		 
		private final ProgressListener _listener;
		private long  transferred;
 
		public CountingOutputStream(final OutputStream out, final ProgressListener listener) {
			super(out);
			_listener        = listener;
			this.transferred = 0;
		}
 
		public void write(byte[] b, int off, int len) throws IOException {
			out.write(b, off, len);
			this.transferred += len;
			_listener.transferred(this.transferred);
		}
 
		public void write(int b) throws IOException {
			out.write(b);
			this.transferred++;
			_listener.transferred(this.transferred);
		}
	}
}
