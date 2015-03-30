package com.mysimplecount;

import java.util.ArrayList;
import java.util.List;

/**
 * Implemente l'interface IObservable. Cet objet est cree dans le main et passe en parametre aux objets Calc et Window, qui l'observent et se mettent a jour des qu'ils detectent un changement, puis le modifient en consequence. Represente le Controller du pattern MVC.
 * @author Julie
 * @see IObservable
 *
 */

public class ObserVal implements IObservable {
	private String _lastExpr = "";
	private String _expr = "0";
	private Window.eType _lastType = Window.eType.BC;
	private Window.eType _type = Window.eType.BC;
	private List<IObserver> _obs = new ArrayList<IObserver>();

	
	/**
	 * Retourne la variable membre expr.
	 * @return String
	 */
	
	public String getExpr() {
		return this._expr;
	}
	
	
	/**
	 * Retourne la variable membre type.
	 * @return Window eType
	 * @see Window eType
	 */
	
	public Window.eType getType() {
		return (this._type);
	}
	
	
	/**
	 * Retourne la variable membre lastType.
	 * @return Window eType
	 * @see Window eType
	 */
	
	public Window.eType getLastType() {
		return (this._lastType);
	}
	
	
	/**
	 * Retourne la variable membre lastExpr.
	 * @return String
	 */
	
	public String getLastExpr() {
		return (this._lastExpr);
	}
	
	
	/**
	 * Checke si le caractere recu est un operateur d'addition, soustraction, multiplication, division ou modulo. Fonction publique statique.
	 * @param c Le caractere a analyser
	 * @return boolean
	 */
	
	public static boolean isOp(char c) {
		return (c == '+' || c == '-' || c == '*' || c == '/' || c == '%');
	}
	
	
	/**
	 * Retourne true ou false afin de determiner si la chaine de caractere passee en parametre est un nombre (entier ou decimal, negatif ou positif). Fonction publique statique.
	 * @param n La String a analyser
	 * @return boolean
	 */
	
	public static boolean isNumber(String n) {
		char[] ca = n.toCharArray();
		for (int x = 0; x < ca.length; x++) {
			if (!((ca[x] >= '0' && ca[x] <= '9') || ca[x] == '.' || ca[x] == '-'))
				return (false);
		}
		return (true);
	}
	
	
	/**
	 * Retourne true ou false afin de determiner si le type passe en parametre fait partie d'une expression a calculer : est-ce que c'est un nombre, un operateur de calcul ou un point.
	 * @param type Le type a analyser
	 * @return boolean
	 */
	
	private boolean isExpr(Window.eType type) {
		return (type == Window.eType.BNUMBER || type == Window.eType.BOPERATOR || type == Window.eType.BPOINT);
	}
	
	
	/**
	 * Retourne true ou false afin de determiner si le caractere passe en parametre est une parenthese ouvrante ou non.
	 * @param c Le caractere a analyser
	 * @return boolean
	 */
	
	private boolean isLeftParenthesis(char c) {
		return (c == '(');
	}
	
	
	/**
	 * Retourne true ou false afin de determiner si le caractere passe en parametre est une parenthese fermante ou non.
	 * @param c Le caractere a analyser
	 * @return boolean
	 */
	
	private boolean isRightParenthesis(char c) {
		return (c == ')');
	}
	
	
	/**
	 * Retourne true ou false afin de determiner si le caractere passe en parametre est un moins ou non.
	 * @param c Le caractere a analyser
	 * @return boolean
	 */
	
	private boolean isNeg(char c) {
		return (c == '-');
	}
	
	
	/**
	 * Retourne true ou false afin de determiner si le caractere passe en parametre est un chiffre ou non.
	 * @param c Le caractere a analyser
	 * @return boolean
	 */
	
	private boolean isNumber(char c) {
		return (c >= '0' && c <= '9');
	}
	
	
	/**
	 * Retourne true ou false afin de determiner le nombre et le type d'operateurs qui se suivent, parmi les negations, les parentheses et les operateurs de calcul.
	 * @param expr L'expression du bouton clique a analyser
	 * @param type Le type du bouton clique a analyser
	 * @return boolean
	 */
	
	private boolean handleMultipleOperators(String expr, Window.eType type) {
		char [] ca = this._expr.toCharArray();
		char c = expr.charAt(0);
		int size = ca.length;
		if (size >= 1) {
			if (this.isZero(this.getExpr()) && (this.isNeg(c) || this.isLeftParenthesis(c)))
				return (true);
			if (this.getType() == Window.eType.BC && !this.isNeg(c))
				return (false);
			if (this.isNumber(ca[size - 1]) && this.isLeftParenthesis(c))
				return (false);
			if (this.isLeftParenthesis(ca[size - 1]) && (this.isNeg(c) || this.isLeftParenthesis(c)))
				return (true);
			if (this.isNumber(ca[size - 1]) || this.isRightParenthesis(ca[size - 1]))
				return (true);
			if (this.isNeg(c) && this.isNeg(ca[size - 1])) {
				if (size < 3)
					return (false);
				else if (size >= 3 && (this.isRightParenthesis(ca[size - 2]) || this.isNumber(ca[size - 2])))
					return (true);
				return (false);
			}
			if (ObserVal.isOp(ca[size - 1]) && (this.isNeg(c) || this.isLeftParenthesis(c)))
				return (true);
		}
		return (false);
	}
	
	
	/**
	 * Retourne true ou false afin de determiner s'il n'y a pas deja un point dans le dernier nombre affiche a l'ecran.
	 * @return boolean
	 */
	
