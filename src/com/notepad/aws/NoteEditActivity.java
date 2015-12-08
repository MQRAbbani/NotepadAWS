package com.notepad.aws;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

public class NoteEditActivity extends Activity {

	private AmazonS3Client s3Client = new AmazonS3Client(
			new BasicAWSCredentials(Helper.ACCESS_KEY_ID,
					Helper.SECRET_KEY));

	private EditText TitleText;
	private EditText BodyText;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = getIntent().getExtras();
		final String current_selected_text = bundle.getString("current_selected_text");

		setContentView(R.layout.note_edit);
		setTitle(R.string.note_edit);

		TitleText = (EditText) findViewById(R.id.title);
		BodyText = (EditText) findViewById(R.id.body);


		TitleText.setText(current_selected_text);

		//get the file data from bucket
		S3Object object = s3Client.getObject(
				new GetObjectRequest(Helper.BUCKET_NAME, current_selected_text + ".txt"));
		InputStream objectData = object.getObjectContent();
		
		String body = getStringFromInputStream(objectData);
		BodyText.setText(body);

		try {
			objectData.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		s3Client.deleteObject(Helper.BUCKET_NAME, current_selected_text + ".txt");

		Button confirmButton = (Button) findViewById(R.id.confirm);

		confirmButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {

				ByteArrayInputStream input = new ByteArrayInputStream(BodyText.getText().toString().getBytes());
				s3Client.putObject(Helper.BUCKET_NAME, TitleText.getText().toString() + ".txt", input, new ObjectMetadata());

				Intent i = new Intent(getApplicationContext(), Notepadv3.class);
				startActivity(i);

			}

		});

	}   


	// convert InputStream to String
	public static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

}
