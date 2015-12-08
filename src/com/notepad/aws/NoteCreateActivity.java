package com.notepad.aws;

import java.io.ByteArrayInputStream;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NoteCreateActivity extends Activity{
	
	private AmazonS3Client s3Client = new AmazonS3Client(
			new BasicAWSCredentials(Helper.ACCESS_KEY_ID,
					Helper.SECRET_KEY));

	private EditText TitleText;
	private EditText BodyText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.note_edit);
		setTitle(R.string.note_create);

		TitleText = (EditText) findViewById(R.id.title);
		BodyText = (EditText) findViewById(R.id.body);

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

}
