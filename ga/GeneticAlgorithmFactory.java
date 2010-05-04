package ga;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 * An abstract factory class for obtaining information about what GA operators are available on the system.
 * A hash or list of class instances is returned for each operator type.
 * @author Matt Oates
 */
public abstract class GeneticAlgorithmFactory {
	
	/**
	 * Get a LinkedList of all the available Selection implementations.
	 */
	public static LinkedList<Selection> getSelectionList() {
		Selection select = null;
		LinkedList<Selection> list = new LinkedList<Selection>();
		File selectionpkg = new File("ga"+File.pathSeparator+"selection");
		//Get a list of all the GeneClasses available in the Genes package.
		String[] selects = selectionpkg.list(new SelectionFilter());
		
		for ( String selectname : selects ) {
			selectname = selectname.replaceAll("\\.java", "");		
			try {
				select = (Selection) Class.forName("ga.selection." + selectname, true, Thread.currentThread().getContextClassLoader()).newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list.add(select);
		}
		return list;
	}
	
	/**
	 * Get a hash of Selection implementations using simplified class names (without the Selection postfix) as a key.
	 * @return The hashtable of selection instances.
	 */
	public static Hashtable<String, Selection> getSelectionHash() {
		Selection select = null;
		Hashtable<String, Selection> hash = new Hashtable<String, Selection>();
		//System.out.println(System.getProperty("user.dir"));
		File selectionpkg = new File("ga"+File.separator+"selection");
		//Get a list of all the GeneClasses available in the Genes package.
		String[] selects = selectionpkg.list(new SelectionFilter());
		
		for ( String selectname : selects ) {
			selectname = selectname.replaceAll("\\.java", "");				
			try {
				select = (Selection) Class.forName("ga.selection." + selectname, true, Thread.currentThread().getContextClassLoader()).newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hash.put(selectname.replaceAll("Selection", ""), select);
		}
		return hash;
	}
	
	/**
	 * Get a LinkedList of all the available Crossover implementations.
	 */
	public static LinkedList<Crossover> getCrossoverList() {
		Crossover cross = null;
		LinkedList<Crossover> list = new LinkedList<Crossover>();
		File crossoverpkg = new File("ga"+File.separator+"crossover");
		//Get a list of all the GeneClasses available in the Genes package.
		String[] crosses = crossoverpkg.list(new CrossoverFilter());
		
		for ( String crossname : crosses ) {
			crossname = crossname.replaceAll("\\.java", "");
			
						
			try {
				cross = (Crossover) Class.forName("ga.crossover." + crossname, true, Thread.currentThread().getContextClassLoader()).newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list.add(cross);
		}
		return list;
	}
	
	/**
	 * Get a hash of Crossover implementations using simplified class names (without the Crossover postfix) as a key.
	 * @return The hashtable of crossover instances.
	 */
	public static Hashtable<String, Crossover> getCrossoverHash() {
		Crossover cross = null;
		Hashtable<String, Crossover> hash = new Hashtable<String, Crossover>();
		File crossoverpkg = new File("ga"+File.separator+"crossover");
		//Get a list of all the GeneClasses available in the Genes package.
		String[] crosses = crossoverpkg.list(new CrossoverFilter());
		//Populate the hash
		for ( String crossname : crosses ) {
			crossname = crossname.replaceAll("\\.java", "");						
			try {
				cross = (Crossover) Class.forName("ga.crossover." + crossname, true, Thread.currentThread().getContextClassLoader()).newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hash.put(crossname.replaceAll("Crossover", ""), cross);
		}
		return hash;
	}
	
	/**
	 * Get a LinkedList of all the available Symbiosis implementations.
	 */
	public static LinkedList<Symbiosis> getSymbiosisList() {
		Symbiosis symb = null;
		LinkedList<Symbiosis> list = new LinkedList<Symbiosis>();
		File symbiosispkg = new File("ga"+File.separator+"symbiosis");
		//Get a list of all the GeneClasses available in the Genes package.
		String[] symbs = symbiosispkg.list(new SymbiosisFilter());
		
		for ( String symbname : symbs ) {
			symbname = symbname.replaceAll("\\.java", "");
			
						
			try {
				symb = (Symbiosis) Class.forName("ga.crossover." + symbname, true, Thread.currentThread().getContextClassLoader()).newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list.add(symb);
		}
		return list;
	}
	
	/**
	 * Get a hash of Symbiosis implementations using simplified class names (without the Symbiosis postfix) as a key.
	 * @return The hashtable of symbiosis instances.
	 */
	public static Hashtable<String, Symbiosis> getSymbiosisHash() {
		Symbiosis symb = null;
		Hashtable<String, Symbiosis> hash = new Hashtable<String, Symbiosis>();
		File symbiosispkg = new File("ga"+File.separator+"symbiosis");
		//Get a list of all the GeneClasses available in the Genes package.
		String[] symbs = symbiosispkg.list(new SymbiosisFilter());
		//Populate the hash
		for ( String symbname : symbs ) {
			symbname = symbname.replaceAll("\\.java", "");						
			try {
				symb = (Symbiosis) Class.forName("ga.symbiosis." + symbname, true, Thread.currentThread().getContextClassLoader()).newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hash.put(symbname.replaceAll("Symbiosis", ""), symb);
		}
		return hash;
	}
	
	/**
	 * Get a named method of selection.
	 */
	public static Selection getSelection(String name) {
		Selection select = null;
		
		name += "Selection";
		
		try {
			select = (Selection) Class.forName("ga.selection." + name, true, Thread.currentThread().getContextClassLoader()).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return select;		
	}
	
	/**
	 * Get a named method of crossover.
	 */
	public static Crossover getCrossover(String name) {
		Crossover cross = null;
		
		name += "Crossover";
		
		try {
			cross = (Crossover) Class.forName("ga.crossover." + name, true, Thread.currentThread().getContextClassLoader()).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cross;		
	}
	
	/**
	 * Get a named method of symbiosis.
	 */
	public static Symbiosis getSymbiosis(String name) {
		Symbiosis symb = null;
		
		name += "Symbiosis";
		
		try {
			symb = (Symbiosis) Class.forName("ga.symbiosis." + name, true, Thread.currentThread().getContextClassLoader()).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return symb;		
	}
	
}

/**
 * Inner class for filtering Selection implementations based on a classname postfix.
 */
class SelectionFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        return (name.endsWith("Selection.java"));
    }
}

/**
 * Inner class for filtering Crossover implementations based on a classname postfix.
 */
class CrossoverFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        return (name.endsWith("Crossover.java"));
    }
}

/**
 * Inner class for filtering Symbiosis implementations based on a classname postfix.
 */
class SymbiosisFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        return (name.endsWith("Symbiosis.java"));
    }
}