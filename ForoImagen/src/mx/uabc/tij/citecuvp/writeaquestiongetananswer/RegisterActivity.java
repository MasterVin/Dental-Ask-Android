package mx.uabc.tij.citecuvp.writeaquestiongetananswer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import beans.Users;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	
	private EditText editUsername;
	private EditText editPassword;
	private EditText editName;
	private EditText editLastName;
	private EditText editEmail;
	private Button registerButton;
	
	private Users user;
	
	private static final String PATTERN_EMAIL="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static boolean email(String email){
		Pattern p=Pattern.compile(PATTERN_EMAIL);
		Matcher m=p.matcher(email);
		return m.matches();
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		
		editUsername=(EditText)findViewById(R.id.username);
		editPassword=(EditText)findViewById(R.id.pass);
		editName=(EditText)findViewById(R.id.name);
		editLastName=(EditText)findViewById(R.id.lastname);
		editEmail=(EditText)findViewById(R.id.email);
		registerButton=(Button)findViewById(R.id.register_button);
		
		registerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(editUsername.getText().length()>0
						&&editPassword.getText().length()>0
						&&editName.getText().length()>0
						&&editLastName.getText().length()>0
						&&editEmail.getText().length()>0){
					
					if(email(editEmail.getText().toString())){
					user=new Users(editUsername.getText().toString(),
							editPassword.getText().toString(),
							editName.getText().toString(),
							editLastName.getText().toString(),
							editEmail.getText().toString());
					
					UploadUser uploadUser=new UploadUser();
					uploadUser.passContent(getApplicationContext());
					uploadUser.execute(user);
					}else{
						Toast.makeText(getApplicationContext(), "Please use a valid Email", Toast.LENGTH_SHORT).show();
					}
					
				}
				else{
					System.out.println("no");
					Toast.makeText(getApplicationContext(),
							"Please fill all the blanks",
							Toast.LENGTH_SHORT)
							.show();
				}
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	public void CloseActivity(){
		finish();
	}
	
public class UploadUser extends AsyncTask<Users, Void, Void>{
		
		private Users user;
		private Context context;
		private int check;
		
		public void passContent(Context context){
			
			this.context=context;
			this.check=0;
			
		}
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
		}
		@Override
		protected Void doInBackground(Users... params) {
			user = params[0];
			
			HttpClient client=new DefaultHttpClient();
			HttpPost enviopost=new HttpPost("http://10.0.2.2:80/android_connect/upload_user_pdo.php"/*http://citecuvp.tij.uabc.mx/foro/crear_usuario_server.php"*/);
										//http://10.0.2.2:80/android_connect/upload_user_pdo.php
			try{
				List<NameValuePair> names=new ArrayList<NameValuePair>();
				names.add(new BasicNameValuePair("username", user.getUsername()));
				names.add(new BasicNameValuePair("password", user.getPassword()));
				names.add(new BasicNameValuePair("name", user.getName()));
				names.add(new BasicNameValuePair("lastname", user.getLastname()));
				names.add(new BasicNameValuePair("email", user.getEmail()));
				enviopost.setEntity(new UrlEncodedFormEntity(names,HTTP.UTF_8));
				try{
					/*HttpResponse response= */client.execute(enviopost);
				}catch(Exception e){
					e.printStackTrace();
					check=1;
					Toast.makeText(context, "Can't register this user", Toast.LENGTH_SHORT).show();
				}
				
			
			}
			catch(Exception e){
				
			}
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		protected void onPostExecute(Void result){
			super.onPostExecute(result);
			if(check==0){
				//this.activity.finish();
				CloseActivity();
			}
		}
		@Override
		protected void onProgressUpdate(Void... values){
			super.onProgressUpdate(values);
		}
		
	}

}
