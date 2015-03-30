package com.mysimplecount;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;


/**
 * C'est dans la classe Pile que sont geres tous les calculs du programme.
 * @author Julie
 *
 */

public class Pile {
	Stack<String> _pile = new Stack<String>();
	Stack<String> _op = new Stack<String>();
	DecimalFormat _df = new DecimalFormat("0.00######");
	
	Map<Character, DoubleBinaryOperator> _doCalc = new HashMap<Character, DoubleBinaryOperator>();
	Map<String, DoubleUnaryOperator> _doOp = new HashMap<String, DoubleUnaryOperator>();

	
	/**
	 * Le constructeur ne prend en rien en parametre et initialise deux hashmap : la premiere contient une association de fonctions lambdas a un caractere afin de gerer les calculs, et la seconde contient une association de fonctions lambdas a une String afin de gerer les fonctions mathematiques.
	 */
	
	public Pile() {
		this._doCalc.put('+', (double op1, double op2) -> { return op1 + op2; });
		this._doCalc.put('-', (double op1, double op2) -> { return op1 - op2; });
		this._doCalc.put('*', (double op1, double op2) -> { return op1 * op2; });
		this._doCalc.put('/', (double op1, double op2) -> { return op1 / op2; });
		this._doCalc.put('%', (double op1, double op2) -> { return op1 % op2; });
		
		this._doOp.put("Cos", (double d1) -> { return Math.cos(Math.toRadians(d1)); });
		this._doOp.put("Sin", (double d1) -> { return Math.sin(Math.toRadians(d1)); });
		this._doOp.put("Tan", (double d1) -> { return Math.tan(Math.toRadians(d1)); });
		this._doOp.put("Log", (double d1) -> { return Math.log10(d1); });
		this._doOp.put("Sqrt", (double d1) -> { return Math.sqrt(d1); });
		this._doOp.put("x2", (double d1) -> { return Math.pow(d1, 2); });
	}
	
	
	/**
	 * Vide la pile et la stack d'operateurs. Est appelee depuis l'objet Calc, une fois les calculs faits.
	 */
	
	public void clear() {
		this._pile.clear();
		this._op.clear();
	}
	
	
	/**
	 * Cette fonction parcourt la chaine a evaluer, recherche les operateurs et les parentheses afin de placer un espace avant et apres, pour mieux decouper la chaine en tableau de String pour la fonction npi.
	 * @param expr L'expression a parcourir et modifier
	 * @return String[]
	 * @see npi
	 */
	
	private String[] splitStr(String expr) {
		StringBuilder bexpr = new StringBuilder(expr);
		char[] ca = expr.toCharArray();
		for (int x = ca.length - 1; x >= 0; x--) {
			if (ObserVal.isOp(ca[x]) || this.isParenthesis(ca[x])) {
				if ((ca[x] == '-' && !this.isNeg(expr, x)) || ca[x] != '-') {
					if ((x + 1) < ca.length)
						bexpr = bexpr.insert(x + 1, ' ');
					if (x > 0)
						bexpr = bexpr.insert(x, ' ');						
				}
			}
		}
		expr = bexpr.toString();
		expr = expr.replaceAll("[ ]+", " ");
		String[] spexpr = expr.split(" ");
		return (spexpr);
	}
	
	
	/**
	 * Cette fonction implemente l'algorithme de Shunting-Yard en parcourant le tableau de String obtenu par la fonction splitStr.
	 * @param expr L'expression a envoyer a splitStr puis a parcourir pour implementer l'algorithme de Shunting-Yard
	 * @see splitStr
	 */
	
	public void npi(String expr) {
		String[] e = this.splitStr(expr);
		
		for (int x = 0; x < e.length; x++) {
			if (this.isNumber(e[x])) {
				this._pile.push(e[x]);
			}
			else if (this.isFunction(e[x])) {
				this._op.push(e[x]);
			}
			else if (ObserVal.isOp(e[x].charAt(0)) && e[x].length() == 1) {
				while (!this._op.isEmpty() && this.priority(e[x].charAt(0), this._op.peek().charAt(0)))
					this._pile.push(this._op.pop());
				this._op.push(e[x]);
			}
			else if (this.isParenthesis(e[x].charAt(0)) && e[x].charAt(0) == '(') {
				this._op.push(e[x]);
			}
			else if (this.isParenthesis(e[x].charAt(0)) && e[x].charAt(0) == ')') {
				while ((!this._op.isEmpty()) && this._op.peek().charAt(0) != '(') {
					this._pile.push(this._op.pop());
				}
				if ((!this._op.isEmpty()) && this._op.peek().charAt(0) == '(')
					this._op.pop();
			}
		}
		while (!this._op.isEmpty()) {
			if (!this.isParenthesis(this._op.peek().charAt(0)))
					this._pile.push(this._op.peek());
			this._op.pop();
		}
	}
	
	
	/**
	 * Cette fonction appelle la fonction lambda associee au caractere passe en parametre dans la hashmap doCalc, et retourne son resultat. Gere les calculs.
	 * @param d1 Le premier operande sous la forme d'un double
	 * @param d2 Le second operande sous la forme d'un double
	 * @param op L'operateur sous la forme d'un char
	 * @return double
	 */
	
	private double doOp(double d1, double d2, char op) {
		return (this._doCalc.get(op).applyAsDouble(d1, d2));
	}
	
	
	/**
	 * Cette fonction appelle la fonction lambda associee a la String passee en parametre dans la hashmap doOp, et retourne son resultat. Gere les fonctions mathematiques.
	 * @param d1 Le parametre a passer a la fonction mathematique, sous la forme d'un double
	 * @param f Le nom de la fonction, sous la forme d'une String
	 * @return double
	 */
	