	private boolean handleMultiplePoints() {
		char[] ca = this._expr.toCharArray();
		boolean b = true;
		for (int x = 0; x < ca.length; x++) {
			if (ca[x] == '.')
				b = false;
			if (ObserVal.isOp(ca[x]) || this.isLeftParenthesis(ca[x]) || this.isRightParenthesis(ca[x]))
				b = true;
		}
		return (b);
	}
	
	
	/**
	 * Retourne true ou false afin d'indiquer si le dernier caractere de la chaine est une parenthese fermante ou non.
	 * @return boolean
	 */
	
	private boolean checkParenthesis() {
		char[] ca = this._expr.toCharArray();
		int size = ca.length - 1;
		if (size > 0 && ca[size] == ')')
			return (false);
		return (true);
	}
	
	
	/**
	 * Retourne true ou false afin de determiner si l'expression actuelle est a 0.
	 * @param n L'expression
	 * @return boolean
	 */
	
	private boolean isZero(String n) {
		return (n.compareToIgnoreCase("0") == 0 || n.compareToIgnoreCase("0.0") == 0);
	}
	
	
	/**
	 * Retourne true ou false afin de determiner si le clic de l'utilisateur sur les boutons de fonctions (cosinus et compagnie) doit etre pris en compte ou non.
	 * @param expr L'expression a analyser
	 * @param type Le type a analyser
	 * @return boolean
	 */
	
	private boolean handleFunctions(String expr, Window.eType type) {
		char[] ca = this._expr.toCharArray();
		int size = ca.length;
		if (this.isZero(this.getExpr()) || (size > 0 && (ObserVal.isOp(ca[size - 1]) || this.isLeftParenthesis(ca[size - 1]))) || this.getLastType() == Window.eType.BEQUAL)
			return (true);
		return (false);
	}
	
	
	/**
	 * Retourne true ou false afin de determiner s'il y a bien un caractere a effacer dans l'expression.
	 * @return boolean
	 */
	
	private boolean checkDel() {
		if (this.getType() == Window.eType.BDEL && !this.getExpr().isEmpty() && this.isZero(this.getExpr()))
			return (true);
		return (false);
	}
	
	
	/**
	 * La fonction principale de gestion d'erreur. En fonction du type du bouton clique, elle verifiera l'expression actuelle et retournera true ou false pour indiquer si le clic doit etre pris en compte et, le cas echeant, l'expression modifiee.
	 * @param expr L'expression a analyser
	 * @param type Le type a analyser
	 * @return boolean
	 */
	
	private boolean handleErrors(String expr, Window.eType type) {
		if (type == Window.eType.BOPERATOR && this.handleMultipleOperators(expr, type) == false)
			return (false);
		if (type == Window.eType.BNUMBER && this.checkParenthesis() == false)
			return (false);
		if (type == Window.eType.BPOINT && this.handleMultiplePoints() == false)
			return (false);
		if (type == Window.eType.BMEMORY && (ObserVal.isNumber(this.getExpr()) || this.getLastExpr() != ""))
			return (true);
		if (type == Window.eType.BOPERATION && this.handleFunctions(expr, type) == false)
			return (false);
		if (expr.compareToIgnoreCase("del") == 0 && this.getExpr() == "")
			return (false);
		return (true);
	}
	
	
	/**
	 * Compte le nombre d'occurence du caractere passe en parametre.
	 * @param expr L'expression a parcourir
	 * @param c Le caractere recherche dans l'expression
	 * @return int
	 * @see cleanExpr
	 */
	
	private int countOccurence(String expr, char c) {
		char[] ca = expr.toCharArray();
		int nb = 0;
		for (int x = 0; x < ca.length; x++) {
			if (ca[x] == c)
				nb++;
		}
		return (nb);
	}
	
	
	/**
	 * Recherche la derniere occurence du caractere passe en parametre et le remplace par un espace.
	 * @param expr L'expression a parcourir
	 * @param c Le caractere recherche dans l'expression
	 * @return String
	 * @see cleanExpr
	 */
	
	private String replaceOccurence(String expr, char c) {
		int idx = expr.lastIndexOf(c);
		StringBuilder bexpr = new StringBuilder(expr);
		bexpr.setCharAt(idx, ' ');
		return (bexpr.toString());
	}
	
	
	/**
	 * Verifie le nombre de parentheses dans l'expression finale, et si besoin, en supprime/rajoute quelques unes a la fin.
	 * @param expr L'expression a analyser
	 * @return String
	 */
	
