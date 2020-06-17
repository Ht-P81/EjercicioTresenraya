package com.example.ejerciciotresenraya;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //dentro de este método que es el primero que se ejecuta vamos a
        //identificar cada una de las casillas y las guardamos mediante el array
        CASILLAS = new int[9];

        //Las casillas las tenemos identificadas en el activity_main (Mirar)
        CASILLAS[0] = R.id.a1;
        CASILLAS[1] = R.id.a2;
        CASILLAS[2] = R.id.a3;
        CASILLAS[3] = R.id.b1;
        CASILLAS[4] = R.id.b2;
        CASILLAS[5] = R.id.b3;
        CASILLAS[6] = R.id.c1;
        CASILLAS[7] = R.id.c2;
        CASILLAS[8] = R.id.c3;
    }

    //creamos el método aJugar
    public void aJugar (View vista){
        //lo primero que hará este método es resetear el tablero.
        ImageView imagen;

        //recorre el array casillas elemento a elemento, y a cada elemento
        //del array lo identifica cada casilla como una imagen, y almaceno
        //el id de cada casilla dentro de este imagen y le asigne la casilla
        //en blanco que representa la imagen casilla. Así limpia el tablero.
        for(int cadaCasilla:CASILLAS){
            imagen = (ImageView)findViewById(cadaCasilla);
            imagen.setImageResource(R.drawable.casilla);
        }

        //Vamos a establecer si será uno o dos jugadores los que jugarán la partida.
        //los dos botones (1 player o 2 players) son vistas que le pasamos al método aJugar por parámetros
        jugadores = 1;
        if(vista.getId() == R.id.dosjug){
            //la variable jugadores se reescribirá (pasará de 1 a 2) si la condición del if se cumple
            jugadores = 2;
        }

        //Vamos a crear ahora los niveles de dificultad, para ello vamos a identificar los RadioButton
        //apuntamos al nombre del recurso ID que tenemos puesta en el xml del grupo de radio buton
        RadioGroup configDificultad = (RadioGroup) findViewById(R.id.configD);

        //almacenamos el id del elemento seleccionado en la variable creada mediante el metodo getCheckedRadioButton
        int id = configDificultad.getCheckedRadioButtonId();

        int dificultad = 0; //será nivel fácil (el primero)

        if(id==R.id.normal){ //será nivel normal (segundo)
            dificultad=1;
        } else if (id==R.id.imposible){
            dificultad=2;
        }

        //además del Main Activity habrá otra clase que se llame empezar Partida al que le podamos pasar al constructor
        //de esa clase el parámetro dificultad
        partida = new Partida (dificultad);

        //una vez que se empiece la partida se inhabilita los botones para que no estén disponibles y no interrumpa
        // la partida.

        //boton de un jugador.
        ((Button) findViewById(R.id.unjug)).setEnabled(false);

        //Los radio group se consiguen inhabilitar con el metodo setAlpha
        ((RadioGroup)findViewById(R.id.configD)).setAlpha(0);

        //boton de 2 jugdores.
        ((Button) findViewById(R.id.dosjug)).setEnabled(false);
    }

    //Vamos a crear el método Toque para que la casilla correspondiente sepa lo que tiene que hacer
    //Para que sepa en cual de las casillas hemos pulsado lo hacemos mediante vistas (la casilla)
    public void toque(View mivista){

        if(partida == null){
           //decimos que si partida es igual a nula, un return y se sale sin ejecutar el resto del
            //codigo que hay en el métdo.
            return;
        }
        //creamos esta variable local para identificar con un entero la posición del array
        int casilla=0;

        //sabemos que el array CASILLAS tiene 9 posiciones por tanto 8 posiciones (0 al 8)
        for (int i = 0; i<CASILLAS.length; i++){
            //se recorre hasta que la posicion de i coincida con el Id
            //por ejemplo si pulsamos en la casilla central del tablero:
            if(CASILLAS [i] == mivista.getId()){
                casilla = i; //obtiene el número de la posicion del array.
                break;
            }
        }

        //creamos dentro de este método toque una notificacion toast
        /*Toast toast = Toast.makeText(this, "Has pulsado la casilla " +  casilla, Toast.LENGTH_SHORT);
        //aquí le decimos donde queremos que se muestre
        toast.setGravity(Gravity.CENTER, 0,0);
        //con esta instrucción es para que se muestre
        toast.show();*/

        //Aquí vamos a comprobar si la casilla está o no está ocupada
        if(partida.comprueba_casilla(casilla) == false){
            return;
        }

        //cuando ya sabemos que casilla hemos pulsado, invocamos el método marca y le pasamos por parámetro
        //la casilla que se ha marcado, la respuesta a nuestra pulsación.
        marca(casilla);

        //aquí invocamos el método turno de la clase partida (previamente instanciada arriba)
        //con esto debería de, al elegir una casilla con un circulo, responder la maquina con un aspa en otra casilla.
        int resultado = partida.turno();

        if(resultado>0){
            termina(resultado);
            return;
        }

        //con esta simple instrucción hace que pueda jugar el otro jugador, sin hacer nada más.
        if(jugadores ==2){
            return;
        }
        //para que sea el programa el que elija la casilla sería:
        //llamamos el método ia (inteligencia articial) mediante el objeto instanciado arriba de la clase partida.
        //Este método devuelve un numero entero int que lo podemos guardar en la variable de tipo int casilla.
        casilla=partida.ia();

        //vamos a decirlque a la maquina que compruebe si la casilla estaba marcada
        //y si estaba marcada que elija otra
        //mientras que comprueba_casilla de casilla no es verdad, genera otra casilla
        while(partida.comprueba_casilla(casilla)!=true){
            //si no es verdad genera otra casilla
            casilla=partida.ia();
        }

        //una vez que ya ha elegido, llamamos de nuevo a marca(casilla); y lo dibujaría
        marca(casilla);

        //aquí debemos cambiar nuevamente el turno para que no se inviertan los dibujos.
        resultado = partida.turno();

        if(resultado>0){
            termina(resultado);
        }
    }

    private void termina(int resultadometodo){
        String mensaje;

        if(resultadometodo==1){
            mensaje = "Ganan los círculos, jugador 1";
        }else if (resultadometodo==2){
            mensaje = "Ganan las aspas";
        }else mensaje = "Empate";

        //mensaje que muestra quien gana
        Toast toast = Toast.makeText(this,  mensaje, Toast.LENGTH_SHORT);
        //aquí le decimos donde queremos que se muestre
        toast.setGravity(Gravity.CENTER, 0,0);
        //con esta instrucción es para que se muestre
        toast.show();

        partida = null;

        //Volvemos a poner disponibles los botones cuando acaba la partida
        ((Button)findViewById(R.id.unjug)).setEnabled(true);
        ((RadioGroup)findViewById(R.id.configD)).setAlpha(1);
        ((Button)findViewById(R.id.dosjug)).setEnabled(true);
    }

    //creamos método marcar y le pasamos por parámetro la casilla que se ha dado el toque
    private void marca(int casilla){
        //creamos un ImageView pq recordemos que las aspas y los círculos los tenemos guardadas como imágenes.
        ImageView imagen;

        //esta imagen le estamos diciendo que nos busque el id de la imagen que hay en la casilla, la imagen en blanco
        imagen= (ImageView) findViewById(CASILLAS[casilla]);

        //ahora hay que decirle que cambie la que está en blanco por un aspa o un circulo lo haremos con un condicional
        if(partida.jugador==1){

            //aquí circulo
            imagen.setImageResource(R.drawable.circulo);
        } else{
            //aquí el aspa
            imagen.setImageResource(R.drawable.aspa);
        }

    }

    //variables que vamos a utilizar
    private int jugadores;

    private int [] CASILLAS;

    private Partida partida;
}
