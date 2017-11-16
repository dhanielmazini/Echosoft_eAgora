package com.eagora.echosoft.eagora;

import android.net.Uri;

import com.eagora.echosoft.eagora.BancoDados.TipoViagemGenerico;
import com.eagora.echosoft.eagora.Maps.Coordenada;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junior on 02/11/17.
 */

public class GlobalAccess {
    public static String nomeUsuario;
    public static String emailUsuario;
    public static  List<String> perfilUsuario;
    public static Coordenada coordenadaUsuario;
    public static Uri userfoto;
    public static Coordenada coordenadaLocalViagem;
    public static List<TipoViagemGenerico> listaLugares = new ArrayList<TipoViagemGenerico>();
    public static List<JSONObject> listaEventos = new ArrayList<JSONObject>();
}
