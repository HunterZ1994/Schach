package schach.backend;

import java.util.ArrayList;
import java.util.HashSet;

import schach.daten.D_Belegung;
import schach.daten.FigurEnum;
import schach.daten.SpielEnum;
import schach.daten.ZugEnum;

public class Belegung {
	private D_Belegung daten;
	//figurenAufBrett wird im Konstruktor nochmal mit new erzeugt
	private Figur[][] figurenAufBrett=new Figur[9][9];
	private ArrayList<Figur> figurenGeschlagen;

	/*
	 * Der Methode wird ein String übergeben. Der String wird in Teile des Chars
	 * konvertiert. Anschließend wird dies in ein Char Array gespeichert.
	 * Durch parseInt wird der String, bzw. der konvertierte String in ein Integer
	 * umgewandelt und je in die Variablen x und y dementsprechend gespeichert.
	 * Zurückgegeben wird ein Array vom Typ int mit den Koordinaten x und y.
	 */
	public static int[] toArrayNotation(String schachNotation){
		try{
			char[] daten=schachNotation.toCharArray();
			//ASCII: 96 = "Hochkomma"; 97 = a
			int x=Integer.parseInt(""+(daten[0]-96));
			int y=Integer.parseInt(""+daten[1]);
			return new int[]{x,y};
		}
		catch (Exception e){
			return null;
		}
	}
	
	/*
	 * Der Methode werden zwei Integer übergeben, die dann durch den
	 * return ertmal weiter and die Methode toZeichen übergeben werden.
	 * Vorher dem Aufruf in return wird der Int y zum Int y addiert.
	 * Dort wird der Integer zurück in ein Char umgewandelt.
	 */	
	public static String toSchachNotation(int x,int y){
		return toZeichen(x)+y;
	}

	/*
	 * Wird von toSchachNotation aufgerufen, bekommt einen int Wert übergeben,
	 * der in ein Char umgewandelt wird und schließlich an toZeichen und 
	 * anschließend an toSchachNotation zurückgegeben wird.
	 * Der Wert + 96 ergibt in der ASCII Tabelle ein Kleinbuchstabe.
	 */
	public static String toZeichen(int wert){
		return ""+(char)(96+wert);
	}

	/*
	 * Der Konstruktor der Klasse Belegung.
	 * Erzeugt eine Objektinstanz von der Klasse D_Belegung, eine von Figur
	 * und eine ArrayList<> mit Figur
	 */
	public Belegung(){
		daten=new D_Belegung();
		figurenAufBrett=new Figur[9][9];
		figurenGeschlagen=new ArrayList<Figur>();
	}

	/*
	 * Gibt den atkuellen Stand vom Objekt Daten
	 */
	public D_Belegung getDaten(){
		return daten;
	}
	/*
	 * setzt den aktuellen Stand vom Objekt daten, mittels den Verweis this
	 */
	public void setDaten(D_Belegung daten){
		this.daten=daten;
	}
	
	/*
	 * Methode bekommt ein String übergeben und überprüft, ob dieser "Leer"
	 * ist, falls ja wird von ="" gesetzt. Andernfalls wird mit dem Objekt
	 * die Methode setString aus der Klasse D aufgerufen und der String "von" und der an die
	 * Methode setVon übergeben String von übergeben.
	 * setString veranlasst, dass in Klasse D setProperty mit zwei Strings
	 * ausgeführt wird
	 */
	public void setVon(String von){
		if (von==null) von="";
		daten.setString("von",von);
	}
	/*
	 * übergibt der getString Methode von der Klasse D den String "von"
	 * und erhält den return Wert der Methode getString von getProperty,
	 * die von java.util.properties kommt
	 */
	public String getVon(){
		return daten.getString("von");
	}
	
	/*
	 * Testet ob der übergebene String "null" ist, solle dies eintreffen wird
	 * nach="" gesetzt. Andernfalls wird ein String und der übergebene String
	 * an die Methode setString in Klasse D übergeben.
	 * In beiden Fällen werden zwei String an die Klasse setString übergeben.
	 * 
	 */
	public void setNach(String nach){
		if (nach==null) nach="";
		daten.setString("nach",nach);
	}
	/*
	 * Ruft die "getString" Methode in Klasse D auf. Und gibt den Wert zurück
	 * an die "getNach" Methode, die dann darauf den String zurückgibt. 
	 */
	public String getNach(){
		return daten.getString("nach");
	}
	
