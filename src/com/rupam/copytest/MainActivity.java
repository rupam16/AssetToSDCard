package com.rupam.copytest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	AssetManager am;
	String base_path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		am = getAssets();
		base_path = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ File.separator + "Rupam";

		copy("Files");
	}

	public void copy(String path) {
		String[] files = null;
		try {
			files = am.list(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		String totalPath = "";
		InputStream in;
		OutputStream out;

		for (String name : files) {
			totalPath = path + File.separator + name;

			try {
				in = am.open(totalPath);

				Log.d("TAG", "Yeah , " + totalPath + " is a file");

				File dir = new File(base_path + File.separator + path);
				if (!dir.exists())
					dir.mkdirs();

				Log.d("TAG", "Creating output file at " + dir.getAbsolutePath());

				File out_file = new File(dir.getAbsolutePath(), name);
				out = new FileOutputStream(out_file);

				copyFile(in, out);
				out.flush();
				
				in.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				Log.d("TAG", "Yeah , " + totalPath + " is a folder. So recurse");
				copy(totalPath);
			} finally {
				in = null;
				out = null;
			}

		}
	}

	private void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tt, menu);
		return true;
	}

}
