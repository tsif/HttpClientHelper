package com.httpclienthelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;

public class Utilities {

	public static synchronized String extractString(HttpEntity  entity) {

		InputStream is = null;

		try {
			is = entity.getContent();
		} catch (IllegalStateException e) {
			return null;

		} catch (IOException e) {
			return null;

		} catch (Exception e) {
			return null;
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder  sb     = new StringBuilder();
		String         line   = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append((line + "\n"));
			}
		} catch (IOException e) {
			return null;
		} catch (Exception e) {
			return null;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				return null;
			}
		}
		return sb.toString();
	}
}
