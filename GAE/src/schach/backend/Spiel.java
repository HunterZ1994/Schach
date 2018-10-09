package schach.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;

import schach.daten.*;

public class Spiel {
	private D_Spiel daten;	//D_Spiel ergibt sich aus D
	private ArrayList<Belegung> belegungen;
	// inital constructor.
	//        Die Klasse Spiel besitzt zwei Konstruktoren:
//        1. einen initialen Konstruktor, der ein komplett neues Spiel aufsetzt.
//			-> Dieser greift zunächst auf D_Spiel zu und erstellt ein Neues Spiel.
//			-> Anschließend wird eine neue ArrayListe mit den Belegungen des Spielfeldes (Den Positionen aller Figuren) erstellt.
//
//        2. einen Konstruktor mit einem Pfad zu einer XML Datei als Parameter.
//			-> Dieser ließt die gegebe Datei ein und speichert ihre Werte in einem String, welcher in das XML Format codiert und als StringArray gespeichert wird.
//			-> Anschließend wird das Spiel anhand der im Array gespeicherten Züge rekonstruiert.
	public Spiel(){
		daten=new D_Spiel();
		belegungen=new ArrayList<Belegung>();
	}
	//Constructor contaiing a path to a saved game.
	public Spiel(String pfad) {
		this();		//calling the initial constructor
		BufferedReader br=null;
		// reading the file with the saved game status
		try {
			pfad=URLDecoder.decode(""+pfad,"ISO-8859-1");
			StringBuffer xml=new StringBuffer();
			br=new BufferedReader(new FileReader(pfad));
			String zeile=br.readLine(); 
	    while (zeile!=null){
	    	xml.append(zeile+"/n");
	      zeile=br.readLine(); 
	    }
	    //converting the read file into an ArrayList
	    ArrayList<D> spielDaten=Xml.toArray(xml.toString());
	    int counter=0;
	    // Daten des Spiels
	    daten=(D_Spiel)spielDaten.get(counter);
	    counter++;
	    // replaying the game with the saved moves to the point were it has been saved
	    for(int i=0;i<=daten.getInt("anzahlZuege");i++){
	    	// Belegungen
		    Belegung b=new Belegung();
		    D_Belegung datenBelegung=(D_Belegung)spielDaten.get(counter);
		    counter++;
		    b.setDaten(datenBelegung); 
		    // Figuren dieser Belegung auf dem Brett
		    for(int j=1;j<=datenBelegung.getInt("anzahlFigurenAufBrett");j++){
		    	D_Figur datenFigur=(D_Figur)spielDaten.get(counter);
	  	    counter++;
	  	    Figur figur=new Figur(datenFigur);
	  	    b.setFigurAufBrett(figur,datenFigur.getString("position"));
		    }
		    // geschlagene Figuren dieser Belegung
		    for(int j=1;j<=datenBelegung.getInt("anzahlFigurenGeschlagen");j++){
		    	D_Figur datenFigur=(D_Figur)spielDaten.get(counter);
	  	    counter++;
	  	    Figur figur=new Figur(datenFigur);
	  	    b.setFigurGeschlagen(figur);
		    }
		    belegungen.add(b);
	    }	    	
		}
    catch (Exception e){
			throw new RuntimeException("Fehler beim Laden des Spiels von "+pfad+": "+e.getMessage());
		}
		finally{
			try {
				br.close();
			} catch (Exception e) {}			
		}
	}
	
	public D_Spiel getDaten(){
		return daten;
	}
	// setzt die initiale Belegung der Figuren auf die Standardbelegung nach einem gegebenen Regelwerk

	//        Die Methode initStandardbelegung:
//        Diese Methode setzt das Schachbrett für eine neue Partie auf.
	public void initStandardbelegung(){
		Belegung belegung=new Belegung();
		Regelwerk.setStartbelegung(belegung);
		belegungen.add(belegung);
	}
	//        Die Methode getAnzahlZüge:
//        Da jede veränderung der Belegung (jeder Zug) in einer Liste gespeichert wird, lässt sich die Anzahl der bisherigen Züge aus deren Größe ableiten. Diese wird daher zurück gegeben.
//
	public int getAnzahlZuege(){
		return belegungen.size()-1;
	}