	private double doOp(double d1, String f) {
		return (this._doOp.get(f).applyAsDouble(d1));
	}
	
	
	/**
	 * Cette fonction parcourt la pile modifiee par la fonction npi et recherche les operateurs et fonctions tant que la taille de la pile est superieure a 1. Une fois le calcul fait, elle remplace le ou les operandes par le resultat, supprime l'autre et l'operateur/la fonction, puis repart de zero. A la toute fin, elle retourne la derniere valeur restante dans la pile.
	 * @return String
	 */
	
	public String doCalc() {
		int x = 0;
		while (this._pile.size() > 1) {
			if (this._pile.elementAt(x).length() == 1 && ObserVal.isOp(this._pile.elementAt(x).charAt(0))) {
				double d1 = Double.valueOf(this._pile.elementAt(x - 2));
				double d2 = Double.valueOf(this._pile.elementAt(x - 1));
				if (d2 == 0 && (this._pile.elementAt(x).charAt(0) == '/' || this._pile.elementAt(x).charAt(0) == '%'))
					return ("Div. / mod. by zero is impossible");
				double res = this.doOp(d1, d2, this._pile.elementAt(x).charAt(0));
				this._pile.remove(x);
				this._pile.remove(x - 1);
				this._pile.set(x - 2, String.valueOf(res));
				x = 0;
			}
			else if (this._pile.elementAt(x).length() > 1 && this.isFunction(this._pile.elementAt(x))) {
				double d1 = Double.valueOf(this._pile.elementAt(x - 1));
				double res = this.doOp(d1, this._pile.elementAt(x));
				this._pile.remove(x);
				this._pile.set(x - 1, String.valueOf(res));
				x = 0;
			}
			else
				x++;
		}
		String f = this._df.format(Double.valueOf(this._pile.pop()));
		f = f.replaceAll(",", ".");
		return (f);
	}
	
	
	/**
	 * Cette fonction determine le niveau de priorite entre deux operateurs et/ou fonctions, afin de le positionner correctement dans la pile.
	 * @param op1 Le premier operateur a comparer
	 * @param op2 Le second operateur a comparer
	 * @return boolean
	 */
	
	private boolean priority(char op1, char op2) {
		return ((this.isOpeLow(op1) && this.isOpeLow(op2)) || (this.isOpeLow(op1) && this.isOpeHigh(op2)) || (this.isOpeHigh(op1) && this.isOpeHigh(op2)) || 
				((this.isOpeLow(op1) || this.isOpeHigh(op1)) && this.isFunction(op2)));
	}
	
	
	/**
	 * Cette fonction determine si l'operateur a un niveau de priorite eleve.
	 * @param op Le caractere a analyser
	 * @return boolean
	 */
	
	private boolean isOpeHigh(char op) {
		return (op == '*' || op == '/' || op == '%');
	}
	
	/**
	 * Cette fonction determine si l'operateur a un niveau de priorite bas.
	 * @param op Le caractere a analyser
	 * @return boolean
	 */
	
	private boolean isOpeLow(char op) {
		return (op == '+' || op == '-');
	}
	
	
	/**
	 * Cette fonction determine si la String passee en parametre est un nombre, decimal ou entier, negatif ou positif.
	 * @param n La String a analyser
	 * @return boolean
	 */
	
	private boolean isNumber(String n) {
		if (n.length() == 1 && n.charAt(0) == '-')
			return (false);
		char[] ca = n.toCharArray();
		for (int x = 0; x < ca.length; x++) {
			if (!((ca[x] >= '0' && ca[x] <= '9') || ca[x] == '.' || ca[x] == '-'))
				return (false);
		}
		return (true);
	}
	
	
	/**
	 * Cette fonction determine si le caractere passe en parametre est une parenthese ou non.
	 * @param p Le caractere a analyser
	 * @return boolean
	 */
	
	private boolean isParenthesis(char p) {
		return (p == '(' || p == ')');
	}
	
	
	/**
	 * Cette fonction determine si la String passee en parametre est une fonction mathematique ou non.
	 * @param f La String a analyser
	 * @return boolean
	 */
	
	private boolean isFunction(String f) {
		if (f.compareToIgnoreCase("cos") == 0 || f.compareToIgnoreCase("tan") == 0 || f.compareToIgnoreCase("sin") == 0 || 
				f.compareToIgnoreCase("log") == 0 || f.compareToIgnoreCase("sqrt") == 0 || f.compareToIgnoreCase("x2") == 0)
			return (true);
		return (false);
	}
	
	
	/**
	 * Cette fonction determine si le caractere passe en parametre est la premiere lettre d'une fonction mathematique ou non.
	 * @param c Le caractere a analyser
	 * @return boolean
	 */
	
	private boolean isFunction(char c) {
		return (c == 'C' || c == 'T' || c == 'S' || c == 'L' || c == 'S' || c == 'x');
	}
	
	
	/**
	 * Cette fonction determine si le caractere a la position idx dans la String s passes en parametre est un moins de negation ou un moins de calcul.
	 * @param s La String concernee
	 * @param idx La position concernee
	 * @return boolean
	 */
	
	private boolean isNeg(String s, int idx) {
		char[] ca = s.toCharArray();
		if (idx > 0) {
			if (ObserVal.isOp(ca[idx - 1]) || ca[idx - 1] == '(')
				return (true);
			return (false);
		}
		return (true);
	}
}
