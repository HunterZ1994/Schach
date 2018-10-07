package schach.backend;

import schach.daten.D_Figur;	//D_Figur erbt ja von D
import schach.daten.FigurEnum;

public class Figur {			
	private D_Figur daten;
	
	private Figur(){			//Beim Erstellen einer 'Figur' arbeitet man eigentlich mit einer 'D_Figur'
		daten=new D_Figur();
	}
	
	public Figur(D_Figur daten){
		this.daten=(D_Figur)daten.clone();
	}
	
	public Figur(FigurEnum typ,boolean isWeiss){	//dieser Konstruktor benötigt bereits Typ und Farbe
		this();
		setTyp(typ);				
		setWeiss(isWeiss);		//je nach boolean oben schwarz oder weiß
		setPosition("");		//wird beim erstellen noch nicht gesetzt
		setBereitsBewegt(false);
	}
	
	public D_Figur getDaten(){
		return daten;
	}
	
	private void setTyp(FigurEnum typ){		//in FigurEnum sind alle zulässigen Typen festgelegt
		daten.setString("typ",""+typ);
	}
	public FigurEnum getTyp(){
		return FigurEnum.toEnumFromString(daten.getString("typ"));
	}
	public String getKuerzel(){
		return FigurEnum.toKuerzel(FigurEnum.toEnumFromString(daten.getString("typ")));
	}
	
	private void setWeiss(boolean isWeiss){
		daten.setBool("isWeiss",isWeiss);
	}
	public boolean isWeiss(){
		return daten.getBool("isWeiss");						//liefert Farbe(durch boolean)
	}
	public boolean isSchwarz(){
		return !daten.getBool("isWeiss");
	}
	
	public void setPosition(String position){		//in dem .daten(-Satz) einer Figur wird der Positions-Parameter
		daten.setString("position",position);		//geändert
	}
	public void setPosition(int x,int y){
		daten.setString("position",Belegung.toSchachNotation(x,y));	//toSchNot liefert einen String, 
																	//also gleiche wie darüber
	}
	public String getPosition(){
		return daten.getString("position");
	}
	public boolean isGeschlagen(){
		return (daten.getString("position")==null)||(daten.getString("position").length()!=2);
																	//wenn Figur geshlagen wurde, hat sie keinen 
																	//Position mehr oder Position ( A5) ist 
																	//normalerweise 2 lang
	}
	
	public void setBereitsBewegt(boolean isBereitsBeweggt){
		daten.setBool("bereitsBewegt",isBereitsBeweggt); 
	}
	public boolean isBereitsBewegt(){
		return daten.getBool("bereitsBewegt");
	}
	
	@Override
	public String toString(){
		return getKuerzel()+getPosition();				//liefert String aus Typ + Position
	}
	
	@Override
	public boolean equals(Object o){				//Vergleicht zwei Figuren auf Gleichheit
		if (!(o instanceof Figur)) return false;
		Figur f=(Figur)o;
		return this.daten.equals(f.daten);
	}
	
	@Override 
	public Figur clone(){							//kopiert eine Figur
		Figur f=new Figur((D_Figur)daten.clone());
		return f;
	}
	
	public String toXml(){
		return daten.toXml();		//schreibt Daten in Xml Datei
	}
}