	public boolean isWeissAmZug(){
		return daten.getInt("anzahlZuege")%2==0;
	}

	public boolean iSchwarzAmZug(){
		return !isWeissAmZug();
	}

	public Belegung getAktuelleBelegung(){
		return belegungen.get(belegungen.size()-1);
	}

	public Belegung getBelegung(int nummer){
		return belegungen.get(nummer);
	}

	//liefert alle bisherigen Züge als array Liste, da diese in Daten gespeichert werden.
	//                Die methode getZugHistorie:
//        Sie liefert die Liste aller Belegungen seit beginn der Partie als Liste zurück. Hierbei werden alle Züge in einer internen Notation, in der lediglich eine Bewegung von Feld A nach Feld B
//        gespeichert wurde. Um welche Figur es sich dabei handelt, lässt sich nur aus der Historie aller Züge zu diesem Feld rekonstruieren.
	public ArrayList<String> getZugHistorie() {
		ArrayList<String> zugHistorie=new ArrayList<String>();
		for(int i=1;i<=daten.getInt("anzahlZuege");i++){
			zugHistorie.add(getZugAlsNotation(i));			
		}
		return zugHistorie;
	}
	//                Die Methode getAlleErlaubtenZüge:
//        Sie liefert alle möglichen Positionen, an die eine Figur von einer bestimmten Position aus ziehen kann als HashSet zurück. Hierbei beteachtet die aktuelle Belegung, welche Figur sich
//        an der gegebenen Position befindet und gibt für diese ale möglichen Züge an. (Ein Bauer kann schließlich nicht so laufen wie ein Turm)
//

	public HashSet<Zug> getAlleErlaubteZuege(){
		return getAktuelleBelegung().getAlleErlaubteZuege(isWeissAmZug());
	}

	public HashSet<Zug> getErlaubteZuege(String position){
		return getAktuelleBelegung().getErlaubteZuege(position);
	}
//       Die Matt/Patt Methoden:
	//        Die Klasse Belegung entscheidet darüber, ob es für eine Farbe bzw. für beide Farben keine Zugmöglichkeiten mehr gibt.
//
	public boolean isWeissImSchach(){
		return getAktuelleBelegung().isSchach(true);
	}

	public boolean isSchwarzImSchach(){
		return getAktuelleBelegung().isSchach(false);
	}
	
	public boolean isWeissSchachMatt(){
		return getAktuelleBelegung().isSchachMatt(true);
	}

	public boolean isSchwarzSchachMatt(){
		return getAktuelleBelegung().isSchachMatt(false);
	}
	
	public boolean isPatt(){
		return getAktuelleBelegung().isPatt(isWeissAmZug());
	}
	