	/*
	 * Übergibt der Methode setString in Klasse D, den String "bemerkung" und
	 * den an die Methode setBemerkung übergebenen ZugEnum. 
	 */
	public void setBemerkung(ZugEnum bemerkung){
		daten.setString("bemerkung",""+bemerkung);
	}
	/*
	 * Bekommt ein String übergeben, der mittels einer IF-Anweisung überprüft
	 * wird, sollte der String null sein wird bemerkung="" gesetzt.
	 * In beiden Fällen wird der Methode "setString" von Klasse D
	 * der String "bemerkung" übergeben, sowie der von Methode bemerkung 
	 */
	public void setBemerkung(String bemerkung){
		if (bemerkung==null) bemerkung="";
		daten.setString("bemerkung",bemerkung);
	}
	/*
	 * Überprüft ob das Objekt daten null ist, falls ja gibt die Methode
	 * null zurück. Andernfalls wird der Methode "toEnumFromString" dem Enum
	 * ZugEnum die Rückgabe der Methode "getString" von Klasse D übergeben. 
	 */
	public ZugEnum getBemerkung(){
		if (daten==null) return null;
		return ZugEnum.toEnumFromString(daten.getString("bemerkung"));
	}
	
	/*
	 * Übergibt der Methode "setString" der Klasse D den String "statuts"
	 * und das übergebene Enum. 
	 */
	public void setStatus(SpielEnum status){
		daten.setString("status",""+status);
	}
	/*
	 * Bekommt ein String übergeben, der auf null überprüft wird.
	 * Sollte er null sein wird status="" gesetzt. In beiden Fällen
	 * wird "status" und der übergeben String an die Methode "setString"
	 * von Klasse D übergeben.
	 */
	public void setStatus(String status){
		if (status==null) status="";
		daten.setString("status",status);
	}
	/*
	 * Überprüft ob das Objekt daten null ist, falls ja gibt die Methode
	 * null zurück. Andernfalls wird der Methode fromString von Enum SpielEnum
	 * den Rückgabewert von Methode "getString" der Klasse D übergeben und am
	 * Ende der Methode zurückgegeben.
	 */
	public SpielEnum getStatus(){
		if (daten==null) return null;
		return SpielEnum.fromString(daten.getString("status"));
	}

	/*
	 * Methode setZugDavor bekommt ein Objekt übergeben.
	 * Die setString Methode der Klasse D bekommt den String "von" und
	 * einen String aus getDaten der Klasse Zug und get String der Klasse D
	 * übergeben 
	 */
	public void setZugDavor(Zug zNeu) {
		daten.setString("von",zNeu.getDaten().getString("von"));
		daten.setString("nach",zNeu.getDaten().getString("nach"));
		daten.setString("status",zNeu.getDaten().getString("status"));
		daten.setString("bemerkung",zNeu.getDaten().getString("bemerkung"));
	}
	
	/*
	 * Bekommt ein Objekt von Figur und einen String übergeben.
	 * Beides wird an die addFigur Methode weitergegeben, nachdem der
	 * String von toArrayNotation umgewandelt wurde.
	 * Es wird die addFigur (Figur figur, int [] xy Methode aufgerufen. 
	 */
	public void addFigur(Figur figur,String position){
		addFigur(figur,toArrayNotation(position));
	}
	/*
	 * Setzt die Figur auf die übergeben Werte im geschachtelten Array.
	 * Anschließend wir die Anzahl der Figuren auf Brett um eines erhöht.
	 * Zum Schluss wird die Position gesetzt, doch davor werden die Werte
	 * von der toSchachNotation umgewandelt.
	 */
	public void addFigur(Figur figur,int x,int y){
		figurenAufBrett[x][y]=figur;
		daten.incInt("anzahlFigurenAufBrett");
		figur.setPosition(toSchachNotation(x,y));	
	}
	/*
	 * Überprüft ob xy null ist, falls ja wird die Figur der ArrayList
	 * hinzugefügt. Anschließend wird die Position mit der Methode "setPosition"
	 * der Klasse D weitergegeben. Die Anzahl der Figuren auf dem Brett werden
	 * mit der Methode decInt der Klasse D reduziert und die Anzahl der 
	 * Figuren Geschlagen wird durch die Methode incInt um eines erhöht.
	 * Sollte die If Bedingung nicht true sein, wird im else die addFigur Methode
	 * (figur, xy[0], xy[1]) aufgerufen.
	 */
	public void addFigur(Figur figur,int[] xy){
		if (xy==null){
			figurenGeschlagen.add(figur);
			figur.setPosition("");
			daten.decInt("anzahlFigurenAufBrett");
			daten.incInt("anzahlFigurenGeschlagen");
		}
		else
			addFigur(figur,xy[0],xy[1]);
	}
	
