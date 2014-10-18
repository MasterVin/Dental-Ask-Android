package beans;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Questions {

	private String idQuestions;
	private String question;
	private String idCategories;
	private String username;
	private String date;
	private String data = null;
	private Bitmap photo;
	//private String bitmapString;

	public Questions(String idQuestions, String question, String idCategories,
			String username, String date) {
		super();
		this.idQuestions = idQuestions;
		this.question = question;
		this.idCategories = idCategories;
		this.username = username;
		this.date = date;
		this.data = null;
		this.photo = null;
	}

	public Questions(String question, String idCategories, String username) {
		super();
		this.question = question;
		this.idCategories = idCategories;
		this.username = username;
	}

	public Questions(String idCategories, String username, Bitmap photo) {
		super();
		this.idCategories = idCategories;
		this.username = username;
		this.data = "";
		this.photo = photo;

	}

	public Questions(String idQuestions, String username) {
		super();
		this.idQuestions = idQuestions;
		this.username = username;
	}

	public Questions(String question) {
		super();
		this.question = question;
	}

	public String getIdQuestions() {
		return idQuestions;
	}

	public void setIdQuestions(String idQuestions) {
		this.idQuestions = idQuestions;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getIdCategories() {
		return idCategories;
	}

	public void setIdCategories(String idCategories) {
		this.idCategories = idCategories;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
		try {
			byte[] byteData = Base64.decode(data, Base64.DEFAULT);
			this.photo = BitmapFactory.decodeByteArray(byteData, 0,
					byteData.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Bitmap getPhoto() {
		return photo;
	}

	/*public void BitMapToString(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		this.bitmapString = Base64.encodeToString(b, Base64.DEFAULT);
		
	}*/

	@Override
	public String toString() {
		return "Questions [idQuestions=" + idQuestions + ", question="
				+ question + ", idCategories=" + idCategories + ", username="
				+ username + ", date=" + date + ", bitmapString="
				+ data + "]]";
	}

}