	//ausführen eines Zuges. Ziehe Figur an position x,y nach position z,a
	//                Die Methode ziehen:
//        Diese Methode nimmt zwei Positionen als Parameter. Hierbei beschreibt die Erste, die Position auf der eine Figur steht und die Zweite, die an der sie nach dem Zug stehen soll.
//        Zunächst wird die aktuelle Belegung des Feldes gecloned und die Figur an der gegebenen Position ausgewählt. Sollte an dieser Position keine Figur stehen (Figur f = Belegung.getFigur(von) == null)
//        wird eine Fehlermeldung ausgegeben.
//
//        Sollte kein Zug möglich sein, wird die Fehlermedung "Das Spiel ist bereits zu Ende" ausgegeben.
//
//                Sollte die Partei zeihen, die nicht am Zug ist, wird "Sie sind nicht am Zug" ausgegeben.
//
//                Nun werden alle erlaubten Züge von der aktuellen Position aus in einem Set gespeichert.
//                hier wird der anvisierte Zug gegengewogen. D. h. ist der anviserte Zug im Set vorhanden, kann er ausgeführt werden. Falls nicht, wird "Zug ist nicht erlaubt" ausgegeben.
//
//                Schlussendlich wird über die Methode moveFigur der Klasse Belegung die Position der aktuellen Figur angepasst und der Zug der Liste der Züge hinzugefügt.
//
//        Sollte es sich bei dem Zug um eine Rochade handeln, so wird diese in einem Kommentar vermerkt.
//
//        Ebenso wenn es sich um ein enPassant, d.h. ein Bauer macht zwei Schritte aus der Startposition herraus, so wird für diesen Bauer ein Marker gesetzt, wodurch er keinen Doppelschritt
//        mehr vornehmen kann. //Kommentar: Mir scheint, als sei der Doppelschritt auch nach den Zug aus der Startposition herraus noch möglich. Bitte um klärung.
//
//                Darüber hinaus wird abgefragt, ob ein Bauer in eine andere Figur umgewandelt werden kann. Hierzu wird in der Belegung nachgesehen, ob der Bauer nach dem Zug im Haus des Gegners steht.
//        Falls ja, wird er aus dem Speil entfernt, und an seiner statt eine andere Figur auf die gegebene Position gesetzt.
//
//                Sollte ein Spieler im Schach stehen, so wird dies im Spielstatus vermerkt, da er nun lediglich seinen König aus dem Schach bringen kann und kein anderer sonst gültiger Zug in dieser Situation
//        zulässig ist.
//
//        Am Ende werden nun noch die Daten, also die Aktuelle Belegung, die Zughistorie, die Anzahl der Züge, sowie Bemerkungen und Spielstatus aktuallisiert und die Belegung nach dem Zug zurück gegeben.
//
	public Belegung ziehe(String von,String nach) {
		Belegung b=getAktuelleBelegung();
		Belegung bNeu=null;
		Zug zNeu=null;
		boolean isEnPassant=false;

		bNeu=b.clone();
		Figur f=bNeu.getFigur(von);
		// keine Figur zum Ziehen
		if (f==null)
			throw new RuntimeException("ziehe: Auf diesem Feld ist keine Figur!");
		// Spiel ist bereits beendet
		SpielEnum zugDavorStatus=b.getStatus();
		if ((zugDavorStatus!=null)&&
				(zugDavorStatus.equals(SpielEnum.WeissSchachMatt)||zugDavorStatus.equals(SpielEnum.SchwarzSchachMatt)||zugDavorStatus.equals(SpielEnum.Patt))){
			throw new RuntimeException("ziehe: Das Spiel ist bereits zu Ende: "+zugDavorStatus);
		}
		// ich bin nicht am Zug
		if(f.isWeiss()!=isWeissAmZug())
			throw new RuntimeException("ziehe: Sie sind nicht am Zug!");
		// ist der Zug erlaubt prüft, ob der eingegebene Zug in der errechneten Liste aller aus dieser Position erlaubten Züge enthaöten ist.
		HashSet<Zug> erlaubteZuege=b.getErlaubteZuege(f.getPosition());
		if (!erlaubteZuege.contains(new Zug(von,nach)))
			throw new RuntimeException("ziehe: Der Zug "+f.getTyp()+" von "+von+" nach "+nach+" ist nicht erlaubt!");
		
		// ZIEHEN:
		bNeu.moveFigur(f,nach);
		// Zug registrieren in der Belegung...
		zNeu=new Zug(von,nach);
		
		// Rochade
		if (f.getTyp().equals(FigurEnum.Koenig)){
			int xAlt=Belegung.toArrayNotation(von)[0];
			int xNeu=Belegung.toArrayNotation(nach)[0];
			if ((xAlt==xNeu+2)||(xAlt==xNeu-2)){
				if (nach.equals("c1")){ // Turm mitziehen
					bNeu.moveFigur(bNeu.getFigur("a1"),"d1");
					zNeu.setBemerkung(ZugEnum.RochadeLang);
				}else if (nach.equals("g1")){
					bNeu.moveFigur(bNeu.getFigur("h1"),"f1");
					zNeu.setBemerkung(ZugEnum.RochadeKurz);
				}else if (nach.equals("c8")){
					bNeu.moveFigur(bNeu.getFigur("a8"),"d8");
					zNeu.setBemerkung(ZugEnum.RochadeLang);
				}else{
					bNeu.moveFigur(bNeu.getFigur("h8"),"f8");
					zNeu.setBemerkung(ZugEnum.RochadeKurz);
				}
			}
		}
		
		// en passant
		if ((b.getBemerkung()!=null)&&(ZugEnum.BauerDoppelschritt.equals(b.getBemerkung()))){
			if ((f.getTyp().equals(FigurEnum.Bauer))&&(b.getFigur(nach)==null)){
				int xAlt=Belegung.toArrayNotation(von)[0];
				int xNeu=Belegung.toArrayNotation(nach)[0];
				if (xAlt!=xNeu){
					bNeu.removeBauerBeiEnPassant(b.getNach());
					isEnPassant=true;						
				}
			}
		}
		
		// Bauernumwandlung
		if (bNeu.isBauerUmwandlungImGange(nach)){
			bNeu.getDaten().decInt("anzahlFigurenAufBrett"); // der alte Bauer ist weg
			Figur fNeu=new Figur(FigurEnum.Dame,isWeissAmZug());
			bNeu.addFigur(fNeu,nach); // dafuer kommt die neue Dame hinzu
			bNeu.setBemerkung(ZugEnum.BauerUmgewandelt);
			zNeu.setBemerkung(ZugEnum.BauerUmgewandelt);
		}

		// Spielstatus hinzufuegen
		if (bNeu.isSchach(true)){
			zNeu.setStatus(SpielEnum.WeissImSchach);
			bNeu.setStatus(SpielEnum.WeissImSchach);
			if (bNeu.isSchachMatt(true)){
				zNeu.setStatus(SpielEnum.WeissSchachMatt);
				bNeu.setStatus(SpielEnum.WeissSchachMatt);
			}
		} else if (bNeu.isSchach(false)){
			zNeu.setStatus(SpielEnum.SchwarzImSchach);
			bNeu.setStatus(SpielEnum.SchwarzImSchach);
			if (bNeu.isSchachMatt(false)){
				zNeu.setStatus(SpielEnum.SchwarzSchachMatt);
				bNeu.setStatus(SpielEnum.SchwarzSchachMatt);
			}
		} else if (bNeu.isPatt(isWeissAmZug())){
			zNeu.setStatus(SpielEnum.Patt);
			bNeu.setStatus(SpielEnum.Patt);
		}
		// Spielbemerkung hinzufuegen
		if (b.isBauerDoppelschritt(von,nach)) zNeu.setBemerkung(ZugEnum.BauerDoppelschritt);
		if (isEnPassant) zNeu.setBemerkung(ZugEnum.EnPassant);
		
		// Spieldaten aktualisieren
		bNeu.setZugDavor(zNeu);
		belegungen.add(bNeu);
		daten.incInt("anzahlZuege");
		daten.setString("bemerkung",""+zNeu.getBemerkung());
		daten.setString("status",""+zNeu.getStatus());

		return bNeu;	
	}
	//speichert die aktuele belegung des Brettes und alle bisher gespielten Züge in einer XML Datei.
	//        Die Methode Speichern:
//        Diese Methode speichert die gesammze Zughistorie durch die toXML Methode in einer angegenenen Datei.
	public String speichern(String pfad){
		PrintWriter pw=null;
		try {
			pfad=URLDecoder.decode(""+pfad,"ISO-8859-1");
			if (!pfad.endsWith(".xml")) pfad=pfad+".xml";
			pw=new PrintWriter(new FileWriter(pfad));
			pw.println(Xml.verpacken(toXml()));
			return Xml.verpacken(Xml.fromD(new D_OK("Spiel erfolgreich gespeichert.")));
		} catch (Exception e) {
			e.printStackTrace();
			return Xml.verpacken(Xml.fromD(new D_Fehler(e.getMessage())));
		}
		finally{
			pw.close();			
		}
	}
	//        Die Methode toXML:
//        Diese Methode fügt alle bisher erfolgten Züge zu einem String im XML format hinzu und gibt diesen Zurück.
	public String toXml(){
		StringBuffer s=new StringBuffer(daten.toXml());
		for(int i=0;i<=daten.getInt("anzahlZuege");i++){
			s.append(belegungen.get(i).toXml());
		}
		return s.toString();
	}
	// Umwandeln eines bereits gespielten Zuges in 	eine leicht lesbare Notation.
	//        die Methode getZugAlsNotation:
//        Diese Methode wandelt einen Zug aus der Zughistorie mit einem bestimmten Index in einen String mit vorgefärtigeter Notation um und gibt diesen Zurück.
	private String getZugAlsNotation(int nummer){
		if ((nummer<1)||(nummer>belegungen.size()))
			throw new RuntimeException("getZugAlsNotation: Diese Zugnummer existiert nicht!");
		String s="";
		Belegung bVorher=getBelegung(nummer-1);
		Belegung bNachher=getBelegung(nummer);
		ZugEnum zugBemerkung=bNachher.getBemerkung();
		SpielEnum zugStatus=bNachher.getStatus();
		
		Figur fBewegt=bVorher.getFigur(bNachher.getVon());
		Figur fGeschlagen=null;
		ArrayList<Figur> g1=bVorher.getGeschlageneFiguren();
		ArrayList<Figur> g2=bNachher.getGeschlageneFiguren();
		if (g2.size()>g1.size()) fGeschlagen=g2.get(g2.size()-1);	

		if ((zugBemerkung!=null)&&(ZugEnum.RochadeKurz.equals(zugBemerkung))) return "0-0";
		if ((zugBemerkung!=null)&&(ZugEnum.RochadeLang.equals(zugBemerkung))) return "0-0-0";
		
		s+=fBewegt.getKuerzel();
		s+=bNachher.getVon();
		if (fGeschlagen==null)
			s+="-";
		else
			s+="x";
		s+=bNachher.getNach();
		
		if ((zugBemerkung!=null)&&(ZugEnum.BauerUmgewandelt.equals(zugBemerkung)))
			s+=bNachher.getFigur(bNachher.getNach()).getKuerzel();
		else if ((zugBemerkung!=null)&&(ZugEnum.EnPassant.equals(zugBemerkung)))
			s+=" e.p.";
		
		if ((zugStatus!=null)&&(SpielEnum.Patt.equals(zugStatus)))
			s+="=";
		else if ((zugStatus!=null)&&(SpielEnum.WeissImSchach.equals(zugStatus)||SpielEnum.SchwarzImSchach.equals(zugStatus)))
			s+="+";
		else if ((zugStatus!=null)&&(SpielEnum.WeissSchachMatt.equals(zugStatus)||SpielEnum.SchwarzSchachMatt.equals(zugStatus)))
			s+="++";

		return s;
	}
	//                Die interne Notation:
//        Wurde eine Figur geschlagen, so wird sie der Liste der Geschlagenen Figuren hinzugefügt.
//        Handelt es sich um eine Rochade, so wird "0-0" für die kurze- und "0-0-0" für die lange Rochade zurück gegeben.
//        Andernfalls besteht der String aus dem Kürzel der bewegten Figur, der Position, von der Sie sich bewegt hat, sollte sie eine Figur geschlagen haben einem "x", ansonsten einem "-"
//        und der Position, an der sie vorher gestanden hat.
//                Solte ein Bauer umgewandelt worden sein, so wird das Kürzel der neuen Figur and den String angehängt.
//        Sollte ein Bauer einen Doppelschritt gemacht haben wird "e.p." angehängt.
//                Bei einem Patt wird ein "=" angehängt, bei einem Schach ein "+" und bei einem Schach Matt ein "++"
//        Dieser String wird am Ende zurück gegeben.
//                Beispeil:
//        Ein Schwarzer Läufer steht auf A3. Er zieht nach E7 und schlägt einen weißen Bauern.
//                Die gespeicherte Notation wäre die Folgende:
//        L13x57
}