	/*
	 * Figur wird auf einen Platz im Schachbrett gesetzt
	 */
	public void setFigurAufBrett(Figur figur,String position){
		int[] xy=toArrayNotation(position);
		figurenAufBrett[xy[0]][xy[1]]=figur;
	}
	/*Fügt eine geschlagene Figur der ArrayList hinzu.
	 * 
	 */
	public void setFigurGeschlagen(Figur figur){
		figurenGeschlagen.add(figur);
	}

	/* 
	 * Entfernt ein Bauer vom Schachbrett falls en passant gilt.
	 * En passant bsp.: ein schwarzer Bauer steht parallel zu einem weißen
	 * Bauer. Weiß ist am Zug und kann den Bauer schlagen, indem er sich
	 * schräg hinter schwarzen Bauer zieht.
	 *  Anschließend wird die Methode addFigur(Figur, String) aufgerufen.
	 */
	public void removeBauerBeiEnPassant(String position) {
		Figur f=getFigur(position);
		if ((f==null)||(!f.getTyp().equals(FigurEnum.Bauer)))
			throw new RuntimeException("removeBauerBeiEnPassant: Figur auf der Position ist ungueltig!");
		addFigur(f,"");
	}

	/* 
	 * Die Figur wird auf eine neue Position belegt. Es wird in der Methode
	 * überprüft, ob eine Figur gegenfalls geschlafen wurde und vom
	 * Schachbrett genommen werden muss. 
	 */
	public void moveFigur(Figur figur,String positionNeu){
		String positionAlt=figur.getPosition();
		int[] xyAlt=toArrayNotation(positionAlt);
		int[] xyNeu=toArrayNotation(positionNeu);
		figurenAufBrett[xyAlt[0]][xyAlt[1]]=null;
		if (hasGegnerFigur(positionNeu,figur.isWeiss())){ // schlage ggf. die Figur auf dem Zielfeld
			Figur fGeschlagen=figurenAufBrett[xyNeu[0]][xyNeu[1]];
			addFigur(fGeschlagen,"");
		}
		figurenAufBrett[xyNeu[0]][xyNeu[1]]=figur;
		figur.setPosition(positionNeu);
		figur.setBereitsBewegt(true);
	}
	
	/* 
	 * Gibt die neuen Position der Figuren auf dem Schachbrett zurück. 
	 */
	public boolean hasFigur(String position){
		return figurenAufBrett[toArrayNotation(position)[0]][toArrayNotation(position)[1]]!=null;
	} 
	
	/*
	 * Gibt der Methode einen Wahrheitswert zurück, nachdem überprüft
	 * wurde, ob die Position weiß ist.
	 * Gibt einen Boolean zurück, nachdem überprüft wurde, ob der Methoden-
	 * aufruf is.Weiss von Klasse Figur ungleich dem übergeben boolean ist.
	 */
	public boolean hasGegnerFigur(String position,boolean binWeiss){
		if (!hasFigur(position)) return false;
		Figur f=figurenAufBrett[toArrayNotation(position)[0]][toArrayNotation(position)[1]];
		return f.isWeiss()!=binWeiss;
	}

	/*
	 * Gibt die Position als String zurück, nachdem die getFigur Methode
	 * (intx, int y) aufgerufen wurde.
	 */
	public Figur getFigur(String position){
		return getFigur(toArrayNotation(position)[0],toArrayNotation(position)[1]);
	}
	/*
	 * Gibt der Methode die Figuren auf Brett mit ihrer Stelle im Array zurück
	 */
	public Figur getFigur(int x,int y){
		return figurenAufBrett[x][y];
	}

