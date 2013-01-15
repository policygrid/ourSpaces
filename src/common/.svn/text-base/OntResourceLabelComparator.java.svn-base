package common;

import java.util.Comparator;

import com.hp.hpl.jena.ontology.OntResource;

/**
 * Used to sort resources alphabetically from there labels
 * @author thomas
 *
 */
public class OntResourceLabelComparator implements Comparator<OntResource> {

	@Override
	public int compare(OntResource arg0, OntResource arg1) {
		return arg0.getLabel(null).compareTo(arg1.getLabel(null));
	}

}
