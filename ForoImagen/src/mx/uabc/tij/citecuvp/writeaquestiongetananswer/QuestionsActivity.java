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

import beans.Questions;
import beans.Users;
import classes.Adapter;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class QuestionsActivity extends TabActivity {

	private ListView listTabImplant;
	/*private ListView listTabPatients;
	private ListView listTabStudents;*/
	private ListView listTabEndodontics;
	private ListView listTabCosmetic;
	private ListView listTabOrthodontics;
	private ListView listTabPeriodontics;
	private ListView listTabRestoration;
	private ListView listTabOralSurgery;
	private ListView actuallist;
	private Button questionButton;
	private Button pictureButton;
	private ImageButton refreshButton;
	private EditText questionText;
	private TabHost tabHost;
	private int actualTab = 1;

	private Users user;
	private Questions quest = null;

	private static int RESULT_LOAD_IMAGE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questions_layout);

		Bundle extras = getIntent().getExtras();
		user = new Users(extras.getString("username"));

		listTabImplant = (ListView) findViewById(R.id.list_implant);
		/*listTabPatients = (ListView) findViewById(R.id.list_patients);
		listTabStudents = (ListView) findViewById(R.id.list_students);*/
		listTabEndodontics = (ListView) findViewById(R.id.list_endodontics);
		listTabCosmetic = (ListView) findViewById(R.id.list_cosmetic);
		listTabOrthodontics = (ListView) findViewById(R.id.list_orthodontics);
		listTabPeriodontics = (ListView) findViewById(R.id.list_periodontics);
		listTabRestoration = (ListView) findViewById(R.id.list_restoration);
		listTabOralSurgery = (ListView) findViewById(R.id.list_oral_surgery);

		switch (actualTab) {
		case 1:
			actuallist = listTabImplant;
			break;
		case 2:
			actuallist = listTabEndodontics;
			break;
		case 3:
			actuallist = listTabCosmetic;
			break;
		case 4:
			actuallist = listTabOrthodontics;
			break;
		case 5:
			actuallist = listTabPeriodontics;
			break;
		case 6:
			actuallist = listTabRestoration;
			break;
		case 7:
			actuallist = listTabOralSurgery;
			break;
		}

		questionButton = (Button) findViewById(R.id.buttonQuestion);
		pictureButton = (Button) findViewById(R.id.buttonPicture);
		questionText = (EditText) findViewById(R.id.writeQuestion);
		// questionText.setText(null);
		// System.out.println(questionText.getText().toString().length());
		refreshButton = (ImageButton) findViewById(R.id.buttonRefresh);

		DownloadList downloadList = new DownloadList();
		downloadList.cargarContenido(getApplicationContext(), actualTab);
		downloadList.execute(actuallist);

		tabHost = getTabHost();

		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Implant", null)
				.setContent(R.id.list_implant));
		/*tabHost.addTab(tabHost.newTabSpec("tab2")
				.setIndicator("Patients", null).setContent(R.id.list_patients));
		tabHost.addTab(tabHost.newTabSpec("tab3")
				.setIndicator("Students", null).setContent(R.id.list_students));*/
		tabHost.addTab(tabHost.newTabSpec("tab2")
				.setIndicator("Endodontics", null)
				.setContent(R.id.list_endodontics));
		tabHost.addTab(tabHost.newTabSpec("tab3")
				.setIndicator("Cosmetic", null).setContent(R.id.list_cosmetic));
		tabHost.addTab(tabHost.newTabSpec("tab4")
				.setIndicator("Orthodontics", null)
				.setContent(R.id.list_orthodontics));
		tabHost.addTab(tabHost.newTabSpec("tab5")
				.setIndicator("Periodontics", null)
				.setContent(R.id.list_periodontics));
		tabHost.addTab(tabHost.newTabSpec("tab6")
				.setIndicator("Restoration", null)
				.setContent(R.id.list_restoration));
		tabHost.addTab(tabHost.newTabSpec("tab7").setIndicator("Oral Surgery", null)
				.setContent(R.id.list_oral_surgery));

		tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				// System.out.println(actualTab);
				quest = null;
				if (tabId.equals("tab1")) {
					setActualTab(1);
					setActualList(1);
				} else if (tabId.equals("tab2")) {
					setActualTab(2);
					setActualList(2);
				} else if (tabId.equals("tab3")) {
					setActualTab(3);
					setActualList(3);
				} else if (tabId.equals("tab4")) {
					setActualTab(4);
					setActualList(4);
				} else if (tabId.equals("tab5")) {
					setActualTab(5);
					setActualList(5);
				} else if (tabId.equals("tab6")) {
					setActualTab(6);
					setActualList(6);
				} else if (tabId.equals("tab7")) {
					setActualTab(7);
					setActualList(7);
				}

				// System.out.println(actuallist);
				DownloadList downloadList = new DownloadList();
				downloadList
						.cargarContenido(getApplicationContext(), actualTab);
				downloadList.execute(actuallist);
			}
		});

		questionButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// System.out.println(questionText.getText().toString()!=null);
				if (questionText.getText().toString().length() != 0) {
					if (quest == null) {
						quest = new Questions(
								questionText.getText().toString(), Integer
										.toString(actualTab), user
										.getUsername());
					} else {
						quest.setQuestion(questionText.getText().toString());
					}

					UploadQuestions uploadQuestions = new UploadQuestions();
					uploadQuestions.passContent(user, getApplicationContext());
					uploadQuestions.execute(quest);

				} else {
					Toast.makeText(getApplicationContext(),
							"Please insert someting in the field",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		pictureButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
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

		actuallist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				launchAnswerActivity(parent, view, position, id);
				// TODO Auto-generated method stub

			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			//Bitmap bm = scaleDownBitmap(BitmapFactory.decodeFile(picturePath), 100, 
					//this.getApplicationContext());
			Bitmap bm = BitmapFactory.decodeFile(picturePath);
			

			quest = new Questions(Integer.toString(actualTab),
					user.getUsername(), bm);
			// Convertimos al imagen a un array de bytes
			/*
			 * ByteArrayOutputStream stream = new ByteArrayOutputStream();
			 * bm.compress(Bitmap.CompressFormat.JPEG, 100, stream); byte[]
			 * byteArray = stream.toByteArray();
			 */
		}

	}

	public static Bitmap scaleDownBitmap(Bitmap photo, int newWidth,
			Context context) {

		final float densityMultiplier = context.getResources()
				.getDisplayMetrics().density;

		int w = (int) (newWidth * densityMultiplier);
		int h = (int) (w * photo.getHeight() / ((double) photo.getWidth()));

		photo = Bitmap.createScaledBitmap(photo, w, h, true);

		return photo;
	}

	public void launchAnswerActivity(AdapterView<?> parent, View view,
			int position, long id) {

		String item = parent.getItemAtPosition(position).toString();
		// System.out.println(item);

		/*
		 * una solucion para el problema de la obtencion de datos de la lista de
		 * respuestas es crear un array con los objetos obtenidos de json (los
		 * mismos que se usan para crear la lista) y obtener los datos con el
		 * parametro position.
		 */
		Intent intent = new Intent(this, AnswersActivity.class);
		intent.putExtra("valores", item);
		intent.putExtra("username", user.getUsername());
		startActivity(intent);
	}

	public void onRestart(Users user) {

		super.onRestart();
		Intent i = new Intent(QuestionsActivity.this, QuestionsActivity.class); // your
																				// class
		i.putExtra("username", user.getUsername());
		startActivity(i);
		finish();

	}

	public void setActualTab(int actualTab) {
		this.actualTab = actualTab;
	}

	public void setActualList(int actualTab) {
		switch (actualTab) {
		case 1:
			actuallist = listTabImplant;
			break;
		case 2:
			actuallist = listTabEndodontics;
			break;
		case 3:
			actuallist = listTabCosmetic;
			break;
		case 4:
			actuallist = listTabOrthodontics;
			break;
		case 5:
			actuallist = listTabPeriodontics;
			break;
		case 6:
			actuallist = listTabRestoration;
			break;
		case 7:
			actuallist = listTabOralSurgery;
			break;
		}
		actuallist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				launchAnswerActivity(parent, view, position, id);
				// TODO Auto-generated method stub

			}
		});
	}

	static class DownloadList extends
			AsyncTask<ListView, Void, ArrayList<Questions>/* Adapter *//*
																	 * ArrayAdapter<
																	 * Preguntas
																	 * >
																	 */> {

		private Context context;
		private ListView list;
		private InputStream is;
		private ArrayList<Questions> questionsList = new ArrayList<Questions>();
		private int actualTab;

		public void cargarContenido(Context context, int actualTab) {
			this.context = context;
			this.actualTab = actualTab;
		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected ArrayList<Questions> doInBackground(ListView... params) {

			list = params[0];
			// String resultado = "failed";
			Questions quest;
			// Crear la conexion HTTP
			HttpClient cliente = new DefaultHttpClient();
			HttpGet peticionGet = new HttpGet(
					"http://citecuvp.tij.uabc.mx/foro/download_questions_server_pdo.php?idCategories="
							+ actualTab);

			try {
				HttpResponse response = (HttpResponse) cliente
						.execute(peticionGet);
				HttpEntity content = response.getEntity();
				BufferedHttpEntity buffer = new BufferedHttpEntity(content);
				is = buffer.getContent();
				// is=content.getContent();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			String aux = "";

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(is));
			// StringBuilder sb=new StringBuilder();
			String line = null;

			try {
				while ((line = bufferedReader.readLine()) != null) {
					// sb.append(line);
					aux += line;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				is.close();
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// resultado=sb.toString();

			try {
				JSONObject json = new JSONObject(aux);
				JSONArray jsonArray = json.getJSONArray("questions");

				for (int i = jsonArray.length() - 1; i >= 0; i--) {
					// int i = 0; i < jsonArray.length(); i++
					JSONObject jsonObject = jsonArray.getJSONObject(i);

					// System.out.println(jsonObject.getString("foto"));
					quest = new Questions(jsonObject.getString("idQuestions"),
							jsonObject.getString("question"),
							jsonObject.getString("idCategories"),
							jsonObject.getString("username"),
							jsonObject.getString("date"));
					if (jsonObject.getString("foto") != ""
							&& jsonObject.getString("foto") != null) {
						quest.setData(jsonObject.getString("foto"));
					}
					// System.out.println(jsonObject.getString("fecha"));
					questionsList.add(quest);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return questionsList;
			// TODO Auto-generated method stub
		}

		@Override
		protected void onPostExecute(ArrayList<Questions>/*
														 * Adapter/*ArrayAdapter<
														 * Preguntas>
														 */result) {
			list.setAdapter(new Adapter(this.context, result) {

				@Override
				public void onEntrada(Object entrada, View view) {
					// TODO Auto-generated method stub
					TextView titulo = (TextView) view.findViewById(R.id.title);
					titulo.setText(((Questions) entrada).getQuestion());

					TextView idpregunta = (TextView) view
							.findViewById(R.id.idpregunta);
					idpregunta.setText(((Questions) entrada).getIdQuestions());

					TextView usuario = (TextView) view.findViewById(R.id.user);
					usuario.setText(((Questions) entrada).getUsername());

					TextView fecha = (TextView) view.findViewById(R.id.date);
					fecha.setText(((Questions) entrada).getDate());

					if (((Questions) entrada).getData() != "") {
						ImageView imagen = (ImageView) view
								.findViewById(R.id.image);
						imagen.setImageBitmap(((Questions) entrada).getPhoto());
					}

				}
			});
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show();
		}
	}

	private class UploadQuestions extends AsyncTask<Questions, Void, Void> {

		private Questions quest;
		private Users user;
		private Context context;

		public void passContent(Users user, Context context) {
			this.user = user;
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Questions... params) {
			quest = params[0];

			HttpClient client = new DefaultHttpClient();
			HttpPost enviopost = new HttpPost(
					"http://citecuvp.tij.uabc.mx/foro/upload_questions_server2.php");
			if (quest.getData() == "" || quest.getData() != null) {
				enviopost = new HttpPost(
						"http://citecuvp.tij.uabc.mx/foro/upload_questions_server2.php");
			}

			/*
			 * System.out.println(pregunta.getIdCategorias());
			 * System.out.println(pregunta.getPregunta());
			 * System.out.println(pregunta.getIdUsuarios());
			 */
			try {

				ByteArrayOutputStream stream = new ByteArrayOutputStream();

				if (quest.getData() == "" || quest.getData() != null) {
					quest.getPhoto().compress(Bitmap.CompressFormat.JPEG, 100,
							stream);
				}
				byte[] byteArray = stream.toByteArray();
				List<NameValuePair> nombres = new ArrayList<NameValuePair>();
				nombres.add(new BasicNameValuePair("question", quest
						.getQuestion()/*
									 * NSERT INTO preguntas(pregunta,
									 * idUsuarios, idCategorias)
									 * VALUES('saaappp', '3', '1')"
									 */));
				nombres.add(new BasicNameValuePair("username", quest
						.getUsername()));
				nombres.add(new BasicNameValuePair("idCategories", quest
						.getIdCategories()));
				nombres.add(new BasicNameValuePair("photo", Base64
						.encodeToString(byteArray, Base64.DEFAULT)));
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
			Toast.makeText(context, "Updating the list...", Toast.LENGTH_SHORT)
					.show();
			onRestart(user);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

	}

}