	/*
	 * Speichert alle Figuren, die sich auf einem Brett befinden in einer
	 * ArrayList ab. Wenn das Ergebnis ungleich null ist, wird die Figur mit
	 * ihrer Position in die ArrayList aufgenommen.
	 */
	public ArrayList<Figur> getAlleFigurenAufBrett(){
		ArrayList<Figur> ergebnis=new ArrayList<Figur>();
		for(int y=8;y>=1;y--){
			for(int x=1;x<=8;x++){
				Figur f=figurenAufBrett[x][y];
				if (f!=null) ergebnis.add(f);
			}
		}
		return ergebnis;
	}
	/*
	 * Gibt alle Figuren auf dem Schachbrett zurück, nachdem die
	 * getFigurenAufBrett() Methode aufgerufen wurde.
	 * Auch hier werden Figuren in die ArrayList aufgenommen.
	 */
	public ArrayList<Figur> getFigurenAufBrett(boolean weisse){
		ArrayList<Figur> ergebnis=new ArrayList<Figur>();
		for(Figur f:getAlleFigurenAufBrett()){
			if (f.isWeiss()==weisse) ergebnis.add(f);
		}
		return ergebnis;
	}
	/*
	 * Geht mit einer For Schleife die kommplette ArrayList durch und fügt
	 * weitere Figuren hinzu. Gibt die ArrayList zurück.
	 */
	public ArrayList<Figur> getGeschlageneFiguren(){
		ArrayList<Figur> ergebnis=new ArrayList<Figur>();
		for(Figur f:figurenGeschlagen) ergebnis.add(f);
		return ergebnis;
	}

	/*
	 * Geht die ArrayList mit einer for Schleife durch und überprüft zwei
	 * boolean Werte, sollte true herauskommen wird der Liste die Figur
	 * hinzugefügt. Zurückgegeben wird die ArrayList.
	 */
	public ArrayList<Figur> getGeschlageneFiguren(boolean weisse){
		ArrayList<Figur> ergebnis=new ArrayList<Figur>();
		for(Figur f:figurenGeschlagen){
			if (f.isWeiss()==weisse) ergebnis.add(f);
		}		
		return ergebnis;
	}
	
	/*
	 * Überprüft ob auf dem Schachbrett die Könige noch vorhanden sind.
	 */
	public Figur getKoenig(boolean isWeiss){
		for(Figur f:getFigurenAufBrett(isWeiss)){
			if (f.getTyp().equals(FigurEnum.Koenig)) return f;
		}
		throw new RuntimeException("getKoenig: Kein Koenig mehr auf dem Brett?");
	}

	/*
	 * Speichert alle erlaubten Züge in einer Hashset, nachdem mit einer
	 * for Schleife alle Figuren auf den Brett ausfindig gemacht worden sind.
	 * Übergibt der in addAll den Rückgabewert von der Methode aus der
	 * Klasse Regelwerk.
	 */
	public HashSet<Zug> getAlleErlaubteZuege(boolean isWeiss){
		HashSet<Zug> zuege=new HashSet<Zug>(); 
		for(Figur f:getFigurenAufBrett(isWeiss)){
			zuege.addAll(Regelwerk.getErlaubteZuege(this,f));
		}
		return zuege;
	}
	
	/*
	 * Nimmt eine Figur und gibt alle möglichen erlaubte Züge dieser
	 * Figur zurück. Ruft beim Return die Methode im Regelwerk auf.
	 */
	public HashSet<Zug> getErlaubteZuege(String position){
		Figur f=getFigur(position);
		if (f==null) return new HashSet<Zug>();
		return Regelwerk.getErlaubteZuege(this,f);
	}

	/*
	 * Überprüft ob der König geschlagen werden kann, mittels der if Bedinung
	 * und gibt return zurück. Andernfalls false.
	 */
	public boolean isSchach(boolean isWeiss){
		Figur meinKoenig=getKoenig(isWeiss);
		for(Figur f:getFigurenAufBrett(!isWeiss)){
			// f ist gegnerische Figur -> kann f jetzt meinen Koenig schlagen?
			if (Regelwerk.kannSchlagen(this,f.getPosition(),meinKoenig.getPosition())) return true;
		}		
		return false;
	}

	/*
	 * Überprüft zuerst, ob der König Schach gesetzt wurde.
	 * ist das HashSet leer oder die Größe des HashSet 0 ist, dann
	 * gib true zurück, wenn eines der beiden wahr ist und somit ist
	 * der König isSchachMatt
	 */
	public boolean isSchachMatt(boolean isWeiss){
		if (!isSchach(isWeiss)) return false;
		HashSet<Zug> zuege=getAlleErlaubteZuege(isWeiss);
		if ((zuege==null)||(zuege.size()==0)) return true;
		return false;
	}
	
	/*
	 * *Überprüft ob das Spiel eine pattSituation geworden ist.
	 */
	public boolean isPatt(boolean weissAmZug){
		if (isSchach(weissAmZug)) return false;
		HashSet<Zug> zuege=getAlleErlaubteZuege(weissAmZug);
		if ((zuege==null)||(zuege.size()==0)) return true;
		return false;
	}
	
