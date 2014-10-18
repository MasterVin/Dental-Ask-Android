package classes;

import java.util.ArrayList;

import mx.uabc.tij.citecuvp.writeaquestiongetananswer.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import beans.Answers;


public abstract class AdapterAnswers extends BaseAdapter{
	
	private final Context context;
	private final ArrayList<Answers> respuestas;
	
	public AdapterAnswers(Context context, ArrayList<Answers> respuestas) {
		super();
		this.context = context;
		this.respuestas = respuestas;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.respuestas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.respuestas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View rowView=convertView;
		if(convertView==null){
			//Create a new view into the list
			LayoutInflater inflater=(LayoutInflater)context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView=inflater.inflate(R.layout.layout_elements,null/*parent*/,true);
		}
		onEntrada(respuestas.get(position), rowView);
		//set data into the view
		/*textPreguntas=(TextView)rowView.findViewById(R.id.title);
		textIdUsuario=(TextView)rowView.findViewById(R.id.user);
		textIdPregunta=(TextView)rowView.findViewById(R.id.idpregunta);
		
		Preguntas pregunta=this.preguntas.get(position);
		textPreguntas.setText(pregunta.getPregunta());
		textIdUsuario.setText(pregunta.getIdUsuarios());
		textIdPregunta.setText(pregunta.getIdPreguntas());
*/		// TODO Auto-generated method stub
		return rowView;
	}
	
	public abstract void onEntrada (Object entrada, View view);

}
