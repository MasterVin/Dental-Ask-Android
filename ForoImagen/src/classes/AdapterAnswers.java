package classes;

import java.util.ArrayList;

import mx.uabc.tij.citecuvp.writeaquestiongetananswer.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
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
		final ViewHolder viewHolder;
		//View rowView=convertView;
		if(convertView==null){
			//Create a new view into the list
			LayoutInflater inflater=(LayoutInflater)context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=inflater.inflate(R.layout.layout_elements,parent,false);
			viewHolder = new ViewHolder();
			
			viewHolder.titulo = (TextView) convertView.findViewById(R.id.title);

			viewHolder.idpregunta = (TextView) convertView.findViewById(R.id.idpregunta);
			

			viewHolder.usuario = (TextView) convertView.findViewById(R.id.user);
			

			viewHolder.fecha = (TextView) convertView.findViewById(R.id.date);
			
				viewHolder.imagen = (ImageView) convertView.findViewById(R.id.image);
				
				convertView.setTag(viewHolder);
		}
		else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Answers answer = this.respuestas.get(position);
		
		viewHolder.titulo.setText(answer.getAnswer());

		viewHolder.idpregunta.setText(answer.getIdAnswers());
		
		viewHolder.usuario.setText(answer.getUsername());

		viewHolder.fecha.setText(answer.getDate());
		
		if(answer.getData()!=""){
			viewHolder.imagen.setImageBitmap(answer.getPhoto());
		}
		/*onEntrada(respuestas.get(position), rowView);
		//set data into the view
		/*textPreguntas=(TextView)rowView.findViewById(R.id.title);
		textIdUsuario=(TextView)rowView.findViewById(R.id.user);
		textIdPregunta=(TextView)rowView.findViewById(R.id.idpregunta);
		
		Preguntas pregunta=this.preguntas.get(position);
		textPreguntas.setText(pregunta.getPregunta());
		textIdUsuario.setText(pregunta.getIdUsuarios());
		textIdPregunta.setText(pregunta.getIdPreguntas());
*/		// TODO Auto-generated method stub
		return convertView;
	}
	
static class ViewHolder {
		
		public ImageView imagen;
		public TextView titulo;
		public TextView idpregunta;
		public TextView usuario;
		public TextView fecha;
		}
	
	//public abstract void onEntrada (Object entrada, View view);

}