	/*
	 * Überprüft ob ein Bauer mit einem Doppelschritt nach vorne
	 * gelaufen ist.
	 */
	public boolean isBauerDoppelschritt(String von,String nach){
		Figur f=getFigur(von);
		if (f==null) return false;
		if (FigurEnum.Bauer.equals(f.getTyp())){
			int[] vonArray=Belegung.toArrayNotation(von);
			int[] nachArray=Belegung.toArrayNotation(nach);
			return ((vonArray[1]==nachArray[1]+2)||(vonArray[1]==nachArray[1]-2));
		}
		return false;
	}

	/*
	 * Überprüft ob gerade ein Bauer eines Spielers am Ende des Spielbretts
	 * gegenüber angekommen ist und umgewandelt werden kann.
	 */
	public boolean isBauerUmwandlungImGange(String nach) {
		Figur f=getFigur(nach);
		if (f==null) return false;
		if (FigurEnum.Bauer.equals(f.getTyp())){
			int y=toArrayNotation(nach)[1];
			return (y==1)||(y==8);
		}
		return false;
	}

	/*
	 * Klont das aktuelle Objekt Belegung in bNeu. Speichert die neue
	 * Figur und gibt diese zurück.Gibt die Werte an moveFigur weiter.
	 */
	public Belegung zieheTestweise(String von,String nach) {
		Belegung bNeu=this.clone();
		Figur f=bNeu.getFigur(von);
		if (f==null)
			throw new RuntimeException("ziehe: Auf diesem Feld ist keine Figur!");
		bNeu.moveFigur(f,nach);
		return bNeu;
	}

	/*
	 * Erzeugt eine String Buffer und geht mit zwei for Schleifen das
	 * komplette Schachbrett durch und gibt sobald f nicht null ist es
	 * an die append Methode weiter. Zurückgegeben wird ein Steing durch die
	 * Methode toString.
	 */
	public String toStringSichtVonWeiss(){
		StringBuffer s=new StringBuffer("-----------------------------------------\n");
		for(int y=8;y>=1;y--){
			for(int x=1;x<=8;x++){
				Figur f=figurenAufBrett[x][y];
				if (f!=null){
					if (f.isWeiss())
						s.append("|w"+f);
					else
						s.append("|s"+f);
				}
				else
					s.append("|    ");
			}
			s.append("|\n");
			s.append("-----------------------------------------\n");
		}
		return s.toString();
	}

	/*
	 * selbes Spiel wie die Methode darüber, nur für schwarze Figuren.
	 */
	public String toStringSichtVonSchwarz(){
		StringBuffer s=new StringBuffer("-----------------------------------------\n");
		for(int y=1;y<=8;y++){
			for(int x=8;x>=1;x--){
				Figur f=figurenAufBrett[x][y];
				if (f!=null){
					if (f.isWeiss())
						s.append("|w"+f);
					else
						s.append("|s"+f);
				}
				else
					s.append("|    ");
			}
			s.append("|\n");
			s.append("-----------------------------------------\n");
		}
		return s.toString();
	}

	
	/*
	 * ruft die toStringSichVonWeiss Methode auf.
	 */
	@Override
	public String toString(){
		return toStringSichtVonWeiss();
	}
	
	/*
	 * Klont die sämtliche D_Belegung, sowie alle Figuren auf dem
	 * Schachbrett.
	 */
	@Override
	public Belegung clone(){
		D_Belegung datenKlon=(D_Belegung)daten.clone();
		datenKlon.setInt("anzahlFigurenAufBrett",0);
		datenKlon.setInt("anzahlFigurenGeschlagen",0);
		Belegung b=new Belegung();
		b.daten=datenKlon;
		for(Figur f:getAlleFigurenAufBrett()){
			Figur f2=f.clone();
			b.addFigur(f2,f2.getPosition());
		}
		for(Figur f:getGeschlageneFiguren()){
			Figur f2=f.clone();
			b.figurenGeschlagen.add(f2);
			b.daten.incInt("anzahlFigurenGeschlagen");
		}
		return b;
	}
	
	/*
	 * Wandelt die Daten in ein XML Format, mittels StringBuffer um.
	 */
	public String toXml(){
		StringBuffer s=new StringBuffer(daten.toXml());
		for(int y=8;y>=1;y--){
			for(int x=1;x<=8;x++){
				Figur f=figurenAufBrett[x][y];
				if (f!=null) s.append(f.toXml());
			}
		}
		if(figurenGeschlagen!=null){
			for(Figur f:figurenGeschlagen){
				s.append(f.toXml());				
			}
		}			
		return s.toString();
	}
}
