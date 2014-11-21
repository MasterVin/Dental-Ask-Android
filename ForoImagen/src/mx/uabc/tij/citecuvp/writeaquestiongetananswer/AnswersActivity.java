package mx.uabc.tij.citecuvp.writeaquestiongetananswer;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import beans.Answers;
import beans.Questions;
import beans.Users;
import classes.AdapterAnswers;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.MediaColumns;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AnswersActivity extends Activity {

	private ListView actuallist;
	private Button answerButton;
	private Button pictureButton;
	private ImageButton refreshButton;
	private TextView textUsername;
	private TextView textDate;
	private TextView textQuestion;
	private TextView textIdPregunta;
	private EditText answerText;
	private ImageView textPicture;
	private Users user;
	private Questions quest;
	private Answers answer=null;
	private String item;

	private static int RESULT_LOAD_IMAGE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answers_layout);

		Bundle extras = getIntent().getExtras();
		//user = new Users(extras.getString("username"));
		
		user=new Users(extras.getString("username"));
		item=extras.getString("valores");
		//System.out.println(extras.getString("valores"));
		int index1=item.indexOf("=")+1;
		int index2=item.indexOf(",");
		String idQuestions=item.substring(index1, index2);
		int index3=item.indexOf("=",index2+1)+1;
		int index4=item.indexOf(", idCategories",index2+1);
		String question=item.substring(index3, index4);
		int index5=item.indexOf("date=")+5;
		int index6=item.indexOf(",", index5+1);
		String date=item.substring(index5, index6);
		int index7=item.indexOf("name=")+5;
		int index8=item.indexOf(",", index7+1);
		String user2=item.substring(index7,index8);
		int index9=item.indexOf("bitmapString=")+13;
		int index10=item.indexOf("]");
		String bitmapString=item.substring(index9, index10);
		
		
		//System.out.println(item);
		
		
		
		
		textDate=(TextView)findViewById(R.id.date2);
		textIdPregunta=(TextView)findViewById(R.id.idpregunta2);
		textQuestion=(TextView)findViewById(R.id.title2);
		textUsername=(TextView)findViewById(R.id.user2);
		textPicture=(ImageView)findViewById(R.id.image2);
		
		textDate.setText(date);
		textIdPregunta.setText(idQuestions);
		textQuestion.setText(question);
		textUsername.setText(user2);
		try {
			byte[] byteData = Base64.decode(bitmapString, Base64.DEFAULT);
			textPicture.setImageBitmap(BitmapFactory.decodeByteArray(byteData, 0,
					byteData.length));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		quest=new Questions(idQuestions, user.getUsername());
		
		/*System.out.println(idQuestions);
		System.out.println(question);
		System.out.println(date);
		System.out.println(extras.getString("username"));
		System.out.println(extras.getString("valores"));
		*/
		actuallist = (ListView) findViewById(android.R.id.list);


		answerButton = (Button) findViewById(R.id.buttonAnswer);
		pictureButton=(Button)findViewById(R.id.buttonPicture2);
		answerText = (EditText) findViewById(R.id.writeAnswer);
		
		refreshButton=(ImageButton)findViewById(R.id.buttonRefresh2);

		DownloadList downloadList = new DownloadList();
		downloadList.cargarContenido(getApplicationContext(), quest);
		downloadList.execute(actuallist);
		

		
		answerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if(answerText.getText().toString().length()!=0){
					//System.out.println(false);
					if(answer==null){
						//System.out.println("normal");
						answer = new Answers(answerText.getText()
								.toString(), quest.getIdQuestions(), user
								.getUsername());
					}
					else{
						//System.out.println("foto");
						answer.setAnswer(answerText.getText().toString());
					}
				
					UploadAnswers uploadAnswers=new UploadAnswers();
					uploadAnswers.passContent(user, getApplicationContext());
					uploadAnswers.execute(answer);
				
				}
				else{
					Toast.makeText(getApplicationContext(), "Please insert someting in the field", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		pictureButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                 
                startActivityForResult(i, RESULT_LOAD_IMAGE);
				
			}
		});
		
		refreshButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				onRestart(user);
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaColumns.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			Bitmap bm = BitmapFactory.decodeFile(picturePath);

			answer = new Answers(quest.getIdQuestions(),
					user.getUsername(), bm);
			// Convertimos al imagen a un array de bytes
			/*
			 * ByteArrayOutputStream stream = new ByteArrayOutputStream();
			 * bm.compress(Bitmap.CompressFormat.JPEG, 100, stream); byte[]
			 * byteArray = stream.toByteArray();
			 */
		}

	}
	
	/*public Bitmap StringToBitMap(String encodedString){
	     try{
	       byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
	       Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
	       //picture=(ImageView)findViewById(R.id.image2);
	       //picture.setImageBitmap(bitmap);
	       return bitmap;
	     }catch(Exception e){
	       e.getMessage();
	       return null;
	     }
	      }*/
	
	public void onRestart(Users user){
		
		super.onRestart();
	    Intent i = new Intent(AnswersActivity.this, AnswersActivity.class);  //your class
	    i.putExtra("username", user.getUsername());
	    i.putExtra("valores", item);
	    startActivity(i);
	    finish();
	    
	}
	

	

	static class DownloadList extends AsyncTask<ListView, Void, ArrayList<Answers>> {

		private Context context;
		private ListView list;
		private InputStream is;
		private ArrayList<Answers> answerList = new ArrayList<Answers>();
		private Questions quest;

		public void cargarContenido(Context context, Questions quest) {
			this.context = context;
			this.quest=quest;
		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected ArrayList<Answers> doInBackground(ListView... params) {

			list = params[0];
			//String resultado = "failed";
			Answers answer;
			// Crear la conexion HTTP
			HttpClient cliente = new DefaultHttpClient();
			HttpGet peticionGet = new HttpGet(
					"http://citecuvp.tij.uabc.mx/foro/download_answers_server_pdo.php?idQuestions="
							+ quest.getIdQuestions());
			

			try{
				HttpResponse response=cliente.execute(peticionGet);
				HttpEntity content=response.getEntity();
				BufferedHttpEntity buffer = new BufferedHttpEntity(content);
			    is = buffer.getContent();
				//is=content.getContent();
			}catch(ClientProtocolException e){
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}

			String aux="";
			BufferedReader bufferedLector = new BufferedReader(
					new InputStreamReader(is));
			//StringBuilder sb = new StringBuilder();
			String line = null;

			try {
				while ((line = bufferedLector.readLine()) != null) {
					//sb.append(line);
					aux+=line;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				is.close();
				bufferedLector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			//resultado = sb.toString();

			try {
				JSONObject json=new JSONObject(aux);
				JSONArray jsonArray = json.getJSONArray("answers");

				for (int i = jsonArray.length()-1; i >= 0; i--) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					answer = new Answers(jsonObject.getString("idAnswers"),
							jsonObject.getString("answer"),
							jsonObject.getString("idQuestions"),
							jsonObject.getString("username"),
							jsonObject.getString("date"));
					answer.setData(jsonObject.getString("photo"));
					// System.out.println(jsonObject.getString("fecha"));
					answerList.add(answer);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return answerList;
			// TODO Auto-generated method stub
		}

		@Override
		protected void onPostExecute(ArrayList<Answers>result) {
			list.setAdapter(new AdapterAnswers(this.context, result) {

				@Override
				public void onEntrada(Object entrada, View view) {
					// TODO Auto-generated method stub
					TextView titulo = (TextView) view.findViewById(R.id.title);
					titulo.setText(((Answers) entrada).getAnswer());

					TextView idpregunta = (TextView) view
							.findViewById(R.id.idpregunta);
					idpregunta.setText(((Answers) entrada).getIdAnswers());

					TextView usuario = (TextView) view.findViewById(R.id.user);
					usuario.setText(((Answers) entrada).getUsername());

					TextView fecha = (TextView) view.findViewById(R.id.date);
					fecha.setText(((Answers) entrada).getDate());
					
					if(((Answers) entrada).getData()!=""){
						ImageView imagen = (ImageView) view.findViewById(R.id.image);
						imagen.setImageBitmap(((Answers) entrada).getPhoto());
					}
				}
			});
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			Toast.makeText(context, "Obtaining data", Toast.LENGTH_SHORT).show();
		}
	}

	private class UploadAnswers extends AsyncTask<Answers, Void, Void> {

		//private Questions quest;
		private Users user;
		private Answers answer;
		private Context context;

		public void passContent(Users user, Context context) {
			this.user = user;
			this.context=context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Answers... params) {
			answer = params[0];

			HttpClient client = new DefaultHttpClient();
			HttpPost enviopost = new HttpPost(
					"http://citecuvp.tij.uabc.mx/foro/upload_answers_server.php");
			//System.out.println(answer.getData()==""||answer.getData()!=null);
			if(answer.getData()==""||answer.getData()!=null){
				enviopost = new HttpPost(
						"http://citecuvp.tij.uabc.mx/foro/upload_answers_server2.php");
			}

		
			try {
				
				ByteArrayOutputStream stream = new ByteArrayOutputStream();	
				
				if(answer.getData()==""||answer.getData()!=null){
	            answer.getPhoto().compress(Bitmap.CompressFormat.JPEG, 100, stream);
				}
				byte[] byteArray = stream.toByteArray();
				List<NameValuePair> nombres = new ArrayList<NameValuePair>();
				nombres.add(new BasicNameValuePair("answer", answer
						.getAnswer()));
				nombres.add(new BasicNameValuePair("username", answer
						.getUsername()));
				nombres.add(new BasicNameValuePair("idQuestions", answer
						.getIdQuestions()));
				nombres.add(new BasicNameValuePair("photo", Base64.encodeToString(byteArray, 
						Base64.DEFAULT)));
				enviopost.setEntity(new UrlEncodedFormEntity(nombres,
						HTTP.UTF_8));
				/* HttpResponse response= */client.execute(enviopost);

				stream.close();
			}

			catch (Exception e) {
				e.printStackTrace();
			}
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Toast.makeText(context, "Updating the list...", Toast.LENGTH_SHORT).show();
			onRestart(user);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

	}

}
