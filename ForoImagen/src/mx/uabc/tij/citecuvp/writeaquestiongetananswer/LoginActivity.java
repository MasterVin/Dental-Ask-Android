package mx.uabc.tij.citecuvp.writeaquestiongetananswer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import beans.Users;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText editUsername;
	private EditText editPassword;
	private Button buttonRegister;
	private Button buttonLogin;
	private Users user;
	private Context context = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		editUsername =(EditText)findViewById(R.id.username_login);
		editPassword =(EditText)findViewById(R.id.password_login);
		buttonRegister =(Button)findViewById(R.id.register_button_login);
		buttonLogin =(Button)findViewById(R.id.login_button);
		
		context=this;
		
		buttonRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent=new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(intent);
				
				// TODO Auto-generated method stub
				
			}
		});
		
		buttonLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				user=new Users(editUsername.getText().toString(),
						editPassword.getText().toString());
				
				Login login=new Login();
				login.passContent(getApplicationContext());
				login.execute(user);
				//launchQuestionActivity();
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	public void launchQuestionActivity(){
		
		user=new Users(editUsername.getText().toString());
		Intent intent=new Intent(this, QuestionsActivity.class);
		intent.putExtra("username", user.getUsername());
		startActivity(intent);
		finish();
	}
	
	public void errorClose(){
		AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Error");
		builder1.setMessage("There is a problem with the server, please try again in another moment");
        builder1.setCancelable(false);
        builder1.setNeutralButton("Ok",
                new DialogInterface.OnClickListener() {
            @Override
			public void onClick(DialogInterface dialog, int id) {
            	finish();
            	//dialog.cancel();
            }
        });

        builder1.show();
        //AlertDialog alert11 = builder1.create();
        //alert11.show();
		//finish();
	}
	
	private class Login extends AsyncTask<Users, Void	, Integer>{

		private Context context;
		private InputStream is;
		private Users user;
		
		
		public void passContent(Context context){
			this.context=context;
			//this.actualTab=actualTab;
		}
		
		@Override
		protected void onPreExecute(){
			
		}
		
		@Override
		protected Integer doInBackground(Users... params) {
			
			user=params[0];
			int number=0;
			
			String result="failed";
			//Crear la conexion HTTP
			HttpClient cliente=new DefaultHttpClient();
			HttpGet petitionGet=new HttpGet("http://citecuvp.tij.uabc.mx/foro/login_user_server.php");
			
			try{
				HttpResponse response=cliente.execute(petitionGet);
				HttpEntity content=response.getEntity();
				is=content.getContent();	
			
			
			BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
			StringBuilder sb=new StringBuilder();
			String linea=null;
			
			try{
				while((linea=bufferedReader.readLine())!=null){
					sb.append(linea);
				}
			}catch (IOException e){
				e.printStackTrace();
			}
			try{
				is.close();
			}catch(IOException e){
				e.printStackTrace();
			}
			
			result=sb.toString();
			
			try{
				JSONArray jsonArray=new JSONArray(result);
				
				for(int i=0;i<jsonArray.length();i++){
					JSONObject jsonObject=jsonArray.getJSONObject(i);
					
					if(jsonObject.getString("username").equals(user.getUsername())){
						if(jsonObject.getString("password").equals(user.getPassword())){
							number=2;
							break;
						}else{
							number=1;
							break;
						}
					}
				}
				
			}catch(JSONException e){
				e.printStackTrace();
			}
			}catch(ClientProtocolException e){
				e.printStackTrace();
			}catch(IOException e){
				number=3;
				//e.printStackTrace();
			}
			return number;
			// TODO Auto-generated method stub
		}
		
		@Override
		protected void onPostExecute(Integer result){
			switch(result){
			case 0: Toast.makeText(context, "This user is not registered", Toast.LENGTH_SHORT).show();
					break;
			case 1: Toast.makeText(context, "Your password is incorrect", Toast.LENGTH_SHORT).show();
					break;
			case 2: Toast.makeText(context, "User confirmed", Toast.LENGTH_SHORT).show();
					launchQuestionActivity();
					break;
			case 3: errorClose();
					break;
			}
		}
		
		@Override
		protected void onProgressUpdate(Void... values){
			
		}
	}
}
