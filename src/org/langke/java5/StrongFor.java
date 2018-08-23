package org.langke.java5;

import java.util.*;

public class StrongFor {
	public void showAll(Collection c) {

		for (Iterator iter = c.iterator(); iter.hasNext();) {

			System.out.println((String) iter.next());

		}

	}
	public void showAll1 (Collection<String> cs) {

	    for (String str : cs) {

	        System.out.println(str);

	    }

	}

	public void showAll2(Collection c) {

		for (Object obj : c) {

			System.out.println((String) obj);

		}

	}

	public void showAll(String[] sa) {

		for (String str : sa) {

			System.out.println(str);

		}

	}

}
