package com.mysimplecount;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;


/**
 * La classe Memory gere tout ce qui est relatif a la memoire et aux boutons associes.
 * @author Julie
 *
 */

public class Memory {
	double _d = 0;
	String _final = "0";
	DecimalFormat _df = new DecimalFormat("0.00######");
	Map<String, DoubleUnaryOperator> _m = new HashMap<String, DoubleUnaryOperator>();
	
	
	/**
	 * Le constructeur initialise une hashmap en associant une String et une fonction lambda d'addition, de soustraction, de remplacement, ou d'affichage. 
	 */
	
	public Memory() {
		this._m.put("M+", (double d) -> { this._d += d; return (this._d); });
		this._m.put("M-", (double d) -> { this._d -= d; return (this._d); });
		this._m.put("MS", (double d) -> { this._d = d; return (this._d); });
		this._m.put("MC", (double d) -> { this._d = 0.0; return (this._d); });
		this._m.put("MR", (double d) -> { return (this._d); });
	}
	
	
	/**
	 * Cette fonction determine si l'expression passee en parametre est strictement un nombre pour etre utilisee pour la memoire. Si elle n'est pas valide, elle cherchera le dernier nombre de l'expression.
	 * @param expr L'expression a analyser
	 * @return String
	 */
	
	private String getNbToUse(String expr) {
		char[] ca = expr.toCharArray();
		int x = ca.length - 1;
		boolean find = false;
		int pos = 0;
		while (x > 0 && find == false) {
			if (!((ca[x] >= '0' && ca[x] <= '9') || ca[x] == '.')) {
				find = true;
				pos = x + 1;
				if (x >= 1 && ca[x] == '-' && !((ca[x - 1] >= '0' && ca[x - 1] <= '9') || ca[x - 1] == ')'))
					pos = x;
			}
			x--;
		}
		expr = expr.substring(pos);
		return (expr);
	}
	
	
	/**
	 * Cette fonction verifie que l'expression est valide et appelle la fonction lambda associee au bouton clique par l'utilisateur afin d'effectuer l'action necessaire. Elle retourne une String a afficher.
	 * @param expr L'expression a analyser
	 * @param val L'objet ObserVal a analyser
	 * @return String
	 */
	
	public String handleMem(String expr, ObserVal val) {
		if (expr.isEmpty() || val.getLastExpr().isEmpty() || val.getLastExpr().compareToIgnoreCase("nan") == 0 ||
				ObserVal.isOp(val.getLastExpr().charAt(val.getLastExpr().length() - 1)))
			return (this._final);
		String toUse = this.getNbToUse(val.getLastExpr());
		this._final = this._df.format(this._m.get(expr).applyAsDouble(Double.valueOf(toUse)));
		this._final = this._final.replaceAll(",", ".");
		return (this._final);
	}
}