	private String cleanExpr(String expr) {
		int pth1 = this.countOccurence(expr, '(');
		int pth2 = this.countOccurence(expr, ')');
		if (pth1 < pth2) {
			for (int x = pth1; x < pth2; ++x) {
				expr = this.replaceOccurence(expr, ')');
			}
		}
		else if (pth1 > pth2) {
			for (int x = pth2; x < pth1; ++x) {
				expr = expr.concat(")");
			}
		}
		return (expr);
	}
	
	
	/**
	 * Met l'objet ObserVal a jour avec les valeurs passees en parametre puis notifie les observateurs de sa mise a jour. La gestion d'erreur au niveau des informations a afficher et a passer aux observateurs est realisees dans cette fonction. 
	 * @param expr Le label du bouton clique
	 * @param type Le type du bouton clique
	 * @param obsType Le type indiquant la classe qui modifie l'objet : le modele ou la vue
	 */
	
	public void setObservable(String expr, Window.eType type, IObserver.obsType obsType) {
		if (this.handleErrors(expr, type) == false)
			return;
		this.setLastExpr(this._expr);
		this.resetExpr(type);
		this.setLastType(this._type);
		this.setType(type);
		if (expr.compareToIgnoreCase("del") == 0) {
			if (this._expr.length() > 1)
				this.setExpr(this._expr.substring(0, this._expr.length() - 1));
			else
				this.setExpr("0");
		}
		else if (expr.compareToIgnoreCase("c") == 0)
			this.setExpr("0");
		else if (this.isExpr(type))
			this.setExpr(this._expr.concat(expr));
		else if (type == Window.eType.BOPERATION)
			this.setExpr(this._expr.concat(expr), "(");
		else
			this.setExpr(expr);
		if (this._type == Window.eType.BEQUAL)
			this.setLastExpr(this.cleanExpr(this.getLastExpr()));
		this.updateObserver(obsType);
	}
	
	
	/**
	 * Vide la variable membre expr en fonction du type passe en parametre et du contenu des variables membres type et lastType : en effet, la fonction membre setExpr, appelee juste apres, aura parfois besoin d'avoir une chaine vide.
	 * @param type Le type a analyser
	 */
	
	private void resetExpr(Window.eType type) {
		if (this.checkDel() == true || this.getType() == Window.eType.BC || this.getType() == Window.eType.BEQUAL ||
				(this.getLastType() == Window.eType.BEQUAL && type != Window.eType.BOPERATOR) || 
				type == Window.eType.BMEMORY || this.getLastType() == Window.eType.BMEMORY)
			this.setExpr("");
	}
	
	
	/**
	 * Change la valeur de la variable membre expr par la String passee en parametre.
	 * @param expr L'expression a stocker dans la variable membre expr
	 */
	
	private void setExpr(String expr) {
		this._expr = expr;
	}
	
	
	/**
	 * Change la valeur de la variable membre expr par la concatenation des deux String passees en parametre.
	 * @param expr L'expression a stocker dans la variable membre expr
	 * @param c La chaine a concatener au premier parametre
	 */
	
	private void setExpr(String expr, String c) {
		expr = expr.concat(c);
		this._expr = expr;
	}
	
	
	/**
	 * Change la valeur de la variable membre lastExpr par la String passee en parametre.
	 * @param expr L'expression a stocker dans la variable membre lastExpr
	 */
	
	private void setLastExpr(String expr) {
		if (expr.compareToIgnoreCase("0") == 0)
			this._lastExpr = "";
		else
			this._lastExpr = expr;
	}
	
	
	/**
	 * Change la valeur de la variable membre type par la valeur passee en parametre.
	 * @param type Le type a stocker dans la variable membre type
	 */
	
	private void setType(Window.eType type) {
		this._type = type;
	}
	
	
	/**
	 * Change la valeur de la variable membre lastType par la valeur passee en parametre.
	 * @param type Le type a stocker dans la variable membre lastType
	 */
	
	private void setLastType(Window.eType type) {
		this._lastType = type;
	}
	
	
	/**
	 * Prend un observateur en parametre et l'ajoute a la liste des observateurs de l'objet.
	 * @param IObserver L'observateur a ajouter a la liste
	 */
	
	public void addObserver(IObserver obs) {
		this._obs.add(obs);
	}
	
	
	/**
	 * Notifie les observateurs d'un changement en appelant leur fonction update. Prends obsType en parametre afin de savoir si l'objet Calc doit egalement se mettre a jour, ou si seul le label doit le faire. 
	 * @param IObserver.obsType Le type de l'appelant afin de savoir si seul l'affichage se met a jour, ou s'il y a des calculs a faire
	 */
	
	public void updateObserver(IObserver.obsType obsType) {
		if (obsType == IObserver.obsType.WINDOW) {
			this._obs.get(1).update(this._expr, this._type);
		}
		this._obs.get(0).update(this._expr, this._type);
	}
}
