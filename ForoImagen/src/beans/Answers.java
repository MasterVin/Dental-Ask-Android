package beans;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Answers {

	private String idAnswers;
	private String answer;
	private String idQuestions;
	private String username;
	private String date;
	private String data = null;
	private Bitmap photo;
	
	public Answers(String idAnswers, String answer, String idQuestions,
			String username, String date) {
		super();
		this.idAnswers = idAnswers;
		this.answer = answer;
		this.idQuestions = idQuestions;
		this.username = username;
		this.date = date;
		this.data = null;
		this.photo = null;
	}

	public Answers(String answer, String idQuestions, String username) {
		super();
		this.answer = answer;
		this.idQuestions = idQuestions;
		this.username = username;
	}
	
	public Answers(String idQuestions, String username, Bitmap photo){
		super();
		this.idQuestions = idQuestions;
		this.username = username;
		this.data = "";
		this.photo = photo;
		
	}

	public String getIdAnswers() {
		return idAnswers;
	}

	public void setIdAnswers(String idAnswers) {
		this.idAnswers = idAnswers;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getIdQuestions() {
		return idQuestions;
	}

	public void setIdQuestions(String idQuestions) {
		this.idQuestions = idQuestions;
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
		      this.photo = BitmapFactory.decodeByteArray( byteData, 0, byteData.length);
		    }
		    catch(Exception e) {
		      e.printStackTrace();
		    }
	}

	public Bitmap getPhoto() {
		return photo;
	}

	@Override
	public String toString() {
		return "Answers [idAnswers=" + idAnswers + ", answer=" + answer
				+ ", idQuestions=" + idQuestions + ", username=" + username
				+ ", date=" + date + "]";
	}
	
	
}
